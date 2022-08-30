package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    public static final String COLUMN_CUSTOMER_COUNTRY_ID = "Country_ID";
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


    public static final String QUERY_ALL_CUSTOMERS = "SELECT * FROM " + TABLE_CUSTOMERS;

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


    public static void loadAllCustomers() {
//        ObservableList<Customer> customers = FXCollections.observableArrayList();
//        Customer.getAllCustomers();

        try {
            PreparedStatement loadCustomers = DBConnection.getConnection().prepareStatement(QUERY_ALL_CUSTOMERS);
            ResultSet result = loadCustomers.executeQuery();

            while (result.next()) {
                int customerId = result.getInt(COLUMN_CUSTOMER_ID);
                String customerName = result.getString(COLUMN_CUSTOMER_NAME);
                String address = result.getString(COLUMN_CUSTOMER_ADDRESS);
                String postalCode = result.getString(COLUMN_CUSTOMER_POSTAL_CODE);
                String phoneNumber = result.getString(COLUMN_CUSTOMER_PHONE);
                Timestamp createDate = result.getTimestamp(COLUMN_CUSTOMER_CREATED_DATE);
                String createdBy = result.getString(COLUMN_CUSTOMER_CREATED_BY);
                Timestamp lastUpdate = result.getTimestamp(COLUMN_CUSTOMER_LAST_UPDATE);
                String lastUpdatedBy = result.getString(COLUMN_CUSTOMER_LAST_UPDATED_BY);
                int divisionId = result.getInt(COLUMN_CUSTOMER_DIVISION_ID);
                int customerCountry = result.getInt(COLUMN_CUSTOMER_COUNTRY_ID);
                Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, customerCountry);

                Customer.addCustomer(customer);

            }
            // TODO: add customer report
        } catch (SQLException e) {
            e.printStackTrace();
            //return null;
        }
    }

    public static boolean addCustomer(int customerId, String name, String address, String postalCode,
                                      String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                                      String lastUpdatedBy, int divisionId) throws SQLException{

        Division newDivision = new Division();

        try {
            PreparedStatement addCustomers = DBConnection.getConnection().prepareStatement(CREATE_CUSTOMER);
            ResultSet result = addCustomers.executeQuery();

            while (result.next()) {
                addCustomers.setInt(INDEX_CUSTOMER_ID, Integer.parseInt(COLUMN_CUSTOMER_ID));
                addCustomers.setString(INDEX_CUSTOMER_NAME, COLUMN_CUSTOMER_NAME);
                addCustomers.setString(INDEX_CUSTOMER_ADDRESS, COLUMN_CUSTOMER_ADDRESS);
                addCustomers.setString(INDEX_CUSTOMER_POSTAL_CODE, COLUMN_CUSTOMER_POSTAL_CODE);
                addCustomers.setString(INDEX_CUSTOMER_PHONE, COLUMN_CUSTOMER_PHONE);
                addCustomers.setTimestamp(INDEX_CUSTOMER_CREATED_DATE, Timestamp.valueOf(LocalDateTime.now(ZoneId.of(COLUMN_CUSTOMER_CREATED_DATE))));
                addCustomers.setString(INDEX_CUSTOMER_CREATED_BY, COLUMN_CUSTOMER_CREATED_BY);
                addCustomers.setTimestamp(INDEX_CUSTOMER_LAST_UPDATE, Timestamp.valueOf(LocalDateTime.now(ZoneId.of(COLUMN_CUSTOMER_LAST_UPDATE))));
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
        Division newDivision = new Division();

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


    public static void addCustomer(Customer customer) {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            PreparedStatement addCustomers = DBConnection.getConnection().prepareStatement(CREATE_CUSTOMER);
            ResultSet result = addCustomers.executeQuery();

            while (result.next()) {

                addCustomers.setInt(INDEX_CUSTOMER_ID, Integer.parseInt(COLUMN_CUSTOMER_ID));
                addCustomers.setString(INDEX_CUSTOMER_NAME, COLUMN_CUSTOMER_NAME);
                addCustomers.setString(INDEX_CUSTOMER_ADDRESS, COLUMN_CUSTOMER_ADDRESS);
                addCustomers.setString(INDEX_CUSTOMER_POSTAL_CODE, COLUMN_CUSTOMER_POSTAL_CODE);
                addCustomers.setString(INDEX_CUSTOMER_PHONE, COLUMN_CUSTOMER_PHONE);
                addCustomers.setTimestamp(INDEX_CUSTOMER_CREATED_DATE, Timestamp.valueOf(LocalDateTime.now(ZoneId.of(COLUMN_CUSTOMER_CREATED_DATE))));
                addCustomers.setString(INDEX_CUSTOMER_CREATED_BY, COLUMN_CUSTOMER_CREATED_BY);
                addCustomers.setTimestamp(INDEX_CUSTOMER_LAST_UPDATE, Timestamp.valueOf(LocalDateTime.now(ZoneId.of(COLUMN_CUSTOMER_LAST_UPDATE))));
                addCustomers.setString(INDEX_CUSTOMER_LAST_UPDATED_BY, COLUMN_CUSTOMER_LAST_UPDATED_BY);
                addCustomers.setInt(INDEX_CUSTOMER_DIVISION_ID, Integer.parseInt(COLUMN_CUSTOMER_DIVISION_ID));

                customerObservableList.add((Customer) addCustomers);
                //Customer.customerArrayList = customerObservableList;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

}
