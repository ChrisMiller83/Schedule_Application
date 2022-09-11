package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */


import dao.AppointmentDAO;
import dao.ContactDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    // Appointment by Month and Type Total Report
    @FXML private Tab totalTab;
    @FXML private TableView<Appointment> totalTable;
    @FXML private TableColumn<Appointment, ?> monthTotalCol;
    @FXML private TableColumn<Appointment, ?> typeTotalCol;
    @FXML private TableColumn<Appointment, ?> totalCol;

    // Contact Appointments Report
    @FXML private Tab scheduleTab;
    @FXML private ChoiceBox<Contact> contactCB;
    @FXML private TableView<Appointment> scheduleTable;
    @FXML private TableColumn<Appointment, Integer> apptIdCol;
    @FXML private TableColumn<Appointment, LocalDate> apptDateCol;
    @FXML private TableColumn<Appointment, LocalTime> startTimeCol;
    @FXML private TableColumn<Appointment, LocalTime> endTimeCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, String> descriptionCol;
    @FXML private TableColumn<Appointment, Integer> customerIdCol;

    // Login Activity Report
    @FXML private Tab loginTab;
    @FXML private TableView<?> loginTable;
    @FXML private TableColumn<?, ?> usernameCol;
    @FXML private TableColumn<?, ?> dateTimeCol;
    @FXML private TableColumn<?, ?> loginStatus;

    @FXML
    void loadContactAppts(ActionEvent event) {
        int contactId = contactCB.getValue().getContactId();
        System.out.println(contactId);
        ObservableList<Appointment> contactAppts = FXCollections.observableArrayList(AppointmentDAO.loadContactAppts(contactId));
        scheduleTable.setItems(contactAppts);
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("apptDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        scheduleTable.refresh();

    }

    @FXML
    void toMainMenu(ActionEvent event) {

    }

    private void setContactCB() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList(ContactDAO.loadAllContacts());
        contactCB.setItems(contactObservableList);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContactCB();
    }
}

