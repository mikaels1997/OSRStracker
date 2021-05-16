import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Main {

    public static JFrame mainFrame;
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
        mainFrame.setVisible(true);
    }

    public static void initPanels(){

        sidePanel = new JPanel();
        sidePanel.setBackground(Color.red);
        sidePanel.setLayout(new GridLayout(9,1));
        
        JScrollPane scroll = new JScrollPane(sidePanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVisible(true);
        
        new AddField();

        skillPanel = new JPanel();
        skillPanel.setBackground(Color.yellow);
        skillPanel.setPreferredSize(new Dimension(100,100));
        skillPanel.setLayout(new BorderLayout());
        skillPanel.setVisible(true);

        mainFrame.add(scroll, BorderLayout.WEST);
        //mainFrame.add(skillscroll, BorderLayout.EAST);
        //mainFrame.add(sidePanel, BorderLayout.WEST);
        mainFrame.add(skillPanel, BorderLayout.CENTER);

        // Creates the player panels for already followed players
        initTrackedPlayers();
    }

    public static void initTrackedPlayers(){
        String[] players = TxtFileHandler.readPlayers();
        for(String player:players){
            if(!player.isEmpty()){
                AddField.playerNames.add(player.strip().toLowerCase());
                updateLayout();
                new PlayerPanel(player.strip());
            }
        }
        Main.sidePanel.revalidate();
        Main.sidePanel.repaint();
    }

    public static void updateLayout(){

        /*This function is called every time player panels are updated (removed or added)
        The purpose of this function is to avoid weird issues with the gridlayout of 
        side panel */

        if(AddField.playerNames.size() > 7){
            Main.sidePanel.setLayout(new GridLayout(0,1));   
        }
        if(AddField.playerNames.size() <= 7 ){
            Main.sidePanel.setLayout(new GridLayout(9,1));   
        }
    }
}
