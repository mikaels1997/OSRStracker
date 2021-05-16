import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Main {

    public static JFrame mainFrame;
    public static JPanel sidePanel;
    public static JPanel skillPanel;

    public static void main(String[] args){

        // Initializing main frame
        mainFrame = new JFrame();
        mainFrame.setTitle("OSRS StatTracker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(900, 600);
        mainFrame.getContentPane().setBackground(new Color(105,105,105));;
        initPanels();
        mainFrame.setVisible(true);
    }

    public static void initPanels(){

        /*Initializes the two main panels of the frame: sidePanel and skillPanel
        Side panel includes the add button and the playerpanels.
        Skill panel includes the infopanel (name and timestamp), stats and
        buttonPanel (4 buttons) */

        sidePanel = new JPanel();
        sidePanel.setBackground(new Color(105,105,105));
        sidePanel.setLayout(new GridLayout(9,1));
        
        //Scroll bar
        JScrollPane scroll = new JScrollPane(sidePanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVisible(true);
        
        // Text field and the button for player adding
        new AddField();

        skillPanel = new JPanel();
        skillPanel.setBackground(new Color(105,105,105));
        skillPanel.setPreferredSize(new Dimension(100,100));
        skillPanel.setLayout(new BorderLayout());
        skillPanel.setVisible(true);

        mainFrame.add(scroll, BorderLayout.WEST);
        mainFrame.add(skillPanel, BorderLayout.CENTER);

        // Creates the player panels for already followed players
        initTrackedPlayers();
    }

    public static void initTrackedPlayers(){

        /*At launch the tracked players are read from previous sessions */

        String[] players = TxtFileHandler.readPlayers();
        for(String player:players){
            if(!player.isEmpty()){
                AddField.playerNames.add(player.strip().toLowerCase());
                updateLayout();
                new PlayerPanel(player.strip());
                continue;
            }
            infoText();
        }
        Main.sidePanel.revalidate();
        Main.sidePanel.repaint();
    }

    public static void updateLayout(){

        /*This function is called every time player panels are updated (removed or added)
        The purpose of this function is to avoid weird issues with the gridlayout and 
        scroll bar of side panel */

        if(AddField.playerNames.size() > 7){
            Main.sidePanel.setLayout(new GridLayout(0,1));   
        }
        if(AddField.playerNames.size() <= 7 ){
            Main.sidePanel.setLayout(new GridLayout(9,1));   
        }
    }

    public static void infoText(){

        /*Informs the user if he has not followed any players */

        JTextArea text = new JTextArea();
        text.setFont(new Font("Dialog", Font.PLAIN, 20));
        text.setText("You have not followed any players. Write the name of the player you want to track by typing the name to the \"Add new player...\" -field");
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        text.setFocusable(false);
        skillPanel.add(text, BorderLayout.CENTER);
    }
}
