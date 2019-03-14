package com.example.demo.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/17 10:09
 */
public class SemaphoreTest {

    //总共有20个线程数
    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        // 定义信号量，并且制定每次可用的许可数量(其实就好像定义了一个资源的最大值，你任何时候都超过不了这个最大值，也就是一个限制)
        final Semaphore semaphore = new Semaphore(4);
        for(int i = 0 ; i < threadCount; i++){
            final int threadNum = i;
            executor.execute(() -> {
                try{
                    semaphore.acquire();
                    test(threadNum);
                    // 这个是关键，不释放，就不能在拿到具体数量的资源了，就不能循环利用了
                    semaphore.release();
/*                    if(semaphore.tryAcquire()){// 尝试获取一个许可，没有获取则直接略过，等同于没有做操作，所有只有4个线程调用了test方法，因为线程会睡眠2s，所有只有前面获取到资源的走了
                        test(threadNum);
                        semaphore.release();
                    }*/
                }catch (Exception e){
                    System.out.println("异常");
                }
            });
            // Thread.sleep(2000);
        }

        // 关闭线程池
        executor.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        System.out.println("threadNum=" + threadNum);
        Thread.sleep(2000);
    }

}
