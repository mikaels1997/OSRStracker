import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.awt.MouseInfo;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;

public class StatPanel implements MouseInputListener {

    private String[] skills; // Skill names in certain order
    public static JPanel statPanel; // Panel containing either skills or update history
    public static JPanel infoPanel; // Panel containing the player name and timestamp
    public JPanel buttonPanel; // Panel containing the 4 buttons

    public static String playerName; // Current name of the player on display
    public String state; // Current display state ("total", "progress", "update log")
    public static int updateIndex; // Current update index 1 being the newest

    public static Popup popup;

    public StatPanel(String n, String s, int u){

        /*Opens up a panel showing the stats of the player or update log and player name 
        and timestamp.
        An instance of this class is always created when the statpanel is opened/updated.
        The functions of this class is always called only when creating an instance
        name = the name of the player
        state = Either "total", "progress" state or "log" state
        updateIndex = which update is the user looking*/

        this.playerName = n;
        this.state = s;
        this.updateIndex = u;
        this.skills = new String[] {"total", "attack", "defence","strength",
        "hitpoints", "archery", "prayer", "magic", "cooking", "woodcutting", "fletching",
        "fishing", "firemaking", "crafting", "smithing", "mining", "herblore", "agility",
        "thieving", "slayer", "farming", "runecrafting", "hunter", "construction"};

        statPanel = new JPanel();
        statPanel.setBackground(Color.gray);

        if(state.equals("total")){
            displayTotal(updateIndex);
        }
        if(state.equals("progress")){
            displayProgress(updateIndex);
        }
        if(state.equals("log")){
            showLog();
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
            timeLabel.setText("From " + prevTimeStamp+" to "+timeStamp);
        }
        else{
            if(updateIndex == 1){ // Newest update
                timeLabel.setText("(Newest) "+timeStamp);
            }
            else{
                timeLabel.setText(timeStamp);
            }
        }

        infoPanel.add(nameLabel, BorderLayout.WEST);
        infoPanel.add(timeLabel, BorderLayout.EAST);

        Main.skillPanel.add(statPanel, BorderLayout.CENTER);
        Main.skillPanel.add(infoPanel, BorderLayout.PAGE_START);

        Main.skillPanel.revalidate();
        Main.skillPanel.repaint();
    }

    private void displayTotal(int updateIndex){

        /*Reads the skills of requested update from text file
        Displays the skill icons and corresponding leves in a grid*/

        statPanel.setLayout(new GridLayout(10,3));
        Border border = BorderFactory.createLineBorder(Color.black, 3);

        String[] stats = TxtFileHandler.readPlayerStats(playerName, updateIndex);
        int statIndex = 1;

        if(stats.length == 1){ // No updates are done -> informing the user
            statPanel.setLayout(new BorderLayout());
            informRefresh();
            return;
        }

        // Loops through skills in the order determined in the "skills" -string array
        // Adds information and graphics for every skill to the grid
        for(String skill:skills){ 

            // Only the level is initially shown in "total" state
            String stat = stats[statIndex].split(",")[1];

            // Fetches the image for every skill
            ImageIcon image = new ImageIcon(new ImageIcon("resources//"+skill+".png")
            .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            JLabel skillLabel = new JLabel();
            skillLabel.setPreferredSize(new Dimension(70,70));

            skillLabel.setText(stat);
            skillLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
            skillLabel.setIcon(image);
            skillLabel.setBorder(border);
            skillLabel.setName(skill);
            skillLabel.addMouseListener(this);
            statPanel.add(skillLabel);

            statIndex += 1;
        }
    }

    private void displayProgress(int updateIndex){

        /*Displays the skill in "progress" mode.
        The progression is determined by comparing the rank, level and
        experience of the player from current selected update to its
        previous update. If there's no previous updates, a text label is shown.
        Returns false if no progress can be shown.*/

        statPanel.setLayout(new GridLayout(12,2));
        int statIndex = 0;

        // In diffs -string array skill statistics are separated by space
        String[] diffs = TxtFileHandler.calcProgress(playerName, updateIndex);
        if(diffs.length == 1){
            // First update; no progress can be shown
            statPanel.setLayout(new BorderLayout());
            firstUpdateInfo();
            return;
        }

        // Loops through skills in the order determined in the "skills" -string array
        // Adds information of every skill to the grid
        for(String skill:skills){ 

            ImageIcon image = new ImageIcon(new ImageIcon("resources//"+skill+".png")
            .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(70,70));

            // In progress mode every statistic is shown initially unlike in total mode
            String rank = diffs[statIndex].split(" ")[0];
            String level = diffs[statIndex].split(" ")[1];
            String xp = diffs[statIndex].split(" ")[2];

            label.setText("Rank: "+rank+" Lvl: "+level+" Xp: "+xp);
            label.setFont(new Font("Dialog", Font.PLAIN, 20));
            label.setIcon(image);
            statPanel.add(label);

            statIndex += 1;
        }   
    }

    private void showLog(){

        /*Displays the update log.
        Update log consists of all the timestamps of the updates of the selected player.
        Update index as a parameter to store the previously selected update in case
        the user switches from update log to "total" or "progress" mode without selecting
        an update*/

        String dates = TxtFileHandler.getUpdateDates(playerName);
        String[] dateArray = dates.split("\n");

        if(dates.length() == 0){
            // Informs the user to press refresh
            statPanel.setLayout(new BorderLayout());
            informRefresh();
            return;
        }

        new UpdateLog(playerName, dateArray);
    }

    private void firstUpdateInfo(){
        JTextArea text = new JTextArea();
        text.setFont(new Font("Dialog", Font.PLAIN, 20));
        text.setText("The selected update was the first update or there's no updates at all; therefore no progress can be shown");
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        text.setFocusable(false);
        statPanel.add(text);
    }

    private void informRefresh(){
        JTextArea text = new JTextArea();
        text.setFont(new Font("Dialog", Font.PLAIN, 20));
        text.setText("Press refresh to start tracking!");
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        text.setFocusable(false);
        statPanel.add(text);
    }

    private void showPopup(JLabel label, String skillname){


        Point mouseCoordinates;
        mouseCoordinates = MouseInfo.getPointerInfo().getLocation();
        JPanel p = new JPanel();

        //Fetching the data from certain skill
        int skillIndex = Arrays.asList(skills).indexOf(skillname);
        String[] textToShow = TxtFileHandler.readCertainSkill(playerName, skillIndex, updateIndex);

        p.add(new JLabel("<html>" + skillname + "<br/>" +"Rank: "+textToShow[0]+ "<br/>" + "Level: "+textToShow[1]+ "<br/>" + "Xp: "+textToShow[2] + "</html>"));
        PopupFactory pf = PopupFactory.getSharedInstance();
        popup = pf.getPopup(label, p, mouseCoordinates.x + 2, mouseCoordinates.y - 2);
        popup.show();
    }

    private void hidePopup(){
        popup.hide();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel hoveredlabel =(JLabel)e.getSource();
        String hoveredskill = hoveredlabel.getName();
        showPopup(hoveredlabel, hoveredskill);     
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hidePopup();

        
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}
