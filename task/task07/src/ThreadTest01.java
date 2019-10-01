import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.concurrent.CountDownLatch;

public class ThreadTest01 {
    public static void main(String [] args){
        calculate();
    }

    public static void calculate(){
        long startTime = System.currentTimeMillis();
        long primeSum10w = 0;
        long perfectSum10w = 0;
        //子线程个数
        int threadNumber = 10;
        CountDownLatch threadSignal = new CountDownLatch(threadNumber);

        MyRunnable [] r = new MyRunnable[10];
        Thread [] t = new Thread[10];
        r[0] = new MyRunnable(threadSignal);
        r[0].setArea(1, 10000);
        t[0] = new Thread(r[0]);
        t[0].start();
        for(int i = 1; i < 10; i++){
            r[i] = new MyRunnable(threadSignal);
            r[i].setArea(i * 10000, (i+1)*10000);
            t[i] = new Thread(r[i]);
            t[i].start();
        }try {
            //等待子线程全部结束而后运行主线程
            threadSignal.await();
            for(int i = 0;i < 10;i++){
                System.out.println(r[i].getPrimeSum());
                System.out.println(r[i].getPerfectSum());
                primeSum10w = primeSum10w + r[i].getPrimeSum();
                perfectSum10w = perfectSum10w + r[i].getPerfectSum();
            }
            System.out.print("质数之和:");
            System.out.println(primeSum10w);
            System.out.print("完全数之和:");
            System.out.println(perfectSum10w);
            System.out.print("10万以内素数之和与完全数之和的乘积为：");
            System.out.println(primeSum10w * perfectSum10w);
            System.out.println("mainend");
            long endTime = System.currentTimeMillis();
            System.out.print("运行时间为:");
            System.out.println(endTime - startTime);
        }catch (InterruptedException E){
            E.printStackTrace();
        }
    }
}
/**
 * 用多线程计算10万以内素数之和与完全数之和的乘积
 */
class MyRunnable implements Runnable{
    /**从自变量开始值*/
    private int from;
    /**自变量结束值*/
    private int to;
    /**质数之和*/
    private long primeSum = 0;
    /**完全数之和*/
    private long perfectSum = 0;

    /**子线程个数*/
    private CountDownLatch threadSignal;

    /**
     * MyRunnable构造函数
     * @param threadSignal:子线程个数
     */
    public MyRunnable(CountDownLatch threadSignal){
        this.threadSignal = threadSignal;
    }

    /**
     * 设置自变量区间.
     * @param from:自变量启始值.
     * @param to：自变量结束值.
     */
    public void setArea(int from, int to){
        this.from = from;
        this.to = to;
    }

    /**
     * 得到质数之和
     * @return primeSum:质数之和.
     */
    public long getPrimeSum(){
        return primeSum;
    }

    /**
     * 得到完全数之和.
     * @return perfectSum:完全数之和.
     */
    public long getPerfectSum(){
        return perfectSum;
    }



    @Override
    public void run(){
        for( ;from < to; from++ ){
            if( judgePrimeNumber(from) ){
                primeSum = primeSum + from;
            }
            if( judgePerfectNumber(from) ){
                perfectSum = perfectSum + from;
            }
        }
        threadSignal.countDown();

    }

    /**
     * 判断质数
     * @param number:整数
     * @return ture：则是质数,flase：则非质数
     */
    private boolean judgePrimeNumber(int number){
        if( number == 1 ){
            return false;
        }
        if( number == 2 ){
            return true;
        }
        for(int i = 2;i < number; i++){
            if( number % i == 0 ){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断完全数
     * @param number:整数
     * @return 则是完全数,flase：则非完全数
     */
    private boolean judgePerfectNumber(int number){
        if(number == 1){
            return false;
        }
        int sum = 1;
        for(int i = 2; i < number; i++){
            if( number % i == 0 ){
                sum = sum + i;
            }
        }
        if( sum == number ){
            return true;
        }else{
            return false;
        }
    }
}


