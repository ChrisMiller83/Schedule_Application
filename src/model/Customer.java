package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Customer model class.
 */
public class Customer {
    public static List<Customer> customers = new ArrayList<>();

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
     * @param address customer address
     * @param phoneNumber customer phone number
     * @param postalCode zip code
     * @param createdDate date customer record was created
     * @param createdBy who created the customer record
     * @param lastUpdated date customer record was last updated
     * @param lastUpdatedBy who last updated the customer record
     * @param divisionID state/province id
     */
    public Customer(int customerId, String customerName, String address, String phoneNumber, String postalCode, Timestamp createdDate, String createdBy,
                    Timestamp lastUpdated, String lastUpdatedBy, int divisionID) {
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
//        this.customerCountry = customerCountry;
    }

    public Customer(int i, String text, String addressTFText, String postalCodeTFText, String phoneNumTFText, int countryId, int countryDivision) {
    }


    public static AtomicInteger getUniqueCustomerId = new AtomicInteger(customers.size() + 1);



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

    public static List<Customer> getCustomers() {
        return customers;
    }

    public static void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
    }

    public static void updateCustomer(int customerId, Customer newCustomer) {
        for (int i = 0; i < customers.size(); ++i) {
            if (customers.get(i).getCustomerId() == customerId) {
                customers.set(i, newCustomer);
            }
        }
    }



    public static boolean deleteCustomer(Customer selectedCustomer) {
        customers.remove(selectedCustomer);
        return true;
    }



    @Override
    public String toString() {
        return this.getCustomerName();
    }
}
