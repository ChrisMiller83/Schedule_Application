package controller;

import DAO.AppointmentDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utilities.ChangeView;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class appointmentsController implements Initializable {
    ObservableList<Appointment> appointmentObservableList = FXCollections.observableList(Appointment.getAllAppointmentsList());

    private static Appointment apptToUpdate;
    private static int selectedAppt;


    @FXML private Button scheduleApptBtn;
    @FXML private Button updateApptBtn;
    @FXML private Button deleteApptBtn;
    @FXML private RadioButton allApptRBtn;
    @FXML private ToggleGroup selectedView;
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

    private FilteredList<Appointment> allAppts;
    private FilteredList<Appointment> weeklyApptList;
    private FilteredList<Appointment> monthlyApptList;

    private Predicate<Appointment> getAllAppts;

    private Predicate<Appointment> getMonthlyAppts;



    public void toAddApptView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/addApptView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toUpdateAppointmentView(ActionEvent actionEvent) throws IOException {
        apptToUpdate = appointmentsTableView.getSelectionModel().getSelectedItem();

        if (apptToUpdate == null) {
            // TODO: add error message
            return;
        }
        selectedAppt = appointmentObservableList.indexOf(apptToUpdate);
        Parent root = FXMLLoader.load((getClass().getResource("/view/updateAppointmentView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteAppointment(ActionEvent actionEvent) throws IOException {
        Appointment deleteAppt = appointmentsTableView.getSelectionModel().getSelectedItem();
        Appointment.getAllAppointmentsList().remove(deleteAppt);
        // TODO: add delete message and report

    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/mainPageView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AppointmentDAO.loadAllAppointments();

        apptTableView(appointmentObservableList);
        // TODO: fix loading issues of duplication appt table view
    }

    private void apptTableView(ObservableList<Appointment> appointments) {
        appointmentsTableView.setItems(appointments);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

    }

    @FXML
    void allApptSelected(ActionEvent actionEvent) {
        apptTableView(appointmentObservableList);
    }

    @FXML
    void weeklyApptSelected(ActionEvent actionEvent) {
        ObservableList<Appointment> weeklyApptsTableView = FXCollections.observableArrayList();
        for (Appointment appts : appointmentObservableList) {
            if (appts.getStartDateTime().toLocalDateTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == ZonedDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)) {
                weeklyApptsTableView.add(appts);
            }
        }
        apptTableView(weeklyApptsTableView);
    }

    @FXML
    void monthlyApptSelected(ActionEvent actionEvent) {
        ObservableList<Appointment> monthlyApptsTableView = FXCollections.observableArrayList();
        for (Appointment appts : appointmentObservableList) {
            if (appts.getStartDateTime().toLocalDateTime().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == ZonedDateTime.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)) {
                monthlyApptsTableView.add(appts);
            }
        }
        apptTableView(monthlyApptsTableView);
    }

    public ObservableList<Appointment> appointmentObservableList() {
        return appointmentObservableList;
    }
}
