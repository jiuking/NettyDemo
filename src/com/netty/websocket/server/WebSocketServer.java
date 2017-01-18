package com.netty.websocket.server;

import com.netty.websocket.handler.WebSocketServerHandler;
import com.netty.websocket.msg.DyMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Optional;

/**
 * Created by Bravowhale on 2017/1/18.
 */
public class WebSocketServer {
    public void run(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.TCP_NODELAY, true);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("http-codec",
                                    new HttpServerCodec());
                            pipeline.addLast("aggregator",
                                    new HttpObjectAggregator(65536));
                            pipeline.addLast("http-chunked",
                                    new ChunkedWriteHandler());
                            pipeline.addLast("handler",
                                    new WebSocketServerHandler());
                        }
                    });
            Channel ch = b.bind(port).sync().channel();
            System.out.println("web socket server started at port "+port+".");
            System.out.println("Open your browser and navigate to http://localhost:"+port+"/");
            ch.closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new DyMessage(),"DynMessage");
        t.start();
        new WebSocketServer().run(8080);
    }
}
