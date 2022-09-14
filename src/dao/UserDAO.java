package dao;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.*;

/**
 * UserDAO -- -- used to connect to the database and allow sql queries, create, update, and deletes.
 */
public class UserDAO {

    /**
     * Instantiates a new user
     */
    public UserDAO() {}

    /**
     * CONSTANTS used to prevent SQL injection into the appointments table
     */
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_ID = "User_ID";
    public static final String COLUMN_USERS_NAME = "User_Name";
    public static final String COLUMN_USERS_PASSWORD = "Password";
    public static final String COLUMN_USERS_CREATED_DATE = "Create_Date";
    public static final String COLUMN_USERS_CREATED_BY = "Created_By";
    public static final String COLUMN_USERS_LAST_UPDATED = "Last_Update";
    public static final String COLUMN_USERS_LAST_UPDATED_BY = "Last_Updated_By";

    /**
     * QUERY CONSTANTS used to prevent SQL injection into the appointments table
     */
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

    /**
     * loadAllUsers -- queries the db and gets all users from the users table
     * @return returns an ObservableList of all users and their data from the users table.
     */
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
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * addUser -- Creates a new user/entry in the db users table
     * @param userName -- user name
     * @param password -- password
     * @param createdDate -- Timestamp when user entry was created
     * @param createdBy -- User name that created the user entry
     * @param lastUpdated -- Timestamp when the user entry was last updated
     * @param lastUpdatedBy -- User name that last updated the user entry
     */
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

    /**
     * updateUser -- updates a user in the db users table that matches the given userId
     * @param userName -- user name
     * @param password -- password
     * @param lastUpdated -- Timestamp when the user entry was last updated
     * @param lastUpdatedBy -- User name that last updated the user entry
     * @param userId -- userId used to find the user entry being updated in the db users table
     */
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

    /**
     * deleteUser -- deletes a user from the db user table that matches the given userId
     * @param userId -- userId used to find the user entry being delete in the users table
     */
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
