package com.netty.aio.server;

import com.netty.aio.handler.AsyncTimeServerHandler;

/**
 * Created by Bravowhale on 2017/1/12.
 */
public class TimeServer {
    public static void main(String[] args) {
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(8080);
        new Thread(timeServer,"AsyncTimeServer-AIO").start();
    }
}
