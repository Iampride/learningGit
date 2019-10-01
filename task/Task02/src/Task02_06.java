/*已知圆球体积为4/3πr3，试编写一个程序，输入圆球半径，经过计算输出圆球的体积。
*/


public class Task02_06 {
    public static void main(String [] args){
        System.out.println(calSphereVolume(2));
    }

    public static double calSphereVolume(double radius){
        double v = (4.0/3) * Math.PI * Math.pow(radius, 3);
        return v;
    }
}
