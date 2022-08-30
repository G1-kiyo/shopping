package entity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class ShoppingTicket {
    private String orderId;
    private String userId;
    private Map<Goods,Integer>cartMap;
    private Date date;
    private Float totalPrice;

    public ShoppingTicket(String userId,Map<Goods,Integer>cartMap,Date date,Float totalPrice) {
        this.userId = userId;
        this.setCartMap(cartMap);
        this.date = date;
        this.totalPrice = totalPrice;
        this.orderId = createRandomOrderID(8);
    }
    public ShoppingTicket(String orderId,String userId,Map<Goods,Integer>cartMap,Date date,Float totalPrice){
        this.orderId = orderId;
        this.userId = userId;
        this.setCartMap(cartMap);
        this.date = date;
        this.totalPrice = totalPrice;
    }
    public  String createRandomOrderID(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer s = new StringBuffer();
        for(int i = 0; i < length; i++){

            Random randomChoice = new Random();
            int num = randomChoice.nextInt(3);
            switch(num){
                case 0:
                    int index0 = randomChoice.nextInt(26)+0;
                    System.out.println(str.charAt(index0));;
                    s.append(str.charAt(index0));
                    break;
                case 1:
                    int index1 = randomChoice.nextInt(26)+26;
                    s.append(str.charAt(index1));
                    break;
                case 2:
                    int index2 = randomChoice.nextInt(9)+52;
                    s.append(str.charAt(index2));
                    break;
                default:break;
            }
            // System.out.println(i);
        }
        
        return s.toString();
    }
    public String getOrderId() {
        return orderId;
    }
    
    public Map<Goods,Integer> getCartMap() {
        return cartMap;
    }
    public void setCartMap(Map<Goods,Integer> cartMap) {
        this.cartMap = cartMap;
    }
    
    public Date getDate() {
        return new Timestamp(date.getTime());
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setTotalPrice(Float totalPrice){
        this.totalPrice = totalPrice;
    }
    public Float getTotalPrice(){
        return totalPrice;
    }

    
}
