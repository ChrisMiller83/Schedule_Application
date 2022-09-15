package model;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Customer model class.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId;
    private int divisionId;


    /** Empty Default Constructor */
    public Customer() {}

    /**
     * Customer constructor
     * @param customerId customer Id
     * @param customerName customer name
     * @param address customer address
     * @param phoneNumber customer phone number
     * @param postalCode zip code
     * @param createDate Date and time customer entry was created
     * @param createdBy Name of user who created the entry
     * @param lastUpdate Date and time customer entry was last updated
     * @param lastUpdatedBy Name of user who made the latest update to customer data
     * @param divisionId state/province id
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber,
                    Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryId,int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
        this.divisionId = divisionId;
    }

    /** customerId setter */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** customerId getter */
    public int getCustomerId() {
        return customerId;
    }

    /** customerName setter */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** customerName getter */
    public String getCustomerName() {
        return customerName;
    }

    /** address setter */
    public void setAddress(String address) {
        this.address = address;
    }

    /** address getter */
    public String getAddress() {
        return address;
    }

    /** phoneNumber setter */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** phoneNumber getter */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** postalCode setter */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** postalCode getter */
    public String getPostalCode() {
        return postalCode;
    }

    /** countryId getter */
    public int getCountryId() {
        return countryId;
    }

    /** countryId setter */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** createDate getter */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /** createDate setter */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /** createdBy getter */
    public String getCreatedBy() {
        return createdBy;
    }

    /** createdBy setter */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** lastUpdate getter */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** lastUpdate setter */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** lastUpdatedBy getter */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** lastUpdatedBy setter */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** divisionId getter */
    public int getDivisionId() {
        return divisionId;
    }

    /** divisionId setter */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }


    @Override
    public String toString() {
        return this.getCustomerName();
    }
}
