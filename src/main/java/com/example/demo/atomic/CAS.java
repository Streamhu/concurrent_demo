package com.example.demo.atomic;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/15 16:02
 */
public class CAS {


    public static void main(String[] args) {

        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 10; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 101));
                    System.out.println(b);
                }
            }).start();

        }

    }

}

class CompareAndSwap {
    //内存值
    private int value;

    //获取内存值
    public synchronized int get() {
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;

        //判断内存值是否等于预期值，如果相等说明内存值没有被其他线程修改，
        //则将新值赋给内存值。如果不相等，说明内存值已经被其他线程修改过了.
        if (oldValue == expectedValue) {
            this.value = newValue;
        }

        //不管是否成功都会将内存值返回
        return oldValue;
    }

    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }

}
