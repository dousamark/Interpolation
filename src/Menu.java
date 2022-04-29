import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;

public class Menu implements Runnable {
    private JPanel panel;
    private DataContainer dataContainer;
    private int screenWidth;
    private int screenHeight;


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
        screenWidth = gd.getDisplayMode().getWidth();
        screenHeight = gd.getDisplayMode().getHeight();
        frame.setBounds(0, 0, screenWidth, screenHeight);

        //creating ending condition
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting up panel
        JPanel panel = setupPanel();

        //setting up DataContainer
        dataContainer = new DataContainer();

        //making visible
        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel setupPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Colors.background);

        JLabel name = new JLabel("Interpolation");
        name.setBounds(screenWidth / 4, 20, 350, 100);
        name.setFont(new Font("Jetbrains Mono", Font.PLAIN, screenHeight / 20));
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
        button.setBounds(screenWidth / 4, screenHeight / 12 * (buttonCount + 2), 300, 60);
        button.setBorder(new RoundedBorder(60));
        button.setBackground(Colors.background);
        button.setFont(new Font("Jetbrains Mono", Font.PLAIN, screenHeight / 40));
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
        frame.dispose();

        //setup JFrame
        dataContainer.Points = new Points(screenWidth, screenHeight);
        JFrame drawFrame = new JFrame();
        drawFrame.setLayout(new BorderLayout());
        drawFrame.setVisible(true);
        drawFrame.setBounds(0, 0, screenWidth, screenHeight);

        //setting drawing panel
        Interpolation interpolator = new Interpolation(screenWidth, screenHeight,dataContainer);
        JPanel drawingPanel = createCanvas(interpolator);
        drawFrame.add(drawingPanel,BorderLayout.CENTER);

        drawFrame.add(createUsabilityMenu(drawingPanel, interpolator),BorderLayout.WEST);

        frame = drawFrame;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JButton createEndProgramButton() {
        JButton endButton = new JButton();
        JLabel label = getLabel("End Program");

        endButton.add(label);
        endButton.setPreferredSize(new Dimension(250,120));
        endButton.addActionListener(e -> frame.dispose());
        return endButton;
    }

    private JPanel createUsabilityMenu(JPanel drawingPanel, Interpolation interpolator) {
        JPanel UIMenu = new JPanel();
        UIMenu.setLayout(new BorderLayout());

        ///list
        DefaultListModel<Point> buttonList = new DefaultListModel<>();
        dataContainer.UIlist = buttonList;

        JList<Point> list = new JList<>(buttonList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setPreferredSize(new Dimension(250,500));

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250,200));
        listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        UIMenu.add(listScroller,BorderLayout.NORTH);

        //manual adding points
        JPanel innerManualHolder = new JPanel();
        innerManualHolder.setLayout(new GridLayout(8,1));

        JLabel blankLabel = getLabel("");
        JLabel manualLabel = getLabel("Manually add points");
        JLabel manualLabel2 = getLabel("x: [0-1000] y: [0-650]");

        JTextField xInput = new JTextField("X coords");
        xInput.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {xInput.setText("");}
            public void focusLost(FocusEvent e) {
                if(xInput.getText().equals("")){
                    xInput.setText("X coords");
                }
            }
        });
        dataContainer.xInput = xInput;

        JTextField yInput = new JTextField("Y Coords");
        yInput.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {yInput.setText("");}
            public void focusLost(FocusEvent e) {
                if(yInput.getText().equals("")){
                    yInput.setText("Y coords");
                }
            }
        });
        dataContainer.yInput = yInput;

        JButton addManualButton = new JButton();
        JLabel addManualButtonText = getLabel("Add point");
        addManualButton.add(addManualButtonText);
        addManualButton.addActionListener(e -> {
            String xVal = xInput.getText();
            String yVal = yInput.getText();
            if(Helper.validateInputCoords(xVal,yVal,dataContainer)){
                //because of inverted Y axis
                Helper.addPoint(Integer.valueOf(xVal),dataContainer.ySize-Integer.valueOf(yVal), drawingPanel, dataContainer,interpolator );
            }
            else{
                JOptionPane.showMessageDialog(null, "Input valid coordinates.");
           }
        });


        innerManualHolder.add(blankLabel);
        innerManualHolder.add(manualLabel);
        innerManualHolder.add(manualLabel2);
        innerManualHolder.add(xInput);
        innerManualHolder.add(yInput);
        innerManualHolder.add(addManualButton);

        UIMenu.add(innerManualHolder,BorderLayout.CENTER);


        //deletion button from list
        JLabel pointDelButtonText = getLabel("Delete point");

        JButton pointDelButton = new JButton();
        pointDelButton.add(pointDelButtonText);
        pointDelButton.setPreferredSize(new Dimension(50,80));

        pointDelButton.addActionListener(e -> {
            try{
                int index = list.getSelectedIndex();
                removePoint(list.getSelectedValue(),drawingPanel);
                buttonList.remove(index);
            }
            catch (NullPointerException ex){
                JOptionPane.showMessageDialog(null,"No point to be deleted.");
            }

        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(250,200));

        buttonPanel.add(pointDelButton,BorderLayout.NORTH);

        buttonPanel.add(createEndProgramButton(),BorderLayout.CENTER);

        UIMenu.add(buttonPanel,BorderLayout.SOUTH);

        return  UIMenu;
    }
    private JLabel getLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Jetbrains Mono", Font.PLAIN, 20));
        label.setForeground(Colors.text);
        return label;
    }
    private void removePoint(Point selectedPoint, JPanel drawingPanel) {
        dataContainer.interpolPoints.remove(selectedPoint);
        selectedPoint.set=false;
        drawingPanel.repaint();
    }

    private CanvasPanel createCanvas(Interpolation interpolator) {
        CanvasPanel canvasPanel = new CanvasPanel(screenWidth,screenHeight, dataContainer);
        canvasPanel.addMouseListener(new ClickListener(canvasPanel, dataContainer, interpolator));

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
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == 0) {
                File fileToSave = fileChooser.getSelectedFile();
                Export exp = new Export(screenWidth, screenHeight);
                exp.export(dataContainer.LastY,fileToSave.getAbsolutePath());
                JOptionPane.showMessageDialog(null, "Export has been saved.");
            }else{
                JOptionPane.showMessageDialog(null, "Not selected correctly.");
            }

        });

        canvasPanel.add(exportSVG);
        return canvasPanel;
    }
}
