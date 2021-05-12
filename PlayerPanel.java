import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Insets;

public class PlayerPanel implements ActionListener {

    public String playerName;
    private PlayerPanel current;
    private JButton removeButton;
    private JButton playerButton;
    private JPanel playerPanel;

    public PlayerPanel(String name){

        current = this;

        this.playerName = name;

        playerPanel = new JPanel();

        playerButton = new JButton();
        //playerButton.setBounds(100, 100, 250, 100);
        playerButton.setPreferredSize(new Dimension(180,50));
        playerButton.addActionListener(this);
        playerButton.setText(name);
        playerButton.setFont(new Font("Dialog", Font.PLAIN, 12));
        playerButton.setFocusable(false);

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
            Main.sidePanel.remove(playerPanel);
            Main.sidePanel.revalidate();
            Main.sidePanel.repaint();
        }
    }
}