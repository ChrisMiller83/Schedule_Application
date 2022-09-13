package controller;

/** @author Christopher Miller - Schedule Application - WGU C195 PA  */

import com.mysql.cj.protocol.Message;
import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.TimeZone;



public class AddApptController implements Initializable {

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
    private Timestamp createDate;
    private User createdBy;
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


    private void setCustomerCB() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(CustomerDAO.loadAllCustomers());
        customerCB.setItems(customerObservableList);
    }

    private void setContactCB() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList(ContactDAO.loadAllContacts());
        contactCB.setItems(contactObservableList);
    }
    private void setUserCB() {
        ObservableList<User> userObservableList = FXCollections.observableArrayList(UserDAO.loadAllUsers());
        userCB.setItems(userObservableList);
    }

    private void setTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Lunch", "Decision-making", "Problem-solving", "Team-building", "Brainstorming", "One-on-one", "Quarterly-planning","Check-in");
        typeCB.setItems(typeList);
    }

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

    public void cancelToAppointments(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "ApptsView.fxml");
    }

    @FXML
    void saveAppointment(ActionEvent actionEvent) throws IOException {

        if (validateAppt()) {
            try {
                title = titleTF.getText();
                description = descriptionTA.getText();
                location = locationTF.getText();
                type = typeCB.getValue();
                start = LocalDateTime.of(apptDatePicker.getValue(), startTimeCB.getValue());
                end = LocalDateTime.of(apptDatePicker.getValue(), endTimeCB.getValue());
                createDate = Timestamp.valueOf(LocalDateTime.now());
                createdBy = userCB.getValue();
                lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                lastUpdatedBy = userCB.getValue();
                customerId = customerCB.getValue().getCustomerId();
                userId = userCB.getValue().getUserId();
                contactId = contactCB.getValue().getContactId();

                boolean confirmAdd = Messages.addConfirmation(title);

                if(confirmAdd) {
                    AppointmentDAO.addAppointment(title, description, location, type, start, end, createDate, createdBy,
                            lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                    System.out.println(title + " added.");
                } else {
                    return;
                }
                //TODO add check for overlapping appointments
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ChangeView(actionEvent, "ApptsView.fxml");
        } else {
            return;
        }
    }

    private boolean validateAppt() {
        if (titleTF.getText().isEmpty()) {
            Messages.validateAppt(1);
            return false;
        }
        if (descriptionTA.getText().isEmpty()) {
            Messages.validateAppt(2);
            return false;
        }
        if (locationTF.getText().isEmpty()) {
            Messages.validateAppt(3);
            return false;
        }
        if (typeCB.getValue() == null) {
            Messages.validateAppt(4);
            return false;
        }
        if (apptDatePicker.getValue() == null) {
            Messages.validateAppt(5);
            return false;
        }
        if (startTimeCB.getValue() == null) {
            Messages.validateAppt(6);
            return false;
        }
        if (endTimeCB.getValue() == null) {
            Messages.validateAppt(7);
            return false;
        }
        if (customerCB.getValue() == null) {
            Messages.validateAppt(9);
            return false;
        }
        if (contactCB.getValue() == null) {
            Messages.validateAppt(10);
            return false;
        }
        if (userCB.getValue() == null) {
            Messages.validateAppt(13);
            return false;
        }

        if (apptDatePicker.getValue().isBefore(LocalDate.now())) {
            Messages.validateAppt(14);
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
            System.out.println("bookedApptStart  " + bookedApptStart);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
