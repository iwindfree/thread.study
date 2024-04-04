package io.windfree.thread.concurrency.atomic.ref;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class LockFreeStack<T> {
    private AtomicReference<StackNode> head = new AtomicReference<>();
    private AtomicInteger counter = new AtomicInteger(0);
    public void push(T value){
        StackNode<T> newHeaderNode = new StackNode<>(value);
        while (true) {
            StackNode<T> currentHeaderNode = head.get();
            newHeaderNode.next = currentHeaderNode;
            if (head.compareAndSet(currentHeaderNode, newHeaderNode)) {
                break;
            } else {
                LockSupport.parkNanos(1);
            }
        }
        counter.incrementAndGet();
    }

    public T pop() {
        StackNode<T> currentHeaderNode = head.get();
        StackNode<T> newHeaderNode;
        while (currentHeaderNode != null) {
            newHeaderNode = currentHeaderNode.next;
            if (head.compareAndSet(currentHeaderNode, newHeaderNode)) {
                break;
            } else {
                LockSupport.parkNanos(1);
                currentHeaderNode = head.get();
            }
        }
        counter.incrementAndGet();
        return currentHeaderNode != null ? currentHeaderNode.value : null;
    }

    public int getConter() {
        return counter.get();
    }


    private static class StackNode<T> {
        public T value;
        public StackNode<T> next;

        public StackNode (T value) {
            this.value = value;
            this.next = next;
        }
    }
}
