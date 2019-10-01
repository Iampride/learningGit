import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeoutException;
import java.net.UnknownHostException;
import java.net.ConnectException;

public class TcpClient {
    public static void main(String [] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TcpClientFrame frame = new TcpClientFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}


class TcpClientFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 550;
    private static final int DEFAULT_HEIGHT = 320;
    private JTextField serverIpText;
    private JTextField serverPortText;
    private JButton connectButton;
    private JTextArea textArea;
    private JTextField sayText;
    private JButton sayButton;

    public int screenWidth;
    public int screenHeight;

    private static String ip;
    private static int port;
    private static String content;
    public Socket socket;
    private OutputStream outputstream;
    private InputStream inputstream;
    private Thread readDataThread;

    private boolean isConnect;
    /**
     * 用于判断文本区内容是否到达最后;
     */
    private static int end = 0;


    private void setStyleFrame(){
        setTitle("客户端");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        int locationX = (screenWidth - DEFAULT_WIDTH) / 2;
        int locationY = (screenHeight - DEFAULT_HEIGHT) / 2;
        setBounds(locationX, locationY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    TcpClientFrame(){
        setStyleFrame();

        //clientSettingPanel = new JPanel();
        //clientSettingPanel.setLayout(new GridLayout(1, 4));
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        JLabel serverIpLabel = new JLabel("Server IP:");
        serverIpLabel.setFont(new Font("黑体", 1, 16));

        JLabel serverPortLabel = new JLabel("Server Port:");
        serverPortLabel.setFont(new Font("黑体", 1, 16));

        serverIpText = new JTextField(10);
        serverIpText.setFont(new Font("dialog", 1, 15));

        serverPortText = new JTextField(10);
        serverPortText.setFont(new Font("dialog", 1, 15));

        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ConnectAction());

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


        this.add(serverIpLabel, new GBC(0, 0,1,1).setAnchor(GBC.EAST));
        this.add(serverIpText, new GBC(1, 0,2,1).setWeight(1,0));
        this.add(serverPortLabel, new GBC(3, 0,1,1).setAnchor(GBC.EAST));
        this.add(serverPortText, new GBC(4, 0,2,1).setWeight(1,0));
        this.add(connectButton, new GBC(6, 0,1,1).setAnchor(GBC.WEST));
        this.add(textArea, new GBC(0,1,7,3).setWeight(1, 1).
                setFill(GBC.BOTH));
        this.add(sayLabel, new GBC(0, 4,1,1).setAnchor(GBC.WEST));
        this.add(sayText, new GBC(1,4,5,1).setWeight(1,0));
        this.add(sayButton, new GBC(6, 4,1,1));

        //clientSettingPanel.add(serverIpLabel);
        //clientSettingPanel.add(serverIpText);
        //clientSettingPanel.add(serverPortLabel);
        //clientSettingPanel.add(serverPortText);
        //clientSettingPanel.revalidate();
        //Border etched = BorderFactory.createEtchedBorder();
        //Border titled = BorderFactory.createTitledBorder(etched, "客户端设置");
        //clientSettingPanel.setBorder(titled);

        //textPanel = new JPanel();

        //textPanel.add(textArea);
        //Border etched2 = BorderFactory.createEtchedBorder();
        //Border titled2 = BorderFactory.createTitledBorder(etched2, "1");
        //textPanel.setBorder(titled2);

        //this.setLayout(new GridLayout(3,0));
        //this.add(clientSettingPanel);
        //this.add(textPanel);

        readDataThread = new Thread(new Receive());
        isConnect = false;

    }

    public class ConnectAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event){
            try {
                ip = serverIpText.getText();
                port = Integer.parseInt(serverPortText.getText());
                socket = new Socket(ip, port);
                System.out.println("连接成功");
                isConnect = true;
                addText("我", "Connect to server…");
                readDataThread.start();
                //readDataThread.start();
                //释放资源
                //socket.close();
            }catch (IllegalThreadStateException e){
                javax.swing.JOptionPane.showMessageDialog(null, "已经连接到服务器");
            }
            catch (UnknownHostException e){
                javax.swing.JOptionPane.showMessageDialog(null, "检查你的IP地址或端口号是否正确");
            }catch (ConnectException e){
                javax.swing.JOptionPane.showMessageDialog(null, "检查你的IP地址或端口号是否正确");
            }
            catch (IllegalArgumentException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "检查你的IP地址或端口号是否正确");
            }
            catch (IOException E){
                E.printStackTrace();
            }
        }
    }

    public class SayAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            content = sayText.getText();
            addText("我", content);
            try{
                //使用Socket对象中的getOutputStream()获取网络输出流OutputStream对象；
                outputstream = socket.getOutputStream();
                //使用网络字节输出流OutputStream对象中的方法write，给服务器发送数据；
                outputstream.write(content.getBytes());
                outputstream.flush();
                //DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                //output.writeUTF(content);
                //output.flush();
            }catch (NullPointerException np) {
                javax.swing.JOptionPane.showMessageDialog(null, "未连接服务器");
            } catch (IOException E){
                E.printStackTrace();
            }
        }
    }


    private class Receive implements Runnable{

        @Override
        public void run(){
            try{
                while (isConnect){
                    //使用Socket对象中的方法getInputStream()获取网络字节输入流InputStream对象;
                    inputstream = socket.getInputStream();
                    //使用网络字节输入流InputStream对象中的方法read(), 读取服务器回写的数据;
                    byte[] bytes = new byte[1024];
                    int len = inputstream.read(bytes);
                    if(len !=0){
                        content = new String(bytes, 0, len);
                        addText("服务器", content);
                    }
                }
            }catch (IllegalThreadStateException e){
                System.out.println("已经连接到服务器");
                e.printStackTrace();
            }
            catch(IOException E){
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



