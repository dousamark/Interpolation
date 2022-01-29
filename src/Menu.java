import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    static JLabel name;
    static JButton buttonStart;
    static JButton buttonProg;
    static JButton buttonUser;
    static JButton buttonExit;
    static JButton[] buttons;
    static JFrame frame;
    static JPanel menuPanel;

    static RectDraw Canvas;

    //has to be here else error
    private JPanel panel;

    public Menu() {
    }

    public static void main(String[] args){
        frame = setupMenu();
    }

    private static JFrame setupMenu() {
        JFrame frame = new JFrame("Menu");

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
        return frame;
    }

    private static JPanel setupPanel() {
        JPanel panel = new JPanel();
        panel.setSize(Global.ScreenWidth, Global.ScreenHeight);
        panel.setLayout(null);
        panel.setBackground(Colors.background);

        name = getName();

        //saving buttons to array for afterwards hiding
        buttons = new JButton[4];
        buttonStart = createButton("Start",0);
        buttonProg = createButton("Program doc",1);
        buttonUser = createButton("User doc",2);
        buttonExit = createButton("Exit",3);

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

    private static JButton createButton(String name, int buttonCount) {
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

        //interactable buttons
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

    private static void startDrawing() {
        hideButtons();

        Global.CanvasWidth = 500;
        Global.XCoord = Global.ScreenWidth/3-(Global.CanvasWidth/2);
        Global.YCoord = Global.ScreenHeight/4-(Global.CanvasWidth/2)+100;

        name = getName();
        Canvas= new RectDraw();
        Canvas.setBackground(Colors.background);


        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point NewPoint = new Point(e.getX(),e.getY());
                if(checkBounds(NewPoint)){
                    Global.LastPoint = NewPoint;
                    Global.Points.add(NewPoint);

                    updateGraph(NewPoint.x, NewPoint.y);

                }
                else{
                    JOptionPane.showMessageDialog(null, "Point is out of bounds");
                }

            }
        });

        frame.add(name);
        frame.add(Canvas);
    }

    private static void updateGraph(int x, int y) {
        Canvas.fillCell(x,y);
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

    private static class RectDraw extends JPanel {

        private List<Point> fillCells;

        public RectDraw() {
            fillCells = new ArrayList<>(25);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (Point fillCell : fillCells) {
                int cellX = 10 + (fillCell.x * 10);
                int cellY = 10 + (fillCell.y * 10);
                g.setColor(Color.RED);
                g.fillRect(cellX, cellY, 10, 10);
            }

            g.drawRect(Global.XCoord,  Global.YCoord, Global.CanvasWidth, Global.CanvasWidth);
            g.setColor(Color.RED);
        }

        public void fillCell(int x, int y) {
            fillCells.add(new Point(x, y));
            repaint();
        }
    }
}
