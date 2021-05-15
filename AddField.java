import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class AddField implements ActionListener {

    /*An instance of this class is created only once in Main since there's always
    possibility of adding a new player.
    The purpose of this class is to add text field and "Add new" -button for the
    sidePanel which is created in Main */

    JTextField nameTextField;
    public static List<String> playerNames;

    public AddField(){

        playerNames = new ArrayList<String>();

        JPanel addNewPanel = new JPanel();
         
        nameTextField = new JTextField("Add new player...", 15);
        nameTextField.setPreferredSize(new Dimension(180,50));

        JButton addButton = new JButton();
        addButton.setPreferredSize(new Dimension(50,50));
        addButton.addActionListener(this);
        addButton.setText("+");
        addButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        addButton.setFocusable(false);

        addNewPanel.add(nameTextField);
        addNewPanel.add(addButton);

        Main.sidePanel.add(addNewPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // "Add player" button is pressed

        String name = nameTextField.getText().toLowerCase();
        String results = URLparser.reqPlayerStats(name);

        if (results == null){ // player stats not found
            if (playerNames.contains(name.toLowerCase())){
                // player changed name or got banned (no easy way to check this unfortunately)
                JOptionPane.showMessageDialog(Main.skillPanel, "Player <"+name+"> has changed nickname or been banned","Player status changed",JOptionPane.PLAIN_MESSAGE);
            }
            else{
                // player doesn't exist
                JOptionPane.showMessageDialog(Main.skillPanel, "Could not find stats for player: <" + name+">"
                ,"Player not found",JOptionPane.PLAIN_MESSAGE);
            }
            return;
        }
        else { // Player stats found

            if (playerNames.contains(name.toLowerCase())){
                // User tries to follow the same player twice
                JOptionPane.showMessageDialog(Main.skillPanel, "Player <"+name+"> is already on follow list" 
                ,"Already tracked",JOptionPane.PLAIN_MESSAGE);
            }
            else{
                // New player added to follow list
                
                
                playerNames.add(name);
                TxtFileHandler.updateCurrent(name, results.split(" "));
                TxtFileHandler.addPlayer(name);

                new PlayerPanel(nameTextField.getText());
                Main.sidePanel.revalidate();
                Main.sidePanel.repaint();
            }
        }
    }
    
}
