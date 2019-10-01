import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task04_044 {
    public static void main(String [] args) throws IOException, NumberFormatException {
        System.out.println("请输入字符串:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        int number = Integer.parseInt(str);
        System.out.printf("\n转换后的结果:%d", number);
        System.out.println("\n没挂");
    }
}
