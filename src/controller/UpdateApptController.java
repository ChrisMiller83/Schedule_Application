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

    public static void getSelectedAppt(Appointment appointment) {
        selectedAppt = appointment;
    }

    public void cancelToAppointments(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "ApptsView.fxml");
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        if (validateAppt()) {
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

                boolean updateConfirm = Messages.updateConfirmation(title);

                if(updateConfirm) {
                    AppointmentDAO.updateAppointment(title, description, location, type, start, end,
                            lastUpdate, lastUpdatedBy, customerId, userId, contactId, apptId);
                    System.out.println(title + " updated.");
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
            Messages.validateAppt(8);
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

        /** ApptDate, startTime, endTime, ZoneId, and ZonedDateTime objects for the appointment */
        LocalDate apptDate = apptDatePicker.getValue();
        LocalTime apptStartTime = startTimeCB.getValue();
        LocalTime apptEndTime = endTimeCB.getValue();
        ZoneId osZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZonedDateTime apptStartZDT = ZonedDateTime.of(apptDate, apptStartTime, osZoneId);
        ZonedDateTime apptEndZDT = ZonedDateTime.of(apptDate, apptEndTime, osZoneId);

        /** Convert appt times to local zoneId */
        ZonedDateTime apptStartToEstZDT = apptStartZDT.withZoneSameInstant(estZoneId);
        ZonedDateTime apptEndToEstZDT = apptEndZDT.withZoneSameInstant(estZoneId);



        /** convert appt times to local time */
        LocalTime startEST = apptStartToEstZDT.toLocalTime();
        LocalTime endEST = apptEndToEstZDT.toLocalTime();

        LocalTime businessDayStart = LocalTime.of(8, 0);
        LocalTime businessDayEnd = LocalTime.of(22, 0);

        if(apptEndTime.isBefore(apptStartTime) || apptStartTime.equals(apptEndTime)) {
            Messages.validateAppt(8);
            return false;
        }



        if(startEST.isBefore(businessDayStart) || endEST.isBefore(businessDayStart) ||
                startEST.isAfter(businessDayEnd) || endEST.isAfter(businessDayEnd)) {
            Messages.validateAppt(12);
            return false;
        }

        customerId = customerCB.getValue().getCustomerId();
        ObservableList<Appointment> appointmentObservableList = AppointmentDAO.loadAllAppts();



        return true;
    }

    public void setSelectedAppt(Appointment selectedAppt) {
        appointmentIdTF.setText(Integer.toString(selectedAppt.getApptId()));
        titleTF.setText(selectedAppt.getApptTitle());
        descriptionTA.setText(selectedAppt.getApptDescription());
        locationTF.setText(selectedAppt.getApptLocation());
        typeCB.getSelectionModel().select(selectedAppt.getApptType());
        apptDatePicker.setValue(selectedAppt.getStartDateTime().toLocalDate());
        startTimeCB.getSelectionModel().select(selectedAppt.getStartDateTime().toLocalTime());
        endTimeCB.getSelectionModel().select(selectedAppt.getEndDateTime().toLocalTime());

        for (Customer customer : CustomerDAO.loadAllCustomers()) {
            if (customer.getCustomerId() == selectedAppt.getCustomerId()) {
                customerCB.setValue(customer);
            }
        }

        for (Contact contact : ContactDAO.loadAllContacts()) {
            if (contact.getContactId() == selectedAppt.getContactId()) {
                contactCB.setValue(contact);
            }
        }

        for (User user : UserDAO.loadAllUsers()) {
            if(user.getUserId() == selectedAppt.getUserId()) {
                userCB.setValue(user);
            }
        }
    }


    private void setCustomerCB() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(CustomerDAO.loadAllCustomers());
        customerCB.setItems(customerObservableList);
    }

    private void setContactCB() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList(ContactDAO.loadAllContacts());
        contactCB.setItems(contactObservableList);
    }
    private void setUserCB() {
        ObservableList<User> userObservableList = FXCollections.observableList(UserDAO.loadAllUsers());
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
