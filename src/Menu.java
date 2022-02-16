import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import java.awt.*;
import java.io.*;

public class Menu implements Runnable {
    private JPanel panel;
    JFrame frame;

    @Override
    public void run() {
        initGUI();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Menu());
    }

    private void initGUI() {
        frame = new JFrame("Interpolation");

        //setting size
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Global.ScreenWidth = gd.getDisplayMode().getWidth();
        Global.ScreenHeight = gd.getDisplayMode().getHeight();
        frame.setBounds(0, 0, Global.ScreenWidth, Global.ScreenHeight);

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
        panel.setLayout(null);
        panel.setBackground(Colors.background);

        JLabel name = new JLabel("Interpolation");
        name.setBounds(Global.ScreenWidth / 4, 20, 350, 100);
        name.setFont(new Font("Jetbrains Mono", Font.PLAIN, Global.ScreenHeight / 20));
        name.setForeground(Colors.text);

        JButton buttonStart = createButton("Start", 0);
        JButton buttonUser = createButton("User doc", 1);
        JButton buttonExit = createButton("Exit", 2);

        //add controls to panel
        panel.add(name);
        panel.add(buttonStart);
        panel.add(buttonUser);
        panel.add(buttonExit);

        return panel;
    }

    private JButton createButton(String name, int buttonCount) {
        JButton button = new JButton(name);
        button.setBounds(Global.ScreenWidth / 4, Global.ScreenHeight / 12 * (buttonCount + 2), 300, 60);
        button.setBorder(new RoundedBorder(60));
        button.setBackground(Colors.background);
        button.setFont(new Font("Jetbrains Mono", Font.PLAIN, Global.ScreenHeight / 40));
        button.setForeground(Colors.text);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            switch (name) {
                case "Start" -> startDrawing();
                case "User doc" -> JOptionPane.showMessageDialog(null, getUserDoc());
                case "Exit" -> frame.dispose();
            }
        });

        //intractable buttons
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Colors.line);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Colors.text);
            }
        });
        return button;
    }

    private String getUserDoc() {
        String doc = "";
        try{
            // read rtf from file
            JEditorPane p = new JEditorPane();
            p.setContentType("text/rtf");
            EditorKit rtfKit = p.getEditorKitForContentType("text/rtf");
            rtfKit.read(new FileReader("User Documentation.rtf"), p.getDocument(), 0);

            // convert to text
            EditorKit txtKit = p.getEditorKitForContentType("text/plain");
            Writer writer = new StringWriter();
            txtKit.write(writer, p.getDocument(), 0, p.getDocument().getLength());
            doc = writer.toString();
        } catch (BadLocationException | IOException e){
            e.printStackTrace();
        }
        return doc;
    }

    private void startDrawing() {
        frame.setVisible(false);

        Global.Points = new Points();
        JFrame drawFrame = new JFrame();

        drawFrame.add(createCanvas());
        drawFrame.setBounds(0, 0, Global.ScreenWidth, Global.ScreenHeight);
        drawFrame.setVisible(true);
        frame = drawFrame;
    }

    private CanvasPanel createCanvas() {
        CanvasPanel canvasPanel = new CanvasPanel();
        canvasPanel.addMouseListener(new ColorListener(canvasPanel));

        JLabel text = new JLabel("Export SVG");
        text.setFont(new Font("Jetbrains Mono", Font.PLAIN, 20));
        text.setForeground(Colors.text);

        JButton exportSVG = new JButton();
        exportSVG.add(text);
        exportSVG.setBackground(Colors.background);
        exportSVG.setBorder(new RoundedBorder(20));
        exportSVG.setBounds(0, 0, 200, 80);
        exportSVG.setVisible(true);

        //adding functionality to button
        exportSVG.addActionListener(e -> {
            Export.export(Global.LastY);
            JOptionPane.showMessageDialog(null, "Export has been saved. Thanks for using.");
            frame.dispose();
        });

        canvasPanel.add(exportSVG);
        return canvasPanel;
    }
}
