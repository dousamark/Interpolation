import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickListener extends MouseAdapter {
    private final CanvasPanel panel;
    private DataContainer dataContainer;
    private Interpolation interpolator;

    public ClickListener(CanvasPanel panel, DataContainer dataContainer, Interpolation interpolation) {
        this.panel = panel;
        this.dataContainer = dataContainer;
        interpolator = interpolation;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            Helper.addPoint(event.getX(),event.getY(), this.panel, dataContainer, interpolator);
        }
    }
}