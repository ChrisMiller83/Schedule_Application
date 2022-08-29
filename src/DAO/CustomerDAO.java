package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomerDAO {

    public CustomerDAO() {}

    public static final String TABLE_CUSTOMERS = "customers";
    public static final String COLUMN_CUSTOMER_ID = "Customer_ID";
    public static final String COLUMN_CUSTOMER_NAME = "Customer_Name";
    public static final String COLUMN_CUSTOMER_ADDRESS = "Address";
    public static final String COLUMN_CUSTOMER_POSTAL_CODE = "Postal_Code";
    public static final String COLUMN_CUSTOMER_PHONE = "Phone";
    public static final String COLUMN_CUSTOMER_CREATED_DATE = "Create_Date";
    public static final String COLUMN_CUSTOMER_CREATED_BY = "Created_By";
    public static final String COLUMN_CUSTOMER_LAST_UPDATE = "Last_Update";
    public static final String COLUMN_CUSTOMER_LAST_UPDATED_BY = "Last_Updated_By";
    public static final String COLUMN_CUSTOMER_DIVISION_ID = "Division_ID";
    public static final int INDEX_CUSTOMER_ID = 1;
    public static final int INDEX_CUSTOMER_NAME = 2;
    public static final int INDEX_CUSTOMER_ADDRESS = 3;
    public static final int INDEX_CUSTOMER_POSTAL_CODE = 4;
    public static final int INDEX_CUSTOMER_PHONE = 5;
    public static final int INDEX_CUSTOMER_CREATED_DATE = 6;
    public static final int INDEX_CUSTOMER_CREATED_BY = 7;
    public static final int INDEX_CUSTOMER_LAST_UPDATE = 8;
    public static final int INDEX_CUSTOMER_LAST_UPDATED_BY = 9;
    public static final int INDEX_CUSTOMER_DIVISION_ID = 10;

    public static final String FLD_TABLE = "first_level_divisions";
    public static final String CONTACT_TABLE = "contacts";
    public static final String COUNTRY_TABLE = "countries";


    public static final String QUERY_ALL_CUSTOMERS = "SELECT * FROM " + TABLE_CUSTOMERS +
            " AS customer INNER JOIN " + FLD_TABLE + " AS divisions ON " + CONTACT_TABLE +
            "." + COLUMN_CUSTOMER_DIVISION_ID +
            " INNER JOIN " + CONTACT_TABLE + " AS country ON " + COUNTRY_TABLE + ".Country_ID = " +
            FLD_TABLE + ".DIVISION_ID";

    public static final String CREATE_CUSTOMER = "INSERT INTO " + TABLE_CUSTOMERS + "( " +
            COLUMN_CUSTOMER_ID + ", " + COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_ADDRESS +
            ", " + COLUMN_CUSTOMER_POSTAL_CODE + ", " + COLUMN_CUSTOMER_PHONE + ", " +
            COLUMN_CUSTOMER_CREATED_DATE + ", " + COLUMN_CUSTOMER_CREATED_BY + ", " +
            COLUMN_CUSTOMER_LAST_UPDATE + ", " + COLUMN_CUSTOMER_LAST_UPDATED_BY +
            ", " + COLUMN_CUSTOMER_DIVISION_ID + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_CUSTOMER = "UPDATE " + TABLE_CUSTOMERS + " SET " +
            COLUMN_CUSTOMER_NAME + " = ?," + COLUMN_CUSTOMER_ADDRESS + " = ?," +
            COLUMN_CUSTOMER_POSTAL_CODE + " = ?," + COLUMN_CUSTOMER_PHONE + " = ?," +
            COLUMN_CUSTOMER_LAST_UPDATE + " = ?," + COLUMN_CUSTOMER_LAST_UPDATED_BY + " = ?," +
            COLUMN_CUSTOMER_DIVISION_ID + " = ? WHERE " + COLUMN_CUSTOMER_ID + " + ?";

    public static final String DELETE_CUSTOMER = "DELETE FROM " + TABLE_CUSTOMERS +
            " WHERE " + COLUMN_CUSTOMER_ID + " = ?";


    public static ObservableList<Customer> loadAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            PreparedStatement loadCustomers = DBConnection.getConnection().prepareStatement(QUERY_ALL_CUSTOMERS);
            ResultSet result = loadCustomers.executeQuery();

            while (result.next()) {
                Customer newCustomer = new Customer(
                result.getInt(COLUMN_CUSTOMER_ID),
                result.getString(COLUMN_CUSTOMER_NAME),
                result.getString(COLUMN_CUSTOMER_ADDRESS),
                result.getString(COLUMN_CUSTOMER_POSTAL_CODE),
                result.getString(COLUMN_CUSTOMER_PHONE),
                result.getTimestamp(COLUMN_CUSTOMER_CREATED_DATE),
                result.getString(COLUMN_CUSTOMER_CREATED_BY),
                result.getTimestamp(COLUMN_CUSTOMER_LAST_UPDATE),
                result.getString(COLUMN_CUSTOMER_LAST_UPDATED_BY),
                result.getInt(COLUMN_CUSTOMER_DIVISION_ID)
                );
                customers.add(newCustomer);
            }
            return customers;
            // TODO: add customer report
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addCustomer(int customerId, String name, String address, String postalCode,
                                      String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                                      String lastUpdatedBy, int divisionId) throws SQLException{
        Division newDivision = DivisionDAO.getDivisionId(division);

        try {
            PreparedStatement addCustomers = DBConnection.getConnection().prepareStatement(CREATE_CUSTOMER);
            ResultSet result = addCustomers.executeQuery();

            while (result.next()) {
                addCustomers.setInt(INDEX_CUSTOMER_ID, Integer.parseInt(COLUMN_CUSTOMER_ID));
                addCustomers.setString(INDEX_CUSTOMER_NAME, COLUMN_CUSTOMER_NAME);
                addCustomers.setString(INDEX_CUSTOMER_ADDRESS, COLUMN_CUSTOMER_ADDRESS);
                addCustomers.setString(INDEX_CUSTOMER_POSTAL_CODE, COLUMN_CUSTOMER_POSTAL_CODE);
                addCustomers.setString(INDEX_CUSTOMER_PHONE, COLUMN_CUSTOMER_PHONE);
                addCustomers.setTimestamp(INDEX_CUSTOMER_CREATED_DATE, Timestamp.valueOf(COLUMN_CUSTOMER_CREATED_DATE));
                addCustomers.setString(INDEX_CUSTOMER_CREATED_BY, COLUMN_CUSTOMER_CREATED_BY);
                addCustomers.setTimestamp(INDEX_CUSTOMER_LAST_UPDATE, Timestamp.valueOf(COLUMN_CUSTOMER_LAST_UPDATE));
                addCustomers.setString(INDEX_CUSTOMER_LAST_UPDATED_BY, COLUMN_CUSTOMER_LAST_UPDATED_BY);
                addCustomers.setInt(INDEX_CUSTOMER_DIVISION_ID, Integer.parseInt(COLUMN_CUSTOMER_DIVISION_ID));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCustomer(int customerId, String name, String address, String postalCode,
                                      String phone, Timestamp lastUpdate,
                                      String lastUpdatedBy, int divisionId) throws SQLException{
        Division newDivision = DivisionDAO.getDivsionId(division);

        try {
            PreparedStatement addCustomers = DBConnection.getConnection().prepareStatement(UPDATE_CUSTOMER);
            ResultSet result = addCustomers.executeQuery();

            while (result.next()) {
                addCustomers.setString(INDEX_CUSTOMER_NAME, COLUMN_CUSTOMER_NAME);
                addCustomers.setString(INDEX_CUSTOMER_ADDRESS, COLUMN_CUSTOMER_ADDRESS);
                addCustomers.setString(INDEX_CUSTOMER_POSTAL_CODE, COLUMN_CUSTOMER_POSTAL_CODE);
                addCustomers.setString(INDEX_CUSTOMER_PHONE, COLUMN_CUSTOMER_PHONE);
                addCustomers.setTimestamp(INDEX_CUSTOMER_LAST_UPDATE, Timestamp.valueOf(COLUMN_CUSTOMER_LAST_UPDATE));
                addCustomers.setString(INDEX_CUSTOMER_LAST_UPDATED_BY, COLUMN_CUSTOMER_LAST_UPDATED_BY);
                addCustomers.setInt(INDEX_CUSTOMER_DIVISION_ID, Integer.parseInt(COLUMN_CUSTOMER_DIVISION_ID));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCustomer (int customerId) {
        try {
            PreparedStatement deleteCustomer = DBConnection.getConnection().prepareStatement(DELETE_CUSTOMER);
            ResultSet result = deleteCustomer.executeQuery();

            while (result.next()) {
                deleteCustomer.setInt(INDEX_CUSTOMER_ID, Integer.parseInt(COLUMN_CUSTOMER_ID));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }










}
