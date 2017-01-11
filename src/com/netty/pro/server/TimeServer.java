package com.netty.pro.server;

import com.netty.pro.handler.TimeServerHandler;
import com.netty.pro.tool.TimeServerHandlerExecutePool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Bravowhale on 2017/1/11.
 */
public class TimeServer {
    public static void main(String[] args) throws IOException{
        int port = 8080;

        Socket socket = null;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            while (true){

                TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50,1000);
                while (true){
                    socket = serverSocket.accept();
                    singleExecutor.execute(new TimeServerHandler(socket));
                }
            }
        }finally {
                if (serverSocket != null){
                    serverSocket.close();
                    serverSocket = null;
                }

        }
    }
}
