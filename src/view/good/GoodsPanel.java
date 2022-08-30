package view.good;

import javax.swing.*;

import entity.Goods;
import global.GoodsList;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GoodsPanel extends JPanel {
    // 根据商品类别，创建panel
    public GoodsPanel(String type, String goodName) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        // panel header
        JLabel typeLabel = new JLabel(type);

        JPanel goodsPanel = new JPanel();
        goodsPanel.setLayout(new GridLayout(5, 4, 5, 5));

        // 判断是否有搜索需求
        if (goodName != null) {
            boolean flag = false;
            String pattern = ".*" + goodName + ".*";
            for (Goods item : GoodsList.goodsList) {
                boolean isMatch = Pattern.matches(pattern, item.getGoodName());
                if (isMatch && item.getType().equals(type)) {
                    goodsPanel.add(new ImgPanel(item));
                    flag = true;
                }
            }
            if (flag == false) {
                JLabel warning = new JLabel("找不到你要的商品");
                warning.setHorizontalAlignment(SwingConstants.CENTER);
                setPreferredSize(new Dimension(0,150));
                add("West", typeLabel);
                add("South", warning);
            } else {
                add("West", typeLabel);
                add("South", goodsPanel);
            }

        } else {
            // 循环列表

            for (Goods item : GoodsList.goodsList) {
                if (item.getType().equals(type)) {
                    goodsPanel.add(new ImgPanel(item));
                }
            }
            add("West", typeLabel);
            add("South", goodsPanel);
        }

        /*
         * if (type == "Meat") { for (Meat item : GoodsList.meatList) { Class<?> class1
         * = item.getClass();
         * 
         * if (goodName != null) { String pattern = ".*" + goodName + ".*"; boolean
         * isMatch = Pattern.matches(pattern, item.getGoodName()); if (isMatch) {
         * goodsPanel.add(new ImgPanel(item)); } } else { ImgPanel goodPanel = new
         * ImgPanel(item); goodsPanel.add(goodPanel); }
         * 
         * } } else if (type == "Vegetable") { for (Vegetable item :
         * GoodsList.vegetableList) { ImgPanel goodPanel = new ImgPanel(item);
         * goodsPanel.add(goodPanel); } } else if (type == "Fruit") { for (Fruit item :
         * GoodsList.fruitList) { ImgPanel goodPanel = new ImgPanel(item);
         * goodsPanel.add(goodPanel); } } add("North", typeLabel); add("South",
         * goodsPanel);
         * 
         * } public void showTargetGoods(String goodName,Object item){ String className
         * = item.getClass().getName();
         * 
         * if(className.equals("Meat")){ Meat obj = (Meat)item; }else
         * if(className.equals("Vegetable")){ Vegetable obj = (Vegetable)item; }else
         * if(className.equals("Fruit")){ Fruit obj = (Fruit)item; }
         * 
         * if (goodName != null) { String pattern = ".*" + goodName + ".*"; boolean
         * isMatch = Pattern.matches(pattern, obj.getGoodName()); if (isMatch) {
         * goodsPanel.add(new ImgPanel(item)); } } else { ImgPanel goodPanel = new
         * ImgPanel(item); goodsPanel.add(goodPanel); }
         */
    }

    /*
     * public void showMatchedGoods(String goodName){ String pattern =
     * ".*"+goodName+".*"; for(Meat meat:GoodsList.meatList){ boolean isMatch =
     * Pattern.matches(pattern, meat.getGoodName()); if(isMatch){ JPanel
     * goodsPanel.add(new ImgPanel(meat)); } } for(Vegetable
     * vegetable:GoodsList.vegetableList){ boolean isMatch =
     * Pattern.matches(pattern, vegetable.getGoodName()); if(isMatch){
     * goodsPanel.add(new ImgPanel(vegetable)); } } for(Fruit
     * fruit:GoodsList.fruitList){ boolean isMatch = Pattern.matches(pattern,
     * fruit.getGoodName()); if(isMatch){ goodsPanel.add(new ImgPanel(fruit)); } }
     * 
     * }
     */

}
