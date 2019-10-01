import com.sun.org.apache.xml.internal.security.utils.JDKXPathAPI;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class TcpServer {
    public static void main(String [] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TcpServerFrame frame = new TcpServerFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}


class TcpServerFrame extends JFrame{
    private static final int DEFAULT_WIDTH = 550;
    private static final int DEFAULT_HEIGHT = 320;
    private JTextField serverPortText;
    private JButton startButton;
    private JTextArea textArea;
    private JTextField sayText;
    private JButton sayButton;

    public int screenWidth;
    public int screenHeight;

    private ServerSocket server;
    private Socket socket;
    private static String content;
    private OutputStream outputstream;
    private InputStream inputstream;
    private Thread writeMessage;
    private Thread readMessage;
    private boolean isStart;
    /**
     * 用于判断文本区内容是否到达最后;
     */
    private static int end = 0;


    private void setStyleFrame(){
        setTitle("服务器");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        int locationX = (screenWidth - DEFAULT_WIDTH) / 2;
        int locationY = (screenHeight - DEFAULT_HEIGHT) / 2;
        setBounds(locationX, locationY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    TcpServerFrame(){
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


        readMessage = new Thread(new MyRunnable());
        isStart = false;
    }

    private class StartAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            try{
                int port = Integer.parseInt(serverPortText.getText());
                //创建服务器ServerSocket对象和系统指定的端口号；
                server = new ServerSocket(port);
                isStart = true;
                addText("我", "Server starting…");
                System.out.println("server start!");
                readMessage.start();
            }catch (IOException E){
                E.printStackTrace();
            }

        }
    }

    private class SayAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            if(isStart){
                content = sayText.getText();
                addText("我", content);
                try{
                    //使用Socket对象中的getOutputStream()获取网络输出流OutputStream对象；
                    outputstream = socket.getOutputStream();
                    //使用网络字节输出流OutputStream对象中的方法write，给服务器发送数据；
                    outputstream.write(content.getBytes());
                    outputstream.flush();
                }catch (NullPointerException np){
                    //System.out.println("接服务器未开启");
                    np.printStackTrace();
                }
                catch (IOException E){
                    E.printStackTrace();
                }
            }else {
                System.out.println("服务器还未开启");
            }
        }
    }

    private class MyRunnable implements Runnable{
        @Override
        public void run(){
            try{
                socket = server.accept();
                if(socket != null){
                    addText("我", "Client connected…");
                }
                while (isStart) {
                    //socket = server.accept();
                    //使用Socket中的方法getInputStream(), 获得网络字节输入流InputStream的对象；
                    inputstream = socket.getInputStream();
                    //使用网络字节输入流InputStream中的read()方法， 读取客户端发送的数据；
                    byte[] bytes = new byte[1024];
                    int len = inputstream.read(bytes);
                    if (len != 0) {
                        content = new String(bytes, 0, len);
                        addText("客户端", content);
                    }
                }
            }catch (IOException E){
                E.printStackTrace();
            }
        }
    }

    private void addText(String object, String text){
        if(end == 9){
            textArea.setText(object + ": " + text + "\n");
            end = 0;
        }else{
            textArea.append(object + ": " + text + "\n");
            end++;
        }
    }


}
