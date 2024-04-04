package io.windfree.thread.concurrency.atomic.ref;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest1 {

    public static void main(String[] args) {
        String oldName = "old name";
        String newName = "new name";
        AtomicReference<String> ref = new AtomicReference<>(oldName);
        if (ref.compareAndSet(oldName, newName)) {
            System.out.printf("new value is : " + ref.get());
        } else {
            System.out.printf("nothing happen");
        }
    }

}
