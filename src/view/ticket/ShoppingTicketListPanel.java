package view.ticket;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map.Entry;
import util.Tab;
import javax.swing.*;

import entity.ShoppingTicket;
import global.ShoppingTicketList;
import global.View;
import sql.SQLConnector;
import view.main.Panel;
import view.user.UserLoginPanel;
import view.user.UserPanel;
import util.Button;

public class ShoppingTicketListPanel extends JPanel implements ActionListener {
    ShoppingTicket shoppingTicket;
    Button checkButton;
    Button cancelButton;
    SQLConnector connector;

    public ShoppingTicketListPanel(Entry<String, ShoppingTicket> entry) {
        shoppingTicket = entry.getValue();
        setLayout(new BorderLayout());
        // setSize(300, 100);
        setBorder(BorderFactory.createLineBorder(new Color(238, 232, 170), 3));

        // 订单索引
        JLabel ticketIndex = new JLabel(entry.getKey().substring(0, 3) + "*");

        // 订单大致信息
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(238, 232, 170), 1));
        JLabel orderId = new JLabel(shoppingTicket.getOrderId());
        orderId.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel date = new JLabel(shoppingTicket.getDate().toString());
        date.setHorizontalAlignment(SwingConstants.RIGHT);
        mainPanel.add("North", orderId);
        mainPanel.add("South", date);

        // 对订单可采取的操作，包括查看和取消
        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new BorderLayout());
        checkButton = new Button("查看", new Color(0, 195, 0), Color.WHITE);
        cancelButton = new Button("取消", new Color(176, 196, 222), Color.BLACK);
        checkButton.addActionListener(this);
        cancelButton.addActionListener(this);
        choicePanel.add("North", checkButton);
        choicePanel.add("South", cancelButton);

        add("West", ticketIndex);
        add("Center", mainPanel);
        add("East", choicePanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object obj = e.getSource();
        // System.out.println("This is"+shoppingTicket.getOrderId());
        // 实现查看和删除订单的按钮功能
        if (obj.equals(checkButton)) {
            ImageIcon tabIcon = new ImageIcon("./src/img/close.png");
            tabIcon.setImage(tabIcon.getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
            JLabel tab = new JLabel();
            tab.setIcon(tabIcon);

            int totalTabCount = View.tabbedPane.getTabCount();
            View.tabbedPane.addTab("", new JPanel());
            View.tabbedPane.setTabComponentAt(totalTabCount,
                    new Tab("订单" + shoppingTicket.getOrderId().substring(0, 3)));
            View.tabbedPane.setComponentAt(totalTabCount, new ShoppingTicketPanel(shoppingTicket));

            View.tabbedPane.setSelectedIndex(totalTabCount);
            View.tabbedPane.updateUI();
            View.tabbedPane.repaint();
            System.out.println(totalTabCount);
        } else if (obj.equals(cancelButton)) {
            int num = JOptionPane.showConfirmDialog(this, "确定取消订单?", "warning", JOptionPane.YES_NO_OPTION);
            if (num == 0) {
                try {
                    // 从两个关联表取消订单
                    connector = new SQLConnector();
                    String result = connector.deleteShoppingList(UserLoginPanel.user.getUserID());
                    if (result.equals("取消成功")) {
                        // 取消成功后更新用户积分数据
                        connector = new SQLConnector();
                        connector.updateUser(UserLoginPanel.user.getUserID(),
                                UserLoginPanel.user.getPoint()+shoppingTicket.getTotalPrice());
                        ShoppingTicketList.shoppingTicketList.remove(shoppingTicket.getOrderId());
                        Panel.panel2.remove(Panel.userPane);
                        Panel.panel2.invalidate();
                        Panel.userPane = new UserPanel();
                        Panel.userPane.setPreferredSize(new Dimension(200, 0));
                        Panel.panel2.add("East", Panel.userPane);
                        Panel.panel2.updateUI();
                        Panel.panel2.repaint();
                    } else {
                        JOptionPane.showMessageDialog(this, result, "error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }

        }

    }
}
