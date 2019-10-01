import java.io.*;

public class Task06_04 {
    public static void main(String [] args)throws IOException{
        Task06_04 t2 = new Task06_04();
        t2.ReceiveInput();
    }

    public void ReceiveInput()throws IOException {
        String name = null;
        BufferedReader read1;
        BufferedWriter write1 = new BufferedWriter( new FileWriter("./doc/task060401.txt", true));
        do{
            System.out.println("请输入姓名:");
            read1 = new BufferedReader( new InputStreamReader(System.in) );
            name = read1.readLine();
            if( name == "\n" ){
                System.out.println("哈哈哈");
            }

            write1.write(name + "\n");
        }while( !name.equals("") );
        read1.close();
        write1.close();
    }
}
