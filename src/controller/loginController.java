package controller;

import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utilities.ChangeView;
import utilities.Messages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML private Label titleLabel;
    @FXML private Label zoneIdLabel;
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label zoneID;
    @FXML private TextField userNameTF;
    @FXML private TextField passwordTF;
    @FXML private Button clearTextFieldsBtn;
    @FXML private Button loginBtn;

    public loginController () {

    }

    public void setZoneID(Label zoneID) {
        zoneID.setText(String.valueOf(ZoneId.systemDefault()));
    }

    public void clearTextFieldsBtn(ActionEvent actionEvent) {
        userNameTF.clear();
        userNameTF.setPromptText("Username");
        passwordTF.clear();
        passwordTF.setPromptText("Password");
    }

    public void loginToMainPage(ActionEvent actionEvent) throws IOException {
        if (isValid()) {
            checkUpcomingAppointments();
            if(!checkUpcomingAppointments()) {
                Messages.noUpcomingAppointment();
            }
            Parent root = FXMLLoader.load((getClass().getResource("/view/mainPageView.fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.show();
            stage.setScene(scene);
        } else {
            Messages.invalidLogin();
        }
    }

    private boolean isValid() {
        for (User user : User.getUserArrayList()) {
            if (user.getUserName().equals(userNameTF.getText().trim()) && user.getPassword().equals(passwordTF.getText().trim())) {
                User.currentUser = user;
                return true;
            }
        }
        return false;
    }

    public boolean checkUpcomingAppointments() {
        boolean hasAppt = false;

        for (Appointment appointment : Appointment.appointmentsList) {
            if (appointment.getStartDateTime().toLocalDateTime().toLocalDate().isBefore(ChronoLocalDate.from(LocalTime.now().plusMinutes(15))) &&
                    User.currentUser.getUserId() == appointment.getUserId()) {
                hasAppt = true;
                Messages.upcomingAppointment(appointment);
                break;
            }
        }
        return hasAppt;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDAO.loadAllUsers();
        setZoneID(zoneID);
        // TODO add code if user language is French

    }
}
