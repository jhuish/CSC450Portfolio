import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Counter count = new Counter();
        IncrementToTwenty inc = new IncrementToTwenty(count);
        DecrementToZero dec = new DecrementToZero(count);
        inc.start();
        try{
            inc.join();
        } catch (Exception e){
            System.out.println(e);
        }

        dec.start();
        try {
            dec.join();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
class Counter{
    private int counter = 0;
    private Lock lock = new ReentrantLock();
    public void incrementCounter(){
        lock.lock();
        try {
            counter++;
        }
        finally {
            lock.unlock();
        }
    }
    public void decrementCounter(){
        lock.lock();
        try {
            counter--;
        }
        finally {
            lock.unlock();
        }
    }
    public int getCounter(){
        return counter;
    }

}
class IncrementToTwenty extends Thread{
    private Counter incCounter;
    public IncrementToTwenty(Counter c){
        this.incCounter = c;
    }
    public void run(){
        for (int i = 0; i < 20; i++) {
            System.out.println(incCounter.getCounter());
            incCounter.incrementCounter();
        }
        System.out.println(incCounter.getCounter());
    }
}
class DecrementToZero extends Thread{
    private Counter decCounter;
    public DecrementToZero(Counter c){
        this.decCounter = c;
    }
    public void run(){
        for (int i = decCounter.getCounter(); i > 0; i--) {
            System.out.println(decCounter.getCounter());
            decCounter.decrementCounter();
        }
        System.out.println(decCounter.getCounter());
    }

}
