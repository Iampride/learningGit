import com.sun.xml.internal.fastinfoset.util.PrefixArray;

import java.io.*;

public class Task06_03 {
    public static void main(String [] args){
        Task06_03 t1 = new Task06_03();
        t1.readDoc();
    }

    public void readDoc(){
        String path1 = "./doc/task060301.txt";
        String path2 = "./doc/task060302.txt";
        String path3 = "./doc/task060303.txt";
        try{
            BufferedReader read1 = new BufferedReader(new FileReader(path1));
            BufferedReader read2 = new BufferedReader(new FileReader(path2));
            BufferedWriter write1 = new BufferedWriter(new FileWriter(path3, true));
            String line = null;
            while( (line = read1.readLine()) != null ){
                write1.write(line);
                write1.write("\n");
            }
            while ( (line = read2.readLine()) != null ){
                write1.write(line);
                write1.write("\n");
            }
            read1.close();
            read2.close();
            write1.close();
        }catch (IOException E){
            E.printStackTrace();
        }
    }
}