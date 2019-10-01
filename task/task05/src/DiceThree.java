import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class DiceThree extends JFrame{
    private int screenWidth;
    private int screenHeight;
    private static final int DEFAULT_WIDTH = 550;
    private static final int DEFAULT_HEIGHT = 350;


    DiceThree(){
        setTitle("DiceThree");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        int locationX = (screenWidth - DEFAULT_WIDTH) / 2;
        int locationY = (screenHeight - DEFAULT_HEIGHT) / 2;
        setBounds(locationX, locationY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        DicePanel panel = new DicePanel();
        this.add(panel);

    }

    class DicePanel extends JPanel{

        @Override
        public void paintComponent(Graphics g){
            Graphics2D g21 = (Graphics2D) g;
            Graphics2D g22 = (Graphics2D) g;
            Graphics2D g23 = (Graphics2D) g;
            Graphics2D g24 = (Graphics2D) g;
            Rectangle2D rect = new Rectangle2D.Double(210, 110, 120, 120);
            g24.draw(rect);
            g21.setColor(Color.blue);
            g21.fillOval(255, 155, 20, 20);
            g22.setColor(Color.blue);
            g22.fillOval(285, 185, 20, 20);
            g23.setColor(Color.blue);
            g23.fillOval(225, 125, 20, 20);

        }
    }
}
