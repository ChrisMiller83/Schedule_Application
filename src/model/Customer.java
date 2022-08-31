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
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
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
     * @param createDate date customer record was created
     * @param createdBy who created the customer record
     * @param lastUpdate date customer record was last updated
     * @param lastUpdatedBy who last updated the customer record
     * @param divisionID state/province id
     */
    public Customer(int customerId, String customerName, String address, String phoneNumber, String postalCode, Timestamp createDate, String createdBy,
                    Timestamp lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
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

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getFormattedCreateDate() {
        return getCreateDate();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Timestamp getFormattedLastUpdate() {
        return getLastUpdate();
    }

    public Timestamp getLastUpdate() {
        return  lastUpdate;
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
