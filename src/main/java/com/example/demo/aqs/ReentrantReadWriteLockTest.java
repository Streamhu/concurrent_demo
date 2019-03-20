package com.example.demo.aqs;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/3/20 15:11
 */
public class ReentrantReadWriteLockTest {

    // 定义map
    private final Map<String, Data> map = new TreeMap<>();

    // 声明读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // 获得读写锁中的读锁（这个应该是用的aqs的共享模式，共享锁）
    private final Lock readLock = lock.readLock();

    // 获得读写锁中的写锁（这个应该是用的aqs的独占模式，排它锁）
    private final Lock writeLock = lock.writeLock();

    // 获取
    public Data get(String key){
        readLock.lock();
        try{
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }

    // 获取
    public Set<String> getAllKeys(){
        readLock.lock();
        try{
            return map.keySet();
        }finally {
            readLock.unlock();
        }
    }

    // 写
    public Data put(String key, Data value){
        // 可能导致饥饿状态，处于一直等待状态
        writeLock.lock();
        try{
            return map.put(key, value);
        }finally {
            writeLock.unlock();
        }
    }






    class Data{

    }
}


