package model;

import DAO.ContactDaoImpl;
import DAO.UserDaoImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private LocalDateTime createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    public static List<Appointment> appointments = new ArrayList<>();

    /**
     * Appointment constructor
     * @param apptId the appointment's id number
     * @param apptTitle apptTitle of the appointment
     * @param apptDescription  description of what the appointment is for
     * @param apptLocation  location of the appointment
     * @param apptType  type of appointment
     * @param startDateTime start time and date of the appointment
     * @param endDateTime  end time and date of the appointment
     * @param createdDate  day the appointment was made
     * @param createdBy  who created the appointment
     * @param lastUpdated  if and when the appointment was changed
     * @param lastUpdatedBy  who changed the appointment
     * @param customerId  customer id the appointment is with
     * @param userId  user id
     * @param contactId  contact id
     */
    public Appointment(int apptId, String apptTitle, String apptDescription, String apptLocation,
                       String apptType, Timestamp startDateTime, Timestamp endDateTime,
                       Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy,
                       int customerId, int userId, int contactId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this. contactId = contactId;
    }



    /**
     * @param apptId sets the appointment id
     */
    public void setapptId(int apptId) {
        this.apptId = apptId;
    }

    /**
     * @return returns the apptId
     */
    public int getapptId() {
        return apptId;
    }

    /**
     * setapptTitle
     * @param apptTitle sets the appointments apptapptTitle
     */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**
     * getapptTitle
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
    public Timestamp getStartDateTime() {
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
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * setCreatedDate
     * @param createdDate sets the date and time the appointment was created
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * getCreatedDate
     * @return returns the date and time the appointment was created
     */
    public Timestamp getCreatedDate(){
        return createdDate;
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
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * getLastUpdated
     * @return returns the time the appointment was last updated
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
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

    /**
     * getContact gets the contact from the Contact model associated with the appointment.
     * @return returns the contact
     * @throws SQLException throws SQL error if found and continues.
     */
    public Contact getContact() throws SQLException {
        ContactDaoImpl contactDao = new ContactDaoImpl();
        Contact contact = contactDao.getContact(getContactId());
        return contact;
    }

    /**
     * getContactName gets the contact name from the Contact model associated with the appointment.
     * @return returns the contact's name.
     * @throws SQLException throws SQL error if found and continues.
     */
    public String getContactName() throws SQLException {
        Contact contact = getContact();
        String name = contact.getContactName();
        return name;
    }

    /**
     * getUser gets the user from the User model associated with the appointment.
     * @return returns the User
     * @throws SQLException throws SQL error if found and continues.
     */
    public User getUser() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = UserDaoImpl.getUser(getUserId());
        return user;
    }

    public static List<Appointment> getAppointments() {
        return appointments;
    }

    public static void updateAppt(int getapptId, Appointment appointment) {
    }
}


