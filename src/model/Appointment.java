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

    /** Empty default constructor */
    public Appointment() {};

    /**
     * Main Appointment constructor
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

    /** the apptId getter */
    public int getApptId() {
        return apptId;
    }

    /** apptId setter */
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    /** apptTitle getter */
    public String getApptTitle() {
        return apptTitle;
    }

    /** apptTitle setter */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /** apptDescription getter */
    public String getApptDescription() {
        return apptDescription;
    }

    /** apptDescription setter */
    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    /** apptLocation getter */
    public String getApptLocation() {
        return  apptLocation;
    }

    /** apptLocation setter */
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /** apptType getter */
    public String getApptType() {
        return apptType;
    }

    /** apptType setter */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /** startDateTime getter */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /** startDateTime setter */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /** endDateTime getter */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /** endDateTime setter */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /** createdDate getter */
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

    /** lastUpdateBy setter */
    public void setLastUpdatedBy(String lastUpdateBy) {
        this.lastUpdatedBy = lastUpdateBy;
    }

    /** customerId getter */
    public int getCustomerId() {
        return customerId;
    }

    /** customerId setter */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** userId getter */
    public int getUserId() {
        return userId;
    }

    /** userId setter */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** contactId getter */
    public int getContactId() {
        return contactId;
    }

    /** contactId setter */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** apptDate getter */
    public LocalDate getApptDate() {
        return apptDate;
    }

    /** apptDate setter */
    public void setApptDate(LocalDate apptDate) {
        this.apptDate = apptDate;
    }

    /** start time getter */
    public LocalTime getStart() {
        return start;
    }

    /** start time setter */
    public void setStart(LocalTime start) {
        this.start = start;
    }

    /** end time getter */
    public LocalTime getEnd() {
        return end;
    }

    /** end time setter */
    public void setEnd(LocalTime end) {
        this.end = end;
    }

    /** month getter */
    public String getMonth() {
        return month;
    }

    /** month setter */
    public void setMonth(String month) {
        this.month = month;
    }

    /** total getter */
    public Integer getTotal() {
        return total;
    }

    /** total setter */
    public void setTotal(Integer total) {
        this.total = total;
    }
}


