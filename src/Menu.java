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

    private JPanel panel;

    public Menu() {



    }

    public static void main(String[] args){
        JFrame frame = setupMenu();

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

        JButton button = createButton();

        buttonStart = new JButton("Start");
        buttonStart.setBounds(screenWidth/4, screenHeight/12*2,300,60);
        buttonStart.setBorder(new RoundedBorder(60));
        buttonStart.setBackground(Colors.background);
        buttonStart.setFont(new Font("Jetbrains Mono", Font.PLAIN, screenHeight/40));
        buttonStart.setForeground(Colors.text);
        buttonStart.setFocusPainted(false);

        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO:startDrawing
            }
        } );

        buttonStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonStart.setForeground(Color.GREEN);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonStart.setForeground(Color.BLUE);
            }
        });

        buttonProg = new JButton("Program doc");
        buttonProg.setBounds(screenWidth/4, screenHeight/12*3,300,60);
        buttonProg.setBorder(new RoundedBorder(60));
        buttonProg.setBackground(Colors.background);
        buttonProg.setFont(new Font("Jetbrains Mono", Font.PLAIN, screenHeight/40));
        buttonProg.setForeground(Colors.text);
        buttonProg.setFocusPainted(false);

        buttonUser = new JButton("User doc");
        buttonUser.setBounds(screenWidth/4, screenHeight/12*4,300,60);
        buttonUser.setBorder(new RoundedBorder(60));
        buttonUser.setBackground(Colors.background);
        buttonUser.setFont(new Font("Jetbrains Mono", Font.PLAIN, screenHeight/40));
        buttonUser.setForeground(Colors.text);
        buttonUser.setFocusPainted(false);

        buttonExit = new JButton("Exit");
        buttonExit.setBounds(screenWidth/4, screenHeight/12*5,300,60);
        buttonExit.setBorder(new RoundedBorder(60));
        buttonExit.setBackground(Colors.background);
        buttonExit.setFont(new Font("Jetbrains Mono", Font.PLAIN, screenHeight/40));
        buttonExit.setForeground(Colors.text);
        buttonExit.setFocusPainted(false);

        //TODO: rozvrhnout si menu interface a dodelat sem buttons
        //TODO: udelat prirucku programatorskou a uzivatelskou

        //TODO: kouknout se na vystup SVG (scalable vector graphics)
        //https://xmlgraphics.apache.org/batik/using/svg-generator.html

        //TODO:pridelat tridu s drawingem (v gui udelat ramecek?)
        //https://stackoverflow.com/questions/2134840/drawing-on-jframe

        //https://www.geeksforgeeks.org/java-swing-jtextfield/
        //https://www.tutorialspoint.com/how-to-change-jlabel-font-in-java
        //https://www.youtube.com/watch?v=mDxEGtMNPtA&t=4318s

        panel.add(name);
        panel.add(buttonStart);
        panel.add(buttonProg);
        panel.add(buttonUser);
        panel.add(buttonExit);

        return panel;
    }

    private static JButton createButton() {
        return new JButton();
    }


}
