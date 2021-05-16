
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class UpdateLog implements ActionListener {

    /*An instance of this class is created when user presses "update log" button.
    The purpose of this class is to create a button for every update and 
    an action listener for the buttons*/

    public static String[] dates;
    public static String name;

    public UpdateLog(String n, String[] d){
        dates = d;
        name = n;

        StatPanel.statPanel.setLayout(new GridLayout(9, 1));

        for(String date : dates){
            JButton updateButton = new JButton();
            updateButton.setPreferredSize(new Dimension(100,100));
            updateButton.setText(date);
            updateButton.addActionListener(this);
            updateButton.setFocusable(false);
            StatPanel.statPanel.add(updateButton);  

        }
        // JScrollPane scroll = new JScrollPane(StatPanel.statPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Main.mainFrame.getContentPane().add(scroll, BorderLayout.CENTER);
        // Main.mainFrame.revalidate();
        // Main.mainFrame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*If the user presses one of the updates, an instance of StatPanel is created
        lika normally. */

        String selectedDate = e.getActionCommand();
        int i = Arrays.asList(dates).indexOf(selectedDate)+1;
        Main.skillPanel.removeAll();
        new SkillPanelButtons();

        // The display will always be in "total" state in this case
        new StatPanel(name, "total", i);
    }
}
