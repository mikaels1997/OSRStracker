import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;



public class SkillPanelButtons implements ActionListener {


    private static ButtonGroup skillbuttonGroup = new ButtonGroup();
    /*Creates a panel with four buttons*/

    public SkillPanelButtons(){

        // Empty panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.red);
        buttonPanel.setLayout(new GridLayout(2,2));
        buttonPanel.setSize(new Dimension(50,50));
        buttonPanel.setVisible(true);

        // Refresh button
        JButton refreshButton = new JButton();
        refreshButton.setPreferredSize(new Dimension(50,50));
        refreshButton.addActionListener(this);
        refreshButton.setText("Refresh");
        refreshButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        refreshButton.setFocusable(false);

        // "Total" -state button
        JToggleButton totalButton = new JToggleButton();
        totalButton.setPreferredSize(new Dimension(50,50));
        totalButton.addActionListener(this);
        totalButton.setText("Total");
        totalButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        totalButton.setFocusable(false);

        // "Progress" -state button
        JToggleButton progressButton = new JToggleButton();
        progressButton.setPreferredSize(new Dimension(50,50));
        progressButton.addActionListener(this);
        progressButton.setText("Update log");
        progressButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        progressButton.setFocusable(false);

        // "Update log" -button
        JToggleButton logButton = new JToggleButton();
        logButton.setPreferredSize(new Dimension(50,50));
        logButton.addActionListener(this);
        logButton.setText("Progress");
        logButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        logButton.setFocusable(false);

        //skillbuttonGroup.add(refreshButton);
        skillbuttonGroup.add(totalButton);
        skillbuttonGroup.add(progressButton);
        skillbuttonGroup.add(logButton);

        buttonPanel.add(refreshButton);
        buttonPanel.add(totalButton);
        buttonPanel.add(progressButton);
        buttonPanel.add(logButton);

        totalButton.setSelected(true);

        Main.skillPanel.add(buttonPanel, BorderLayout.PAGE_END);
        Main.sidePanel.revalidate();
        Main.sidePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*One of the four buttons are pressed */

        String name = StatPanel.current.playerName;

        // Removes the current stats panel from the skillpanel
        Main.skillPanel.remove(StatPanel.statPanel);
        Main.skillPanel.remove(StatPanel.infoPanel);
        Main.skillPanel.revalidate();
        Main.skillPanel.repaint();

        if(e.getActionCommand().equals("Refresh")){
            // Updates the stats of the player, stores them into txt file 
            // displays the latest stats in "total" -mode

            String results = URLparser.reqPlayerStats(name);
            TxtFileHandler.updateCurrent(name, results.split(" "));
            skillbuttonGroup.clearSelection();
            new StatPanel(name, "total", 1);
        }
        if(e.getActionCommand().equals("Total")){
            // Displays the latest stats of the player in "total" mode
            new StatPanel(name, "total", StatPanel.current.updateIndex);
        }
        if(e.getActionCommand().equals("Progress")){
            // Displays the stats in "progress" mode
            new StatPanel(name, "progress", StatPanel.current.updateIndex);
        }  
        if(e.getActionCommand().equals("Update log")){
            // Displays the update history and their timestamps
            new StatPanel(name, "log", StatPanel.current.updateIndex);
        }   
    }
}
