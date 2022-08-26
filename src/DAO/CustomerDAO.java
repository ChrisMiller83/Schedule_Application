package DAO;

import model.Country;
import model.Customer;

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

    public static final String QUERY_ALL_CUSTOMERS = "SELECT * FROM " + TABLE_CUSTOMERS;

    public static final String QUERY_ALL_CUSTOMER_NAMES = "SELECT " + TABLE_CUSTOMERS + '.' + COLUMN_CUSTOMER_NAME +
            " FROM " + TABLE_CUSTOMERS;

    public static void loadAllCustomers() {
        try {
            PreparedStatement loadCustomers = DBConnection.getConnection().prepareStatement(QUERY_ALL_CUSTOMERS);
            ResultSet result = loadCustomers.executeQuery();

            while (result.next()) {
                int customerId = result.getInt(COLUMN_CUSTOMER_ID);
                String customerName = result.getString(COLUMN_CUSTOMER_NAME);
                String address = result.getString(COLUMN_CUSTOMER_ADDRESS);
                String zipCode = result.getString(COLUMN_CUSTOMER_POSTAL_CODE);
                String phoneNumber = result.getString(COLUMN_CUSTOMER_PHONE);
                Timestamp createdDate = result.getTimestamp(COLUMN_CUSTOMER_CREATED_DATE);
                String createdBy = result.getString(COLUMN_CUSTOMER_CREATED_BY);
                Timestamp lastUpdated = result.getTimestamp(COLUMN_CUSTOMER_LAST_UPDATE);
                String lastUpdatedBy = result.getString(COLUMN_CUSTOMER_LAST_UPDATED_BY);
                int divisionId = result.getInt(COLUMN_CUSTOMER_DIVISION_ID);
                Customer customer = new Customer(customerId, customerName, address, zipCode, phoneNumber,
                        createdDate, createdBy, lastUpdated, lastUpdatedBy, divisionId);
                Customer.customerArrayList.add(customer);
            }
            // TODO: add customer report
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
