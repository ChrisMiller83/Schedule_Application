package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.AppointmentDAO;
import dao.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.User;
import utilities.ChangeView;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private static final String filename = "login_activity.txt";

    public LoginController() {
    }

    public void setZoneID(Label zoneID) {
        zoneID.setText(String.valueOf(ZoneId.systemDefault()));
    }

    public void clearTextFieldsBtn(ActionEvent actionEvent) {
        userNameTF.clear();
        passwordTF.clear();
        userNameTF.setPromptText(languages.getString("Username"));
        passwordTF.setPromptText(languages.getString("Password"));
    }

    public void loginToMainPage(ActionEvent actionEvent) throws IOException {
        if (validLogin()) {
            storeLoginActivity();
            int userId = User.currentUser.getUserId();

            ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime timePlus15 = LocalDateTime.now().plusMinutes(15);
            boolean hasAppt = false;

            for (Appointment appointment : appointments) {
                LocalDateTime start = appointment.getStartDateTime();
                if((start.isAfter(timeNow)) && (start.isBefore(timePlus15)) && appointment.getUserId() == userId) {
                    hasAppt = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(languages.getString("UpcomingApptTitle"));
                    alert.setHeaderText(languages.getString("AppointmentID") + " " + appointment.getApptId() +
                            "\n" + languages.getString("StartDate") + " " + appointment.getStartDateTime().toLocalDate() +
                            "\n" + languages.getString("StartTime") + " " + appointment.getStartDateTime().toLocalTime());
                    alert.showAndWait();
                }
            }

            if(!hasAppt) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(languages.getString("ErrorNoApptsTitle"));
                alert.setHeaderText(languages.getString("NoUpcomingAppts"));
                alert.showAndWait();
            }

            /** Once login and appt messages are shown, reset the Locale language to English and open the Main Page View */
            Locale currentLanguage = new Locale("en");
            Locale.setDefault(currentLanguage);
            new ChangeView(actionEvent, "MainPageView.fxml");
        } else {
            storeLoginActivity();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languages.getString("ErrorInvalidLogin"));
            alert.setHeaderText(languages.getString("ErrorCheckUsernameAndPassword"));
            alert.showAndWait();
            return;
        }
    }

    private boolean validLogin() throws IOException {
        ObservableList<User> allUsers = UserDAO.loadAllUsers();
        for (User user : Objects.requireNonNull(allUsers)) {
            if (user.getUserName().equals(userNameTF.getText().trim()) && user.getPassword().equals(passwordTF.getText().trim())) {
                User.currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void storeLoginActivity() throws IOException {
        File file = new File(filename);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fileWriter);

        bw.write("Login attempt by username:   " + userNameTF.getText() + "    Date/Time:  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")) + "   Login: ");
        if (validLogin()) {
            bw.write("Successful");
        } else {
            bw.write("Failed");
        }
        bw.newLine();
        bw.close();
    }

    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }
}
