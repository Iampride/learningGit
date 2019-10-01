package task06_05;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import org.omg.PortableServer.THREAD_POLICY_ID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class StudentSystemFrame extends JFrame {
    private JFrame mainFrame;
    private ArrayList<StudentData> list;
    private HashSet<String> set;

    private JTextField nameField;
    private JTextField idField;
    private JTextField scoreField;
    /**
     * 窗口默认长, 宽
     */
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 250;

    /**
     * 屏幕分辨率
     */
    public int screenWidth;
    public int screenHeight;

    private void setStyleFrame() {
        /**
         * 设置服务器显示窗口样式和属性
         */
        mainFrame.setTitle("学生管理系统");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        int locationX = (screenWidth - DEFAULT_WIDTH) / 2;
        int locationY = (screenHeight - DEFAULT_HEIGHT) / 2;
        mainFrame.setBounds(locationX, locationY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    StudentSystemFrame() {
        list = new ArrayList<>();
        set = new HashSet<>();
        initData();

        mainFrame = new JFrame();
        setStyleFrame();

        GridBagLayout layout = new GridBagLayout();
        mainFrame.setLayout(layout);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("黑体", 1, 16));

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("黑体", 1, 16));

        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setFont(new Font("黑体", 1, 16));

        JButton entryButton = new JButton("Entry");
        entryButton.addActionListener(new EntryAction());

        JButton readButton = new JButton("Read");
        readButton.addActionListener(new ReadAction());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearAction());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitAction());

        nameField = new JTextField();
        nameField.setFont(new Font("黑体", 1, 13));

        idField = new JTextField();
        idField.setFont(new Font("黑体", 1, 13));

        scoreField = new JTextField();
        scoreField.setFont(new Font("黑体", 1, 13));

        ImageIcon img = new ImageIcon("./doc/xmind.jpg");

        mainFrame.add(nameField, new GBC(0, 0, 2, 1).
                setWeight(1, 0).setFill(GBC.BOTH));
        mainFrame.add(idField, new GBC(0, 1, 2, 1).
                setWeight(1, 0).setFill(GBC.BOTH));
        mainFrame.add(scoreField, new GBC(0, 2, 2, 1).
                setWeight(1, 0).setFill(GBC.BOTH));
        mainFrame.add(nameLabel, new GBC(2, 0, 1, 1).setAnchor(GBC.EAST));
        mainFrame.add(idLabel, new GBC(2, 1, 1, 1).setAnchor(GBC.EAST));
        mainFrame.add(scoreLabel, new GBC(2, 2, 1, 1).setAnchor(GBC.EAST));


        mainFrame.add(entryButton, new GBC(0, 3, 1, 1).
                setWeight(1, 0).setFill(GBC.BOTH));
        mainFrame.add(readButton, new GBC(1, 3, 1, 1).
                setWeight(1, 0).setFill(GBC.BOTH));
        mainFrame.add(clearButton, new GBC(2, 3, 1, 1).setFill(GBC.BOTH));
        mainFrame.add(exitButton, new GBC(3, 3, 1, 1).
                setWeight(1, 0).setFill(GBC.BOTH));

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    private class StudentData {
        private String name;
        private String  id;
        private int score;

        StudentData(String name, String id, int score){
            this.name = name;  this.id = id;  this.score = score;
        }

        String getName(){
            return this.name;
        }
        String getId(){
            return this.id;
        }
        int getScore(){
            return score;
        }
    }

    private void initData(){
        String path = "./doc/task060501.txt";
        try {
            BufferedReader readData = new BufferedReader(new FileReader(path));
            String line = null;
            while( ( line=readData.readLine() ) != null ){
                String [] s = line.split("\t");
                list.add(new StudentData(s[0], s[1], Integer.parseInt(s[2])));
                set.add(s[1]);
            }
            readData.close();
        }catch (IOException E){
            E.printStackTrace();
        }
    }
    /**
    * 此类为readButton的监听器
     * */
    private class ReadAction implements ActionListener{
        JFrame readFrame;
        JTextArea area;

        @Override
        public void actionPerformed(ActionEvent event){
            new ReadAction();
            readFrame.setVisible(true);
            String head = "姓名" + "\t" + "学号" + "\t" + "成绩" + "\n";
            area.setText(head); //表头
            int sum = 0, min, max;
            Collections.sort(list, new StudentCompare());
            for( StudentData s : list ){
                String data = s.getName() + "\t" + s.getId() + "\t" + s.getScore() + "\n";
                area.append(data);
                sum = s.getScore() + sum;
            }
            float average = sum / list.size();
            max = list.get(0).getScore();
            min = list.get(list.size() - 1).getScore();
            String total = "\n平均分:" + average + "\t最高分:" + max + "\t最低分:" + min + "\n";
            area.append(total);
            String path = "./doc/task060502.txt";
            try{
                BufferedWriter datawrite = new BufferedWriter(new FileWriter(path, false));
                for(StudentData s : list){
                    String data = s.getName() + "\t" + s.getId() + "\t" + s.getScore() + "\n";
                    datawrite.write(data);
                }
                datawrite.write(total);
                datawrite.close();
            }catch (IOException E){
                E.printStackTrace();
            }
        }

        ReadAction(){
            readFrame = new JFrame();
            readFrame.setTitle("读取");
            int locationX = (screenWidth - DEFAULT_WIDTH) / 2;
            int locationY = (screenHeight - DEFAULT_HEIGHT) / 2;
            readFrame.setBounds(locationX, locationY, DEFAULT_WIDTH + 100, DEFAULT_HEIGHT);
            readFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            area = new JTextArea();
            area.setLineWrap(true);
            area.setEnabled(true);
            area.setFont(new Font("微软雅黑",2, 25));
            area.setSize(DEFAULT_WIDTH + 100 , DEFAULT_HEIGHT -30);
            JScrollPane readPanel = new JScrollPane(area);
            readPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            readFrame.add(readPanel);
        }
    }

    /**
     * 此类为entryButton的监听器
     */
    private class EntryAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if( nameField.getText().length() == 0 ){
                System.out.println("null");
            }
            if( nameField.getText().length() == 0 || idField.getText().length() == 0 ||
                    scoreField.getText().length() == 0){
                javax.swing.JOptionPane.showMessageDialog(null, "请输入完整信息!");
                return;
            }
            String path = "./doc/task060501.txt";
            try{
                BufferedWriter dataWrite = new BufferedWriter( new FileWriter( path, true ));
                String name = nameField.getText();
                String id = idField.getText();
                int score = Integer.parseInt(scoreField.getText());
                if( set.contains(id) ){
                    javax.swing.JOptionPane.showMessageDialog(null, "该生信息已保存");
                    return;
                }else{
                    list.add(new StudentData(name, id, score));
                    set.add(id);
                }
                String content = name + "\t" + id + "\t" + Integer.toString(score) + "\n";
                dataWrite.write(content);
                dataWrite.close();
            }catch (IOException E){
                E.printStackTrace();
            }
        }
    }

    /**
     * 此类为cancleButton的监听类
     */
    private class ClearAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            nameField.setText("");
            idField.setText("");
            scoreField.setText("");
        }
    }

    private class ExitAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            mainFrame.dispose();
        }
    }

    private class ScoreCompare implements Comparator<Integer>{

        @Override
        public int compare(Integer integer1, Integer integer2) {
            if( integer1 > integer2 )
                return 1;
            else if( integer1.equals(integer2) ){
                return 0;
            }else{
                return -1;
            }
        }
    }

    private class StudentCompare implements Comparator<StudentData>{

        @Override
        public int compare( StudentData s1, StudentData s2 ){
            if( s1.getScore() > s2.getScore() ){
                return -1;
            }else if( s1.getScore() == s2.getScore() ){
                return 0;
            }else{
                return 1;
            }
        }
    }




}
