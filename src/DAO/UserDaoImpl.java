package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.main;
import model.User;

import javax.management.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class UserDaoImpl {
    static boolean act;
    public static User getUser(int userName) throws SQLException, Exception {
        DBConnection.makeConnection();
        String sqlStatement = "select * FROM users WHERE User_Name = '" + userName + "'";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result = Query.getResult();
        while (result.next()) {
            int userid = result.getInt("User_ID");
            String userName = result.getString("User_Name");
            String password = result.getString("Password");
            int active = result.getInt("active");
            if(active==1) act=true;
            String createdDate = result.getString("createdDate");
            String createdBy = result.getString("createdBy");
            String lastUpdated = result.getString("lastUpdated");
            String lastUpdatedBy = result.getString("lastUpdatedBy");
            Calendar createDateCalendar = stringToCalendar(createdDate);
            Calendar lastedUpdatedCalendar = stringToCalendar(lastUpdated);

            userResult = new User (userid, userName, password, act, createDateCalendar, createdBy, lastedUpdatedCalendar, lastUpdatedBy);
            return userResult;
        }
        DBConnection.closeConnection();
        return null;
    }

    public static ObservableList<User> getAllUsers() throws SQLException, Exception {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        String sqlStatement = "select * from users";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int userid = result.getInt("User_ID");
            String userName = result.getString("User_Name");
            String password = result.getString("Password");
            int active = result.getInt("active");
            if (active==1) act=true;
            String createdDate = result.getString("createdDate");
            String createdBy = result.getString("createdBy");
            String lastUpdated = result.getString("lastUpdated");
            String lastUpdatedBy = result.getString("lastUpdatedBy");
            Calendar createDateCalendar = stringToCalendar(createdDate);
            Calendar lastedUpdatedCalendar = stringToCalendar(lastUpdated);
            User userResult = new User(userid, userName,  password, act, createDateCalendar, createdBy, lastedUpdatedCalendar, lastUpdatedBy);
            allUsers.add(userResult);
        }
        DBConnection.closeConnection();
        return allUsers;
    }
}
