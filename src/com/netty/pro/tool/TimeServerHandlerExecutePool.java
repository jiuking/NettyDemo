package com.netty.pro.tool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bravowhale on 2017/1/11.
 */
public class TimeServerHandlerExecutePool {
    private ExecutorService executor;

    public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize){
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                maxPoolSize,
                210L,
                TimeUnit.DAYS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task){
        executor.execute(task);
    }
}
