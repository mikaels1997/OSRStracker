
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class UpdateLog {

    /*An instance of this class is created when user presses "update log" button.
    The purpose of this class is to create a button for every update and 
    an action listener for the buttons*/

    public static List<String> dates;
    public static String name;

    public UpdateLog(String n, String[] d){
        dates = new ArrayList<String>(Arrays.asList(d));
        name = n;

        StatPanel.statPanel.setLayout(new GridLayout(9, 1));

        for(String date : dates){
            new UpdatePanel(date, name);

        }
        // JScrollPane scroll = new JScrollPane(StatPanel.statPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Main.mainFrame.getContentPane().add(scroll, BorderLayout.CENTER);
        // Main.mainFrame.revalidate();
        // Main.mainFrame.repaint();
    }


}
