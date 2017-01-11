package com.netty.pro.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Bravowhale on 2017/1/11.
 */
public class TimeServerHandler implements Runnable{
    private Socket socket;
    public TimeServerHandler(){

    }
    public TimeServerHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
            String body = null;
            String currentTime = null;
            while(true){
                body = in.readLine();
                if (body == null)
                    break;
                System.out.println("The time server receive order :"+body);
                currentTime = body.equalsIgnoreCase("QUERY TIME ORDER") ? new Date().toString():"BAD ORDER";
                out.println(currentTime);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
