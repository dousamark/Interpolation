import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    static JLabel name;
    static JButton buttonStart;
    static JButton buttonProg;
    static JButton buttonUser;
    static JButton buttonExit;
    static JButton[] buttons;
    static JFrame frame;
    static JPanel menuPanel;

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
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        frame.setBounds(0,0,screenWidth,screenHeight);

        //creating ending condition
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting up panel
        JPanel panel = setupPanel(screenWidth,screenHeight);

        //making visible
        frame.add(panel);
        frame.setVisible(true);
        return frame;
    }

    private static JPanel setupPanel(int screenWidth, int screenHeight) {
        JPanel panel = new JPanel();
        panel.setSize(screenWidth,screenHeight);
        panel.setLayout(null);
        panel.setBackground(Colors.background);

        name = new JLabel("Interpolation");
        name.setBounds(screenWidth/4, 20,350,100);
        name.setFont(new Font("Jetbrains Mono", Font.PLAIN, screenHeight/20));
        name.setForeground(Colors.text);

        //saving buttons to array for afterwards hiding
        buttons = new JButton[4];
        buttonStart = createButton("Start",screenWidth,screenHeight,0);
        buttonProg = createButton("Program doc",screenWidth,screenHeight,1);
        buttonUser = createButton("User doc",screenWidth,screenHeight,2);
        buttonExit = createButton("Exit",screenWidth,screenHeight,3);

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

    private static JButton createButton(String name, int screenWidth, int screenHeight, int buttonCount) {
        JButton button = new JButton(name);
        button.setBounds(screenWidth/4, screenHeight/12*(buttonCount+2),300,60);
        button.setBorder(new RoundedBorder(60));
        button.setBackground(Colors.background);
        button.setFont(new Font("Jetbrains Mono", Font.PLAIN, screenHeight/40));
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
        //TODO:udelat 2d graph

        /*
        RectDraw newrect= new RectDraw();
        newrect.setBackground(Colors.background);
        frame.add(newrect);
        //mozna nedelat novej panel ale jenom do puvodniho dat ramecek?
         */
    }

    private static void hideButtons() {
        for (JButton button : buttons){
            button.setVisible(false);
        }
        menuPanel.setVisible(false);
    }

    //in progress
    /*
    private static class RectDraw extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(230,80,10,10);
            g.setColor(Color.RED);
            g.fillRect(230,80,10,10);
        }

        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    }
     */
}
