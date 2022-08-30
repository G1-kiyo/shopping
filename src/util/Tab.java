package util;

import javax.swing.*;

import entity.ShoppingTicket;
import global.View;

import java.awt.*;
import java.awt.event.*;

public class Tab extends JPanel implements MouseListener{
    JLabel title;
    JLabel close;
    ImageIcon tabIcon;
    // int index;
    public Tab(String tabTitle){
        setLayout(new GridLayout(1,2,3,3));
        // System.out.println(tabTitle);
        title = new JLabel(tabTitle);
        close = new JLabel();
        close.setHorizontalAlignment(SwingConstants.RIGHT);
        close.addMouseListener(this);

        this.add(title);
        this.add(close);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource().equals(close)){
            View.tabbedPane.remove(View.tabbedPane.indexOfTabComponent(this));
            View.tabbedPane.invalidate();
            View.tabbedPane.repaint();
            View.tabbedPane.validate();
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        tabIcon = new ImageIcon("./src/img/close.png");
        tabIcon.setImage(tabIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        
        
        // close.setText("×");
        close.setIcon(tabIcon);
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        close.setIcon(null);
    }


    
}
