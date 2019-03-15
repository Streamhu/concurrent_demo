package com.example.demo.aqs;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/3/15 17:31
 */
public class ReentrantLockTest {

    // 请求总数
    private static int clientTotal = 5000;

    // 同时并发执行的线程数
    private static int threadTotal = 10;

    // 初始化count
    private static int count = 0;

    // 声明锁的实例，调用构造方法，默认生成一个不公平的锁
    private final static Lock lock = new ReentrantLock();

    // 设置信号量，也就是同时并发的上限数量
    private final static Semaphore semaphore = new Semaphore(threadTotal);

    // 设置资源数
    private final static CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0; i <clientTotal; i++){
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    // Thread.sleep(1000); // 加睡眠可以看到semaphore信号量的作用
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        // 一直阻塞，直到资源用尽
        countDownLatch.await();
        System.out.println("complete");
        System.out.println("count = " + count);
        executorService.shutdown();
    }

    private static void add(){
        // 加锁
        lock.lock();
        try{
            count++;
            System.out.println("调用的第几次" + count);
        }finally {
            // 最后释放锁
            lock.unlock();
        }
    }

}
