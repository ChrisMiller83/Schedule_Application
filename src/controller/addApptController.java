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
import java.util.ResourceBundle;

import static java.time.LocalTime.parse;

public class addApptController implements Initializable {
    private ZonedDateTime startTimeConversion;
    private ZonedDateTime endTimeConversion;

    private ZonedDateTime timeConversion(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

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
    private Timestamp createDate;
    private String createdBy;
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
        //appointmentIdTF.setText(Appointment.getNewApptId());
        setCustomerCB();
        setUserIdTF();
        setContactCB();
        setTimeCB();
        setTypeCB();
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

    private Timestamp getTimestamp(LocalDate LocalDate, LocalTime LocalTime) {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate, LocalTime).format(DBConnection.dtFormatter));
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

        if (validateAppt()) {
            try {
                title = titleTF.getText();
                description = descriptionTA.getText();
                location = locationTF.getText();
                type = typeCB.getValue();
                start = timestamp(apptDatePicker.getValue(), startTimeCB.getValue());
                end = timestamp(apptDatePicker.getValue(), endTimeCB.getValue());
                createDate = Timestamp.valueOf(LocalDateTime.now());
                createdBy = User.currentUser.getUserName();
                lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                lastUpdatedBy = User.currentUser.getUserName();
                customerId = customerCB.getValue().getCustomerId();
                userId = User.currentUser.getUserId();
                contactId = contactCB.getValue().getContactId();

                boolean confirmAdd = Messages.addConfirmation(title);

                if(confirmAdd) {
                    AppointmentDAO.addAppointment(title, description, location, type, start, end, createDate, createdBy,
                            lastUpdate, lastUpdatedBy, customerId, userId, customerId);
                }


                //TODO add check for overlapping appointments


            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        Parent root = FXMLLoader.load(getClass().getResource("/view/appointmentsView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        if (typeCB.getSelectionModel().isEmpty()) {
            Messages.apptEmptyField(4);
            return false;
        }
        if (apptDatePicker.getValue() == null) {
            Messages.apptEmptyField(5);
            return false;
        }
        if (startTimeCB.getSelectionModel().isEmpty()) {
            Messages.apptEmptyField(6);
            return false;
        }
        if (endTimeCB.getSelectionModel().isEmpty()) {
            Messages.apptEmptyField(8);
            return false;
        }
        if (customerCB.getSelectionModel().isEmpty()) {
            Messages.apptEmptyField(9);
            return false;
        }
        if (contactCB.getSelectionModel().isEmpty()) {
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

    private Timestamp timestamp(LocalDate date, LocalTime time) {
        return Timestamp.valueOf(LocalDateTime.of(date, time).format(DBConnection.dtFormatter));
    }


}
