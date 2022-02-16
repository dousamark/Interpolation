import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickListener extends MouseAdapter {
    private final CanvasPanel panel;

    public ClickListener(CanvasPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            Global.Points.field[event.getX()][event.getY()].set = true;
            Global.interpolPoints.add(new Point(event.getX(), event.getY()));
            panel.repaint();
        }
    }
}