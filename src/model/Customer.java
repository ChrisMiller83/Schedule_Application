package model;

import DAO.CountryDAO;
import DAO.DivisionDAO;
import com.mysql.cj.protocol.WatchableStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.plaf.IconUIResource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Customer model class.
 */
public class Customer {
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int divisionID;
    private int customerCountry;



    /**
     * Customer constructor
     * @param customerId customer Id
     * @param customerName customer name
     * @param address customers street address
     * @param phoneNumber customers phone number
     * @param postalCode customers zip code
     * @param createdDate date and time customer was created
     * @param createdBy user's name who created the customer
     * @param lastUpdated date and time customer info was updated or changed
     * @param lastUpdatedBy user's name that last updated the customer info
     * @param divisionID state id code
     * @param customerCountry countryID
     */
    public Customer(int customerId, String customerName, String address,
                    String phoneNumber, String postalCode, Timestamp createdDate,
                    String createdBy, Timestamp lastUpdated, String lastUpdatedBy,
                    int divisionID, int customerCountry) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.customerCountry = customerCountry;
    }

    public Customer(int i, String text, String addressTFText, String postalCodeTFText, String phoneNumTFText, int countryId, int countryDivision) {
    }


    public static AtomicInteger getUniqueCustomerId = new AtomicInteger(allCustomers.size() + 1);

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setCreatedDateTime(Timestamp createdDateTime) {
        this.createdDate = createdDate;
    }

    public Timestamp getCreatedDateTime() {
        return createdDate;
    }

    public Timestamp getFormattedCreatedDate() {
        return getCreatedDateTime();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Timestamp getFormattedLastUpdated() {
        return getLastUpdated();
    }

    public Timestamp getLastUpdated() {
        return  lastUpdated;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public int getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(int customerCountry) {
        this.customerCountry = customerCountry;
    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    public static void updateCustomer(int customerId, Customer newCustomer) {
        for (int i = 0; i < allCustomers.size(); ++i) {
            if (allCustomers.get(i).getCustomerId() == customerId) {
                allCustomers.set(i, newCustomer);
            }
        }
    }



    public static boolean deleteCustomer(Customer selectedCustomer) {
        allCustomers.remove(selectedCustomer);
        return true;
    }



    @Override
    public String toString() {
        return this.getCustomerName();
    }
}
