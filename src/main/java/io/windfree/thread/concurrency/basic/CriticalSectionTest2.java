package io.windfree.thread.concurrency.basic;

/**
 * method 안에서 synchronized 블럭을 이용하면 ThreadA 가 say1 을 실행하는동안
 * ThreadB 는 say2 를 실행할 수 있음.
 */
public class CriticalSectionTest2 {
    public static void main(String[] args) {
        Test1 test1 = new Test1();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test1.say1();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test1.say2();
            }
        });
        thread2.start();

    }

    public static class Test1 {
        Object lock1 = new Object();
        Object lock2 = new Object();

        public  void say1() throws InterruptedException {
            synchronized (lock1) {
                Thread.sleep(2000);
                System.out.println("Hello, say1");
            }

        }

        public  void say2() {
            synchronized (lock2) {
                System.out.println("Hello, say2");
            }
        }
    }


}
