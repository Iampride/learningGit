import java.io.*;

public class Task04_04 {
    public static void main(String [] args) {
        try {
            System.out.println("请输入字符串:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str = br.readLine();
            int number = Integer.parseInt(str);
            System.out.printf("\n转换后的结果:%d", number);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getCause());
        }
        System.out.println("\n没挂");
    }
}
