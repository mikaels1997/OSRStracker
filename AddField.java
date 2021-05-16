import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class AddField implements ActionListener {

    /*An instance of this class is created only once in Main since there's always
    possibility of adding a new player.
    The purpose of this class is to create text field and button to add players to the
    sidePanel which is created in Main */

    JTextField nameTextField;
    public static List<String> playerNames;

    public AddField(){

        playerNames = new ArrayList<String>();

        JPanel addNewPanel = new JPanel();
         
        // Text field
        nameTextField = new JTextField("Add new player...", 16);
        nameTextField.setPreferredSize(new Dimension(180,51));

        // Add button
        JButton addButton = new JButton();
        addButton.setPreferredSize(new Dimension(50,50));
        addButton.addActionListener(this);
        addButton.setText("+");
        addButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        addButton.setFocusable(false);

        // Adding text field and button to a panel
        addNewPanel.add(nameTextField);
        addNewPanel.add(addButton);

        Main.sidePanel.add(addNewPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // "Add player" button is pressed

        // Getting the text from field and requesting stats from URL
        String name = nameTextField.getText().toLowerCase();
        String results = URLparser.reqPlayerStats(name);

        if (results == null){ // player stats not found
            if (playerNames.contains(name.toLowerCase())){
                // player changed name or got banned (no easy way to rename the player unfortunately)
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
                Main.updateLayout(); // To make scrollbar work
                TxtFileHandler.updateCurrent(name, results.split(" ")); // Updating to txtfile
                TxtFileHandler.addPlayer(name); // Adding player to player.txt

                new PlayerPanel(nameTextField.getText()); // Creating panel for the player
                Main.sidePanel.revalidate();
                Main.sidePanel.repaint();
            }
        }
    }
    
}
