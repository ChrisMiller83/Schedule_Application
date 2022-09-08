package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;

public class CustomerDAO {

    public CustomerDAO() {}

    public static final String TABLE_CUSTOMERS = "customers";
    public static final String COLUMN_CUSTOMER_ID = "Customer_ID";
    public static final String COLUMN_CUSTOMER_NAME = "Customer_Name";
    public static final String COLUMN_CUSTOMER_ADDRESS = "Address";
    public static final String COLUMN_CUSTOMER_POSTAL_CODE = "Postal_Code";
    public static final String COLUMN_CUSTOMER_PHONE = "Phone";
    public static final String COLUMN_CUSTOMER_CREATE_DATE = "Create_Date";
    public static final String COLUMN_CUSTOMER_CREATED_BY = "Created_By";
    public static final String COLUMN_CUSTOMER_LAST_UPDATE = "Last_Update";
    public static final String COLUMN_CUSTOMER_LAST_UPDATED_BY = "Last_Updated_By";
    public static final String COLUMN_CUSTOMER_COUNTRY_ID = "Country_ID";
    public static final String COLUMN_CUSTOMER_DIVISION_ID = "Division_ID";

    public static final String TABLE_DIVISIONS = "first_level_divisions";
    public static final String COLUMN_DIVISION_ID = "Division_ID";
    public static final String TABLE_COUNTRIES = "countries";
    public static final String COLUMN_COUNTRY_ID = "Country_ID";



    public static final String QUERY_ALL_CUSTOMERS = "SELECT * FROM " + TABLE_CUSTOMERS +
            ", " + TABLE_DIVISIONS + ", " + TABLE_COUNTRIES + " WHERE " +
            TABLE_CUSTOMERS + "." + COLUMN_DIVISION_ID + " = " +
            TABLE_DIVISIONS + "." + COLUMN_DIVISION_ID + " AND " +
            TABLE_DIVISIONS + "." + COLUMN_COUNTRY_ID + " = " +
            TABLE_COUNTRIES + "." + COLUMN_COUNTRY_ID +
            " ORDER BY " + COLUMN_CUSTOMER_ID;

//    public static final String QUERY_ALL_CUSTOMERS = "SELECT * FROM " + TABLE_CUSTOMERS +
//            " ORDER BY " + COLUMN_CUSTOMER_ID;

    public static final String CREATE_CUSTOMER = "INSERT INTO " + TABLE_CUSTOMERS + "( " +
            COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_ADDRESS +
            ", " + COLUMN_CUSTOMER_POSTAL_CODE + ", " + COLUMN_CUSTOMER_PHONE + ", " +
            COLUMN_CUSTOMER_CREATE_DATE + ", " + COLUMN_CUSTOMER_CREATED_BY + ", " +
            COLUMN_CUSTOMER_LAST_UPDATE + ", " + COLUMN_CUSTOMER_LAST_UPDATED_BY + ", " +
            COLUMN_CUSTOMER_DIVISION_ID + " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_CUSTOMER = "UPDATE " + TABLE_CUSTOMERS + " SET " +
            COLUMN_CUSTOMER_NAME + " = ?,  " + COLUMN_CUSTOMER_ADDRESS + " = ?, " +
            COLUMN_CUSTOMER_POSTAL_CODE + " = ?, " + COLUMN_CUSTOMER_PHONE + " = ?, " +
            COLUMN_CUSTOMER_LAST_UPDATE + " = ?, " + COLUMN_CUSTOMER_LAST_UPDATED_BY + " = ?, " +
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
                Timestamp createDate = result.getTimestamp(COLUMN_CUSTOMER_CREATE_DATE);
                String createdBy = result.getString(COLUMN_CUSTOMER_CREATED_BY);
                Timestamp lastUpdate = result.getTimestamp(COLUMN_CUSTOMER_LAST_UPDATE);
                String lastUpdateBy = result.getString(COLUMN_CUSTOMER_LAST_UPDATED_BY);
                int countryId = result.getInt(COLUMN_CUSTOMER_COUNTRY_ID);
                int divisionId = result.getInt(COLUMN_CUSTOMER_DIVISION_ID);

                Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber,
                        createDate, createdBy, lastUpdate, lastUpdateBy, countryId, divisionId);

                customersList.add(customer);

            }
            // TODO: add customer report
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }

    public static void addCustomer(String customerName, String address, String postalCode, String phoneNumber,
                                   Timestamp createDate, String createdBy, Timestamp lastUpdate,
                                   String lastUpdatedBy, int divisionId) {
        try {
            PreparedStatement addCustomers = DBConnection.getConnection().prepareStatement(CREATE_CUSTOMER);
            addCustomers.setString(1, customerName);
            addCustomers.setString(2, address);
            addCustomers.setString(3, postalCode);
            addCustomers.setString(4, phoneNumber);
            addCustomers.setTimestamp(5, createDate);
            addCustomers.setString(6, createdBy);
            addCustomers.setTimestamp(7, lastUpdate);
            addCustomers.setString(8, lastUpdatedBy);
            addCustomers.setInt(9, divisionId);
            addCustomers.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updateCustomer (String customerName, String address, String postalCode, String phoneNumber,
                                       Timestamp lastUpdate, String lastUpdatedBy,  int divisionId, int customerId) {
        try {
            //String updateCustomerData = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?,
            //                  Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement updateCustomer = DBConnection.getConnection().prepareStatement(UPDATE_CUSTOMER);
            updateCustomer.setString(1, customerName);
            updateCustomer.setString(2, address);
            updateCustomer.setString(3, postalCode);
            updateCustomer.setString(4, phoneNumber);
            updateCustomer.setTimestamp(5, lastUpdate);
            updateCustomer.setString(6, lastUpdatedBy);
            updateCustomer.setInt(7, divisionId);
            updateCustomer.setInt(8, customerId);
            updateCustomer.executeUpdate();
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
