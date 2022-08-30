package entity;

public class User {
    
    private String userID;
    private String password;
    private Float point;
    public String getUserID() {
        return userID;
    }
    public Float getPoint() {
        return point;
    }
    public void setPoint(Float point) {
        this.point = point;

    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

}
