package com.example.demo.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/17 9:32
 */
public class CountDownLatchTest {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {
        // 定义线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        // 定义CountDown
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount ; i++){
            final int threadNum = i;
            // 这里多线程调用完了，不会等待后面会继续执行
            executor.execute(() -> {
                try{
                    test(threadNum);
                }catch (Exception e){
                    System.out.println("exception =" + e);
                }finally {
                    // 完成一次，释放一个资源
                    countDownLatch.countDown();
                }
            });
            // Thread.sleep(100);
            System.out.println("我是并发之外，他们多线程调用的就去执行他们的吧，我在主线程内，嘿嘿嘿，后面的awati()方法会阻塞，直到所有资源都释放完");
        }
        countDownLatch.await();
        // 等到指定时间后，不等待其他线程走完继续走下面的流程
        // countDownLatch.await(100, TimeUnit.MILLISECONDS);
        // 所有任务执行完后才会执行
        System.out.println("finish");
        // 线程池不再使用时需要关闭
        executor.shutdown();

    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        System.out.println("threadNum" + threadNum);
        Thread.sleep(100);
    }

}
