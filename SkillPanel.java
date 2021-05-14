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

public class SkillPanel {

    JPanel statPanel;
    JPanel buttonPanel;
    String playerName;
    String state;
    String[] skills;
    int updateIndex;

    public SkillPanel(String n, String s, int u){

        /*Opens up a panel showing the stats of the player or update log
        name = the name of the player
        state = Either "total", "progress" state or "log" state
        updateIndex = which update date is the user looking*/

        this.playerName = n;
        this.state = s;
        this.updateIndex = u;
        this.skills = new String[] {"attack", "attack", "defence","strength",
        "hitpoints", "archery", "prayer", "magic", "cooking", "woodcutting", "fletching",
        "fishing", "firemaking", "crafting", "smithing", "mining", "herblore", "agility",
        "thieving", "slayer", "farming", "runecrafting", "hunter", "construction"};

        statPanel = new JPanel();
        statPanel.setBackground(Color.green);
        //statPanel.setPreferredSize(new Dimension(50,50));

        if(state.equals("total")){
            showTotal(playerName, updateIndex);
        }
        if(state.equals("progress")){
            showTotal(playerName, updateIndex);
        }
        if(state.equals("log")){
            showTotal(playerName, updateIndex);
        }
        Main.skillPanel.add(statPanel, BorderLayout.CENTER);
        Main.skillPanel.revalidate();
        Main.skillPanel.repaint();
    }

    public void showTotal(String name, int updateIndex){
        statPanel.setLayout(new GridLayout(10,3));
        Border border = BorderFactory.createLineBorder(Color.black, 3);
        String[] stats = TxtFileHandler.readPlayerStats(playerName, 1);
        int statIndex = 1;
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
        System.out.println("TASSA"+stats[1]);
    }

    public void showProgress(String name, int updateIndex){

    }
    public void showLog(String name){

    }
}
