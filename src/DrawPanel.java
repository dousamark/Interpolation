import javax.swing.*;
import java.awt.*;

class DrawPanel extends JPanel {

    // Set background in constructor.

    public DrawPanel ()
    {
        this.setSize(500,500);
        this.setBackground (Color.BLACK);
    }


    // Override paintComponent():

    public void paintComponent (Graphics g)
    {
        // Always call super.paintComponent (g):
        super.paintComponent(g);

        // drawString() is a Graphics method.
        // Draw the string "Hello World" at location 100,100
        g.drawString ("Hello World!", 100, 100);

        // Let's find out when paintComponent() is called.
        System.out.println ("Inside paintComponent");
    }

}