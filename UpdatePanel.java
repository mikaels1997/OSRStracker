import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UpdatePanel implements ActionListener {
    
    private String name;
    private String date;
    private JPanel updatePanel = new JPanel();

    public UpdatePanel(String date, String n){
        
        this.name = n;
        this.date = date;

        JButton updateButton = new JButton();
        updateButton.setPreferredSize(new Dimension(180, 30));
        updateButton.setText(date);
        updateButton.addActionListener(this);
        updateButton.setFocusable(false);
        StatPanel.statPanel.add(updateButton);  

        JButton removeUpdateButton = new JButton();
        removeUpdateButton.setPreferredSize(new Dimension(70,30));
        removeUpdateButton.setText("DEL");
        removeUpdateButton.addActionListener(this);
        removeUpdateButton.setFocusable(false);

        updatePanel.add(updateButton, BorderLayout.EAST);
        updatePanel.add(removeUpdateButton, BorderLayout.EAST);

        StatPanel.statPanel.add(updatePanel);  
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*If the user presses one of the updates, an instance of StatPanel is created
        lika normally. */

        if(e.getActionCommand().equals("DEL")){      
            // Remove update (DEL) button is pressed -> that update is deleted

            TxtFileHandler.removeUpdate(name, UpdateLog.dates.indexOf(date)+1);

            StatPanel.statPanel.remove(updatePanel);

            // Updating the update indexes
            UpdateLog.dates.remove(UpdateLog.dates.indexOf(date));

            // In case user goes to total or progress state straight from update log
            // and the deleted update was selected
            StatPanel.updateIndex = 1;

            StatPanel.statPanel.revalidate();
            StatPanel.statPanel.repaint();
        }
        else{
            // The timestamp of the update is pressed -> "total" -state from that update

            String selectedDate = e.getActionCommand();
            int i = UpdateLog.dates.indexOf(selectedDate)+1; // Selected update index
            Main.skillPanel.removeAll();
            new SkillPanelButtons();

            // The display will always be in "total" state in this case
            new StatPanel(name, "total", i);
        }
    }
    
}
