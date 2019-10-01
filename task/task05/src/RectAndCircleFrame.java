import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;


public class RectAndCircleFrame extends JFrame {
    private int screenWidth;
    private int screenHeight;
    private static final int DEFAULT_WIDTH = 550;
    private static final int DEFAULT_HEIGHT = 350;
    private static final int DEFAULT_RECTANGLE_WIDTH = 15;
    private static final int DEFAULT_RECTANGLE_HEIGHT = 15;
    private static final int DEFAULT_CIRCLE_RADIUS = 15;
    private LinkedList<Rectangle2D> rect;
    private LinkedList<Ellipse2D> circle;

    RectAndCircleFrame(){
        setTitle("DiceThree");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        int locationX = (screenWidth - DEFAULT_WIDTH) / 2;
        int locationY = (screenHeight - DEFAULT_HEIGHT) / 2;
        setBounds(locationX, locationY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.add(new MousePanel());
    }

    private class MousePanel extends JPanel{

        public MousePanel(){
            rect = new LinkedList<>();
            circle = new LinkedList<>();
            addMouseListener(new MouseAction());
        }

        @Override
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            for(Rectangle2D r : rect){
                g2.draw(r);
            }
            for(Ellipse2D c : circle){
                g2.draw(c);
            }
        }


        private class MouseAction extends MouseAdapter{

            @Override
            public void mouseClicked(MouseEvent event){
                /**鼠标右键被按下*/
                if(  event.getModifiers() == InputEvent.BUTTON3_MASK ){
                    Rectangle2D r = new Rectangle2D.Double();
                    r.setFrameFromCenter(event.getX(), event.getY(),
                            event.getX()+DEFAULT_RECTANGLE_WIDTH, event.getY()+DEFAULT_RECTANGLE_HEIGHT);
                    rect.add(r);
                }else if(event.getModifiers() == InputEvent.BUTTON1_MASK){
                    Ellipse2D c = new Ellipse2D.Double();
                    c.setFrameFromCenter(event.getX(), event.getY(),
                            event.getX()+DEFAULT_CIRCLE_RADIUS, event.getY()+DEFAULT_CIRCLE_RADIUS);
                    circle.add(c);
                }
                repaint();
            }
        }
    }

}
