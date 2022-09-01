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
    public static final String COLUMN_CUSTOMER_DIVISION_ID = "Division_ID";



    public static final String QUERY_ALL_CUSTOMERS = "SELECT * FROM " + TABLE_CUSTOMERS;

    public static final String CREATE_CUSTOMER = "INSERT INTO " + TABLE_CUSTOMERS + "( " +
            COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_ADDRESS +
            ", " + COLUMN_CUSTOMER_POSTAL_CODE + ", " + COLUMN_CUSTOMER_PHONE + ", " +
            COLUMN_CUSTOMER_DIVISION_ID + " ) VALUES (?, ?, ?, ?, ?)";

    public static final String UPDATE_CUSTOMER = "UPDATE " + TABLE_CUSTOMERS + " SET " +
            COLUMN_CUSTOMER_NAME + " = ?," + COLUMN_CUSTOMER_ADDRESS + " = ?," +
            COLUMN_CUSTOMER_POSTAL_CODE + " = ?," + COLUMN_CUSTOMER_PHONE + " = ?," +
            COLUMN_CUSTOMER_DIVISION_ID + " = ? WHERE " + COLUMN_CUSTOMER_ID + " = ?";

    public static final String DELETE_CUSTOMER = "DELETE FROM " + TABLE_CUSTOMERS +
            " WHERE " + COLUMN_CUSTOMER_ID + " = ?";


    public static ObservableList<Customer> loadAllCustomers() {
        ObservableList<Customer> customersList = FXCollections.observableArrayList();
        try {
            PreparedStatement loadCustomers = DBConnection.getConnection().prepareStatement(QUERY_ALL_CUSTOMERS);
            ResultSet result = loadCustomers.executeQuery();


            while (result.next()) {
                int customerId = result.getInt(COLUMN_CUSTOMER_ID);
                String customerName = result.getString(COLUMN_CUSTOMER_NAME);
                String address = result.getString(COLUMN_CUSTOMER_ADDRESS);
                String postalCode = result.getString(COLUMN_CUSTOMER_POSTAL_CODE);
                String phoneNumber = result.getString(COLUMN_CUSTOMER_PHONE);
                int divisionId = result.getInt(COLUMN_CUSTOMER_DIVISION_ID);

                Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId);

                customersList.add(customer);

            }
            return customersList;
            // TODO: add customer report
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addCustomer(String customerName, String address, String postalCode, String phoneNumber, int divisionId) {
        try {
            PreparedStatement addCustomers = DBConnection.getConnection().prepareStatement(CREATE_CUSTOMER);
            addCustomers.setString(1, customerName);
            addCustomers.setString(2, address);
            addCustomers.setString(3, postalCode);
            addCustomers.setString(4, phoneNumber);
            addCustomers.setInt(5, divisionId);
            addCustomers.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updateCustomer(String customerName, String address, String postalCode, String phoneNumber, int divisionId, int customerId) {

        try {
            PreparedStatement updateCustomers = DBConnection.getConnection().prepareStatement(UPDATE_CUSTOMER);

            updateCustomers.setString(1, customerName);
            updateCustomers.setString(2, address);
            updateCustomers.setString(3, postalCode);
            updateCustomers.setString(4, phoneNumber);
            updateCustomers.setInt(5, divisionId);
            updateCustomers.setInt(6, customerId);
            updateCustomers.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer (int customerId) {
        try {

            PreparedStatement deleteCustomer = DBConnection.getConnection().prepareStatement(DELETE_CUSTOMER);
            deleteCustomer.setInt(1, customerId);
            deleteCustomer.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
