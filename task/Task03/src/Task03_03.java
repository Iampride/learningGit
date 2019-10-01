/*(1)定义一个抽象方法CanCry，描述会吼叫的方法public void cry() 。
 *(2)分别定义狗类（Dog）和猫类（Cat），实现CanCry方法。实现方法的功能分别为：
 *打印输出“我是狗，我的叫声是汪汪汪” 、“我是猫，我的叫声是喵喵喵” 。
 *(3)定义一个主类，定义一个void makeCry(CanCry c)方法，其中让会吼叫的事物吼叫。
 *在主类main 方法中创建狗类对象（dog）、猫类对象（cat），调用makecry方法，让狗和猫吼叫。
 * */


public class Task03_03 {
    public static void main(String [] args){
        Task03_03 t = new Task03_03();
        CanCry c1 = new Dog();
        CanCry c2 = new Cat();
        t.makeCru(c1);
        t.makeCru(c2);
    }

    void makeCru(CanCry c){
        c.cry();
    }
}
