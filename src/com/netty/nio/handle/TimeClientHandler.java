package com.netty.nio.handle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Bravowhale on 2017/1/11.
 */
public class TimeClientHandler implements Runnable{
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;
    private String host;
    private int port;
    public TimeClientHandler(){}
    public TimeClientHandler(String host,int port){
        this.host = host;
        this.port = port;
        try{
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try{
            doConnect();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                    key = it.next();
                    it.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e){
                        key.cancel();
                        if(key.channel() != null){
                            key.channel().close();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        if(selector != null){
            try{
                selector.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            SocketChannel sc = (SocketChannel) key.channel();
            if (sc.finishConnect()){
                sc.register(selector,SelectionKey.OP_READ);
                doWrite(sc);
            }else
                System.exit(1);
            if (key.isReadable()){
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("Now is :"+body);
                    this.stop = true;
                }else if (readBytes < 0){
                    key.cancel();
                    sc.close();
                }else{

                }

            }
        }

    }
    private void doConnect() throws IOException{
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            socketChannel.register(selector,SelectionKey.OP_READ);
            doWrite(socketChannel);

        }else{
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel socketChannel)throws IOException{
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        if(!byteBuffer.hasRemaining()){
            System.out.println("Send order 2 server successed");
        }
    }
}
