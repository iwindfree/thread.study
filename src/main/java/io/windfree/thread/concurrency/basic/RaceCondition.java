package io.windfree.thread.concurrency.basic;

/**
 * Race Condition 이란?
 *  - 공유 리소스에 접근하는 여러 쓰레드가 존재하는 경우 최소 하나의 쓰레드가 리소스를 변경하려고 시도하는 경우
 *    쓰레드 스케줄링의 순서나 시점에 따라 결과가 달라지는 상황
 *  - 문제의 핵심은 공유 리소스에 대하여 비원자적 연산이 실행되는 것.
 *
 * Data Race
 *   - 컴파일러와 CPU 가 최적화된 실행을 위해서 논리적으로 어긋나지 않는 범위내에서 코드 실행 순서를 변경할 수 있음
 *
 */
public class RaceCondition {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }

        });

        thread1.start();
        thread2.start();
    }

    public static class SharedClass {
        private int x = 0;
        private int y = 0;

        public void increment() {
            x++;
            y++;
        }

        // x >= y 인 상황은 발생할 수 있지만 y > x 인 상황은 발생할 수 없음
        // 그러나, 최적화 과정에서 y 가 x 보다 증감연산을 먼저 실행할 수 있기 때문에 y > x 인 상황이 발생함
        // 이러한 것을 방지하려면 volatile 로 x, y 를 선언해줌.
        public void checkForDataRace() {
            if (y > x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}
