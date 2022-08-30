package global;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import entity.Goods;

public class CartMap{
    
    public static Map<Goods,Integer> cartMap = new HashMap<Goods,Integer>();
    // 查询购物车是否有当前点击购买的商品
    public static boolean containKey(String name){
        for(Goods key:cartMap.keySet()){
            if(key.getGoodName().equals(name)){
                return true;
            }
        }
        return false;
    }
    // 通过商品名称查询购物车
    public static Goods findKeyByName(String name) {
        for(Goods key:cartMap.keySet()){
            if(key.getGoodName().equals(name)){
                return key;
            }
        }
        return null;
    }
    // 通过商品名称查询指定商品现有购买的数量
    public static Integer findValueByName(String name) {
        for(Goods key:cartMap.keySet()){
            if(key.getGoodName().equals(name)){
                return cartMap.get(key);
            }
        }
        return null;
    }
    // 上述两种方法的结合
    public static Entry<Goods,Integer> findKeyValByName(String name){
        for(Entry<Goods,Integer> entry:cartMap.entrySet()){
            Goods good = entry.getKey();
            if(good.getGoodName().equals(name)){
                return entry;
            }
        }
        return null;
    }
    // 更新购物车指定商品的购买数量，可自定义加减
    public static void updateValue(String name,Boolean isPlus){
        for(Entry<Goods,Integer> entry : cartMap.entrySet()){
            if(entry.getKey().getGoodName()==name){
                Integer value = entry.getValue();
                if(isPlus){
                    entry.setValue(value+1);
                }else{
                    entry.setValue(value-1);
                }

                
            }
        }
    }
    // 计算购物车所有商品金额
    public static Float getTotalPrice() {
        Float totalPrice =0.0f;
        for(Entry<Goods,Integer>item:cartMap.entrySet()){

            Goods good = item.getKey();
            Integer quantity = item.getValue();
            totalPrice = totalPrice + quantity*good.getPrice();
            // System.out.println(totalPrice+","+quantity);

        }
        return totalPrice;
    }
}
