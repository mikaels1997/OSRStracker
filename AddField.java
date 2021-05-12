import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddField implements ActionListener {

    JTextField playerTextField;

    public AddField(JPanel sidePanel){

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
        String results = URLparser.reqPlayerStats(playerTextField.getText());
        System.out.println(results);
        new PlayerPanel(playerTextField.getText());
        Main.sidePanel.revalidate();
        Main.sidePanel.repaint();
    }
    
}
