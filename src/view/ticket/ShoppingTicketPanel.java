package view.ticket;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import entity.Goods;
import entity.ShoppingTicket;
import global.CartMap;
import global.ShoppingTicketList;

import java.awt.*;
import java.util.Map;
import java.util.Map.Entry;
public class ShoppingTicketPanel extends JPanel {
    ShoppingTicket shoppingTicket;
    Map<Goods,Integer> cartMap;
    public ShoppingTicketPanel(ShoppingTicket shoppingTicket){
        this.shoppingTicket = shoppingTicket;
        cartMap = shoppingTicket.getCartMap();
        setLayout(new BorderLayout());

        JPanel titlePanel = addTitlePanel();
        JScrollPane cartList = addCartList(shoppingTicket.getOrderId());
        JPanel totalAccountPanel = addTotalAccountPanel();

        this.add("North",titlePanel);
        this.add("Center",cartList);
        this.add("South",totalAccountPanel);
    }

    public JPanel addTitlePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel shoppingPlace = new JLabel("在线购物平台");
        shoppingPlace.setHorizontalAlignment(SwingConstants.CENTER);
        shoppingPlace.setFont(new Font("Microsoft YaHei", Font.BOLD,18));

        JPanel relatedPanel = new JPanel();
        relatedPanel.setLayout(new BorderLayout());
        JLabel orderLabel = new JLabel("订单号:"+shoppingTicket.getOrderId());
        JLabel dateLabel = new JLabel("日期:"+shoppingTicket.getDate());
        relatedPanel.add("West",orderLabel);
        relatedPanel.add("East",dateLabel);

        panel.add("North",shoppingPlace);
        panel.add("South",relatedPanel);

        return panel;
    }

    public JScrollPane addCartList(String orderID) {
        
        // JPanel panel = new JPanel();
        
        int num = cartMap.size();
        // panel.setLayout(new GridLayout(num,1,2,2));

        String[][] data = new String[num][4];
        String[] labelName = {"商品名","数量","单价","金额"};

        for(Entry<Goods,Integer> entry : cartMap.entrySet()){
            for(int i = 0; i < num; i++) {
                Goods good = entry.getKey();
                Integer quantity = entry.getValue();
                data[i][0] = good.getGoodName();
                data[i][1] = Integer.toString(quantity);
                data[i][2] = Float.toString(good.getPrice());
                data[i][3] = Float.toString(good.getPrice()*quantity);
            }
        }
        
        JTable table = new JTable(data,labelName);
        
        JScrollPane scrollPanel = new JScrollPane(table);
        scrollPanel.setVerticalScrollBarPolicy(20);
        scrollPanel.setHorizontalScrollBarPolicy(30);
        return scrollPanel;
    }
    public JPanel addTotalAccountPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel totalName = new JLabel("合计");
        totalName.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel totalAccount = new JLabel(Float.toString(shoppingTicket.getTotalPrice()));
        totalAccount.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add("West",totalName);
        panel.add("East",totalAccount);

        return panel;
    }

}
