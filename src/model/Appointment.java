package model;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Appointment model class
 */
public class Appointment {
    private int apptId;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private LocalDate apptDate;
    private LocalTime start;
    private LocalTime end;
    private String month;
    private Integer total;

    /**
     * Appointment constructor
     * @param apptId the appointment's id number
     * @param apptTitle apptTitle of the appointment
     * @param apptDescription  description of what the appointment is for
     * @param apptLocation  location of the appointment
     * @param apptType  type of appointment
     * @param startDateTime start time and date of the appointment
     * @param endDateTime  end time and date of the appointment
     * @param customerId  customer id the appointment is with
     * @param userId  user id
     * @param contactId  contact id
     */
    public Appointment(int apptId, String apptTitle, String apptDescription, String apptLocation,
                       String apptType, LocalDateTime startDateTime, LocalDateTime endDateTime,
                       Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                       int customerId, int userId, int contactId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this. contactId = contactId;
    }

    /**
     * Appointment constructor -- used in the ReportsController to display upcoming appointments that match the customerId given.
     * @param apptId -- appointment id
     * @param apptDate -- appointent date
     * @param start -- appointment start time
     * @param end -- appointment end time
     * @param apptTitle -- title
     * @param apptType -- type of appointment
     * @param apptDescription -- description
     * @param customerId -- customerId used to find all appointments that match this id.
     */
    public Appointment(int apptId, LocalDate apptDate, LocalTime start, LocalTime end, String apptTitle, String apptType, String apptDescription, int customerId) {
        this.apptId = apptId;
        this.apptDate = apptDate;
        this.start = start;
        this.end = end;
        this.apptTitle = apptTitle;
        this.apptType = apptType;
        this.apptDescription = apptDescription;
        this.customerId = customerId;
    }

    /**
     * Appointment construction -- used in the ReportsController to display the count of appointments by Month and Type
     * @param month -- displays the month of the appointments
     * @param apptType -- displays the type of appointments
     * @param total -- displays the total count of the appointments by Month and Type
     */
    public Appointment(String month, String apptType, Integer total) {
        this.month = month;
        this.apptType = apptType;
        this.total = total;
    }

    /**
     * @return returns the apptId
     */
    public int getApptId() {
        return apptId;
    }

    /**
     * @param apptId sets the appointment id
     */
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    /**
     * getApptTitle
     * @return returns the appointments apptTitle
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     * setApptTitle
     * @param apptTitle sets the appointments apptTitle
     */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**
     * getApptDescription
     * @return returns a description of the appointment
     */
    public String getApptDescription() {
        return apptDescription;
    }

    /**
     * setApptDescription
     * @param apptDescription sets the description of the appointment
     */
    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    /**
     * getApptLocation
     * @return returns the appointment location.
     */
    public String getApptLocation() {
        return  apptLocation;
    }

    /**
     * setApptLocation
     * @param apptLocation sets the appointment location.
     */
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /**
     * getApptType
     * @return returns type of appointment.
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * setApptType
     * @param apptType sets the type of appointment
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /**
     * getStartDateTime
     * @return returns the date and time the appointment starts
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * setStartDateTime
     * @param startDateTime sets the date and time the appointment starts
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * getEndDateTime
     * @return returns the date and time the appointment ends
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * setEndDateTime
     * @param endDateTime sets the date and time the appointment ends
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * getCreatedDate
     * @return returns/gets the date and time the appointment was created
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * setCreateDate
     * @param createDate sets the date and time the appointment was created
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * getCreatedBy
     * @return returns/gets the user name that created the appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * setCreatedBy
     * @param createdBy sets the user name that created the appointment
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * getLastUpdate
     * @return returns/gets the date and time the appointment was last updated
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * setLastUpdate
     * @param lastUpdate sets the date and time of last update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * getLastUpdatedBy
     * @return returns/gets the user name who last updated the appointment
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * setLastUpdatedBy
     * @param lastUpdateBy sets the user name who last updated the appointment
     */
    public void setLastUpdatedBy(String lastUpdateBy) {
        this.lastUpdatedBy = lastUpdateBy;
    }

    /**
     * getCustomerId
     * @return returns the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * setCustomerId
     * @param customerId sets the customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * getUserId
     * @return returns the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * setUserId
     * @param userId sets the userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * getContactId gets the contactId
     * @return contactId -- returns the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * setContactId
     * @param contactId sets the contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * getApptDate
     * @return apptDate -- Date of an appointment
     */
    public LocalDate getApptDate() {
        return apptDate;
    }

    public void setApptDate(LocalDate apptDate) {
        this.apptDate = apptDate;
    }

    /**
     * getStart
     * @return start -- start time of appointment
     */
    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    /**
     * getEnd
     * @return end -- end time of appointment
     */
    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }



    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}


