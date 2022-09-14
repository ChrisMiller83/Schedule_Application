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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import utilities.ChangeView;
import utilities.Messages;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * ReportsController -- displays reports
 */
public class ReportsController implements Initializable {

    /** Appointment by Month and Type Total Report */
    @FXML private Tab totalTab;
    @FXML private TableView<Appointment> totalTable;
    @FXML private TableColumn<Appointment, String> monthTotalCol;
    @FXML private TableColumn<Appointment, String> typeTotalCol;
    @FXML private TableColumn<Appointment, Integer> totalCol;

    /** Contact Appointments Report */
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

    /** Login Activity Report */
    @FXML private Tab loginTab;
    @FXML private TextArea loginTA;
    private static final String filename = "login_activity.txt";

    /** Deleted Appointments Report */
    @FXML private Tab deletedApptsTab;
    @FXML private TextArea deletedApptsTA;


    /**
     * loadContactAppts -- loads all upcoming appointments for the selected contact
     * @param event -- contact is selected and the Load Contacts Appointments button is clicked.
     */
    public void loadContactAppts(ActionEvent event) {
        /** gets the Contact name from the choice box */
        Contact selectedContact = contactCB.getSelectionModel().getSelectedItem();
        /** if no contact was selected display error message */
        if(selectedContact == null){
            Messages.selectionNeeded();
            return;
        } else {
            /** A contact was selected, get the contact id, query the db for all appts with the contact id, and
             *  display the upcoming appointments for the contact in the table.
             */
            int contactId = contactCB.getValue().getContactId();
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
    }

    /**
     * setTotalTable -- loads the totalTable with data queried from the db of the total appts of each appt Type for each month.
     */
    private void setTotalTable() {
        ObservableList<Appointment> totals = FXCollections.observableArrayList(AppointmentDAO.loadTotals());
        totalTable.setItems(totals);
        monthTotalCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeTotalCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalTable.refresh();
    }

    /**
     * toMainMenu -- changes the view to the Main Page View
     * @param actionEvent -- Main Menu button clicked
     * @throws IOException
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "MainPageView.fxml");
    }

    private void setContactCB() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList(ContactDAO.loadAllContacts());
        contactCB.setItems(contactObservableList);
    }
    @FXML
    void setLoginTextArea() {
        loginTA.setText(String.valueOf(readLoginActivity()));
    }

    public ObservableList<String> readLoginActivity() {
        ObservableList<String> lines = FXCollections.observableArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String s;
            while ((s = br.readLine()) != null) {
                lines.add(s);
                lines.add("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @FXML
    void setDeletedApptsTA() {
        deletedApptsTA.setText(String.valueOf(readDeleteAppts()));
    }

    public ObservableList<String> readDeleteAppts() {
        ObservableList<String> lines = FXCollections.observableArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader("deletedAppts.txt"));
            String s;
            while ((s = br.readLine()) != null) {
                lines.add(s);
                lines.add("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContactCB();
        setLoginTextArea();
        setTotalTable();
        setDeletedApptsTA();
    }
}

