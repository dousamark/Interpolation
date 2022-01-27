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
    static DrawPanel canvas;

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

        JPanel panel = setupPanel(screenWidth,screenHeight);

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

        buttons = new JButton[4];
        buttonStart = createButton("Start",screenWidth,screenHeight,0);
        buttonProg = createButton("Program doc",screenWidth,screenHeight,1);
        buttonUser = createButton("User doc",screenWidth,screenHeight,2);
        buttonExit = createButton("Exit",screenWidth,screenHeight,3);

        //TODO: kouknout se na vystup SVG (scalable vector graphics)
        //https://xmlgraphics.apache.org/batik/using/svg-generator.html

        //https://www.youtube.com/watch?v=mDxEGtMNPtA&t=4318s

        panel.add(name);
        panel.add(buttonStart);
        panel.add(buttonProg);
        panel.add(buttonUser);
        panel.add(buttonExit);

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
        //TODO:udelat drawing

        //https://stackoverflow.com/questions/9612684/drawing-in-java-using-canvas/9612991
        //mozna nedelat novej panel ale jenom do puvodniho dat ramecek?
    }

    private static void hideButtons() {
        for (JButton button : buttons){
            button.setVisible(false);
        }
    }
}
