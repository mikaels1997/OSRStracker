import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Main {

    private static JFrame mainFrame;
    public static JPanel sidePanel;
    public static JPanel skillPanel;

    public static void main(String[] args){

        mainFrame = new JFrame();
        mainFrame.setTitle("Test");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(900, 600);
        mainFrame.getContentPane().setBackground(new Color(105,105,105));;
        initPanels();

        new PlayerPanel("mmmmmmmmmmmm");
        new PlayerPanel("lol");
        new PlayerPanel("asd");
        new PlayerPanel("hehe");
        new PlayerPanel("hehe2");
        new PlayerPanel("hehe3");
        new PlayerPanel("hehe4");
        new PlayerPanel("hehe5");
        new PlayerPanel("hehe6");
        new PlayerPanel("hehe7");
        new PlayerPanel("hehe8");
        new PlayerPanel("hehe9");
        new PlayerPanel("hehe10");

        mainFrame.setVisible(true);
    }

    public static void initPanels(){

        sidePanel = new JPanel();
        sidePanel.setBackground(Color.red);
        sidePanel.setLayout(new GridLayout(0, 1));
        
        JScrollPane scroll = new JScrollPane(sidePanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVisible(true);
        
        new AddField(sidePanel);

        skillPanel = new JPanel();
        skillPanel.setBackground(Color.yellow);
        skillPanel.setPreferredSize(new Dimension(100,100));
        skillPanel.setLayout(new BorderLayout());
        //JTextArea textarea = new JTextArea(100,100);
        //JScrollPane scroll = new JScrollPane(textarea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //skillPanel.add(scroll);
        skillPanel.setVisible(true);

        mainFrame.add(scroll, BorderLayout.WEST);
        //mainFrame.add(sidePanel, BorderLayout.WEST);
        mainFrame.add(skillPanel, BorderLayout.CENTER);
    }
}
