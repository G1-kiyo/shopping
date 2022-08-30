package view.main;

import javax.swing.*;

import entity.ShoppingTicket;
import global.CartMap;
import global.ShoppingTicketList;
import sql.SQLConnector;
import view.cart.CartGoodsContainer;
import view.good.GoodsPanel;
import view.ticket.ShoppingTicketListPanel;
import view.user.UserLoginPanel;
import view.user.UserPanel;
import util.Button;
import entity.Goods;
import global.View;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
// import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    /*
     * public ArrayList<Meat> meatList; public ArrayList<Vegetable> vegetableList;
     * public ArrayList<Fruit> fruitList;
     */
    SQLConnector connector;
    public static CartGoodsContainer cartScrollPane = null;
    public static ShoppingTicketListPanel shoppingTicketListPanel = null;
    public static ShoppingTicket shoppingTicket;
    Button searchButton;
    Button endButton;
    Button confirmButton;
    Button guideCartButton;
    Button guideUserButton;
    TextField textField;
    JScrollPane goodsPanel;
    public static JTabbedPane userPane;
    JScrollPane pane;
    JPanel buyPanel;
    JPanel cartPanel;
    public static JPanel userPanel;
    JPanel buttonPanel;
    JPanel panel1;
    public static JPanel panel2;
    public static JPanel cart;
    public Panel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        // setSize(800, 600);

        // 搜索panel，包括搜索框，检索按钮和结束按钮
        searchButton = new Button("搜索", new Color(124, 58, 237), Color.white);
        textField = new TextField(20);
        ImageIcon searchIcon = new ImageIcon("./src/img/search.png");
        searchIcon.setImage(searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        searchButton.setIcon(searchIcon);
        endButton = new Button("结束搜索", new Color(124, 58, 237), Color.white);
        // JButton btn2 = new Button("批量选择", new Color(124, 58, 237), Color.white);
        searchButton.addActionListener(this);
        endButton.addActionListener(this);
        // 输入参数为null，表示并非搜索，展示全部商品
        goodsPanel = addGoodsPanel(null);

        buttonPanel = new JPanel();
        buttonPanel.add(textField);
        buttonPanel.add(searchButton);
        buttonPanel.add(endButton);
        // buttonPanel.add(btn2);

        // 商品购买检索区
        buyPanel = new JPanel();
        buyPanel.setLayout(new BorderLayout());
        buyPanel.add("East", buttonPanel);
        buyPanel.add("South", goodsPanel);
        pane = new JScrollPane(buyPanel);
        pane.setVerticalScrollBarPolicy(20);
        pane.setHorizontalScrollBarPolicy(30);

        // 功能区
        JPanel functionZone = new JPanel();
        functionZone.setLayout(new BorderLayout());
        // 商品购物车区
        cartPanel = addCartPanel();

        // 用户个人区
        userPanel = addUserPanel();

        functionZone.add("West",cartPanel);
        functionZone.add("East",userPanel);

        this.add("Center", pane);
        this.add("East", functionZone);

    }

    public JScrollPane addGoodsPanel(String goodName) {
        JPanel component = new JPanel();

        component.setLayout(new GridLayout(3, 1, 5, 5));
        component.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        // 将不同类型的商品分区
        GoodsPanel meatPanel = new GoodsPanel("Meat", goodName);
        GoodsPanel vegetablePanel = new GoodsPanel("Fruit", goodName);
        GoodsPanel fruitPanel = new GoodsPanel("Seafood", goodName);
        component.add(meatPanel);
        component.add(vegetablePanel);
        component.add(fruitPanel);

        JScrollPane panel = new JScrollPane(component);
        panel.setVerticalScrollBarPolicy(22);
        panel.setHorizontalScrollBarPolicy(30);

        return panel;
    }

    public JPanel addCartPanel() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        // 安排控制按钮
        guideCartButton = new Button("<html>购<br/>物<br/>车<br/>收<br/>起</html>", new Color(135, 206, 250), Color.white);
        guideCartButton.setPreferredSize(new Dimension(30,0));
        guideCartButton.setHorizontalAlignment(SwingConstants.CENTER);
        guideCartButton.addActionListener(this);

        // 购物车
        cart = new JPanel();
        cart.setPreferredSize(new Dimension(200,0));
        cart.setLayout(new BorderLayout());

        // 购物车header
        JLabel cartLabel = new JLabel("我的购物车");
        // cartLabel.setBackground(Color.BLACK);
        cartLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        cartLabel.setForeground(Color.BLACK);

        // 纳入购物车的商品名单，初始化时为空
        cartScrollPane = new CartGoodsContainer();

        // 确认支付按钮
        JPanel confJPanel = new JPanel();
        JLabel label = new JLabel("合计金额：" + CartMap.getTotalPrice());
        confirmButton = new Button("支付", new Color(255, 69, 0), Color.WHITE);
        confirmButton.addActionListener(this);
        confJPanel.add(label);
        confJPanel.add(confirmButton);

        cart.add("North", cartLabel);
        cart.add("Center", cartScrollPane);
        cart.add("South", confirmButton);

        panel1.add("West", guideCartButton);
        panel1.add("East", cart);

        return panel1;
    }

    public JPanel addUserPanel() {
        panel2 = new JPanel();
        // setSize(250, 600);
        panel2.setLayout(new BorderLayout());

        // 安排控制按钮
        guideUserButton = new Button("<html>个<br/>人<br/>中<br/>心<br/>收<br/>起</html>", new Color(135, 206, 250), Color.white);
        guideUserButton.setPreferredSize(new Dimension(30,0));
        guideUserButton.setHorizontalAlignment(SwingConstants.CENTER);
        guideUserButton.addActionListener(this);

        // 用户个人中心panel
        userPane = new UserPanel();
        userPane.setPreferredSize(new Dimension(200,0));

        panel2.add("West", guideUserButton);
        panel2.add("East", userPane);

        return panel2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object source = e.getSource();
        // 进入搜索界面
        if (source.equals(searchButton)) {
            String entryVal = textField.getText();
            if (entryVal.length() == 0) {
                JOptionPane.showMessageDialog(this, "请输入关键词!", "warning", JOptionPane.INFORMATION_MESSAGE);
            } else {
                buyPanel.remove(goodsPanel);
                goodsPanel = addGoodsPanel(entryVal);
                buyPanel.add("South", goodsPanel);
                buyPanel.updateUI();
                buyPanel.repaint();

            }
            // 回到全商品界面
        } else if (source.equals(endButton)) {
            buyPanel.remove(goodsPanel);
            goodsPanel = addGoodsPanel(null);
            buyPanel.add("South", goodsPanel);
            buyPanel.updateUI();
            buyPanel.repaint();
            
            // 确认支付界面
        } else if (source.equals(confirmButton)) {
            // 首先判断用户积分是否充足
            if (UserLoginPanel.user.getPoint() >= CartMap.getTotalPrice()) {
                // 创建新的小票，并插入订单列表
                shoppingTicket = new ShoppingTicket(UserLoginPanel.user.getUserID(), CartMap.cartMap, new Date(),CartMap.getTotalPrice());
                // ShoppingTicketList.updateIndex();
                ShoppingTicketList.shoppingTicketList.put(shoppingTicket.getOrderId(), shoppingTicket);
                // 扣除用户积分
                UserLoginPanel.user.setPoint(UserLoginPanel.user.getPoint()-CartMap.getTotalPrice());
                // 写入新的订单信息，更新用户信息
                connector = new SQLConnector();
                String insertResult = connector.insertOrderList(shoppingTicket.getOrderId(), UserLoginPanel.user.getUserID(), shoppingTicket.getDate(), shoppingTicket.getCartMap(), shoppingTicket.getTotalPrice());
                if(insertResult.equals("添加成功")){
                    JOptionPane.showMessageDialog(this, insertResult, "success", JOptionPane.PLAIN_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this, insertResult, "error", JOptionPane.ERROR_MESSAGE);
                }
                panel2.remove(userPane);
                panel2.invalidate();
                userPane = new UserPanel();
                userPane.setPreferredSize(new Dimension(200,0));
                panel2.add("East",userPane);
                panel2.updateUI();
                panel2.repaint();

                // 清除当前购物车
                CartMap.cartMap = new HashMap<Goods,Integer>();
                cart.remove(cartScrollPane);
                cart.invalidate();
                cartScrollPane = new CartGoodsContainer();
                cart.repaint();
                cart.validate();

            } else {
                JOptionPane.showMessageDialog(this, "您的积分不足", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
            // 关于两个按钮的实现
        } else if (source.equals(guideCartButton)) {
            this.cartChangeStatus(guideCartButton);
        } else if (source.equals(guideUserButton)) {
            this.userPaneChangeStatus(guideUserButton);
        }

    }
    public void cartChangeStatus(Object obj){
        Button btn = (Button) obj;
        if (btn.getText().equals("<html>购<br/>物<br/>车<br/>展<br/>开</html>")) {
            btn.setText("<html>购<br/>物<br/>车<br/>收<br/>起</html>");
            panel1.remove(cart);
            panel1.invalidate();
            cart.setPreferredSize(new Dimension(200,0));
            panel1.add("East",cart);
            panel1.repaint();
            panel1.validate();

            this.remove(pane);
            this.invalidate();
            this.add("Center",pane);
            this.repaint();
            this.validate();
            // View.tabbedPane.remove(this);
            // View.tabbedPane.invalidate();
            // View.tabbedPane.add("首页",this);
            // View.tabbedPane.repaint();
            // View.tabbedPane.validate();
        }else{
            btn.setText("<html>购<br/>物<br/>车<br/>展<br/>开</html>");
            panel1.remove(cart);
            panel1.invalidate();
            cart.setPreferredSize(new Dimension(0,0));
            panel1.add("East",cart);
            panel1.repaint();
            panel1.validate();

            this.remove(pane);
            this.invalidate();
            this.add("Center",pane);
            this.repaint();
            this.validate();
            // View.tabbedPane.remove(this);
            // View.tabbedPane.invalidate();
            // View.tabbedPane.add("首页",this);
            // View.tabbedPane.repaint();
            // View.tabbedPane.validate();
        }
    }
    public void userPaneChangeStatus(Object obj){
        Button btn = (Button) obj;
        if (btn.getText().equals("<html>个<br/>人<br/>中<br/>心<br/>展<br/>开</html>")) {
            btn.setText("<html>个<br/>人<br/>中<br/>心<br/>收<br/>起</html>");
            panel2.remove(userPane);
            panel2.invalidate();
            userPane.setPreferredSize(new Dimension(200,0));
            panel2.add("East",userPane);
            panel2.repaint();
            panel2.validate();

            this.remove(pane);
            this.invalidate();
            this.add("Center",pane);
            this.repaint();
            this.validate();
            // View.tabbedPane.remove(this);
            // View.tabbedPane.invalidate();
            // View.tabbedPane.add("首页",this);
            // View.tabbedPane.repaint();
            // View.tabbedPane.validate();
        }else{
            btn.setText("<html>个<br/>人<br/>中<br/>心<br/>展<br/>开</html>");
            panel2.remove(userPane);
            panel2.invalidate();
            userPane.setPreferredSize(new Dimension(0,0));
            panel2.add("East",userPane);
            panel2.repaint();
            panel2.validate();

            this.remove(pane);
            this.invalidate();
            this.add("Center",pane);
            this.repaint();
            this.validate();
            // View.tabbedPane.remove(this);
            // View.tabbedPane.invalidate();
            // View.tabbedPane.add("首页",this);
            // View.tabbedPane.repaint();
            // View.tabbedPane.validate();
        }
    }
    // public void changeStatus(Object obj, Component panel) {
    //     Button btn = (Button) obj;
    //     JPanel p;
    //     JTabbedPane tp;
    //     String cartClass = cart.getClass().getName();
    //     // String userClass = userPane.getClass().getName();
    //     // System.out.println(panel.getClass().getName());
    //     if (btn.getText().equals("<html>展<br/>开</html>")) {
    //         btn.setText("<html>收<br/>起</html>");
            
    //         if(panel.getClass().getName().equals(cartClass)){
    //             p = (JPanel)panel;
    //             panel1.remove(p);
    //             panel1.invalidate();
    //             p.setPreferredSize(new Dimension(0,0));
    //             panel1.add("East",p);
    //             panel1.updateUI();
    //             panel1.repaint();
    //             panel1.validate();
    //         }else{
    //             tp = (JTabbedPane)panel;
    //             panel2.remove(tp);
    //             panel2.invalidate();
    //             tp.setPreferredSize(new Dimension(0,0));
    //             panel2.add("East",tp);
    //             panel2.updateUI();
    //             panel2.repaint();
    //             panel2.validate();
    //         }
    //     } else if (btn.getText().equals("<html>收<br/>起</html>")) {
    //         btn.setText("<html>展<br/>开</html>");
    //         if(panel.getClass().getName().equals(cartClass)){
    //             p = (JPanel)panel;
    //             panel1.remove(p);
    //             panel1.invalidate();
    //             p.setPreferredSize(new Dimension(200,0));
    //             panel1.add("East",p);
    //             panel1.updateUI();
    //             panel1.repaint();
    //             panel1.validate();
    //         }else{
    //             tp = (JTabbedPane)panel;
    //             panel2.remove(tp);
    //             panel2.invalidate();
    //             tp.setPreferredSize(new Dimension(200,0));
    //             panel2.add("East",tp);
    //             panel2.updateUI();
    //             panel2.repaint();
    //             panel2.validate();
    //         }
    //     }
    // }

}
