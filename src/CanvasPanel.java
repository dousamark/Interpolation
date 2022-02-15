import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CanvasPanel extends JPanel {

    private Color backgroundColor;

    public CanvasPanel() {
        this.backgroundColor = Colors.background;
        this.setBorder(new LineBorder(Color.BLACK));
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //default setup
        g.setColor(getBackgroundColor());
        g.drawLine(100,100,100,600);
        g.drawLine(100,600,1200,600);

        //points
        for (int i = 0; i < Global.ScreenWidth; i++) {
            for (int j = 0; j < Global.ScreenHeight; j++) {
                if(Global.Points.field[i][j].set){
                    g.drawLine(i-5,j,i+5,j);
                    g.drawLine(i,j-5,i,j+5);
                }
            }
        }

        //interpolation
        Global.LastY = Interpolation.interpolate(Global.interpolPoints);

        //each 5 for practical use
        for (int x = 0; x < Global.ScreenWidth-5; x=x+5) {
            g.drawLine(x,Global.LastY [x],x+5,Global.LastY [x+5]);
        }
    }
}