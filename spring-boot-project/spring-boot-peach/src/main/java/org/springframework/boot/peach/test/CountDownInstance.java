package org.springframework.boot.peach.test;

import java.util.concurrent.CountDownLatch;

public class CountDownInstance implements  Runnable {
    private CountDownLatch countDownLatch;

    public CountDownInstance(CountDownLatch countDownLatch){
        this.countDownLatch =countDownLatch;
    }
    @Override
    public void run() {
        try{
            Thread.sleep(200);
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName()+" run.");
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
