import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class StatPanel {

    private String[] skills; // Skill names in certain order
    public static JPanel statPanel; // Panel containing either skill or update history
    public JPanel buttonPanel; // Panel containing the 4 buttons

    public String playerName; // Current name of the player on display
    public String state; // Current display state ("total", "progress", "update log")
    public int updateIndex; // Current update index 1 being the newest

    public static StatPanel current;

    public StatPanel(String n, String s, int u){

        /*Opens up a panel showing the stats of the player or update log
        name = the name of the player
        state = Either "total", "progress" state or "log" state
        updateIndex = which update is the user looking
        The functions of this class is always called only when creating an instance*/

        current = this;

        this.playerName = n;
        this.state = s;
        this.updateIndex = u;
        this.skills = new String[] {"total", "attack", "defence","strength",
        "hitpoints", "archery", "prayer", "magic", "cooking", "woodcutting", "fletching",
        "fishing", "firemaking", "crafting", "smithing", "mining", "herblore", "agility",
        "thieving", "slayer", "farming", "runecrafting", "hunter", "construction"};

        statPanel = new JPanel();
        statPanel.setBackground(Color.green);

        if(state.equals("total")){
            displayTotal(playerName, updateIndex);
        }
        if(state.equals("progress")){
            displayTotal(playerName, updateIndex);
        }
        if(state.equals("log")){
            displayTotal(playerName, updateIndex);
        }
        Main.skillPanel.add(statPanel, BorderLayout.CENTER);
        Main.skillPanel.revalidate();
        Main.skillPanel.repaint();
    }

    private void displayTotal(String name, int updateIndex){

        /*Reads the skills of latest update from text file
        Displays the skill icons and corresponding leves in a grid*/

        statPanel.setLayout(new GridLayout(10,3));
        Border border = BorderFactory.createLineBorder(Color.black, 3);
        String[] stats = TxtFileHandler.readPlayerStats(playerName, 1);
        int statIndex = 1;

        // Loops through skills in the order determined in the "skills" -string array
        for(String skill:skills){ 

            String stat = stats[statIndex].split(",")[1];

            ImageIcon image = new ImageIcon(new ImageIcon("resources//"+skill+".png")
            .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(70,70));

            label.setText(stat);
            label.setFont(new Font("Dialog", Font.PLAIN, 20));
            label.setIcon(image);
            label.setBorder(border);
            statPanel.add(label);

            statIndex += 1;
        }
    }

    private void showProgress(String name, int updateIndex){

    }
    private void showLog(String name){

    }
}
