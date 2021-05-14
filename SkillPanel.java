import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class SkillPanel {

    JPanel statPanel;
    JPanel buttonPanel;

    public SkillPanel(String name, String state, int updateIndex){
        /*Opens up the skill panel.
        name = the name of the player
        state = Either "total", or "progress" state
        updateIndex = which update date is the user looking*/

        statPanel = new JPanel();
        statPanel.setBackground(Color.green);
        statPanel.setPreferredSize(new Dimension(50,50));

        Main.skillPanel.add(statPanel, BorderLayout.NORTH);
        Main.skillPanel.revalidate();
        Main.skillPanel.repaint();
    }
}
