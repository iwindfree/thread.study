package io.windfree.thread.basic.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
여러 쓰레드를 병렬로 실행할 때 쓰레드 cordination 을 고려해야 합니다.
쓰레드가 예상치 못하게 길게 실행되는 경우를 대비해서 join 에 시간을 지정하는 것을 고려합니다.
daemon 처리를 하여 예상치 못하게 길게 수행되는 쓰레드를 종료하는 것을 고려해야 합니다.
 */
public class JoinThread {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(100000000L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true); // 2초 이상 실행되는 쓰레드가 있을 수 있으니, main 이 종료되면 종료될 수 있도록 daemon 처리.
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join(2000); // 2초이상 계산되는 것은 우선 skip 하고 결과 출력
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
