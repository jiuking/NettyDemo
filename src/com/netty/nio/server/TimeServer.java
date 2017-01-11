package com.netty.nio.server;

/**
 * Created by Bravowhale on 2017/1/11.
 */
public class TimeServer {
    public static void main(String[] args){
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(8080);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
    }
}
