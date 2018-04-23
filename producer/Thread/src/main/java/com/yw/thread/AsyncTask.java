package com.yw.thread;

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

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<String>(new Task()) {
            // 执行完成的回调
            @Override
            protected void done() {
                try {
                    // 不在done里面回调可能是阻塞的
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

        // 不在done里面回调可能是阻塞的 -- 知道获得运行结果
        try {
            futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "Hello World.";
        }
    }

}
