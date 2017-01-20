package com.netty.websocket.msg;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.AbstractEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/1/18.
 */
public class DyMessage implements Runnable{
    public static ChannelGroup audiences = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static String request;
    static public void addAudience(Channel ch,String request){
        audiences.add(ch);
        DyMessage.request = request;
    }

    static public void removeAudience(Channel ch){
        audiences.remove(ch);
    }

    public static String getMessage(){
        StringBuffer sb = new StringBuffer();
        sb.append(request);
        sb.append(",欢迎使用Netty WebSocket服务，现在时刻：");
        sb.append(new Date().toString());
        return sb.toString();
//		return message;
    }

    @Override
    public void run() {
        System.out.println("DynMessage start");
        for(;;){
            String msg = getMessage();
            radiate(msg);
            try{Thread.sleep(1000); }catch(Exception ex){}

        }
    }

    void radiate(String msg){
        audiences.write(new TextWebSocketFrame(msg));
    }
}
