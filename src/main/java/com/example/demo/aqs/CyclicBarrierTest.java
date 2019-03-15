package com.example.demo.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/17 10:26
 */
public class CyclicBarrierTest {

    // 定义屏障，指定数量为5个
   private final static CyclicBarrier barrier = new CyclicBarrier(5);

   // 定义线程数量（余1个线程，会一直阻塞，这个时候就可以加时间了）
   private final static int threadCount = 21;

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
          final int threadNum = i;
            Thread.sleep(1000);
            executor.execute( () -> {
                try{
                    test(threadNum);
                }catch(Exception e){
                    System.out.println("异常");
                }
            });

        }
        executor.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(1000);
        System.out.println(threadNum + " is ready");
        // 如果当前线程就绪，则告诉CyclicBarrier 需要等待（这是一个阻塞方法，等屏障一个一个减掉以后，又重新变成5个）
        barrier.await();
        // 当达到指定数量时，继续执行下面代码
        System.out.println(threadNum + " continue");
    }

}
