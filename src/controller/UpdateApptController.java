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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateApptController implements Initializable {

    private static Appointment selectedAppt;

    private int apptId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerId;
    private int userId;
    private int contactId;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private LocalDate apptDate;

    @FXML private TextField appointmentIdTF;
    @FXML private TextField userTF;
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
                start = timestamp(apptDatePicker.getValue(), startTimeCB.getValue());
                end = timestamp(apptDatePicker.getValue(), endTimeCB.getValue());
                lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                lastUpdatedBy = User.currentUser.getUserName();
                customerId = customerCB.getValue().getCustomerId();
                userId = User.currentUser.getUserId();
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

    private Timestamp timestamp(LocalDate date, LocalTime time) {
        return Timestamp.valueOf(LocalDateTime.of(date, time).format(DBConnection.dtFormatter));
    }

    private boolean validateAppt() {
        if (titleTF.getText().isEmpty()) {
            Messages.apptEmptyField(1);
            return false;
        }
        if (descriptionTA.getText().isEmpty()) {
            Messages.apptEmptyField(2);
            return false;
        }
        if (locationTF.getText().isEmpty()) {
            Messages.apptEmptyField(3);
            return false;
        }
        if (typeCB.getValue() == null) {
            Messages.apptEmptyField(4);
            return false;
        }
        if (apptDatePicker.getValue() == null) {
            Messages.apptEmptyField(5);
            return false;
        }
        if (startTimeCB.getValue() == null) {
            Messages.apptEmptyField(6);
            return false;
        }
        if (endTimeCB.getValue() == null) {
            Messages.apptEmptyField(8);
            return false;
        }
        if (customerCB.getValue() == null) {
            Messages.apptEmptyField(9);
            return false;
        }
        if (contactCB.getValue() == null) {
            Messages.apptEmptyField(10);
            return false;
        }

        // TODO: fix: throws error even if date is in the future
//        if (startDatePicker.getValue().isBefore(LocalDate.from(LocalDateTime.now())) ||
//                endDatePicker.getValue().isBefore(LocalDate.from(LocalDateTime.now())) ||
//                startTimeCB.getValue().isBefore(LocalTime.from(LocalDateTime.now())) ||
//                endTimeCB.getValue().isBefore(LocalTime.from(LocalDateTime.now()))) {
//            Messages.checkApptDates();
//            return false;
//        }

        if (startTimeCB.getValue().isAfter(endTimeCB.getValue())) {
            Messages.checkStartTime();
            return false;
        }

        if (endTimeCB.getValue().isBefore(startTimeCB.getValue())) {
            Messages.checkEndTime();
            return false;
        }
        return true;
    }

    public void setSelectedAppt(Appointment selectedAppt) {
        appointmentIdTF.setText(Integer.toString(selectedAppt.getApptId()));
        titleTF.setText(selectedAppt.getApptTitle());
        descriptionTA.setText(selectedAppt.getApptDescription());
        locationTF.setText(selectedAppt.getApptLocation());
        typeCB.getSelectionModel().select(selectedAppt.getApptType());
        apptDatePicker.setValue(selectedAppt.getStartDateTime().toLocalDateTime().toLocalDate());
        startTimeCB.getSelectionModel().select(selectedAppt.getStartDateTime().toLocalDateTime().toLocalTime());
        endTimeCB.getSelectionModel().select(selectedAppt.getEndDateTime().toLocalDateTime().toLocalTime());
        userTF.setText(User.currentUser.getUserName());

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
    }


    private void setCustomerCB() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(CustomerDAO.loadAllCustomers());
        customerCB.setItems(customerObservableList);
    }

    private void setContactCB() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList(ContactDAO.loadAllContacts());
        contactCB.setItems(contactObservableList);
    }
    private void setUserIdTF() {
        userTF.setText(User.currentUser.getUserName());
        userTF.setDisable(true);
    }

    private void setTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Lunch", "Planning Session", "Follow-up", "Project Meeting", "Open Meeting");
        typeCB.setItems(typeList);
    }

    private void setTimeCB() {
        ObservableList<LocalTime> timeOptions = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(22, 0);

        timeOptions.add(startTime);

        while (startTime.isBefore(endTime)){
            startTime = startTime.plusMinutes(15);
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
        setUserIdTF();
        setContactCB();
        setTimeCB();
        setTypeCB();

    }
}
