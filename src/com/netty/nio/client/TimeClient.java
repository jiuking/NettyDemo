package com.netty.nio.client;

import com.netty.nio.handle.TimeClientHandler;

/**
 * Created by Bravowhale on 2017/1/11.
 */
public class TimeClient {
    public static void main(String[] args){
        new Thread(new TimeClientHandler("127.0.0.1",8080),"TimeClient").start();
    }
}
