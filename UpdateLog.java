import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


public class UpdateLog implements ActionListener {

    public static String[] dates;
    public static String name;

    public UpdateLog(String n, String[] d){
        dates = d;
        name = n;

        for(String date : dates){
            JButton updateButton = new JButton();
            updateButton.setText(date);
            updateButton.addActionListener(this);
            StatPanel.statPanel.add(updateButton);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedDate = e.getActionCommand();
        int i = Arrays.asList(dates).indexOf(selectedDate)+1;
        Main.skillPanel.removeAll();
        new SkillPanelButtons();
        new StatPanel(name, "total", i);
    }
}
