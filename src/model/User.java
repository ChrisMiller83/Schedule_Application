package model;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * User model class
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

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

    /** userId setter */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** userId getter */
    public int getUserId() {
        return userId;
    }

    /** userName setter */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** userName getter */
    public String getUserName() {
        return userName;
    }

    /** password setter */
    public void setPassword(String password) {
        this.password = password;
    }

    /** password getter */
    public String getPassword() {
        return password;
    }

    /** createdDate setter */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /** createdDate getter */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /** createdBy setter */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** createdBy getter */
    public String getCreatedBy() {
        return createdBy;
    }

    /** lastUpdated setter */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /** lastUpdated getter */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /** lastUpdatedBy setter */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** lastUpdatedBy getter */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** currentUser getter */
    public static User getCurrentUser(String userName) {return currentUser;}

    @Override
    public String toString() {
        return userName;
    }
}
