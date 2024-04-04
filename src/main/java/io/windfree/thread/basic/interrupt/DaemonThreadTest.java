package io.windfree.thread.basic.interrupt;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class DaemonThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new LongTask());
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);

       // t.interrupt();
    }

    private static class LongTask implements Runnable {
        String filePath = System.getProperty("user.dir") + "/1.txt";
        @Override
        public void run() {
            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                try {
                    targetFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(filePath, true));
                System.out.printf(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (true) {
                try {
                    writer.write("add add add...");
                    writer.newLine();
                    writer.flush();
                    Thread.sleep(1000);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void appendFile() {

        }
    }
}
