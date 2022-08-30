package view.good;


import javax.swing.*;

import entity.Goods;
import global.CartMap;
import util.Button;
import view.cart.CartGoodsContainer;
import view.main.Panel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ImgPanel extends JPanel implements ActionListener {
    Goods good;
    JLabel goodsLabel;
    // 带有图片的商品栏
    public ImgPanel(Goods good) {
        this.good = good;
        this.setLayout(new BorderLayout());

        // 利用label加载图片
        ImageIcon icon = new ImageIcon(good.getImgURL());
        icon.setImage(icon.getImage().getScaledInstance(60, 100, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(icon);
        // 商品名称
        goodsLabel = new JLabel(good.getGoodName());
        goodsLabel.setHorizontalAlignment(JLabel.LEFT);

        // 商品单价及单位
        String price = good.getPrice() + "/" + good.getUnit();
        JLabel priceLabel = new JLabel(price);
        priceLabel.setHorizontalAlignment(JLabel.RIGHT);

        JPanel infoPanel = new JPanel();
        infoPanel.add(goodsLabel);
        infoPanel.add(priceLabel);

        // 购物按钮
        ImageIcon buyIcon = new ImageIcon("./src/img/buy.png");
        buyIcon.setImage(buyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        JButton button = new Button("购买", new Color(255, 99, 71), Color.WHITE);
        button.setIcon(buyIcon);

        button.addActionListener(this);

        this.add("North", label);
        this.add("Center", infoPanel);
        this.add("South", button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // this.addDialog();
        String name = good.getGoodName();

        // 判断购物车中是否已有当前选中商品
        if (!CartMap.containKey(name)) {
            // 若没有，创建新的购物车商品panel，并向购物车列表添加信息
            CartMap.cartMap.put(good, 1);
            /* Entry<Goods,Integer> entry = CartMap.findKeyValByName(name);
            CartGoodPanel cartGoodPanel = new CartGoodPanel(good);
            Panel.cartScrollPane.add(cartGoodPanel); */
            
        } else {
            // 若已有，更新当前加入购物车的商品数量
            CartMap.updateValue(name,true);
        }
        Panel.cart.remove(Panel.cartScrollPane);
        Panel.cart.invalidate();
        Panel.cartScrollPane = new CartGoodsContainer();
        Panel.cart.add("Center",Panel.cartScrollPane);
        Panel.cart.repaint();
        Panel.cart.validate();
        /*
         * if(Panel.cartScrollPane.getCartGoods().containsKey(good.getGoodName())){
         * CartGoodPanel component =
         * Panel.cartScrollPane.getCartGoods().get(good.getGoodName()); int count =
         * component.getComponentCount(); for(int i = 0; i < count; i++){
         * if(component.getComponent(i) instanceof TextField){ TextField quantity =
         * (TextField) component.getComponent(i);
         * if(Integer.parseInt(quantity.getText())<good.getQuantity()){
         * quantity.setText(quantity.getText()+1); }else{
         * JOptionPane.showConfirmDialog(this,"库存不足","error",JOptionPane.YES_OPTION); }
         * 
         * } } } CartGoodPanel cartGoodPanel = new CartGoodPanel(good);
         * Panel.cartScrollPane.add(cartGoodPanel);
         * Panel.cartScrollPane.addToCart(good.getGoodName(),cartGoodPanel);
         */

    }

    /*
     * public JDialog addDialog(){
     * 
     * JButton minusButton = new JButton(); JButton plusButton = new JButton();
     * JTextField textField = new JTextField(10); JButton confirmButton = new
     * Button("确认",new Color(124, 58, 237),Color.white); JButton cancelButton = new
     * Button("取消",new Color(124, 58, 237),Color.white); JPanel inputPanel = new
     * JPanel(); JPanel buttonPanel = new JPanel(); JOptionPane panel = new
     * JOptionPane(new JPanel()); JDialog dialog = panel.createDialog(null, "购买窗口");
     * // JPanel dialog = new JPanel(); panel.setSize(0,0); dialog.setLayout(new
     * BorderLayout()); dialog.setSize(300,200); inputPanel.setLayout(new
     * FlowLayout()); buttonPanel.setLayout(new FlowLayout()); ImageIcon minusIcon =
     * new ImageIcon("./src/img/minus.png");
     * minusIcon.setImage(minusIcon.getImage().getScaledInstance(15,20,
     * Image.SCALE_DEFAULT)); minusButton.setIcon(minusIcon);
     * minusButton.setBackground(Color.WHITE); // minusButton.setSize(10,10);
     * ImageIcon plusIcon = new ImageIcon("./src/img/plus.png");
     * plusIcon.setImage(plusIcon.getImage().getScaledInstance(15,20,
     * Image.SCALE_DEFAULT)); plusButton.setIcon(plusIcon);
     * plusButton.setBackground(Color.WHITE); // textField.setSize(10,30);
     * 
     * 
     * 
     * inputPanel.add(minusButton); inputPanel.add(textField);
     * inputPanel.add(plusButton);
     * 
     * buttonPanel.add(confirmButton); buttonPanel.add(cancelButton);
     * 
     * dialog.add("North",inputPanel); dialog.add("East",buttonPanel);
     * 
     * dialog.setVisible(true); return dialog; }
     */
}