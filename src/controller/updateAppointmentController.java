package controller;

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
import utilities.ChangeView;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class updateAppointmentController implements Initializable {

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




    public static void getSelectedAppt(Appointment appointment) {
        selectedAppt = appointment;
    }

    public void cancelToAppointments(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "appointmentsView.fxml");
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {




        new ChangeView(actionEvent, "appointmentsView.fxml");

    }

    public void setSelectedAppt(Appointment selectedAppt) {
        appointmentIdTF.setText(Integer.toString(selectedAppt.getApptId()));
        titleTF.setText(selectedAppt.getApptTitle());
        descriptionTA.setText(selectedAppt.getApptDescription());
        locationTF.setText(selectedAppt.getApptLocation());
        typeCB.getSelectionModel().select(selectedAppt.getApptType());
//        datePicker.getValue(selectedAppt.getD)
        startTimeCB.getSelectionModel().select(selectedAppt.getStartDateTime());
        endTimeCB.getSelectionModel().select(selectedAppt.getEndDateTime());
        customerCB.getSelectionModel().select(selectedAppt.getCustomerId());
        userTF.setText(selectedAppt.getUserId());
        contactCB.getSelectionModel().select(selectedAppt.getContactId());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSelectedAppt(selectedAppt);

    }
}
