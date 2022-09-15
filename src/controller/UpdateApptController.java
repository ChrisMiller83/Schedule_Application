package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Update Appt Controller -- allows appointments to be updated/modified and saved to the db.
 */
public class UpdateApptController implements Initializable {

    private static Appointment selectedAppt;

    private int apptId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    private Timestamp lastUpdate;
    private User lastUpdatedBy;
    private LocalDate apptDate;

    @FXML private TextField appointmentIdTF;
    @FXML private ChoiceBox<User> userCB;
    @FXML private ChoiceBox<Customer> customerCB;
    @FXML private TextField titleTF;
    @FXML private TextField locationTF;
    @FXML private ChoiceBox<String> typeCB;
    @FXML private ChoiceBox<Contact> contactCB;
    @FXML private DatePicker apptDatePicker;
    @FXML private ChoiceBox<LocalTime> startTimeCB;
    @FXML private ChoiceBox<LocalTime> endTimeCB;
    @FXML private TextArea descriptionTA;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;

    /**
     * getSelectedAppt -- gets the selected appt from the ApptsController
     * @param appointment appt that was selected.
     */
    public static void getSelectedAppt(Appointment appointment) {
        selectedAppt = appointment;
    }

    /**
     * cancelToAppointments -- cancel an appt update and redirects to ApptsView
     * @param actionEvent cancel button pressed
     * @throws IOException
     */
    public void cancelToAppointments(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "ApptsView.fxml");
    }

    /**
     * updateAppointment -- calls validateAppt method to run validation checks,
     * if passes validation checks, all info from the fields are gathered and appt is
     * updated in the db otherwise error messages are given.
     * @param actionEvent saveBtn was pressed
     * @throws IOException
     */
    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        /** validateAppt method called to check for errors */
        if (validateAppt()) {
            /** if no errors gather data from fields */
            try {
                title = titleTF.getText();
                description = descriptionTA.getText();
                location = locationTF.getText();
                type = typeCB.getValue();
                start = LocalDateTime.of(apptDatePicker.getValue(), startTimeCB.getValue());
                end = LocalDateTime.of(apptDatePicker.getValue(), endTimeCB.getValue());
                lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                lastUpdatedBy = userCB.getValue();
                customerId = customerCB.getValue().getCustomerId();
                userId = userCB.getValue().getUserId();
                contactId = contactCB.getValue().getContactId();
                apptId = Integer.parseInt(appointmentIdTF.getText());
                /** Confirmation message to add appt */
                boolean updateConfirm = Messages.updateConfirmation(title);
                /** If confirmation was ok/yes appt is added to the db. */
                if(updateConfirm) {
                    AppointmentDAO.updateAppointment(title, description, location, type, start, end,
                            lastUpdate, lastUpdatedBy, customerId, userId, contactId, apptId);
                    /** console message verifying update */
                    System.out.println(title + " updated.");
                } else {
                    /** If confirmation was no/cancel, returns to addApptView with current add data in the fields */
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /** ChangeView brings the user back to the ApptsView page when an appointment is updated to the db. */
            new ChangeView(actionEvent, "ApptsView.fxml");
        } else {
            /** validateAppt found an error, error message displayed, and user is returned to addAppt page. */
            return;
        }
    }

    /**
     * validateAppt:  Is called in updateAppointment
     *  It checks for empty fields/choice boxes/date pickers, checks if appt is within EST business hours,
     *  and checks if appts overlap with booked customer appts.
     * @return false if one of the checks are found, otherwise returns true and allow updateAppointment to continue
     */
    private boolean validateAppt() {
        /** Checker:  Title text field is empty */
        if (titleTF.getText().isEmpty()) {
            Messages.validateAppt(1);
            return false;
        }
        /** Checker:  Description text field is empty */
        if (descriptionTA.getText().isEmpty()) {
            Messages.validateAppt(2);
            return false;
        }
        /** Checker:  Description length is more than 50 chars */
        if (descriptionTA.getText().length() > 50) {
            Messages.validateAppt(17);
            return false;
        }
        /** Checker:  Location text field is empty */
        if (locationTF.getText().isEmpty()) {
            Messages.validateAppt(3);
            return false;
        }
        /** Checker:  Type Choice box is empty */
        if (typeCB.getValue() == null) {
            Messages.validateAppt(4);
            return false;
        }
        /** Checker:  Appt Date Picker is empty */
        if (apptDatePicker.getValue() == null) {
            Messages.validateAppt(5);
            return false;
        }
        /** Checker:  Start Time Choice box is empty */
        if (startTimeCB.getValue() == null) {
            Messages.validateAppt(6);
            return false;
        }
        /** Checker:  End Time Choice box is empty */
        if (endTimeCB.getValue() == null) {
            Messages.validateAppt(7);
            return false;
        }
        /** Checker:  Customer Choice box is empty */
        if (customerCB.getValue() == null) {
            Messages.validateAppt(9);
            return false;
        }
        /** Checker:  Contact Choice box is empty */
        if (contactCB.getValue() == null) {
            Messages.validateAppt(10);
            return false;
        }
        /** Checker:  User Choice box is empty */
        if (userCB.getValue() == null) {
            Messages.validateAppt(13);
            return false;
        }
        /** Checker:  Appt Date is earlier than current local date */
        if (apptDatePicker.getValue().isBefore(LocalDate.now())) {
            Messages.validateAppt(14);
            return false;
        }
        /** Checker:  Appt Date = Current local date but Appt Start time is earlier than current local time */
        if (apptDatePicker.getValue().equals(LocalDate.now()) && startTimeCB.getValue().isBefore(LocalTime.now())) {
            Messages.validateAppt(15);
            return false;
        }
        /** Checker:  Appt Date = Current local date but Appt End time is earlier than current local time */
        if (apptDatePicker.getValue().equals(LocalDate.now()) && endTimeCB.getValue().isBefore(LocalTime.now())) {
            Messages.validateAppt(16);
            return false;
        }

        /** pickedApptDate, pickedStartTime, pickedEndTime, osZoneId(operating systems ZoneId), and ZonedDateTime objects for the appointment */
        LocalDate pickedApptDate = apptDatePicker.getValue();
        LocalTime pickedStartTime = startTimeCB.getValue();
        LocalTime pickedEndTime = endTimeCB.getValue();
        ZoneId osZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime pickedStartZDT = ZonedDateTime.of(pickedApptDate, pickedStartTime, osZoneId);
        ZonedDateTime pickedEndZDT = ZonedDateTime.of(pickedApptDate, pickedEndTime, osZoneId);

        /** pickedStartLDT and pickedEndLDT used to check against booked appointments */
        LocalDateTime pickedStartLDT = LocalDateTime.of(pickedApptDate, pickedStartTime);
        LocalDateTime pickedEndLDT = LocalDateTime.of(pickedApptDate, pickedEndTime);

        /** Convert appt times to EST ZonedDateTime */
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZonedDateTime apptStartToEstZDT = pickedStartZDT.withZoneSameInstant(estZoneId);
        ZonedDateTime apptEndToEstZDT = pickedEndZDT.withZoneSameInstant(estZoneId);

        /** convert appt times to local time of EST in order to check against business hours */
        LocalTime startEST = apptStartToEstZDT.toLocalTime();
        LocalTime endEST = apptEndToEstZDT.toLocalTime();

        /** Creates a LocalTime for the start and end of the business day */
        LocalTime businessDayStart = LocalTime.of(8, 0);
        LocalTime businessDayEnd = LocalTime.of(22, 0);

        /** Checks start and end times do not match, or that start is after end, or end is before start */
        if(pickedEndTime.isBefore(pickedStartTime) || pickedStartTime.equals(pickedEndTime)) {
            Messages.validateAppt(8);
            return false;
        }

        /** Checks if appointment times are within business hours */
        if(startEST.isBefore(businessDayStart) || endEST.isBefore(businessDayStart) ||
                startEST.isAfter(businessDayEnd) || endEST.isAfter(businessDayEnd)) {
            Messages.validateAppt(12);
            return false;
        }

        /** Check customer overlapping appointment times */
        customerId = customerCB.getValue().getCustomerId();
        ObservableList<Appointment> customerAppts = AppointmentDAO.loadCustomerAppts(customerId);

        for(Appointment appointment: customerAppts) {
            LocalDateTime bookedApptStart = appointment.getStartDateTime();
            LocalDateTime bookedApptEnd = appointment.getEndDateTime();

            /** ex: picked start 10:30 end 11:30  and booked start 10:00 end 12:00 (ps-pe 10:30-11:30 is inside bs-be 10:00-12:00) */
            if((pickedStartLDT.isAfter(bookedApptStart)) && (pickedEndLDT.isBefore(bookedApptEnd))){
                Messages.overlappingAppts(appointment.getApptId(), appointment.getStartDateTime(), appointment.getEndDateTime());
                return false;
            }
            /** ex: picked end 11:30  and booked start 10:00 end 12:00 (pe of 11:30 is inside bs-be 10:00-12:00) */
            if((pickedEndLDT.isAfter(bookedApptStart)) && (pickedEndLDT.isBefore(bookedApptEnd))) {
                Messages.overlappingAppts(appointment.getApptId(), appointment.getStartDateTime(), appointment.getEndDateTime());
                return false;
            }
            /** ex: picked start 10:30 and booked start 10:00 end 12:00 (ps of 10:30 is inside bs-be 10:00-12:00) */
            if ((pickedStartLDT.isAfter(bookedApptStart)) && (pickedStartLDT.isBefore(bookedApptEnd))) {
                Messages.overlappingAppts(appointment.getApptId(), appointment.getStartDateTime(), appointment.getEndDateTime());
                return false;
            }
            /** ex: picked start 09:30 end 12:30  and booked start 10:00 end 12:00 (bs-be 10:00-12:00 is inside ps-pe 09:30-12:30) */
            if((pickedStartLDT.isBefore(bookedApptStart)) && (pickedEndLDT.isAfter(bookedApptEnd))) {
                Messages.overlappingAppts(appointment.getApptId(), appointment.getStartDateTime(), appointment.getEndDateTime());
                return false;
            }
            /** ex: picked start 10:00 end 12:00  and booked start 10:00 end 12:00 (ps of 10:00 == bs 10:00 or pe 12:00 == be 12:00) */
            if(pickedStartLDT.equals(bookedApptStart) || (pickedEndLDT.equals(bookedApptEnd))) {
                Messages.overlappingAppts(appointment.getApptId(), appointment.getStartDateTime(), appointment.getEndDateTime());
                return false;
            }
        }
        return true;
    }

    /**
     * setSelectedAppt -- gets data of appt selected to update and displays it in appropriate fields
     * @param selectedAppt -- appointment selected in ApptsController that is being updated.
     */
    public void setSelectedAppt(Appointment selectedAppt) {
        appointmentIdTF.setText(Integer.toString(selectedAppt.getApptId()));
        titleTF.setText(selectedAppt.getApptTitle());
        descriptionTA.setText(selectedAppt.getApptDescription());
        locationTF.setText(selectedAppt.getApptLocation());
        typeCB.getSelectionModel().select(selectedAppt.getApptType());
        apptDatePicker.setValue(selectedAppt.getStartDateTime().toLocalDate());
        startTimeCB.getSelectionModel().select(selectedAppt.getStartDateTime().toLocalTime());
        endTimeCB.getSelectionModel().select(selectedAppt.getEndDateTime().toLocalTime());

        /** Loops through the customer list to find the matching customerId of the selectedAppt and displays the
         * selectedAppts' customer name.
         */
        for (Customer customer : CustomerDAO.loadAllCustomers()) {
            if (customer.getCustomerId() == selectedAppt.getCustomerId()) {
                customerCB.setValue(customer);
            }
        }

        /** Loops through the contact list to find the matching contactId of the selectedAppt and displays the
         * selectedAppts' contact name.
         */
        for (Contact contact : ContactDAO.loadAllContacts()) {
            if (contact.getContactId() == selectedAppt.getContactId()) {
                contactCB.setValue(contact);
            }
        }

        /** Loops through the user list to find the matching userId of the selectedAppt and displays the
         * selectedAppts' user name.
         */
        for (User user : UserDAO.loadAllUsers()) {
            if(user.getUserId() == selectedAppt.getUserId()) {
                userCB.setValue(user);
            }
        }
    }

    /**
     * setCustomerCB -- adds customer names to the customer choice box
     */
    private void setCustomerCB() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(CustomerDAO.loadAllCustomers());
        customerCB.setItems(customerObservableList);
    }

    /**
     * setContactCB -- adds contact names to the contact choice box
     */
    private void setContactCB() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList(ContactDAO.loadAllContacts());
        contactCB.setItems(contactObservableList);
    }

    /**
     * setUserCB -- adds user names to the user choice box
     */
    private void setUserCB() {
        ObservableList<User> userObservableList = FXCollections.observableList(UserDAO.loadAllUsers());
        userCB.setItems(userObservableList);
    }

    /**
     * setTypeCB -- adds hard coded type choices to the type choice box
     */
    private void setTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Lunch", "Decision-making", "Problem-solving", "Team-building", "Brainstorming", "One-on-one", "Quarterly-planning","Check-in");
        typeCB.setItems(typeList);
    }

    /**
     * setTimeCB -- adds time choices to the start/end choice boxes
     *  times choices start at 04:00-22:00 to allow for different time zones to choice appt times
     */
    private void setTimeCB() {
        ObservableList<LocalTime> timeOptions = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(4, 0);
        LocalTime endTime = LocalTime.of(22, 0);

        timeOptions.add(startTime);

        while (startTime.isBefore(endTime)){
            startTime = startTime.plusMinutes(30);
            timeOptions.add(startTime);
        }
        startTimeCB.setItems(timeOptions);
        endTimeCB.setItems(timeOptions);
    }

    /**
     * initialize -- loads all choice boxes when page is opened, as well as, gets the selectedAppt and
     * populates its data in appropriate fields.
     * @param url not used
     * @param resourceBundle not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSelectedAppt(selectedAppt);
        UserDAO.loadAllUsers();
        CustomerDAO.loadAllCustomers();
        ContactDAO.loadAllContacts();
        setCustomerCB();
        setUserCB();
        setContactCB();
        setTimeCB();
        setTypeCB();

    }
}
