package com.netty.nettyio.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Bravowhale on 2017/1/16.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws UnsupportedEncodingException {

//        String  body = (String) msg;
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8")
                .substring(0,req.length-System.getProperty("line.separator").length());

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)
                ?new Date().toString():"BAD ORDER";
//        currentTime = currentTime + System.getProperty("line.separator");
        System.out.println("The time server receive order "+body
        +";the counter is:"+ ++counter);
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx){
        ctx.close();
    }
}
