import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickListener extends MouseAdapter {
    private final CanvasPanel panel;

    public ClickListener(CanvasPanel panel) {
        this.panel = panel;
        Helper.drawingPanel = this.panel;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            Helper.addPoint(event.getX(),event.getY());
        }
    }
}