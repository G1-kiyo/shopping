package view.cart;
import javax.swing.*;

import entity.Goods;
import global.CartMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
public class CartGoodsContainer extends JScrollPane{
    
    public CartGoodsContainer(){
        JPanel panel = new JPanel();
        
        // 判断购物车是否为空
        if(CartMap.cartMap.size()!=0){
            panel.setLayout(new GridLayout(CartMap.cartMap.size(),1,5,5));
            for(Entry<Goods,Integer> entry:CartMap.cartMap.entrySet()){
                panel.add(new CartGoodPanel(entry));
            }
        }
        setViewportView(panel);
        setVerticalScrollBarPolicy(20);
        setHorizontalScrollBarPolicy(30);
        // setSize(200,500);

    }
    /* public void addToCart(String name,CartGoodPanel cartGoodPanel){
        goodMap.put(name,cartGoodPanel);
        
    }
    public Map<String,CartGoodPanel> getCartGoods(){
        return goodMap;
    } */
}
