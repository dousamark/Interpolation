import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CanvasPanel extends JPanel {
    int screenWidth;
    int screenHeight;
    private Interpolation Interpolator;
    private DataContainer dataContainer;

    public CanvasPanel(int screenWidth, int screenHeight, DataContainer dataContainer) {
        this.setBorder(new LineBorder(Colors.text));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        Interpolator = new Interpolation(screenWidth, screenHeight,dataContainer);
        this.dataContainer = dataContainer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //interpolation

        int[] funcValues = Interpolator.interpolate();

        dataContainer.LastY = funcValues;


        g.setColor(Colors.points);
        //points
        for (int i = 0; i < screenWidth; i++) {
            for (int j = 0; j < screenHeight; j++) {
                if(dataContainer.Points.field[i][j].set){
                    g.drawLine(i-5,j,i+5,j);
                    g.drawLine(i,j-5,i,j+5);
                }
            }
        }

        g.setColor(Colors.line);


        //each 5 for practical use
        for (int x = 0; x < screenWidth-5; x=x+5) {
            g.drawLine(x,dataContainer.LastY [x],x+5,dataContainer.LastY [x+5]);
        }
    }
}