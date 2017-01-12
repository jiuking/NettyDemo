package com.netty.aio.client;

import com.netty.aio.handler.AsyncTimeClientHandler;
import com.netty.aio.handler.AsyncTimeServerHandler;

/**
 * Created by Bravowhale on 2017/1/12.
 */
public class TimeClient {
    public static void main(String[] args) {

        new Thread(new AsyncTimeClientHandler("127.0.0.1",8080),"AIO-Client").start();
    }
}
