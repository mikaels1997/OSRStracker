import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;



public class SkillPanelButtons implements ActionListener {

    /*Creates a panel with four buttons*/

    public SkillPanelButtons(){

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.red);
        buttonPanel.setLayout(new GridLayout(2,2));
        buttonPanel.setSize(new Dimension(50,50));
        buttonPanel.setVisible(true);

        JButton refreshButton = new JButton();
        refreshButton.setPreferredSize(new Dimension(50,50));
        refreshButton.addActionListener(this);
        refreshButton.setText("Refresh");
        refreshButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        refreshButton.setFocusable(false);

        JButton totalButton = new JButton();
        totalButton.setPreferredSize(new Dimension(50,50));
        totalButton.addActionListener(this);
        totalButton.setText("Total");
        totalButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        totalButton.setFocusable(false);

        JButton progressButton = new JButton();
        progressButton.setPreferredSize(new Dimension(50,50));
        progressButton.addActionListener(this);
        progressButton.setText("Update log");
        progressButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        progressButton.setFocusable(false);

        JButton logButton = new JButton();
        logButton.setPreferredSize(new Dimension(50,50));
        logButton.addActionListener(this);
        logButton.setText("Progress");
        logButton.setFont(new Font("Dialog", Font.PLAIN, 27));
        logButton.setFocusable(false);

        buttonPanel.add(refreshButton);
        buttonPanel.add(totalButton);
        buttonPanel.add(progressButton);
        buttonPanel.add(logButton);

        Main.skillPanel.add(buttonPanel, BorderLayout.PAGE_END);
        Main.sidePanel.revalidate();
        Main.sidePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Refresh")){
        }
        if(e.getActionCommand().equals("Total")){
        }
        if(e.getActionCommand().equals("Update Log")){
        }
        if(e.getActionCommand().equals("Progress")){
        }      
    }
}
