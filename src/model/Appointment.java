package model;

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
    public static List<Appointment> appointmentsList = new ArrayList<>();

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

    public Appointment(String month, String apptType, Integer total) {
        this.month = month;
        this.apptType = apptType;
        this.total = total;
    }


    /**
     * @param apptId sets the appointment id
     */
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    /**
     * @return returns the apptId
     */
    public int getApptId() {
        return apptId;
    }

    /**
     * setApptTitle
     * @param apptTitle sets the appointments apptTitle
     */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**
     * getApptTitle
     * @return returns the appointments apptTitle
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     * setApptDescription
     * @param apptDescription sets the description of the appointment
     */
    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    /**
     * getApptDescription
     * @return returns a description of the appointment
     */
    public String getApptDescription() {
        return apptDescription;
    }

    /**
     * setApptLocation
     * @param apptLocation sets the appointment location.
     */
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /**
     * getApptLocation
     * @return returns the appointment location.
     */
    public String getApptLocation() {
        return  apptLocation;
    }

    /**
     * setApptType
     * @param apptType sets the type of appointment
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /**
     * getApptType
     * @return returns type of appointment.
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * setStartDateTime
     * @param startDateTime sets the date and time the appointment starts
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * getStartDateTime
     * @return returns the date and time the appointment starts
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * setEndDateTime
     * @param endDateTime sets the date and time the appointment ends
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * getEndDateTime
     * @return returns the date and time the appointment ends
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdateBy) {
        this.lastUpdatedBy = lastUpdateBy;
    }

    /**
     * setCustomerId
     * @param customerId sets the customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * getCustomerId
     * @return returns the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * setUserId
     * @param userId sets the userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * getUserId
     * @return returns the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * setContactId
     * @param contactId sets the contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * getContactId gets the contactId
     * @return returns the contactId
     */
    public int getContactId() {
        return contactId;
    }

    public LocalDate getApptDate() {
        return apptDate;
    }

    public void setApptDate(LocalDate apptDate) {
        this.apptDate = apptDate;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public static List<Appointment> getAppointmentList() {
        return appointmentsList;
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


