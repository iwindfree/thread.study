package io.windfree.thread.concurrency.basic;

/**
 * ThreadA 가 Test1 class 의 say1 method 를 수행하면 ThreadB 는 say1 , say2 를 모두 실행하지 못함.
 * synchronize 가 객체레벨에서 적용되기 때문.
 * public synchronize void say1()
 * ==>
 * public void say1()
 * {
 *     synchronize(this) {
 *
 *     }
 * }
 * 와 동일함.
 */
public class CriticalSectionTest1 {
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
        public synchronized void say1() throws InterruptedException {
            Thread.sleep(2000);
            System.out.println("Hello, say1");

        }

        public synchronized void say2() {
            System.out.println("Hello, say2");
        }
    }


}
