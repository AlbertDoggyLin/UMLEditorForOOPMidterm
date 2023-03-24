package app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Map;

public class GUI extends JFrame {
    public JMenu editMenu;
    public Canvas canvas;
    private Box leftPanel;
    public GUI(){
        super("UML editor Albert Version");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setMinimumSize(new Dimension(900, 900));
        JMenuBar menuBar = new JMenuBar();
        getContentPane().add(menuBar, BorderLayout.NORTH);
        JMenu menu1 = new JMenu("FILE");
        menuBar.add(menu1);

        JMenu menu2 = editMenu = new JMenu("Edit");
        menuBar.add(menu2);
        Box mainArea = Box.createHorizontalBox();
        getContentPane().add(mainArea, BorderLayout.CENTER);
        mainArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Box leftPanel = this.leftPanel = Box.createVerticalBox();
        mainArea.add(leftPanel);

        JLayeredPane canvas = this.canvas = new Canvas();
        mainArea.add(canvas);
        canvas.setBackground(Color.white);
        canvas.setLayout(null);
        canvas.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    public void setupButtons(AbstractList<Map.Entry<String, Runnable>> buttonsInfo){
        for(Map.Entry<String, Runnable> entry : buttonsInfo){
            JButton button;
            try {
                button = new JButton(new ImageIcon(
                        ImageIO.read(new File("resource/"+entry.getKey())).getScaledInstance(80, 80, Image.SCALE_SMOOTH)
                ));
            } catch (IOException ioException) {
                button = new JButton(entry.getKey());
                System.out.println("failed to load picture from path: resource/" +entry.getKey());
            }
            button.addActionListener(e->{
                assert e.getSource() instanceof JButton;
                entry.getValue().run();
                repaint();
            });
            JPanel container = new JPanel();
            container.add(button);
            setButtonGUI(button, container);
            leftPanel.add(container);
        }
    }

    public void setupEditMenu(AbstractList<Map.Entry<String, Runnable>> menuInfo){
        for(Map.Entry<String, Runnable> entry : menuInfo){
            JMenuItem menuItem = new JMenuItem(entry.getKey());
            menuItem.addActionListener(e->entry.getValue().run());
            editMenu.add(menuItem);
        }
        revalidate();
        setVisible(true);
    }

    private void setButtonGUI(JButton button, JPanel container) {
        container.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
        // container.setBackground(Color.BLUE);
        container.setSize(100, 100);
        container.setMaximumSize(new Dimension(100, 120));

        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        button.addMouseListener(new ButtonGroupEffect(button));
    }
}
