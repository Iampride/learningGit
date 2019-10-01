import com.sun.org.apache.xml.internal.security.utils.JDKXPathAPI;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeoutException;

public class Test01 {
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


class TcpClientFrame1 extends JFrame{
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
    /**
    * 客户端设置面板
    * 文本区域面板
    * 对话区域面板
    */
    private JPanel clientSettingPanel;
    private JPanel textPanel;
    private JPanel talkPanel;


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

    TcpClientFrame1(){
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


    }

    private class ConnectAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            String ip = serverIpText.getText();
            String port = serverPortText.getText();
            System.out.println(ip);
            System.out.println(port);
        }
    }

    private class SayAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            String content = sayText.getText();
            textArea.setText(content);
            System.out.println(content);
        }
    }


}
