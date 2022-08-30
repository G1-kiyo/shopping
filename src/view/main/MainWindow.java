package view.main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import sql.SQLConnector;
import view.user.UserLoginPanel;

public class MainWindow extends JFrame implements WindowListener {

    SQLConnector connector;

    // 构造器
    public MainWindow() {
        // 标题
        super("在线购物平台");
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        // setSize(1400,600);
        setPreferredSize(new Dimension(1100, 600));
        pack();

        setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (UserLoginPanel.user == null) {
            System.exit(0);
        } else {
            connector = new SQLConnector();
            String result = connector.updateUser(UserLoginPanel.user.getUserID(), UserLoginPanel.user.getPoint());
            if (result.equals("更新成功")) {
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(this, "上传数据失败，请稍后重试", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }
}
