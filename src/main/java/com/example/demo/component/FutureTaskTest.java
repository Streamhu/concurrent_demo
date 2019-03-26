package com.example.demo.component;

import java.util.concurrent.*;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/17 14:49
 */
public class FutureTaskTest {

    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    static class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            System.out.println("do something in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }

    public static void main(String[] args) throws Exception {
        // Future测试
        futureTest();
        // futureTask测试
        futureTaskTest();
    }

    public static void futureTest() throws Exception{
        // 线程池提交Callable任务，并得到Future
        Future<String> future = executorService.submit(new MyCallable());
        System.out.println("do something in main");
        Thread.sleep(1000);
        String result = future.get();
        System.out.println("result = " + result);
    }

    public static void futureTaskTest() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(()-> {
            System.out.println("do something in callable");
            Thread.sleep(5000);
            return "Done";
        });
        executorService.submit(futureTask);
        System.out.println("do something in main");
        // 调用isDone()判断任务是否结束
        if(!futureTask.isDone()){
            System.out.println("task is not done");
        }
        // get()是个阻塞方法
        String result = futureTask.get();
        System.out.println("result = " + result);
    }
}
