package com.netty.websocket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.time.LocalTime;
import java.util.Date;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;

/**
 * Created by Bravowhale on 2017/1/18.
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    public WebSocketServerHandler() {
    }

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest)
            handleHttpRequest(ctx,(FullHttpRequest)msg);
        else if (msg instanceof WebSocketFrame)
            handleWebSocketFrame(ctx,(WebSocketFrame)msg);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if(!req.decoderResult().isSuccess()
                ||(!"websocket".equals(req.headers().get("Upgrade")))){
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory =new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket",null,false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else {
            handshaker.handshake(ctx.channel(),req);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.status().code() != 200){
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res,res.content().readableBytes());
        }
//        ChannelFuture f = ctx.channel().writeAndFlush(res);
//        if (!isKeepAlive(req) || res.status().code() != 200){
//            f.addListener(ChannelFutureListener.CLOSE);
//        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if(!(frame instanceof  TextWebSocketFrame)){
            throw new UnsupportedOperationException(String .format("%s frame types not supported",frame.getClass().getName() ));
        }
        String  request = ((TextWebSocketFrame) frame).text();
//        ctx.channel().write(new TextWebSocketFrame(request
//                + ",111欢迎使用Netty WebSocket服务，现在时刻1111："
//                + new Date().toString()));
//        DyMessage.addAudience(ctx.channel(),request);
        Channel channel = ctx.channel();
        channel.write(new TextWebSocketFrame(request
                + ",欢迎使用Netty WebSocket服务，现在时刻："
                + new Date().toString()));
        System.out.println(channel);
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(LocalTime.now());

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//                System.out.println(channel);
            channel.write(new TextWebSocketFrame(request
                    + ",111欢迎使用Netty WebSocket服务，现在时刻1111："
                    + new Date().toString()));
            System.out.println(LocalTime.now());

//            }
//        });
//        t.start();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
