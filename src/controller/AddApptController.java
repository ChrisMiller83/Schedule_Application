package controller;

/** @author Christopher Miller - Schedule Application - WGU C195 PA  */

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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.TimeZone;



public class AddApptController implements Initializable {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    private ZonedDateTime convertLocalDateTimeToEST(LocalDate date, LocalTime time) {
        return ZonedDateTime.of(date, time, ZoneId.of("America/New_York"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/view/ApptsView.fxml"));
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

        /** ApptDate, startTime, endTime, ZoneId, and ZonedDateTime objects for the appointment */
        LocalDate apptDate = apptDatePicker.getValue();
        LocalTime apptStartTime = startTimeCB.getValue();
        LocalTime apptEndTime = endTimeCB.getValue();
        ZoneId apptZoneId = ZoneId.of("America/New_York");
        ZonedDateTime apptStartZDT = ZonedDateTime.of(apptDate, apptStartTime, apptZoneId);
        ZonedDateTime apptEndZDT = ZonedDateTime.of(apptDate, apptEndTime, apptZoneId);

        /** Get the ZoneId of the current OS and create an instant */
        ZoneId osZoneId = ZoneId.of(TimeZone.getDefault().getID());
        Instant osStartToUTC = apptStartZDT.toInstant();
        Instant osEndToUTC = apptEndZDT.toInstant();

        /** Convert appt times to local zoneId */
        ZonedDateTime apptStartToLocalZDT = apptStartZDT.withZoneSameInstant(osZoneId);
        ZonedDateTime apptEndToLocalZDT = apptEndZDT.withZoneSameInstant(osZoneId);



        /** convert appt times to local time */
        ZonedDateTime StartUTCToLocalZDT = osStartToUTC.atZone(osZoneId);
        ZonedDateTime EndUTCToLocalZDT = osEndToUTC.atZone(osZoneId);


        LocalTime businessDayStart = LocalTime.of(8, 0);
        LocalTime businessDayEnd = LocalTime.of(22, 0);

        if(apptEndTime.isBefore(apptStartTime) || apptStartTime.equals(apptEndTime)) {
            Messages.validateAppt(8);
            return false;
        }



        if(apptStartTime.isBefore(businessDayStart) || apptEndTime.isBefore(businessDayStart) ||
           apptStartTime.isAfter(businessDayEnd) || apptEndTime.isAfter(businessDayEnd)) {
            Messages.validateAppt(12);
            return false;
        }

        customerId = customerCB.getValue().getCustomerId();
        ObservableList<Appointment> appointmentObservableList = AppointmentDAO.loadAllAppts();



        return true;
    }

    private Timestamp timestamp(LocalDate date, LocalTime time) {
        return Timestamp.valueOf(LocalDateTime.of(date, time).format(dateTimeFormatter));
    }


}
