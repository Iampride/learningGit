/*
* 试编写一个将摄氏温度转换为华氏温度的程序，其转换公式如下：华氏温度=（9/5）*摄氏温度+32
*/


public class Task02_05 {
    public static void main(String [] args){
        System.out.println(tempConvert(100));
    }
    public static double tempConvert(double c){
        double h = (9.0/5) * c +32;
        return h;
    }
}
