package dao;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 * DBConnection -- connects the database to allow queries, creating, updates, and deletes.
 */
public class DBConnection {
     private static final String protocol = "jdbc";
     private static final String vendor = ":mysql:";
     private static final String location = "//localhost/";
     private static final String databaseName = "client_schedule";
     private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
     private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
     private static final String userName = "sqlUser"; // Username
     private static final String password = "Passw0rd!"; // Password
     private static Connection connection = null;  // Connection Interface
     private static PreparedStatement preparedStatement;
     public static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    /**
     * makeConnection -- establishes a connection to the db.
     */
    public static void makeConnection() {
          try {
              Class.forName(driver); // Locate Driver
              connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
              System.out.println("Connection successful!");
          } catch(ClassNotFoundException e) {
              System.out.println("Error:" + e.getMessage());
          } catch(SQLException e) {
                      System.out.println("Error:" + e.getMessage());
          }
     }


     public static Connection getConnection() {
        return connection;
     }

    /**
     * closeConnection -- shuts down the db connection when the application closes.
     */
    public static void closeConnection() {
          try {
              connection.close();
              System.out.println("Connection closed!");
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          }
     }
}
