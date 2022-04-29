import javax.swing.*;
import java.util.List;

public class Helper {
    static void addPoint(int x, int y, JPanel drawingPanel, DataContainer dataContainer, Interpolation interpolator){
        if(IsSecondYPoint(x, dataContainer.interpolPoints)){
            JOptionPane.showMessageDialog(null, "Same X cant have two functional values.");
            return;
        }
        if(!interpolator.canInterpolate(x,y)){
            JOptionPane.showMessageDialog(null, "Matrix is early singular. Please consider other points to interpolate.");
            return;
        }

        Point pickedPoint = dataContainer.Points.field[x][y];
        pickedPoint.set = true;

        pickedPoint.ID =String.valueOf(dataContainer.maxPointsCounter);
        dataContainer.maxPointsCounter+=1;

        //showing to user
        dataContainer.xInput.setText(Integer.toString(x));
        dataContainer.yInput.setText(Integer.toString(dataContainer.ySize-y));

        dataContainer.interpolPoints.add(pickedPoint);
        dataContainer.UIlist.addElement(pickedPoint);
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

    public static boolean validateInputCoords(String x, String y, DataContainer dataContainer) {
        int xVal;
        int yVal;
        try{
            xVal = Integer.parseInt(x);
            yVal = Integer.parseInt(y);

        }catch (NumberFormatException e){
            return false;
        }

        if(xVal>=0 && xVal<=dataContainer.xSize){
            if(yVal>=0 && yVal<= dataContainer.ySize){
                return true;
            }
        }
        return false;
    }
}
