package com.example.demo.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/3/20 15:25
 */
public class ConditionTest {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        // 从reentrantLock取得Condition对象，此时在AQS中生成Condition队列（可以有多个）
        Condition condition = reentrantLock.newCondition();

        // 线程1
        new Thread(() -> {
            try{
                // 加入AQS的等待队列里
                reentrantLock.lock();
                // 输出等待信号操作
                System.out.println("wait signal");
                // 线程1沉睡，对应的操作即是锁的释放，然后加入Condition队列中
                condition.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("get signal");
            reentrantLock.unlock();
        }).start();

        // 线程2
        new Thread(() -> {
            // 因为线程1释放锁，这时得到锁
            reentrantLock.lock();
            System.out.println("get lock");
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            // 发送信号，这时Condition队列中有线程1的结点，被取出加入AQS等待队列（注意，线程1没有被唤醒）
            System.out.println("send signal");
            condition.signal();
            // 释放锁会唤醒AQS队列
            reentrantLock.unlock();
        }).start();
    }
}
