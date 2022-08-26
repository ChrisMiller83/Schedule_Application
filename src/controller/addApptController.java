package controller;

import DAO.CustomerDAO;
import DAO.DBConnection;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class addApptController implements Initializable {

    @FXML private TextField appointmentIdTF;
    @FXML private TextField userTF;
    @FXML private ChoiceBox<Customer> customerCB;
    @FXML private TextField titleTF;
    @FXML private TextField locationTF;
    @FXML private TextField typeTF;
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
        setCustomerCB();
        setUserIdTF();
        setContactCB();
        setApptID();
        setTimeCB();
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

    private void setApptID() {
        int max_id = 0;
        for (Appointment appointment : Appointment.getAllAppointmentsList()) {
            if (appointment.getApptId() >= max_id) {
                max_id = appointment.getApptId() + 1;
            }
        }
        appointmentIdTF.setText(String.valueOf(max_id));
    }

    private Timestamp timestamp(LocalDate date, LocalTime time) {
        return Timestamp.valueOf(LocalDateTime.of(date, time).format(DBConnection.dtFormatter));
    }

    private void setTimeCB() {
        LocalTime timeChoice = LocalTime.of(8, 0);
        ObservableList<LocalTime> timeObservableList = FXCollections.observableArrayList(timeChoice);

        while (!timeChoice.equals (LocalTime.of(22, 0))){
            timeChoice = timeChoice.plusMinutes(15);
            timeObservableList.add(timeChoice);
        }
        startTimeCB.setItems(timeObservableList);
        endTimeCB.setItems(timeObservableList);
    }

    public void cancelToAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointmentsView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveAppointment(ActionEvent actionEvent) throws IOException {



        Parent root = FXMLLoader.load(getClass().getResource("/view/appointmentsView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
