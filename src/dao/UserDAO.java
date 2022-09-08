package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.*;

public class UserDAO {

    public UserDAO() {}


    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_ID = "User_ID";
    public static final String COLUMN_USERS_NAME = "User_Name";
    public static final String COLUMN_USERS_PASSWORD = "Password";
    public static final String COLUMN_USERS_CREATED_DATE = "Create_Date";
    public static final String COLUMN_USERS_CREATED_BY = "Created_By";
    public static final String COLUMN_USERS_LAST_UPDATED = "Last_Update";
    public static final String COLUMN_USERS_LAST_UPDATED_BY = "Last_Updated_By";


    public static final String QUERY_ALL_USERS = "SELECT * FROM " + TABLE_USERS +
            " ORDER BY " + COLUMN_USERS_ID;

    public static final String CREATE_USER = "INSERT INTO " + TABLE_USERS + " (" +
            COLUMN_USERS_NAME + ", " + COLUMN_USERS_PASSWORD + ", " + COLUMN_USERS_CREATED_DATE +
            ", " + COLUMN_USERS_CREATED_BY + ", " + COLUMN_USERS_LAST_UPDATED + ", " +
            COLUMN_USERS_LAST_UPDATED_BY + ") VALUES (?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_USER = "UPDATE " + TABLE_USERS + " SET " +
            COLUMN_USERS_NAME + " = ?," + COLUMN_USERS_PASSWORD + " = ?," +
            COLUMN_USERS_LAST_UPDATED + " = ?," + COLUMN_USERS_LAST_UPDATED_BY +
            " = ? WHERE " + COLUMN_USERS_ID + " = ?";

    public static final String DELETE_USER = "DELETE FROM " + TABLE_USERS +
            " WHERE " + COLUMN_USERS_ID + " = ?";

    public static ObservableList<User> loadAllUsers() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        try {
            PreparedStatement loadUsers = DBConnection.getConnection().prepareStatement(QUERY_ALL_USERS);
            ResultSet result = loadUsers.executeQuery();

            while (result.next()) {
                int userId = result.getInt(COLUMN_USERS_ID);
                String userName = result.getString(COLUMN_USERS_NAME);
                String password = result.getString(COLUMN_USERS_PASSWORD);
                Timestamp createdDate = result.getTimestamp(COLUMN_USERS_CREATED_DATE);
                String createdBy = result.getString(COLUMN_USERS_CREATED_BY);
                Timestamp lastUpdated = result.getTimestamp(COLUMN_USERS_LAST_UPDATED);
                String lastUpdatedBy = result.getString(COLUMN_USERS_LAST_UPDATED_BY);

                User user = new User(userId, userName, password, createdDate, createdBy, lastUpdated, lastUpdatedBy);
                usersList.add(user);

            }
            return usersList;
            // TODO: add user report
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addUser(String userName, String password, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        try {
            PreparedStatement addUsers = DBConnection.getConnection().prepareStatement(CREATE_USER);
            addUsers.setString(1, userName);
            addUsers.setString(2, password);
            addUsers.setTimestamp(3, createdDate);
            addUsers.setString(4, createdBy);
            addUsers.setTimestamp(5, lastUpdated);
            addUsers.setString(6, lastUpdatedBy);
            addUsers.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(String  userName, String password, Timestamp lastUpdated, String lastUpdatedBy, int userId) {
        try {
            PreparedStatement updateUsers = DBConnection.getConnection().prepareStatement(UPDATE_USER);
            updateUsers.setString(1, userName);
            updateUsers.setString(2, password);
            updateUsers.setTimestamp(3, lastUpdated);
            updateUsers.setString(4, lastUpdatedBy);
            updateUsers.setInt(5, userId);
            updateUsers.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int userId) {
        try {
            PreparedStatement deleteUsers = DBConnection.getConnection().prepareStatement(DELETE_USER);
            deleteUsers.setInt(1, userId);
            deleteUsers.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
