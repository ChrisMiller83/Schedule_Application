package model;

import DAO.UserDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    private String userName;
    private String password;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    public static List<User> userArrayList = new ArrayList<>();
    public static User currentUser;
    

    public User(int userId, String userName, String password, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public static List<User> getUserArrayList() {
        return userArrayList;
    }


    @Override
    public String toString() {
        return userName;
    }
}
