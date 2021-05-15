import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;



public class SkillPanelButtons implements ActionListener {

    /*Creates a panel with four buttons*/

    public SkillPanelButtons(){

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.red);
        buttonPanel.setLayout(new GridLayout(2,2));
        buttonPanel.setSize(new Dimension(50,50));
        buttonPanel.setVisible(true);

        JButton refreshButton = new JButton();
        refreshButton.setPreferredSize(new Dimension(50,50));
        refreshButton.addActionListener(this);
        refreshButton.setText("Refresh");
        refreshButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        refreshButton.setFocusable(false);

        JButton totalButton = new JButton();
        totalButton.setPreferredSize(new Dimension(50,50));
        totalButton.addActionListener(this);
        totalButton.setText("Total");
        totalButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        totalButton.setFocusable(false);

        JButton progressButton = new JButton();
        progressButton.setPreferredSize(new Dimension(50,50));
        progressButton.addActionListener(this);
        progressButton.setText("Update log");
        progressButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        progressButton.setFocusable(false);

        JButton logButton = new JButton();
        logButton.setPreferredSize(new Dimension(50,50));
        logButton.addActionListener(this);
        logButton.setText("Progress");
        logButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        logButton.setFocusable(false);

        buttonPanel.add(refreshButton);
        buttonPanel.add(totalButton);
        buttonPanel.add(progressButton);
        buttonPanel.add(logButton);

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
            new StatPanel(name, "log", -1);
        }   
    }
}
