import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
    JLabel name;
    JButton[] buttons;
    JFrame frame;
    JPanel menuPanel;

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
        //panel.setSize(Global.ScreenWidth, Global.ScreenHeight);
        panel.setLayout(null);
        panel.setBackground(Colors.background);

        name = getName();

        //saving buttons to array for afterwards hiding
        buttons = new JButton[3];
        JButton buttonStart = createButton("Start",0);
        JButton buttonUser = createButton("User doc",1);
        JButton buttonExit = createButton("Exit",2);

        //TODO: kouknout se na vystup SVG (scalable vector graphics)
        //https://developer.mozilla.org/en-US/docs/Web/SVG/Element/polyline

        //add controls to panel
        panel.add(name);
        panel.add(buttonStart);
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

        Global.CanvasWidth = 500;
        Global.XCoord = Global.ScreenWidth/3-(Global.CanvasWidth/2);
        Global.YCoord = Global.ScreenHeight/4-(Global.CanvasWidth/2)+100;

        frame.remove(menuPanel);

        JPanel canvas = createCanvas();

        frame.add(canvas);
    }

    private JPanel createCanvas() {

        JPanel panel = new JPanel();

        panel.setBounds(10,10,100,100);

        CanvasPanel canvasPanel = new CanvasPanel();

        canvasPanel.addMouseListener(new ColorListener(canvasPanel));
        panel.add(canvasPanel);

        return panel;
    }

    public class CanvasPanel extends JPanel {

        private Color backgroundColor;

        public CanvasPanel() {
            this.backgroundColor = Color.GRAY;
            this.setBorder(new LineBorder(Color.BLACK));

            JLabel jlabel = new JLabel("Testovací label");
            jlabel.setFont(new Font("Verdana",1,20));
            this.add(jlabel);

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

            //g.drawLine(100,0,200,100);
        }
    }

    public class ColorListener extends MouseAdapter {

        private CanvasPanel panel;

        public ColorListener(CanvasPanel panel) {
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

    private void hideButtons() {
        for (JButton button : buttons){
            button.setVisible(false);
        }
        menuPanel.setVisible(false);
    }
}
