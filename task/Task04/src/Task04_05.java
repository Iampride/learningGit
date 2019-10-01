import java.io.IOException;

public class Task04_05 {
    public static void main(String [] args) throws IOException {
        //int size = System.in.available();
        //if(size > 0){
            System.out.println("请输入字符:");
            byte [] data = new byte[100];
            System.in.read(data);
            System.out.println(data);

    }
}
