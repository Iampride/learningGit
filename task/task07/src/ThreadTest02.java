import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest02 {
    public static void main(String [] args){
        CountDownLatch threadsignal = new CountDownLatch(2);
        for(int i = 0; i < 2; i++){
            Thread t = new MyThread2("A"+i, threadsignal);
            t.start();
        }
        try{
            threadsignal.await();
        }catch (InterruptedException E){
            E.printStackTrace();
        }
        System.out.println("mainend");


    }
}


class MyThread2 extends Thread{
    private String s;
    private CountDownLatch threadsignal;

    MyThread2(String name, CountDownLatch threadsignal){
        //super(name);
        s = name;
        this.threadsignal = threadsignal;
    }


    @Override
    public void run(){
        for(int i = 0; i < 5000; i++){
            System.out.println(s);
        }
        threadsignal.countDown();
    }


}