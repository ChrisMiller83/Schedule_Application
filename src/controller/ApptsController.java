package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.MessageLambdaInterface;
import utilities.ChangeView;
import utilities.Messages;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * ApptsController -- Displays tables of appointments, allows appointment deletion, and redirects to add/update appt page.
 */
public class ApptsController implements Initializable {
    static ObservableList<Appointment> appointments;

    private static Appointment apptToUpdate;
    private static Appointment selectedAppt;
    private static final String filename = "deletedAppts.txt";

    @FXML private Button scheduleApptBtn;
    @FXML private Button updateApptBtn;
    @FXML private Button deleteApptBtn;
    @FXML private ToggleGroup selectedView;
    @FXML private RadioButton allApptRBtn;
    @FXML private RadioButton monthlyApptBtn;
    @FXML private RadioButton weeklyApptRBtn;
    @FXML private TableView<Appointment> appointmentsTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, String> descriptionCol;
    @FXML private TableColumn<Appointment, String> locationCol;
    @FXML private TableColumn<Appointment, String> typeCol;
    @FXML private TableColumn<Appointment, Timestamp> startTimeCol;
    @FXML private TableColumn<Appointment, Timestamp> endTimeCol;
    @FXML private TableColumn<Appointment, Timestamp> createDateCol;
    @FXML private TableColumn<Appointment, String> createdByCol;
    @FXML private TableColumn<Appointment, Timestamp> lastUpdateCol;
    @FXML private TableColumn<Appointment, String> lastUpdatedByCol;
    @FXML private TableColumn<Appointment, Integer> customerIdCol;
    @FXML private TableColumn<Appointment, Integer> userIdCol;
    @FXML private TableColumn<Appointment, String> contactCol;
    @FXML private Button mainMenuBtn;


    /**
     * setSelectedView -- displays different appt tables depending on radio button selection.
     * @param event -- radio button selected
     */
    public void setSelectedView(ActionEvent event)  {
        try {
            if (allApptRBtn.isSelected()) {
                appointments = AppointmentDAO.loadAllAppts();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            } else if (selectedView.getSelectedToggle().equals(weeklyApptRBtn)) {
                appointments = AppointmentDAO.getApptsThisWeek();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            } else if (selectedView.getSelectedToggle().equals(monthlyApptBtn)) {
                appointments = AppointmentDAO.getApptsThisMonth();
                appointmentsTableView.setItems(appointments);
                appointmentsTableView.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * toAddApptView -- redirects to AddAppt page
     * @param actionEvent -- Add Appointment button clicked
     * @throws IOException
     */
    public void toAddApptView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "AddApptView.fxml");
    }

    /**
     * toUpdateAppointmentView -- redirects to UpdateAppt page once an appointment to update is selected.
     * @param actionEvent -- Update Appointment button clicked.
     * @throws IOException
     */
    public void toUpdateAppointmentView(ActionEvent actionEvent) throws IOException {
        apptToUpdate = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (apptToUpdate == null) {
            Messages.selectAnItemToUpdate("Appointment");
            return;
        }
        /** gets the selected appointment and transfers its data to the UpdateApptController */
        UpdateApptController.getSelectedAppt(apptToUpdate);
        new ChangeView(actionEvent, "UpdateApptView.fxml");
    }

    /**
     * deleteOldAppts -- deletes old/missed appointments from the appointments table when page is loaded,
     * and stores the deleted appointments in a txt file
     * A For Each Lambda expression replaces the for loop that loops through the appointment list.
     * @throws IOException
     */
    public void deleteOldAppts() throws IOException {
        ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();
        /** FOR EACH ******LAMBDA EXPRESSION ****** */
        appointments.forEach(appointment -> {
            if (appointment.getStartDateTime().isBefore(LocalDateTime.now())) {
                int apptId = appointment.getApptId();
                try {
                    storeDeletedAppts(appointment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AppointmentDAO.deleteAppointment(apptId);
                appointmentsTableView.refresh();
            }
        });
    }

    /**
     * storeDeletedAppts -- method to store deleted appointments in a txt file.
     * @param appointment -- appointment that was deleted.
     * @throws IOException
     */
    public void storeDeletedAppts(Appointment appointment) throws IOException {
        File file = new File(filename);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fileWriter);

        bw.write("Appointment Deleted on:  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH-mm-ss")) +
                "\tApptID = " + appointment.getApptId() + "\tTitle:  " + appointment.getApptTitle() +
                "\t Description: " + appointment.getApptDescription() + "\tType: " + appointment.getApptType() + "\tStart: " +
                appointment.getStartDateTime() + "\tEnd: " + appointment.getEndDateTime() + "\t Contact ID: " + appointment.getContactId() +
                "\tCustomer ID:  " + appointment.getCustomerId() + "\tUser ID:  " + appointment.getUserId());
        bw.newLine();
        bw.close();
        appointmentsTableView.refresh();
    }

    /**
     * deleteAppointment -- allows the user to delete selected appointments and stores the deleted appt in a txt file.
     * @param actionEvent -- appt to delete is selected and Delete Appointment button is clicked.
     * @throws IOException
     */
    public void deleteAppointment(ActionEvent actionEvent) throws IOException {
        selectedAppt = appointmentsTableView.getSelectionModel().getSelectedItem();
        if(selectedAppt == null) {
            Messages.selectionNeeded();
            return;
        } else {
            int apptId = selectedAppt.getApptId();
            boolean deleteConfirm = Messages.deleteApptConfirmation(selectedAppt.getApptId(), selectedAppt.getApptType());
            if(deleteConfirm) {
                storeDeletedAppts(selectedAppt);
                AppointmentDAO.deleteAppointment(apptId);

                /** Lambda expression -- console message verifying delete */
                MessageLambdaInterface message = s -> System.out.println(s + " deleted.");
                message.displayMessage(selectedAppt.getApptTitle());

                appointmentsTableView.setItems(AppointmentDAO.loadAllAppts());
                appointmentsTableView.refresh();
            } else {
                return;
            }
        }
    }

    /**
     * toMainMenu -- redirects user to the main page.
     * @param actionEvent -- Main Menu button clicked.
     * @throws IOException
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "MainPageView.fxml");
    }

    /**
     * setAppointmentsTableView -- populates appointment data to appropriate columns in appointment table
     * @param appointments -- ObservableList of all appointments in the db.
     */
    public void setAppointmentsTableView(ObservableList<Appointment> appointments) {
        appointmentsTableView.setItems(appointments);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }

    /**
     * initialize -- loads appointment tables and deletes old appts automatically when page is loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allApptRBtn.setToggleGroup(selectedView);
        weeklyApptRBtn.setToggleGroup(selectedView);
        monthlyApptBtn.setToggleGroup(selectedView);
        setAppointmentsTableView(AppointmentDAO.loadAllAppts());
        try {
            deleteOldAppts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}


