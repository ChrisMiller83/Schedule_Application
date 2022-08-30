package controller;

import DAO.AppointmentDAO;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class appointmentsController implements Initializable {

    static ObservableList<Appointment> appointments;

    private static Appointment apptToUpdate;
    private static int selectedAppt;


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


    @FXML
    void setSelectedView(ActionEvent event) throws SQLException {
        if(allApptRBtn.isSelected()) {
            appointments = AppointmentDAO.getAllAppts();
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
    }


    public void toAddApptView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/createApptView.fxml")));
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
        //selectedAppt = appointmentObservableList.indexOf(apptToUpdate);
        Parent root = FXMLLoader.load((getClass().getResource("/view/updateAppointmentView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteAppointment(ActionEvent actionEvent) throws IOException {
        Appointment deleteAppt = appointmentsTableView.getSelectionModel().getSelectedItem();
        Appointment.deleteAppt(deleteAppt);
        AppointmentDAO.deleteAppointment(deleteAppt);
        appointmentsTableView.getItems().remove(deleteAppt);

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
        allApptRBtn.setToggleGroup(selectedView);
        weeklyApptRBtn.setToggleGroup(selectedView);
        monthlyApptBtn.setToggleGroup(selectedView);
        try {
            appointments = AppointmentDAO.getAllAppts();
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
            lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>(" lastUpdated"));
            lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}


