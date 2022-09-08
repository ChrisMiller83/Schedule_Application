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
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
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
        passwordTF.clear();
        userNameTF.setPromptText(languages.getString("Username"));
        passwordTF.setPromptText(languages.getString("Password"));
    }

    public void loginToMainPage(ActionEvent actionEvent) throws IOException {
        if (validLogin()) {
            int userId = User.currentUser.getUserId();

            ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

            Timestamp timeNow = Timestamp.valueOf(LocalDateTime.now());
            Timestamp timePlus15 = Timestamp.valueOf(LocalDateTime.now().plusMinutes(15));


            for (Appointment appointment : appointments) {
                Timestamp start = appointment.getStartDateTime();
                if((start.after(timeNow)) && (start.before(timePlus15)) && appointment.getUserId() == userId) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(languages.getString("UpcomingApptTitle"));
                    alert.setHeaderText(languages.getString("AppointmentID") + " " + appointment.getApptId() + "\n" +
                            languages.getString("StartTime") + " " + appointment.getStartDateTime().toLocalDateTime().toLocalDate());
                    alert.showAndWait();
                    break;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(languages.getString("ErrorNoApptsTitle"));
                    alert.setHeaderText(languages.getString("NoUpcomingAppts"));
                    alert.showAndWait();
                    break;
                }
            }
            new ChangeView(actionEvent, "MainPageView.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languages.getString("ErrorInvalidLogin"));
            alert.setHeaderText(languages.getString("ErrorCheckUsernameAndPassword"));
            alert.showAndWait();
            return;
        }
    }

    private boolean validLogin() {
        ObservableList<User> allUsers = UserDAO.loadAllUsers();
        for (User user : Objects.requireNonNull(allUsers)) {
            if (user.getUserName().equals(userNameTF.getText().trim()) && user.getPassword().equals(passwordTF.getText().trim())) {
                User.currentUser = user;
                return true;
            }
        }
        return false;
    }

    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(String.valueOf(LocalDateTime.now()));
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

    }
}
