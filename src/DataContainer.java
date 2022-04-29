import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DataContainer {
    public Points Points;
    public List<Point> interpolPoints = new ArrayList<>();
    public int[] LastY;
    public int maxPointsCounter = 0;
    public DefaultListModel<Point> UIlist;
    public JTextField xInput;
    public JTextField yInput;
    public int xSize=1000;
    public int ySize=650;
}
