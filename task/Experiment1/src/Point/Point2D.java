package Point;


/*
* 分别编写两个类Point2D，Point3D来表示二维空间和三维空间的点，使之满足下列要求：
* 1) Point2D有两个整型成员变量x, y (分别为二维空间的X,Y方向坐标)，Point2D的构造方法要实现对其成员变量x, y的初始化。
* 2) Point2D有一个void型成员方法offset(int a, int b)，它可以实现Point2D的平移。
* 3) Point3D是Point2D的直接子类，它有有三个整型成员变量x,y,z (分别为三维空间的X,Y,Z方向坐标)，
* Point3D有两个构造方法：Point3D(int x, int y, int z)和Point3D(Point2D p, int z)，
* 两者均可实现对Point3D的成员变量x, y, z的初始化。
* 4) Point3D有一个void型成员方法offset(int a, int b, int c)，该方法可以实现Point3D的平移。
* 5) 在Point3D中的主函数main()中实例化两个Point2D的对象p2d1，p2d2，打印出它们之间的距离，
* 再实例化两个Point2D的对象p3d1，p3d2，打印出他们之间的距离。*/

public class Point2D{
    int x, y;

    Point2D(){}

    Point2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    void offset(int a, int b){
        this.x = a;
        this.y = b;
    }

}
