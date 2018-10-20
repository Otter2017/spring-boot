package org.springframework.boot.peach.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierInstance implements Runnable {
    private Logger logger= LoggerFactory.getLogger(CyclicBarrierInstance.class);
    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierInstance(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier =cyclicBarrier;
    }

    @Override
    public void run() {
        try{
            Thread.sleep(new Random().nextInt(500));
            // * 当等候的线程数为0时重置
//            if(cyclicBarrier.getNumberWaiting() == 0 ){
//                cyclicBarrier.reset();
//                logger.info("cyclicBarrier reset.");
//            }
            logger.info("{} waitting.",cyclicBarrier.getNumberWaiting());
            // * 也可使用await(long timeout, TimeUnit unit) 等候固定时长后继续执行
            cyclicBarrier.await();
            // *到达设置的阻塞数目时，同时执行下面的代码
            logger.info("{} after wait.",Thread.currentThread().getName());
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
