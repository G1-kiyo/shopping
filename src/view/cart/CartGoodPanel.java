package view.cart;
import javax.swing.*;

import entity.Goods;
import global.CartMap;
import util.Button;
import view.main.Panel;
import java.awt.*;
import java.awt.event.*;
import java.util.Map.Entry;

public class CartGoodPanel extends JPanel implements ActionListener {
    JPanel infoPanel;
    JPanel quantityJPanel;
    JLabel nameLabel;
    JLabel priceLabel;
    JLabel imgLabel;
    JButton minusButton;
    JButton plusButton;
    TextField textField;
    Button deleteButton;
    Goods good;

    public CartGoodPanel() {

    }

    public CartGoodPanel(Entry<Goods, Integer> entry) {
        this.good = entry.getKey();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(new Color(238, 232, 170),3));
        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createLineBorder(new Color(238, 232, 170),1));

        // 商品名
        nameLabel = new JLabel(good.getGoodName());
        String price = "￥" + good.getPrice();
        // 商品单价
        priceLabel = new JLabel(price);

        // 商品数量控制panel
        quantityJPanel = new JPanel();
        minusButton = new JButton("-");
        minusButton.addActionListener(this);
        minusButton.setSize(10, 10);
        textField = new TextField(Integer.toString(entry.getValue()), 5);
        plusButton = new JButton("+");
        plusButton.addActionListener(this);
        plusButton.setSize(10, 10);

        quantityJPanel.add(minusButton);
        quantityJPanel.add(textField);
        quantityJPanel.add(plusButton);

        infoPanel.add("North", nameLabel);
        infoPanel.add("Center", priceLabel);
        infoPanel.add("South", quantityJPanel);

        // 商品图片
        ImageIcon goodIcon = new ImageIcon(good.getImgURL());
        goodIcon.setImage(goodIcon.getImage().getScaledInstance(50, 100, Image.SCALE_DEFAULT));
        imgLabel = new JLabel(goodIcon);

        // 删除按钮
        deleteButton = new Button("删除", new Color(176, 196, 222), Color.BLACK);
        deleteButton.addActionListener(this);

        this.add("West", imgLabel);
        this.add("Center", infoPanel);
        this.add("East", deleteButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object obj = e.getSource();
        int val = Integer.parseInt(textField.getText());
        // 实现加减按钮逻辑
        if (obj.equals(minusButton)) {
            if (val > 1) {
                textField.setText(Integer.toString(val - 1));
                CartMap.updateValue(good.getGoodName(), false);
            } else {
                JOptionPane.showMessageDialog(this, "禁止操作!", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (obj.equals(plusButton)) {
            if (val < good.getQuantity()) {
                textField.setText(Integer.toString(val + 1));
                CartMap.updateValue(good.getGoodName(), true);
            } else {
                JOptionPane.showMessageDialog(this, "库存不足!", "warning", JOptionPane.INFORMATION_MESSAGE);
            }
            // 删除选中商品
        } else if (obj.equals(deleteButton)) {
            CartMap.cartMap.remove(good);
        }
        Panel.cart.remove(Panel.cartScrollPane);
        Panel.cart.invalidate();
        Panel.cartScrollPane = new CartGoodsContainer();
        Panel.cart.add("Center",Panel.cartScrollPane);
        Panel.cart.repaint();
        Panel.cart.validate();
    }
}
