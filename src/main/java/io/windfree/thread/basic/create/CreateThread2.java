package io.windfree.thread.basic.create;

public class CreateThread2 {
    public static void main(String[] args) {
        Thread thread = new NewThread();
        thread.start();
    }

    private static class NewThread extends  Thread {
        @Override
        public void run() {
            System.out.printf("Hello, new thread run.." + this .getName());
        }

    }
}
