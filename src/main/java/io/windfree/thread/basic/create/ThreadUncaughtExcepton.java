package io.windfree.thread.basic.create;

public class ThreadUncaughtExcepton {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Raise Exception");
            }
        });

        thread.setName("thread1");
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.printf("Error catch : " + t.getName() + " -> " + e.getMessage());
            }
        });
        thread.start();
    }
}
