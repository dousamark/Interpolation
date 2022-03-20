import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CanvasPanel extends JPanel {

    public CanvasPanel() {
        this.setBorder(new LineBorder(Colors.text));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //interpolation
        int[] funcValues = Interpolation.interpolate();
        if( funcValues== null){
            JOptionPane.showMessageDialog(null, "Input valid coordinates.");
            return;
        }

        Helper.LastY = funcValues;


        g.setColor(Colors.points);
        //points
        for (int i = 0; i < Helper.ScreenWidth; i++) {
            for (int j = 0; j < Helper.ScreenHeight; j++) {
                if(Helper.Points.field[i][j].set){
                    g.drawLine(i-5,j,i+5,j);
                    g.drawLine(i,j-5,i,j+5);
                }
            }
        }

        g.setColor(Colors.line);


        //each 5 for practical use
        for (int x = 0; x < Helper.ScreenWidth-5; x=x+5) {
            g.drawLine(x,Helper.LastY [x],x+5,Helper.LastY [x+5]);
        }
    }
}