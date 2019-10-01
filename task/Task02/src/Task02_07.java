/*用do…while 循环，计算1！+2！+…+100！的总和
 */


import java.math.BigInteger;

public class Task02_07 {
    public static void main(String []args) {
        BigInteger vec[] = new BigInteger[102];
        BigInteger sum = new BigInteger("0");

        BigInteger n = new BigInteger("1");
        do{
            vec[0] = BigInteger.ONE;
            vec[1] = BigInteger.ONE;
            if( vec[n.intValue()] == null )
                break;
            sum = sum.add(vec[n.intValue()]);
            vec[n.intValue()+1] = vec[n.intValue()].multiply((n.add(BigInteger.ONE)));
            n = n.add(BigInteger.ONE);
        }while(n.intValue() <= 100);
        System.out.println(sum);
    }


}
