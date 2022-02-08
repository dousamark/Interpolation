import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/30356545/java-creating-a-jframe-using-gridlayout-with-mouse-interactive-jpanels
public class Menu implements Runnable {
    static JLabel name;
    static JButton[] buttons;
    static JFrame frame;
    static JPanel menuPanel;
    static Canvas Canvas;

    //has to be here else error
    private JPanel panel;

    @Override
    public void run() {
        initGUI();
    }
    public static void main(String[] args){
        EventQueue.invokeLater(new Menu());
    }

    private void initGUI() {
        frame = new JFrame("Interpolation");

        //setting size
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Global.ScreenWidth = gd.getDisplayMode().getWidth();
        Global.ScreenHeight = gd.getDisplayMode().getHeight();
        frame.setBounds(0,0, Global.ScreenWidth, Global.ScreenHeight);

        //creating ending condition
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting up panel
        JPanel panel = setupPanel();

        //making visible
        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel setupPanel() {
        JPanel panel = new JPanel();
        panel.setSize(Global.ScreenWidth, Global.ScreenHeight);
        panel.setLayout(null);
        panel.setBackground(Colors.background);

        name = getName();

        //saving buttons to array for afterwards hiding
        buttons = new JButton[4];
        JButton buttonStart = createButton("Start",0);
        JButton buttonProg = createButton("Program doc",1);
        JButton buttonUser = createButton("User doc",2);
        JButton buttonExit = createButton("Exit",3);

        //TODO: kouknout se na vystup SVG (scalable vector graphics)
        //https://developer.mozilla.org/en-US/docs/Web/SVG/Element/polyline

        //add controls to panel
        panel.add(name);
        panel.add(buttonStart);
        panel.add(buttonProg);
        panel.add(buttonUser);
        panel.add(buttonExit);

        menuPanel = panel;
        return panel;
    }

    private JButton createButton(String name, int buttonCount) {
        JButton button = new JButton(name);
        button.setBounds(Global.ScreenWidth/4, Global.ScreenHeight/12*(buttonCount+2),300,60);
        button.setBorder(new RoundedBorder(60));
        button.setBackground(Colors.background);
        button.setFont(new Font("Jetbrains Mono", Font.PLAIN, Global.ScreenHeight/40));
        button.setForeground(Colors.text);
        button.setFocusPainted(false);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (name){
                    case "Start":
                        startDrawing();
                        break;

                    case "Program doc":
                        //TODO: udelat prirucku programatorskou a show program doc
                        break;

                    case "User doc":
                        //TODO: udelat prirucku user a show user doc
                        break;

                    case "Exit":
                        frame.dispose();
                        break;

                    default:
                        break;
                }

            }
        } );

        //intractable buttons
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Colors.points);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Colors.text);
            }
        });
        buttons[buttonCount] = button;
        return button;
    }

    private void startDrawing() {
        hideButtons();

        Global.CanvasWidth = 50;
        Global.XCoord = Global.ScreenWidth/3-(Global.CanvasWidth/2);
        Global.YCoord = Global.ScreenHeight/4-(Global.CanvasWidth/2)+100;

        frame.remove(menuPanel);

        JPanel pixels = createPixels();

        frame.add(pixels);
        //frame.pack();
    }

    private JPanel createPixels() {
        //int width = Global.CanvasWidth;
        int width = 10;
        int height = 10;

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(400,40,40,40));

        GridLayout grid = new GridLayout(width, height);

        panel.setLayout(grid);

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                PixelPanel pixelPanel = new PixelPanel();
                pixelPanel.addMouseListener(new ColorListener(pixelPanel));
                panel.add(pixelPanel);
            }
        }

        return panel;
    }

    public class PixelPanel extends JPanel {

        private static final int PIXEL_SIZE = 2;

        private Color backgroundColor;

        public PixelPanel() {
            this.backgroundColor = Color.WHITE;
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //this.setSize(new Dimension(PIXEL_SIZE-1, PIXEL_SIZE+3));
            this.setMaximumSize(new Dimension(1,1));
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

            g.setColor(getBackgroundColor());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public class ColorListener extends MouseAdapter {

        private PixelPanel panel;

        public ColorListener(PixelPanel panel) {
            this.panel = panel;
        }

        @Override
        public void mousePressed(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {
                panel.setBackgroundColor(Color.BLUE);
                panel.repaint();
            } else if (event.getButton() == MouseEvent.BUTTON3) {
                panel.setBackgroundColor(Color.WHITE);
                panel.repaint();
            }
        }
    }


    private static boolean checkBounds(Point newPoint) {
        if(newPoint.x>=Global.XCoord && newPoint.x<=Global.XCoord+Global.CanvasWidth){
            if(newPoint.y>=Global.YCoord && newPoint.y<=Global.YCoord+Global.CanvasWidth){
                return true;
            }
        }
        return false;
    }

    private static JLabel getName() {
        JLabel name = new JLabel("Interpolation");
        name.setBounds(Global.ScreenWidth/4, 20,350,100);
        name.setFont(new Font("Jetbrains Mono", Font.PLAIN, Global.ScreenHeight/20));
        name.setForeground(Colors.text);
        return name;
    }

    private static void hideButtons() {
        for (JButton button : buttons){
            button.setVisible(false);
        }
        menuPanel.setVisible(false);
    }
}
