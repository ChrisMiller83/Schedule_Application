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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.Appointment;
import model.Contact;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML private ChoiceBox<Contact> contactCB;
    @FXML private RadioButton typeRB;
    @FXML private RadioButton monthRB;
    @FXML private ToggleGroup totalApptsToggleGroup;


    @FXML
    void generateTotalAppts(ActionEvent actionEvent) throws SQLException {

        // Type list
        ObservableList<String> lunch = FXCollections.observableArrayList();
        ObservableList<String> decisionMaking = FXCollections.observableArrayList();
        ObservableList<String> problemSolving = FXCollections.observableArrayList();
        ObservableList<String> teamBuilding = FXCollections.observableArrayList();
        ObservableList<String> brainstorming = FXCollections.observableArrayList();
        ObservableList<String> oneOnOne = FXCollections.observableArrayList();
        ObservableList<String> quarterlyPlanning = FXCollections.observableArrayList();
        ObservableList<String> checkIn = FXCollections.observableArrayList();

        // Month list
        ObservableList<Integer> january = FXCollections.observableArrayList();
        ObservableList<Integer> february = FXCollections.observableArrayList();
        ObservableList<Integer> march = FXCollections.observableArrayList();
        ObservableList<Integer> april = FXCollections.observableArrayList();
        ObservableList<Integer> may = FXCollections.observableArrayList();
        ObservableList<Integer> june = FXCollections.observableArrayList();
        ObservableList<Integer> july = FXCollections.observableArrayList();
        ObservableList<Integer> august = FXCollections.observableArrayList();
        ObservableList<Integer> september = FXCollections.observableArrayList();
        ObservableList<Integer> october = FXCollections.observableArrayList();
        ObservableList<Integer> november = FXCollections.observableArrayList();
        ObservableList<Integer> december = FXCollections.observableArrayList();

        ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

        for (Appointment appointment : appointments) {
            String type = appointment.getApptType();
            LocalDateTime date = appointment.getStartDateTime();

            // Add each appt type to appropriate observableArrayList
            if (type.equals("Lunch")) {
                lunch.add(type);
            }
            if (type.equals("Decision-making")) {
                decisionMaking.add(type);
            }
            if (type.equals("Problem-solving")) {
                problemSolving.add(type);
            }
            if (type.equals("Team-building")) {
                teamBuilding.add(type);
            }
            if (type.equals("Brainstorming")) {
                brainstorming.add(type);
            }
            if (type.equals("One-on-one")) {
                oneOnOne.add(type);
            }
            if (type.equals("Quarterly-planning")) {
                quarterlyPlanning.add(type);
            }
            if (type.equals("Check-in")) {
                checkIn.add(type);
            }

            // Add each appt month to appropriate observableArrayList
            if (date.getMonth().equals(Month.of(1))) {
                january.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(2))) {
                february.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(3))) {
                march.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(4))) {
                april.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(5))) {
                may.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(6))) {
                june.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(7))) {
                july.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(8))) {
                august.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(9))) {
                september.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(10))) {
                october.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(11))) {
                november.add(date.getMonthValue());
            }
            if (date.getMonth().equals(Month.of(12))) {
                december.add(date.getMonthValue());
            }

        }

        if(typeRB.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Total Appointments by Type Report");
            alert.setContentText("Total Appointments by Type Report" +
                    "\nLunch: " + lunch.size() +
                    "\nDecision-making: " + decisionMaking.size() +
                    "\nProblem-solving: " + problemSolving.size() +
                    "\nTeam-building: " + teamBuilding.size() +
                    "\nBrainstorming: " + brainstorming.size() +
                    "\nOne-on-one: " + oneOnOne.size() +
                    "\nQuarterly-planning: " + quarterlyPlanning.size() +
                    "\nCheck-in: " + checkIn.size());
            alert.setResizable(true);
            alert.showAndWait();
        }
        if(monthRB.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Total Appointments by Month Report");
            alert.setContentText("Total Appointments by Month Report" +
                    "\nJanuary: " + january.size() +
                    "\nFebruary: " + february.size() +
                    "\nMarch: " + march.size() +
                    "\nApril: " + april.size() +
                    "\nMay: " + may.size() +
                    "\nJune: " + june.size() +
                    "\nJuly: " + july.size() +
                    "\nAugust: " + august.size() +
                    "\nSeptember: " + september.size() +
                    "\nOctober: " + october.size() +
                    "\nNovember: " + november.size() +
                    "\nDecember: " + december.size());
            alert.setResizable(true);
            alert.showAndWait();
        }
    }

    @FXML
    void generateContactSchedule(ActionEvent actionEvent) throws SQLException {
        Integer contactId = contactCB.getValue().getContactId();
        String contactName = contactCB.getValue().getContactName();

        List<Appointment> contactAppts = new ArrayList<>();


        if (contactCB.getValue() == null) {
            Messages.selectionNeeded();
        } else {
            ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();
            for (Appointment appointment : appointments) {
                if (appointment.getContactId() == contactId) {
                    contactAppts.add(appointment);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(contactName + " Scheduled Appointments:");
                    alert.setContentText("Appointment Id: " + appointment.getApptId() +
                            "\nTitle: " + appointment.getApptTitle() +
                            "\n Type: " + appointment.getApptType() +
                            "\nDescription: " + appointment.getApptDescription() +
                            "\nStart Date and Time: " + appointment.getStartDateTime() +
                            "\nEnd Date and Time: " + appointment.getEndDateTime() +
                            "\nWith Customer Id#: " + appointment.getCustomerId() + "\n");
                    alert.setResizable(true);
                    alert.showAndWait();
                }
            }

        }
    }

    @FXML
    void generateLoginReport(ActionEvent actionEvent) {

    }

    @FXML
    void toMainPage(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "MainPageView.fxml");
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
