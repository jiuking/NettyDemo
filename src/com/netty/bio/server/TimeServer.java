package com.netty.bio.server;

import com.netty.bio.handler.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Bravowhale on 2017/1/10.
 */
public class TimeServer {
    public static void main(String[] args)throws IOException{
        int port = 8080;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port:"+port);
            Socket socket =null;
            while (true){
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        }finally {
            if(serverSocket != null){
                System.out.print("The time server close");
                serverSocket.close();
                serverSocket = null;
            }
        }
    }
}
