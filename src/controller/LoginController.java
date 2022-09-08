package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.User;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {

    @FXML private Label titleLabel;
    @FXML private Label locationLabel;
    @FXML private Label zoneID;
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField userNameTF;
    @FXML private TextField passwordTF;
    @FXML private Button clearTextFieldsBtn;
    @FXML private Button loginBtn;
    @FXML private ResourceBundle languages = ResourceBundle.getBundle("languages/Language");

    public LoginController() {}

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
            new ChangeView(actionEvent, "MainPageView.fxml");
        } else {
            Messages.invalidLogin();
        }
    }

    private boolean isValid() {
        ObservableList<User> allUsers = UserDAO.loadAllUsers();
        for (User user : Objects.requireNonNull(allUsers)) {
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
        Locale currentLanguage = Locale.getDefault();
        Locale.setDefault(currentLanguage);
        titleLabel.setText(languages.getString("Title"));
        locationLabel.setText(languages.getString("Location"));
        userNameLabel.setText(languages.getString("Username"));
        userNameTF.setPromptText(languages.getString("Username"));
        passwordLabel.setText(languages.getString("Password"));
        passwordTF.setPromptText(languages.getString("Password"));
        clearTextFieldsBtn.setText(languages.getString("Clear"));
        loginBtn.setText(languages.getString("Login"));

        // TODO add code if user language is French

    }
}
