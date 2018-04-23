package com.yw.producer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 作者：create by YW
 * 日期：2018.01.19 14:01
 * 描述：
 */

public class AsyncTask {

    public static void m() {
        FutureTask<String> futureTask = new FutureTask<String>(new Task()) {
            // 执行完成的回调
            @Override
            protected void done() {
                try {
                    String s = get();
                    System.out.println("result: " + s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);
    }

    static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "Hello World.";
        }
    }

}
