package io.windfree.thread.concurrency.basic;

import java.util.Random;

/**
 * Java 에서 atomic 연산에 해당되는 것들
 * 1) all reference assignment
 *   ex) Object a = new Object(); Object b = new Object();
 *       a = b ;  // atomic operation
 *
 *  2) 객체를 갖고 오거나 할당하는 getter, setter
 *  3) all assignment to primitive types are atomic except long and double
 *     (long 과 double 은 64bit 여서 32비트씩 조개서 작업하는 로직이 있기 때문에 atomic 이 아님.)
 *     이러한 경우는 volatile 로 선언하면 됨.
 *     https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.7
 *
 *
 */
public class AtomicTest {
    public static void main(String[] args) {
        Metrics metrics = new Metrics();

        BusinessLogic businessLogicThread1 = new BusinessLogic(metrics);

        BusinessLogic businessLogicThread2 = new BusinessLogic(metrics);

        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        businessLogicThread1.start();
        businessLogicThread2.start();
        metricsPrinter.start();
    }

    public static class MetricsPrinter extends Thread {
        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }

                double currentAverage = metrics.getAverage();

                System.out.println("Current Average is " + currentAverage);
            }
        }
    }

    public static class BusinessLogic extends Thread {
        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(random.nextInt(2));
                } catch (InterruptedException e) {
                }

                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }
    }

    public static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        // average 를 volatile 로 선언해서 안전하게 실행될 수 있음.
        public double getAverage() {
            return average;
        }
    }
}
