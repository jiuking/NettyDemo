package com.netty.aio.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

/**
 * Created by Bravowhale on 2017/1/12.
 */
public class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer>{
    private AsynchronousSocketChannel result;
    public ReadCompletionHandler() {
    }

    public ReadCompletionHandler(AsynchronousSocketChannel result) {
        if(this.result == null)
            this.result = result;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body,"UTF-8");
            System.out.println("the time server receive order "+req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req)?new Date().toString():"BAD ORDER";
            doWrite(currentTime);
            
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void doWrite(String currentTime) {
        if(currentTime != null && currentTime.trim().length() > 0){
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            result.write(writeBuffer, writeBuffer,
                    new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result1, ByteBuffer attachment) {
                            if (attachment.hasRemaining())
                                result.write(attachment,attachment,this);
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try{
                                result.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.result.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
