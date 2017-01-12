package com.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bravowhale on 2017/1/12.
 */
public class DateUtil {
    public DateUtil() {
    }
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws InterruptedException{
        ExecutorService executor = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 500; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        try {
                            DATE_FORMAT.parse("2017-01-12");
                        }catch (ParseException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
