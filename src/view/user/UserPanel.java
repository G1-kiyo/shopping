package view.user;


import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import entity.ShoppingTicket;
import global.ShoppingTicketList;
import sql.SQLConnector;
import util.Button;
import view.ticket.ShoppingTicketListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map.Entry;

public class UserPanel extends JTabbedPane implements ActionListener{
    // 保存当前订单信息
    public static JScrollPane orderPanel;
    // 连接数据库
    SQLConnector connector;
    public UserPanel() {
        JPanel userInfoPanel =addUserInfoPanel();
        JScrollPane orderPanel = addOrderPanel(); 

        this.add("个人中心",userInfoPanel);
        this.add("购物订单",orderPanel);
    }
    // 用户基本信息区，包括账号、密码和积分
    public JPanel addUserInfoPanel(){
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridLayout(4,1,2,2));
        connector = new SQLConnector();
        UserLoginPanel.user = connector.selectUser(UserLoginPanel.user.getUserID());
        String[][] infoStr = new String[][]{
            {"账号:",UserLoginPanel.user.getUserID()},
            {"密码:",UserLoginPanel.user.getPassword()},
            {"积分:",Float.toString(UserLoginPanel.user.getPoint())}
        };
        // 添加基本信息栏
        for(int i=0; i<infoStr.length; i++){
            JPanel info = new JPanel();
            JLabel label = new JLabel(infoStr[i][0]);
            JLabel label2 = new JLabel(infoStr[i][1]);
            info.add(label);
            info.add(label2);
            userInfoPanel.add(info);
        }
        // 设置退出按钮
        Button exit = new Button("退出",new Color(255, 165, 0), Color.white);
        exit.addActionListener(this);

        userInfoPanel.add(exit);
        
        return userInfoPanel;
    }
    // 用户订单区，初始化为空
    public JScrollPane addOrderPanel(){
        int len = ShoppingTicketList.shoppingTicketList.size();
        JPanel panel = new JPanel();
        if(len==0){
            panel.setLayout(new FlowLayout());
        }else{
            panel.setLayout(new GridLayout(len,1,5,5));
        }
        
        // 判断当前订单列表是否为空
        if(len!=0){
            for(Entry<String,ShoppingTicket>entry:ShoppingTicketList.shoppingTicketList.entrySet()){
                panel.add(new ShoppingTicketListPanel(entry));
            }
        }
        orderPanel = new JScrollPane(panel);
        orderPanel.setVerticalScrollBarPolicy(20);
        orderPanel.setHorizontalScrollBarPolicy(30);
        // orderPanel.setSize(300,500);
        return orderPanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        connector = new SQLConnector();

        String result = connector.updateUser(UserLoginPanel.user.getUserID(), UserLoginPanel.user.getPoint());
        if(result.equals("更新成功")){
            System.exit(0);
        }else{
            JOptionPane.showMessageDialog(this, "上传数据失败，请稍后重试", "error", JOptionPane.ERROR_MESSAGE);
        }
       
    }    

}
