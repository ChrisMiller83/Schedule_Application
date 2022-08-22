package model;

import java.sql.Timestamp;

/**
 * Customer model class.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String phoneNumber;
    private String zipCode;
    private Timestamp createdDateTime;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int divisionID;


    /**
     * Empty default Customer constructor.
     */
    public Customer() {
    }

    /**
     * Customer constructor
     * @param customerId customer Id
     * @param customerName customer name
     * @param address customers street address
     * @param phoneNumber customers phone number
     * @param zipCode customers zip code
     * @param createdDateTime date and time customer was created
     * @param createdBy user's name who created the customer
     * @param lastUpdated date and time customer info was updated or changed
     * @param lastUpdatedBy user's name that last updated the customer info
     * @param divisionID state id code
     */
    public Customer(int customerId, String customerName, String address,
                    String phoneNumber, String zipCode, Timestamp createdDateTime,
                    String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionID) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.createdDateTime = createdDateTime;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

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

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setCreatedDateTime(Timestamp createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Timestamp getCreatedDateTime() {
        return createdDateTime;
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

    @Override
    public String toString() {
        return customerId + ": " + customerName;
    }
}
