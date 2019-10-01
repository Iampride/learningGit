package Point;

public class Point3D extends Point2D{
    int z;

    Point3D(int x, int y, int z){
        super(x, y);
        this.z = z;
    }

    Point3D(Point2D p, int z){
        this.x = p.x;
        this.y = p.y;
        this.z = z;
    }

    void offset(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void main(String [] args){
        Point2D p2d1 = new Point2D(1, 1);
        Point2D p2d2 = new Point2D(4, 5);
        double  distance = Math.pow((Math.pow((p2d1.x - p2d2.x), 2) + Math.pow((p2d1.y - p2d2.y), 2)), 0.5);
        System.out.printf("From p2d1 to p2d2, the distance is %f", distance);

        Point3D p3d1 = new Point3D(p2d1, 1);
        Point3D p3d2 = new Point3D(p2d2, 2);

        double  distance2 = Math.pow((Math.pow((p2d1.x - p2d2.x), 2) +
                Math.pow((p2d1.y - p2d2.y), 2) + Math.pow((p3d1.z - p3d2.z), 2)), 0.5);
        System.out.printf("\nFrom p3d1 to p3d2, the distance is %f", distance2);
    }
}
