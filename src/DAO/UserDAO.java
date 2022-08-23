package DAO;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
    public static final int INDEX_USERS_ID = 1;
    public static final int INDEX_USERS_NAME = 2;
    public static final int INDEX_USERS_PASSWORD= 3;
    public static final int INDEX_USERS_CREATED_DATE = 4;
    public static final int INDEX_USERS_CREATED_BY = 5;
    public static final int INDEX_USERS_LAST_UPDATED = 6;
    public static final int INDEX_USERS_LAST_UPDATED_BY = 7;

    public static final String QUERY_ALL_USERS = "SELECT * FROM " + TABLE_USERS;

    public static void loadAllUsers() {
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
                User.userArrayList.add(user);

            }
            // TODO: add user report
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
