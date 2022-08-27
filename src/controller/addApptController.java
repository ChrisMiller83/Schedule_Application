package controller;

import DAO.*;
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
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class addApptController implements Initializable {
    private ZonedDateTime startTimeConversion;
    private ZonedDateTime endTimeConversion;

    private ZonedDateTime timeConversion(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    @FXML private TextField appointmentIdTF;
    @FXML private TextField userTF;
    @FXML private ChoiceBox<Customer> customerCB;
    @FXML private TextField titleTF;
    @FXML private TextField locationTF;
    @FXML private ChoiceBox<String> typeCB;
    @FXML private ChoiceBox<Contact> contactCB;
    @FXML private DatePicker startDatePicker;
    @FXML private ChoiceBox<LocalTime> startTimeCB;
    @FXML private DatePicker endDatePicker;
    @FXML private ChoiceBox<LocalTime> endTimeCB;
    @FXML private TextArea descriptionTA;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDAO.loadAllUsers();
        CustomerDAO.loadAllCustomers();
        ContactDAO.loadAllContacts();
        setCustomerCB();
        setUserIdTF();
        setContactCB();
//        setApptID();
        setTimeCB();
        setTypeCB();
    }

    private void setCustomerCB() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(Customer.getCustomers());
        customerCB.setItems(customerObservableList);
    }

    private void setUserIdTF() {
        userTF.setText(User.currentUser.getUserName());
        userTF.setDisable(true);
    }

    private void setContactCB() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList(Contact.getContacts());
        contactCB.setItems(contactObservableList);
    }

//    private void setApptID() {
//        int max_id = 1;
//        for (Appointment appointment : Appointment.appointmentsList) {
//            if (appointment.getApptId() >= max_id) {
//                max_id = appointment.getApptId() + 1;
//            }
//        }
//        appointmentIdTF.setText(String.valueOf(max_id));
//    }

    private void setTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Lunch", "Planning Session", "Follow-up", "Project Meeting", "Open Meeting");
        typeCB.setItems(typeList);
    }

    private Timestamp timestamp(LocalDate date, LocalTime time) {
        return Timestamp.valueOf(LocalDateTime.of(date, time).format(DBConnection.dtFormatter));
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

    public void cancelToAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointmentsView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void saveAppointment(ActionEvent actionEvent) throws IOException {
        try {
            AppointmentDAO.addAppointment(
            Integer.parseInt(appointmentIdTF.getText()),
            titleTF.getText(),
            descriptionTA.getText(),
            typeCB.getValue(),
            locationTF.getText(),
            timestamp(startDatePicker.getValue(), startTimeCB.getSelectionModel().getSelectedItem()),
            timestamp(endDatePicker.getValue(), endTimeCB.getSelectionModel().getSelectedItem()),
            LocalDateTime.now(),
            userTF.getText(),
            LocalDateTime.now(),
            userTF.getText(),
            customerCB.getValue().getCustomerId(),
            User.currentUser.getUserId(),
            contactCB.getValue().getContactId());

            //TODO add check for overlapping appointments

            if (!customerCB.hasProperties()) {
                Messages.emptyField();
                return;
            }

//            AppointmentDAO.addAppointment(apptId,title, description, location, type, startDateTime, endDateTime, createdDate,
//                    createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
//            Appointment appointment = new Appointment(apptId, title, description, location, type, startDateTime, endDateTime,
//                    createdDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
//            AppointmentDAO.addAppointment(appointment);



        } catch (Exception e) {
            e.printStackTrace();
        }



        Parent root = FXMLLoader.load(getClass().getResource("/view/appointmentsView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean apptIsValid() {
        if (titleTF.getText().isEmpty()) {
            Messages.emptyField(1);
            return false;
        }
        if (descriptionTA.getText().isEmpty()) {
            Messages.emptyField(2);
            return false;
        }
        if (locationTF.getText().isEmpty()) {
            Messages.emptyField(3);
            return false;
        }
        if (typeCB.getSelectionModel().isEmpty()) {
            Messages.emptyField(4);
            return false;
        }
        if (startDatePicker.getValue() == null) {
            Messages.emptyField(5);
            return false;
        }
        if (startTimeCB.getSelectionModel().isEmpty()) {
            Messages.emptyField(6);
            return false;
        }
        if (endDatePicker.getValue() == null) {
            Messages.emptyField(7);
            return false;
        }
        if (endTimeCB.getSelectionModel().isEmpty()) {
            Messages.emptyField(8);
            return false;
        }
        if(customerCB.getSelectionModel().isEmpty()) {
            Messages.emptyField(9);
            return false;
        }
        if (contactCB.getSelectionModel().isEmpty()) {
            Messages.emptyField(10);
            return false;
        }

        if (startDatePicker.getValue().isBefore(LocalDate.from(LocalDateTime.now())) ||
                endDatePicker.getValue().isBefore(LocalDate.from(LocalDateTime.now())) ||
                startTimeCB.getValue().isBefore(LocalTime.from(LocalDateTime.now())) ||
                endTimeCB.getValue().isBefore(LocalTime.from(LocalDateTime.now()))) {
            Messages.checkApptDates();
            return false;
        }

        if (startTimeCB.getValue().isAfter(endTimeCB.getValue())) {
            Messages.checkStartTime();
            return false;
        }

        if (endTimeCB.getValue().isBefore(startTimeCB.getValue())) {
            Messages.checkEndTime();
            return false;
        }

        startDateTimeConversion = timestamp(startDatePicker.getValue(), startTimeCB.getSelectionModel().getSelectedItem()).compareTo(timeConversion());
                timestamp(endDatePicker.getValue(), endTimeCB.getSelectionModel().getSelectedItem()));

        return true;
    }


}
