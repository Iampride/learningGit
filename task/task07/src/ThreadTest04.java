

import java.util.concurrent.CountDownLatch;

public class ThreadTest04 {
    public static void main(String [] args){
        long startTime = System.currentTimeMillis();
        CountDownLatch threadSignal = new CountDownLatch(10);
        try {
            long primeSum10W = 0;
            long perfectSum10W = 0;
            MyRunnable2 [] r = new MyRunnable2[10];
            Thread [] t = new Thread[10];
            r[0] = new MyRunnable2();
            r[0].setArea(1, 10000);
            t[0] = new Thread(r[0]);
            t[0].start();
            t[0].join();
            for(int i = 1; i < 10; i++) {
                r[i] = new MyRunnable2();
                r[i].setArea(i * 10000, (i + 1) * 10000);
                t[i] = new Thread(r[i]);
                t[i].start();
                t[i].join();
            }
            for(int i = 0;i < 10;i++){
                System.out.println(r[i].getPrimeSum());
                System.out.println(r[i].getPerfectSum());
                primeSum10W = primeSum10W + r[i].getPrimeSum();
                perfectSum10W = perfectSum10W + r[i].getPerfectSum();
            }
            System.out.println(primeSum10W);
            System.out.println(perfectSum10W);
            System.out.print("10万以内素数之和与完全数之和的乘积为：");
            System.out.println(primeSum10W * perfectSum10W);
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
class MyRunnable2 implements Runnable{
    /**从自变量开始值*/
    private int from;
    /**自变量结束值*/
    private int to;
    /**质数之和*/
    private long primeSum = 0;
    /**完全数之和*/
    private long perfectSum = 0;



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

    }

    /**
     * 判断质数
     * @param number:整数
     * @return ture：则是质数,flase：则非质数
     */
    public boolean judgePrimeNumber(int number){
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
    public boolean judgePerfectNumber(int number){
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


