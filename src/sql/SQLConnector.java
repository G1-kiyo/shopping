package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.sql.*;
import java.util.Map;
import java.util.Map.Entry;

import com.mysql.cj.jdbc.*;

import entity.Goods;
import entity.ShoppingTicket;
import entity.User;
import global.GoodsList;
import global.ShoppingTicketList;

public class SQLConnector {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement preStmt = null;
    String comnSql;

    public SQLConnector() {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://localhost:3306/shopping";
            String username = "root";
            String password = "wudongnan@123";
            conn = DriverManager.getConnection(url, username, password);

            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 1-商品ID，2-商品名称 3-商品描述，4-商品类型，5-商品价格，6-商品单位,7-商品图片地址 8.商品库存
    public void selectItem() {
        comnSql = "SELECT * FROM goods ";
        try {
            if (stmt.execute(comnSql) == true) {
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    Goods good = new Goods();
                    good.setGoodID(rs.getString("goodID"));
                    good.setGoodName(rs.getString("goodName"));
                    good.setType(rs.getString("type"));
                    good.setDescription(rs.getString("description"));
                    good.setPrice(rs.getFloat("price"));
                    good.setUnit(rs.getString("unit"));
                    good.setQuantity(rs.getInt("stock"));
                    good.setImgURL(rs.getString("imgURL"));
                    GoodsList.goodsList.add(good);

                    /*
                     * String type = rs.getString(2); switch (type) { case "meat": Meat meat = new
                     * Meat(); meat.setGoodName(rs.getString(1)); meat.setType(rs.getString(2));
                     * meat.setDescription(rs.getString(3)); meat.setPrice(rs.getFloat(4));
                     * meat.setUnit(rs.getString(5)); meat.setQuantity(rs.getInt(6));
                     * meat.setImgURL(rs.getString(7)); GoodsList.meatList.add(meat); break;
                     * 
                     * case "vegetable": Vegetable vegetable = new Vegetable();
                     * vegetable.setGoodName(rs.getString(1)); vegetable.setType(rs.getString(2));
                     * vegetable.setDescription(rs.getString(3));
                     * vegetable.setPrice(rs.getFloat(4)); vegetable.setUnit(rs.getString(5));
                     * vegetable.setQuantity(rs.getInt(6)); vegetable.setImgURL(rs.getString(7));
                     * GoodsList.vegetableList.add(vegetable); break;
                     * 
                     * case "fruit": Fruit fruit = new Fruit(); fruit.setGoodName(rs.getString(1));
                     * fruit.setType(rs.getString(2)); fruit.setDescription(rs.getString(3));
                     * fruit.setPrice(rs.getFloat(4)); fruit.setUnit(rs.getString(5));
                     * fruit.setQuantity(rs.getInt(6)); fruit.setImgURL(rs.getString(7));
                     * GoodsList.fruitList.add(fruit); break;
                     * 
                     * }
                     */

                    // rs = rs.next();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Goods selectItemByName(String name) {
        // comnSql = "SELECT * FROM goods WHERE goodName = " + name;
        comnSql = "SELECT * FROM goods WHERE goodName = ?";

        try {
            preStmt = conn.prepareStatement(comnSql);
            preStmt.setString(1, name);
            ResultSet rs = preStmt.executeQuery();
            Goods good = new Goods();
            while (rs.next()) {
                good.setGoodName(rs.getString(1));
                good.setType(rs.getString(2));
                good.setDescription(rs.getString(3));
                good.setPrice(rs.getFloat(4));
                good.setUnit(rs.getString(5));
                good.setQuantity(rs.getInt(6));
                good.setImgURL(rs.getString(7));
            }
            return good;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preStmt != null) {
                try {
                    preStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 购物车1.订单号 2.客户账号 3.订单生成日期 4.订单总额
    public String insertOrderList(String orderID, String userID, Date date, Map<Goods, Integer> cartMap,Float totalPrice) {

        comnSql = "INSERT INTO orders(orderID,userID,orderDate,totalPrice) VALUES(?,?,?,?)";
        // subSql = "INSERT INTO orderdetails(orderID,goodName,quantity) VALUES(" + ")";
        try {
            preStmt = conn.prepareStatement(comnSql);
            preStmt.setString(1, orderID);
            preStmt.setString(2, userID);
            Timestamp timeStamp = new Timestamp(date.getTime());
            preStmt.setTimestamp(3, timeStamp);
            preStmt.setFloat(4, totalPrice);
            int result = preStmt.executeUpdate();
            if (result != 0) {
                String insertStatus = this.insertOrderDetails(orderID, cartMap);
                if (insertStatus.equals("已全部添加完毕")) {
                    return "添加成功";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preStmt != null) {
                try {
                    preStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "添加失败";
    }

    // 订单详情 1.订单号 2.商品id 3.商品购买数量
    public String insertOrderDetails(String orderID, Map<Goods, Integer> cartMap) {
        comnSql = "INSERT INTO orderdetails VALUES (?,?,?)";
        int target = cartMap.size();
        int result = 0;
        try {
            for (Entry<Goods, Integer> entry : cartMap.entrySet()) {
                preStmt = conn.prepareStatement(comnSql);
                preStmt.setString(1, orderID);
                preStmt.setString(2, entry.getKey().getGoodID());
                preStmt.setInt(3, entry.getValue());
                result = result + preStmt.executeUpdate();
            }
            if (result == target) {
                return "已全部添加完毕";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preStmt != null) {
                try {
                    preStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "添加失败";
    }

    // 1.用户账号 2.订单号 3.商品id 4.商品购买数量 5.订单生成日期 6.商品名称 7.商品类型 8.商品单价 9.商品单位 10.商品库存
    // 11.商品描述 12.商品图片 13.订单总额
    public void selectShoppingTicket(String userID) {
        comnSql = "SELECT orders.*,orderdetails.quantity,goods.* FROM orders,orderdetails,goods WHERE orders.userID = ? AND orders.orderID = orderdetails.orderID AND orderdetails.goodID = goods.goodID";
        // Map<String, ShoppingTicket> orderList = new HashMap<String, ShoppingTicket>();
        try {
            preStmt = conn.prepareStatement(comnSql);
            preStmt.setString(1, userID);
            ResultSet rs = preStmt.executeQuery();
            if(rs.next()){
                do{
                    String orderID = rs.getString("orderID");
                    Float totalPrice = rs.getFloat("totalPrice");
                    Goods good = new Goods();
                    good.setGoodID(rs.getString("goodID"));
                    good.setGoodName(rs.getString("goodName"));
                    good.setDescription(rs.getString("description"));
                    good.setPrice(rs.getFloat("price"));
                    good.setUnit(rs.getString("unit"));
                    good.setImgURL(rs.getString("imgURL"));
                    good.setQuantity(rs.getInt("stock"));
                    good.setType(rs.getString("type"));
                    Integer num = rs.getInt("quantity");
                    Date date = rs.getTimestamp("orderDate");
                    if (ShoppingTicketList.shoppingTicketList.containsKey(orderID)) {
                        Map<Goods, Integer> cartMap = ShoppingTicketList.shoppingTicketList.get(orderID).getCartMap();
                        cartMap.put(good, num);
                    } else {
                        Map<Goods, Integer> cartMap = new HashMap<Goods, Integer>();
                        cartMap.put(good, num);
                        ShoppingTicketList.shoppingTicketList.put(orderID, new ShoppingTicket(orderID,userID, cartMap, date,totalPrice));
                    }
                }while(rs.next());
            }
           
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preStmt != null) {
                try {
                    preStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // return "添加失败";
    }
    // 删除订单
    public String deleteShoppingList(String orderID){
        comnSql = "DELETE FROM orderdetails WHERE orderId=?";
        try{
            preStmt = conn.prepareStatement(comnSql);
            preStmt.setString(1, orderID);
            int result = preStmt.executeUpdate();
            if(result==0){
                comnSql = "DELETE FROM orders WHERE orderId=?";
                preStmt = conn.prepareStatement(comnSql);
                preStmt.setString(1, orderID);
                result = preStmt.executeUpdate();
                if(result==0){
                    return "取消成功";
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preStmt != null) {
                try {
                    preStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "取消失败，请重新尝试";
    }

    // 1.用户账号 2.用户密码 3.用户账户积分
    public User selectUser(String userID) {

        comnSql = "SELECT * FROM user WHERE userID=?";

        User user = new User();
        try {
            preStmt = conn.prepareStatement(comnSql);
            preStmt.setString(1, userID);
            ResultSet rs = preStmt.executeQuery();
            if (rs.next()) {

                do {
                    user.setUserID(rs.getString(1));
                    user.setPassword(rs.getString(2));
                    user.setPoint(Float.parseFloat(rs.getString(3)));
                } while (rs.next());
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preStmt != null) {
                try {
                    preStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String insertUser(String userID, String password) {
        comnSql = "INSERT INTO user(userID,password,point) VALUES(?,?,?)";
        Float point = 1000.0f;
        try {
            preStmt = conn.prepareStatement(comnSql);
            preStmt.setString(1, userID);
            preStmt.setString(2, password);
            preStmt.setFloat(3, point);
            int result = preStmt.executeUpdate();
            if (result != 0) {
                return "注册成功";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preStmt != null) {
                try {
                    preStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "注册失败";
    }
    public String updateUser(String userID,Float point){
        comnSql = "UPDATE user SET point = ? WHERE userID = ?";
        
        try{
            preStmt = conn.prepareStatement(comnSql);
            preStmt.setFloat(1,point);
            preStmt.setString(2,userID);
            int result = preStmt.executeUpdate();
            if(result!=0){
                return "更新成功";
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preStmt != null) {
                try {
                    preStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "更新失败";
    }

}
