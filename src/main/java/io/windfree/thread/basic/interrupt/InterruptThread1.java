package io.windfree.thread.basic.interrupt;
/*
 Interrupt 를 사용하는 경우
  1. thread 가 사용하는 method 가 interrupt exception 을 throw 했을 때
  2. thread 내부에서 interuupt signal 을 handling 할때.
 */
public class InterruptThread1 {
    public static void main(String[] args) {
        Thread t  = new Thread(new BlockingThread());
        t.start();
        t.interrupt();

    }

    private static class BlockingThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread...");
            }
        }
    }
}
