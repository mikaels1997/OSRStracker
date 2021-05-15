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

        //new PlayerPanel("mmmmmmmmmmmm");
        //new PlayerPanel("lol");
        //new PlayerPanel("asd");
        //new PlayerPanel("hehe");
        

        mainFrame.setVisible(true);
    }

    public static void initPanels(){

        sidePanel = new JPanel();
        sidePanel.setBackground(Color.red);
        int height = GridYaxis();
        sidePanel.setLayout(new GridLayout(height,1));
        
        JScrollPane scroll = new JScrollPane(sidePanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVisible(true);
        
        new AddField(sidePanel);

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
                new PlayerPanel(player.strip());
            }
        }
        Main.sidePanel.revalidate();
        Main.sidePanel.repaint();
    }

    public static int GridYaxis() {

        int panelHeight = 9;
        if (AddField.playerNames == null) {
            return panelHeight;

        } else if (panelHeight >= AddField.playerNames.size()) {
            return panelHeight;

        } else {
            //int listLength = AddField.playerNames.size();
            return 0;
        }
    }
}
