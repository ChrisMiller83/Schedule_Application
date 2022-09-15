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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * LoginController -- Logs the user in, displays the login page in the users current language settings (English or French)
 */
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
    @FXML private final ResourceBundle languages = ResourceBundle.getBundle("languages/Language");

    private static final String filename = "login_activity.txt";

    public LoginController() {
    }

    /**
     * setZoneID -- gets the zoneID of the users location settings
     * @param zoneID -- displays the user's zoneId in the zoneId label
     */
    public void setZoneID(Label zoneID) {
        zoneID.setText(String.valueOf(ZoneId.systemDefault()));
    }

    /**
     * clearTextFieldsBtn -- clears the Username and Password text fields
     * @param actionEvent -- Clear button is clicked
     */
    public void clearTextFieldsBtn(ActionEvent actionEvent) {
        userNameTF.clear();
        passwordTF.clear();
        userNameTF.setPromptText(languages.getString("Username"));
        passwordTF.setPromptText(languages.getString("Password"));
    }

    /**
     * loginToMainPage -- logs in the user and redirects the view to the main page if username and password are valid
     * A For Each Lambda expression is used in this method to replace the for loop that loops through the appointments list.
     * @param actionEvent -- Login button clicked
     * @throws IOException
     */
    public void loginToMainPage(ActionEvent actionEvent) throws IOException {
        /** validLogin checks if the username and password entered match username and password in the db. */
        storeLoginActivity();
        if (validLogin()) {
            /** storeLoginActivity is a BufferedWriter that stores login attempts */

            int userId = User.currentUser.getUserId();

            ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime timePlus15 = LocalDateTime.now().plusMinutes(15);
            AtomicBoolean hasAppt = new AtomicBoolean(false);

            /** FOR EACH-LAMBDA --Loops through all appointments in the db looking for appts starting within 15 minutes that match the user id, displays a message
             * with the upcoming appt info or displays a message with no appts if no appts were found. (Messages are display in current languge
             * settings)
            */
            appointments.forEach(appointment -> {
                LocalDateTime start = appointment.getStartDateTime();
                if((start.isAfter(timeNow)) && (start.isBefore(timePlus15)) && appointment.getUserId() == userId) {
                    hasAppt.set(true);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(languages.getString("UpcomingApptTitle"));
                    alert.setHeaderText(languages.getString("AppointmentID") + " " + appointment.getApptId() +
                            "\n" + languages.getString("StartDate") + " " + appointment.getStartDateTime().toLocalDate() +
                            "\n" + languages.getString("StartTime") + " " + appointment.getStartDateTime().toLocalTime());
                    alert.showAndWait();
                }
            });
            if(!hasAppt.get()) {
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languages.getString("ErrorInvalidLogin"));
            alert.setHeaderText(languages.getString("ErrorCheckUsernameAndPassword"));
            alert.showAndWait();
            return;
        }
    }

    /**
     * validLogin -- Checks the username and password the user entered against usernames/passwords in the db.
     * @return -- returns true if username/password match an entry in the db, returns false if the username or password do not match an entry.
     * @throws IOException
     */
    private boolean validLogin() throws IOException {
        ObservableList<User> allUsers = UserDAO.loadAllUsers();
        for (User user : allUsers) {
            if (user.getUserName().equals(userNameTF.getText().trim()) && user.getPassword().equals(passwordTF.getText().trim())) {
                User.currentUser = user;
                return true;
            }
        }
        return false;
    }

    /**
     * storeLoginActivity -- method that uses a BufferWriter to save login attempt entries in a txt file that is later read in the ReportsController
     * @throws IOException
     */
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


    /**
     * initialize -- when the page loads, the initializer gets the current zoneId, gets the current Locale language setting and changes all labels
     * and text fields diplay text to the current language(English or French)
     * @param url -- not used
     * @param resourceBundle -- Language bundle for English or French phrases used on the login page and error messages.
     */
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
