import com.sun.security.ntlm.Client;
import sun.print.ServiceDialog;

import javax.management.ServiceNotFoundException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

public class TcpServer2 {
    public static void main(String [] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TcpServerFrame2 frame = new TcpServerFrame2();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}


class TcpServerFrame2 extends JFrame{
    /** 窗口默认长, 宽*/
    private static final int DEFAULT_WIDTH = 550;
    private static final int DEFAULT_HEIGHT = 320;

    /** 服务器端口号*/
    private JTextField serverPortText;

    /** 打开服务器"start按钮"*/
    private JButton startButton;

    /** 显示接受消息文本区域*/
    private JTextArea textArea;

    /** 发送数据文本区域*/
    private JTextField sayText;

    /** 发送消息"say"按钮*/
    private JButton sayButton;

    /** 屏幕分辨率*/
    public int screenWidth;
    public int screenHeight;

    /** 服务端套接字*/
    private ServerSocket server;

    /** 存放客户端链表*/
    private LinkedList<ClientM> clientListed = new LinkedList<>();

    /** 客户端发送内容*/
    private static String content;

    /** 判断是否开启服务*/
    private boolean isStart;

    /** 用于判断文本区内容是否到达最后;*/
    private static int end = 0;

    private void setStyleFrame(){
        /**
         * 设置服务器显示窗口样式和属性
         */
        setTitle("服务器");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        int locationX = (screenWidth - DEFAULT_WIDTH) / 2;
        int locationY = (screenHeight - DEFAULT_HEIGHT) / 2;
        setBounds(locationX, locationY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    TcpServerFrame2(){

        setStyleFrame();

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);


        JLabel serverPortLabel = new JLabel("Port:");
        serverPortLabel.setFont(new Font("黑体", 1, 16));


        serverPortText = new JTextField(30);
        serverPortText.setFont(new Font("dialog", 1, 15));

        startButton = new JButton("Start");
        startButton.addActionListener(new StartAction());

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEnabled(false);
        textArea.setBorder(BorderFactory.createEtchedBorder());

        textArea.setFont(new Font("微软雅黑", 1, 20));
        JLabel sayLabel = new JLabel("Say:");

        sayLabel.setFont(new Font("黑体", 1, 16));
        sayText = new JTextField(25);
        sayText.setFont(new Font("dialog", 1, 15));

        sayButton = new JButton("Say");
        sayButton.addActionListener(new SayAction());


        this.add(serverPortLabel, new GBC(0, 0,1,1).setAnchor(GBC.EAST));
        this.add(serverPortText, new GBC(1, 0,2,1).setWeight(1,0));
        this.add(startButton, new GBC(6, 0,1,1).setAnchor(GBC.WEST));
        this.add(textArea, new GBC(0,1,7,3).setWeight(1, 1).
                setFill(GBC.BOTH));
        this.add(sayLabel, new GBC(0, 4,1,1).setAnchor(GBC.WEST));
        this.add(sayText, new GBC(1,4,5,1).setWeight(1,0));
        this.add(sayButton, new GBC(6, 4,1,1));

        isStart = false;
    }

    private void startServer(){
        try{
            int port = Integer.parseInt(serverPortText.getText());
            //创建服务器ServerSocket对象和系统指定的端口号；
            server = new ServerSocket(port);
            isStart = true;
            addText("我", "Server starting…");
        }catch (BindException e){
            javax.swing.JOptionPane.showMessageDialog(null,"端口号已被占用");
        }catch (IllegalArgumentException e){
            javax.swing.JOptionPane.showMessageDialog(null, "端口号超出正常范围");
        } catch (IOException E){
            javax.swing.JOptionPane.showMessageDialog(null, "服务端开启失败");
            E.printStackTrace();
        }
        try{
            while (isStart){
                Socket s = server.accept();
                ClientM c = new ClientM(s);
                clientListed.add(c);
                new Thread(c).start();
                System.out.println(s.getInetAddress() + "connect successfully");
            }
        }catch (IOException E){
            E.printStackTrace();
        }
    }

    private void addText(String object, String text){
        /**
         * 将文本添加到文本显示区域
         */
        if(end == 9){
            textArea.setText(object + ": " + text + "\n");
            end = 0;
        }else{
            textArea.append(object + ": " + text + "\n");
            end++;
        }
    }


    private class StartAction implements ActionListener, Runnable{

        @Override
        public void actionPerformed(ActionEvent event){
            new Thread(this).start();
        }

        @Override
        public void run() {
            startServer();
        }
    }

    /*private class ServerNotStart implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            new ServerNotStartDialog();
        }
    }

    private class ServerNotStartDialog extends JDialog{
        private static final int DEFAULT_DIALOG_WIDTH = 250;
        private static final int DEFAULT_DIALOG_HEIGHT = 180;

        ServerNotStartDialog(){
            setTitle("ConnectError");
            setBounds((screenWidth - DEFAULT_DIALOG_WIDTH) / 2, (screenHeight - DEFAULT_DIALOG_HEIGHT) / 2,
                    DEFAULT_DIALOG_WIDTH, DEFAULT_DIALOG_HEIGHT);
            JLabel label = new JLabel("服务器未开启!");
            this.add(label, BorderLayout.CENTER);
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }*/

    private class SayAction implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (isStart) {
                new Thread(this).start();
            }else{
                javax.swing.JOptionPane.showMessageDialog(null, "服务器未开启");
            }
        }
        @Override
        public void run() {
            write();
        }

        private void write() {
            ClientM c = null;
            content = sayText.getText();
            addText("我", content);
            try {
                for(int i = 0; i < clientListed.size(); i++) {
                    c = (ClientM) clientListed.get(i);
                    c.send(content);
                }
            } catch (NullPointerException np) {
                javax.swing.JOptionPane.showMessageDialog(null, "服务器未开启");
                np.printStackTrace();
            } catch (IOException E) {
                clientListed.remove(c);
                E.printStackTrace();
            }
        }
    }

    private class ClientM implements Runnable{
        /**
         * 此类用于当客户端和服务器连接时, 开始服务端开始发送和接受数据.
         */

        /** 当前连接的客户端套接字*/
        private Socket socket = null;

        private boolean isConnect = false;

        private ClientM(Socket s){
            socket = s;
            isConnect = true;
        }


        private void send(String sendContent)throws IOException{
            /**
             * 给客户端发送数据
             */
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(sendContent.getBytes());
            outputStream.flush();
            //DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            //output.writeUTF(sendContent);
        }

        private void read()throws IOException{
            /**
             * 读取客户端发送的数据
             */
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            if (len != 0) {
                content = new String(bytes, 0, len);
                System.out.println(content);
                addText("客户端", content);
            }else{
                System.out.println("len==0");
            }
        }
        @Override
        public void run(){
            Client c;
            try{
                if (socket != null) {
                    addText("我", "Client connected…");
                }
                while (isConnect) {
                        read();
                    }
                }catch (IOException E){
                    E.printStackTrace();
                }
        }
    }



}
