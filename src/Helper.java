import javax.swing.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    static int ScreenWidth;
    static int ScreenHeight;

    static int xSize=1000;
    static int ySize=650;

    static Points Points;
    static List<Point> interpolPoints = new ArrayList<>();
    static int[] LastY;
    static int maxPointsCounter = 0;
    static DefaultListModel<Point> UIlist;
    static JPanel drawingPanel;
    static JTextField xInput;
    static JTextField yInput;

    static void addPoint(int x, int y ){
        if(IsSecondYPoint(x,interpolPoints)){
            JOptionPane.showMessageDialog(null, "Same X cant have two functional values.");
            return;
        }
        Point pickedPoint = Helper.Points.field[x][y];
        pickedPoint.set = true;

        pickedPoint.ID =String.valueOf(maxPointsCounter);
        maxPointsCounter+=1;

        //showing to user
        xInput.setText(Integer.toString(x));
        yInput.setText(Integer.toString(y));

        interpolPoints.add(pickedPoint);
        UIlist.addElement(pickedPoint);
        drawingPanel.repaint();
    }

    private static boolean IsSecondYPoint(int x, List<Point> interpolPoints) {
        for (Point point : interpolPoints) {
            if(point.x == x){
                return true;
            }
        }
        return false;
    }

    public static boolean validateInputCoords(String x, String y) {
        int xVal;
        int yVal;
        try{
            xVal = Integer.parseInt(x);
            yVal = Integer.parseInt(y);

        }catch (NumberFormatException e){
            return false;
        }

        if(xVal>=0 && xVal<=xSize){
            if(yVal>=0 && yVal<=ySize){
                return true;
            }
        }
        return false;
    }
}
