import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.security.Timestamp;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;

public class StatPanel implements MouseInputListener {

    private String[] skills; // Skill names in certain order
    public static JPanel statPanel; // Panel containing either skill or update history
    public static JPanel infoPanel; // Panel containing the player name and timestamp
    public JPanel buttonPanel; // Panel containing the 4 buttons

    public String playerName; // Current name of the player on display
    public String state; // Current display state ("total", "progress", "update log")
    public int updateIndex; // Current update index 1 being the newest

    public static StatPanel current;

    public StatPanel(String n, String s, int u){

        /*Opens up a panel showing the stats of the player or update log and player name 
        and timestamp.
        An instance of this class is always created when the statpanel is opened/updated.
        The functions of this class is always called only when creating an instance
        name = the name of the player
        state = Either "total", "progress" state or "log" state
        updateIndex = which update is the user looking*/

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
            displayProgress(playerName, updateIndex);
        }
        if(state.equals("log")){
            showLog(playerName);
        }

        // Player name and timestamp
        String timeStamp = TxtFileHandler.getTimestamp(playerName, updateIndex);
        infoPanel = new JPanel(); // Contains player name and timestamp
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(60,60));
        JLabel nameLabel = new JLabel();
        JLabel timeLabel = new JLabel();
        nameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        timeLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        nameLabel.setText(playerName);

        if(this.state.equals("progress")){ // Shows two timestamps
            String prevTimeStamp = TxtFileHandler.getTimestamp(playerName, updateIndex+1);
            timeLabel.setText(timeStamp+" - "+prevTimeStamp);
        }
        else{
            timeLabel.setText(timeStamp);
        }

        infoPanel.add(nameLabel, BorderLayout.WEST);
        infoPanel.add(timeLabel, BorderLayout.EAST);

        //JScrollPane skillscroll = new JScrollPane(statPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //skillscroll.setVisible(true);

        //Main.skillPanel.add(skillscroll, BorderLayout.EAST);
        Main.skillPanel.add(statPanel, BorderLayout.CENTER);
        Main.skillPanel.add(infoPanel, BorderLayout.PAGE_START);

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
            label.setName(skill);
            label.addMouseListener(this);
            statPanel.add(label);

            statIndex += 1;
        }
    }

    private void displayProgress(String name, int updateIndex){

        statPanel.setLayout(new GridLayout(24,1));
        int statIndex = 0;

        // In diffs -string array skill statistics are separated by space
        String[] diffs = TxtFileHandler.calcProgress(name, updateIndex);

        for(String skill:skills){ 

            ImageIcon image = new ImageIcon(new ImageIcon("resources//"+skill+".png")
            .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(70,70));

            String rank = diffs[statIndex].split(" ")[0];
            String level = diffs[statIndex].split(" ")[1];
            String xp = diffs[statIndex].split(" ")[2];

            label.setText("Rank: "+rank+" Level: "+level+" Experience: "+xp);
            label.setFont(new Font("Dialog", Font.PLAIN, 20));
            label.setIcon(image);
            statPanel.add(label);

            statIndex += 1;
        }   
    }
    private void showLog(String name){
        statPanel = new JPanel(new GridLayout(0,1));
        String dates = TxtFileHandler.getUpdateDates(name);
        String[] dateArray = dates.split("\n");
        new UpdateLog(playerName, dateArray);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        JLabel hoveredlabel =(JLabel)e.getSource();
        String hoveredskill = hoveredlabel.getName(); 
        //System.out.println("entered " + hoveredskill);
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        JLabel hoveredlabel =(JLabel)e.getSource();
        String hoveredskill = hoveredlabel.getName(); 
        //System.out.println("exited " + hoveredskill);
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
