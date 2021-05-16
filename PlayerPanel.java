import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class PlayerPanel implements ActionListener {

    /*An instance of this class if created whenever new player is added to follow list.
    The purpose of this class is to create player panel, which contains
    remove button and the button to check the stats of the player. */

    public String playerName;
    private JButton removeButton;
    private JToggleButton playerButton;
    private JPanel playerPanel;
    private static ButtonGroup playerButtonGroup = new ButtonGroup();

    public PlayerPanel(String name){

        this.playerName = name;

        playerPanel = new JPanel();

        playerButton = new JToggleButton();
        playerButton.setPreferredSize(new Dimension(180,50));
        playerButton.addActionListener(this);
        playerButton.setText(name);
        playerButton.setFont(new Font("Dialog", Font.PLAIN, 12));
        playerButton.setFocusable(false);
        playerButtonGroup.add(playerButton);
        

        //Adjusting the appearance of the button
        // playerButton.setFocusPainted(false);
        // playerButton.setMargin(new Insets(0, 0, 0, 0));
        // playerButton.setContentAreaFilled(false);
        // playerButton.setBorderPainted(false);
        // playerButton.setOpaque(false);
        // playerButton.setVisible(true);

        removeButton = new JButton();
        removeButton.setPreferredSize(new Dimension(50,50));
        removeButton.addActionListener(this);
        removeButton.setText("-");
        removeButton.setFont(new Font("Dialog", Font.PLAIN, 30));
        removeButton.setFocusable(false);

        //Adjusting the appearance of the button
        // removeButton.setFocusPainted(false);
        // removeButton.setMargin(new Insets(0, 0, 0, 0));
        // removeButton.setContentAreaFilled(false);
        // removeButton.setBorderPainted(false);
        // removeButton.setOpaque(false);
        // removeButton.setVisible(true);

        playerPanel.add(playerButton, BorderLayout.EAST);
        playerPanel.add(removeButton, BorderLayout.EAST);

        Main.sidePanel.add(playerPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("-")){
            
            // Remove player (-) button is pressed -> the player
            // and every update of that player is deleted

            AddField.playerNames.remove(playerName.toLowerCase());
            TxtFileHandler.removePlayer(playerName);

            Main.updateLayout();
            Main.sidePanel.remove(playerPanel);
            Main.skillPanel.removeAll();
            Main.sidePanel.revalidate();
            Main.sidePanel.repaint();
            Main.skillPanel.revalidate();
            Main.skillPanel.repaint();
        }
        else {
            // The name of the player is pressed -> stats of that player is displayed
            Main.skillPanel.removeAll();
            new SkillPanelButtons();
            new StatPanel(playerName, "total", 1);
        }
    }
}
