package io.windfree.thread.concurrency.reentrantlock;

/**
 * Reentrant lock
 *  - Reentrant lock 은 객체에 적용된 synchronized 처럼 동작한다.
 *  - 그렇지만 동기화 블로과 달리 명시적으로 locking 과 unlocking 이 필요하다.
 *  - 코딩상의 실수나,예외 발생시 unlocking 을 하지 못할 수 있으므로, try - finally 블럭에서 unlocking 하는 것을
 *     습관하 하는 것이 좋다.
 *  - 테스트를 할 때 유용한 method 들
 *    . getQueuedThreads(), getOwner(), isHeldByCurrentThread(), isLocked()
 *
 *  - lockInterruptibly() 메서드를 사용하여 다른 쓰레드에서 발생시킨 interrupt 시그널에 응답할 수 있음.
 *     > deadlock 을 detection 하고 recovery 하거나, lock 을 대기하면서 멈춰 있는 쓰레드를 깨워서 중지하는데 유용함.
 *  - 가장 핵심 기능은 tyrLock()
 *     > lock 을 얻을 수 있으면 return 을 반환하고 lock 을 획득
 *     > lock 을 얻을 수 없으면 false 를 반환하고 다음 명령으로 넘어감.
 *     > if (lockObj.tryLock()) {
 *         try{
 *             ...
 *         } finally {
 *             lockObj.unlock();
 *         }
 *       }
 *     > lock 때문에 작업이 지연되는 것을 허용하지 않은 실시간 앱에 적합함
 *       >> video/image processing 어플리케이션
 *       >> 빠른 처리를 보장해야 하는 트레이딩 어플리케이션
 *       >> UI 앱
 */
public class reentrantlock1 {
}
