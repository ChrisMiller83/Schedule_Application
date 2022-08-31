package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
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
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
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
     * @param createDate  day the appointment was made
     * @param createdBy  who created the appointment
     * @param lastUpdate  if and when the appointment was changed
     * @param lastUpdatedBy  who changed the appointment
     * @param customerId  customer id the appointment is with
     * @param userId  user id
     * @param contactId  contact id
     */
    public Appointment(int apptId, String apptTitle, String apptDescription, String apptLocation,
                       String apptType, Timestamp startDateTime, Timestamp endDateTime,
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

    public Appointment() {

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
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * getStartDateTime
     * @return returns the date and time the appointment starts
     */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * setEndDateTime
     * @param endDateTime sets the date and time the appointment ends
     */
    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * getEndDateTime
     * @return returns the date and time the appointment ends
     */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * setCreatedDate
     * @param createdDate sets the date and time the appointment was created
     */
    public void setCreateDate(Timestamp createdDate) {
        this.createDate = createdDate;
    }

    /**
     * getCreatedDate
     * @return returns the date and time the appointment was created
     */
    public Timestamp getCreateDate(){
        return createDate;
    }

    /**
     * setCreatedBy
     * @param createdBy sets the name of the user who created the appointment.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * getCreatedBy
     * @return returns the user's name who created the appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * setLastUpdated
     * @param lastUpdated sets the time the appointment was last updated.
     */
    public void setLastUpdate(Timestamp lastUpdated) {
        this.lastUpdate = lastUpdated;
    }

    /**
     * getLastUpdated
     * @return returns the time the appointment was last updated
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * setLastUpdatedBy
     * @param lastUpdatedBy set the user who last updated the appointment
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * getLastUpdatedBy
     * @return returns the user's name who last updated the appointment
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
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





    public static List<Appointment> getAppointmentList() {
        return appointmentsList;
    }



    public static void addAppt(Appointment newAppointment) {
        appointmentsList.add(newAppointment);
    }

    public static void updateAppt(int apptId, Appointment updatedAppt) {
        for (int i = 0; i < appointmentsList.size(); ++i) {
            if (appointmentsList.get(i).getApptId() == apptId) {
                appointmentsList.set(i, updatedAppt);
            }
        }
    }

    public static boolean deleteAppt(Appointment selectedAppt) {
        return appointmentsList.remove(selectedAppt);
    }
}


