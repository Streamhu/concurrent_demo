package com.example.demo.component;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/17 14:49
 */
public class FutureTaskTest {

    public static void main(String[] args) throws Exception {

        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("do something in callable");
                Thread.sleep(5000);
                return "Done";
            }
        });

        new Thread(futureTask).start();
        System.out.println("do something in main");
        // 1. 调用isDone()判断任务是否结束
        if(!futureTask.isDone()) {
            System.out.println("Task is not done");
            try {
                //阻塞主线程一秒钟
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = futureTask.get();
        System.out.println("result = " + result);
    }

}
