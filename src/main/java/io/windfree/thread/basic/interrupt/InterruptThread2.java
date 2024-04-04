package io.windfree.thread.basic.interrupt;

import java.math.BigInteger;

public class InterruptThread2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new LongTask(new BigInteger("200"), new BigInteger("1000000")));
        t.start();
        Thread.sleep(3000);
        t.interrupt();
    }

    private static class LongTask implements Runnable {
        private BigInteger base;
        private BigInteger pow;

        public  LongTask(BigInteger base, BigInteger pow) {
            this.base = base;
            this.pow = pow;
        }
        @Override
        public void run() {
            System.out.println(base + "^" + pow + " = " + pow(base,pow));
        }

        private BigInteger pow(BigInteger base, BigInteger pow ) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(pow) != 0; i = i.add(BigInteger.ONE)) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("OK, I interrupted");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }

    }

}
