import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class AddField implements ActionListener {


    JTextField playerTextField;
    public static List<String> playerNames;

    public AddField(JPanel sidePanel){

        playerNames = new ArrayList<String>();

        JPanel playerPanel = new JPanel();
         
        playerTextField = new JTextField("Add new player...", 15);
        playerTextField.setPreferredSize(new Dimension(180,50));

        JButton addButton = new JButton();
        addButton.setPreferredSize(new Dimension(50,50));
        addButton.addActionListener(this);
        addButton.setText("+");
        addButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        addButton.setFocusable(false);

        playerPanel.add(playerTextField);
        playerPanel.add(addButton);

        Main.sidePanel.add(playerPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add player button is pressed

        JFrame popup = new JFrame();
        
        String name = playerTextField.getText().toLowerCase();
        String results = URLparser.reqPlayerStats(name);

        if (results == null){ // player stats not found
            if (playerNames.contains(name.toLowerCase())){
                // player changed name or got banned
                JOptionPane.showMessageDialog(popup, name + "has changed nickname or been banned","Player status changed",JOptionPane.PLAIN_MESSAGE);
            }
            else{
                // player doesn't exist
                JOptionPane.showMessageDialog(popup, "Could not find stats for player named " + name,"Player not found",JOptionPane.PLAIN_MESSAGE);
            }
            // TODO, error popup
            return;
        }
        else { // Player stats found

            if (playerNames.contains(name.toLowerCase())){
                // User tries to follow the same player twice
                //TODO, error popup
                JOptionPane.showMessageDialog(popup, "you are already tracking " + name,"Already tracked",JOptionPane.PLAIN_MESSAGE);
            }
            else{
                // New player added to follow list

                playerNames.add(name);
                TxtFileHandler.updateCurrent(name, results.split(" "));
                TxtFileHandler.addPlayer(name);

                new PlayerPanel(playerTextField.getText());
                Main.sidePanel.revalidate();
                Main.sidePanel.repaint();
            }
        }
    }
    
}
