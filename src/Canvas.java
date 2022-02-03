import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;

class Canvas extends JPanel {


    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;

    public Canvas(){

        this.setBackground(Color.BLACK);
        this.setBounds(10,10,100,100);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare();
            }
        });


    }
    private void moveSquare() {
        int x = Global.LastPoint.x;
        int y = Global.LastPoint.y;
        repaint(x,y,squareW,squareH);
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillRect(squareX,squareY,squareW,squareH);
    }
}