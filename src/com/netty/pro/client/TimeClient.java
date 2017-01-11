package com.netty.pro.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Bravowhale on 2017/1/11.
 */
public class TimeClient {
    public static void main(String[] args)throws Exception{
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println("QUERY TIME ORDER");
            System.out.println("send order successed");
            System.out.println(in.readLine());
        } finally{
            if(socket != null){
                socket.close();
                socket = null;
            }
        }
    }
}
