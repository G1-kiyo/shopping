package entity;
public class Goods {
    private String goodID;
    private String imgURL;
    private String goodName;
    private Float price;
    private int quantity;
    private String type;
    private String description;
    private String unit;
    
    public String getImgURL() {
        return imgURL;
    }
    public String getGoodID() {
        return goodID;
    }
    public void setGoodID(String goodID) {
        this.goodID = goodID;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getGoodName() {
        return goodName;
    }
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
