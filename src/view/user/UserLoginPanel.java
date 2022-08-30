package view.user;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import view.main.Panel;
import entity.User;
import global.View;
import sql.SQLConnector;
import util.Button;

public class UserLoginPanel extends JPanel implements ActionListener {
    Button loginButton;
    Button registerButton;
    String userID = null;
    String password = null;
    JLabel accountLabel;
    JLabel passwordLabel;
    TextField accountTextField;
    TextField passwordTextField;
    SQLConnector connector;
    // 保存当前用户信息
    public static User user;

    public UserLoginPanel() {
        setLayout(new BorderLayout());
        // setbounds、setlocation与setsize只能在设置layout时使用
        // 登录的header
        JLabel titleLabel = new JLabel("登录");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // 填写表单主要内容的panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        System.out.println(mainPanel.getPreferredSize());
        JPanel accountPanel = addInfoPanel("账号", 0, 50);
        JPanel passwordPanel = addInfoPanel("密码", 0, 50);

        mainPanel.add("North", accountPanel);
        mainPanel.add("South", passwordPanel);

        // 登录和注册的按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        loginButton = new Button("登录", new Color(0, 195, 0), Color.WHITE);
        registerButton = new Button("注册", new Color(138, 43, 226), Color.WHITE);
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        // buttonPanel.add("West", loginButton);
        // buttonPanel.add("East", registerButton);

        // Button registerButton = new Button("注册",new Color(138, 43, 226),Color.WHITE);

        // borderlayout会将剩余空间全部分给center
        this.add("North", titleLabel);
        this.add("Center", mainPanel);
        this.add("South", buttonPanel);
    }

    public JPanel addInfoPanel(String labelName, int width, int height) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        JLabel titleLabel = new JLabel(labelName);
        if (labelName.equals("账号")) {
            accountTextField = new TextField(25);
            panel.add(titleLabel);
            panel.add(accountTextField);
        } else if (labelName.equals("密码")) {
            passwordTextField = new TextField(25);
            panel.add(titleLabel);
            panel.add(passwordTextField);
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object source = e.getSource();

        // 登录，检查数据库有无用户数据
        userID = accountTextField.getText();
        password = passwordTextField.getText();
        if (source.equals(loginButton)) {
            if (userID == "" || password == "") {
                JOptionPane.showMessageDialog(this, "账号或密码不能为空", "warning", JOptionPane.INFORMATION_MESSAGE);
            } else {

                try {
                    connector = new SQLConnector();
                    user = connector.selectUser(userID);
                    // System.out.println("this is"+user);
                    if (user != null) {

                        if (user.getPassword().equals(password)) {
                            // 查询所有商品列表以及用户订单列表

                            try {
                                connector = new SQLConnector();
                                connector.selectItem();
                                connector = new SQLConnector();
                                connector.selectShoppingTicket(user.getUserID());
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }

                            // 登录成功，移除原有的登录组件，添加新的panel
                            View.contentPane.removeAll();
                            View.contentPane.invalidate();
                            View.tabbedPane.add("首页", new Panel());
                            View.contentPane.setLayout(new BorderLayout());
                            View.contentPane.add("Center", View.tabbedPane);
                            View.contentPane.repaint();
                            View.contentPane.validate();

                        } else {
                            JOptionPane.showMessageDialog(this, "密码不正确", "error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "账号不存在", "error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else if (source.equals(registerButton)) {
            // 注册，检查用户是否已经存在
            if (userID == "" || password == "") {
                JOptionPane.showMessageDialog(this, "账号或密码不能为空", "warning", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    SQLConnector connector = new SQLConnector();
                    user = connector.selectUser(userID);
                    // System.out.println("test"+user.getPassword());
                    if (user != null) {
                        JOptionPane.showMessageDialog(this, "该账号已注册", "warning", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        try {
                            SQLConnector connector2 = new SQLConnector();
                            String result = connector2.insertUser(userID, password);

                            if (result.equals("注册成功")) {
                                try {
                                    SQLConnector getItemConnector = new SQLConnector();
                                    getItemConnector.selectItem();
                                    SQLConnector getUserConnector = new SQLConnector();
                                    user = getUserConnector.selectUser(userID);

                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }

                                View.contentPane.removeAll();
                                View.contentPane.invalidate();
                                View.tabbedPane.add("首页", new Panel());
                                View.contentPane.setLayout(new BorderLayout());
                                View.contentPane.add("Center", View.tabbedPane);
                                View.contentPane.repaint();
                                View.contentPane.validate();
                                // View.app.pack();
                                // View.app.setVisible(true);;
                            } else {
                                JOptionPane.showMessageDialog(this, result, "error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
