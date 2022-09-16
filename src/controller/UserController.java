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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.MessageLambdaInterface;
import model.User;
import utilities.ChangeView;
import utilities.Messages;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/**
 * UserController -- -- Displays tables of users, allows user deletion, and redirects to add/update user page.
 */
public class UserController implements Initializable {
    static ObservableList<User> usersList = UserDAO.loadAllUsers();
    private static User selectedUser;

    /** user table components */
    @FXML private TableView<User> userTableView;
    @FXML private TableColumn<User, Integer> userIdCol;
    @FXML private TableColumn<User, String> userNameCol;
    @FXML private TableColumn<User, String> passwordCol;
    @FXML private TableColumn<User, Timestamp> createDateCol;
    @FXML private TableColumn<User, String> createdByCol;
    @FXML private TableColumn<User, Timestamp> lastUpdateCol;
    @FXML private TableColumn<User, String> lastUpdatedByCol;


    /**
     * toAddUser -- changes the view to AddUserView
     * @param actionEvent -- Add User button clicked
     * @throws IOException
     */
    public void toAddUser(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "AddUserView.fxml");
    }

    /**
     * toMainMenu -- changes the view to the Main Page View
     * @param actionEvent -- Main Menu button clicked
     * @throws IOException
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "MainPageView.fxml");
    }

    /**
     * toUpdateUser -- admin selects a user to update and changes the view to UpdateContactView
     * @param actionEvent -- user to update is selected and then Update User button is clicked
     * @throws IOException
     */
    public void toUpdateUser(ActionEvent actionEvent) throws IOException {
        selectedUser = userTableView.getSelectionModel().getSelectedItem();
        /** if no user was selected an error message is displayed */
        if (selectedUser == null) {
            Messages.selectionNeeded();
            return;
        } else {
            /** the selected user's data is sent to the update user controller */
            UpdateUserController.getSelectedUser(selectedUser);
            /** changes the view to the UpdateUserView */
            new ChangeView(actionEvent, "UpdateUserView.fxml");
        }
    }

    /**
     * noAppointments -- checks if user to delete has upcoming appointments.
     * @return -- Returns false if user has upcoming appointments, returns true if user does not have an appts scheduled.
     */
    private boolean noAppointments() {
        selectedUser = userTableView.getSelectionModel().getSelectedItem();
        int userId = selectedUser.getUserId();
        ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

        /** loops through the appointment db looking for appointments with the user id, if an appt is found an
         *  error message is displayed telling the admin they must delete the appt before they can delete the user
         */
        for(Appointment appointment : appointments) {
            if(appointment.getUserId() == userId) {
                Messages.hasAppointments(selectedUser.getUserName());
                return false;
            }
        }
        return true;
    }

    /**
     * deleteUser -- deletes selected user if user does not have upcoming appts.
     * @param actionEvent -- user to be deleted is selected and Delete User button is clicked.
     */
    public void deleteUser(ActionEvent actionEvent) {
        selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if (selectedUser.getUserId() == 2) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Unable to delete admin");
            alert.setContentText("admin can not be deleted, admin has special privileges.");
            alert.showAndWait();
            return;
        }
        /** Displays error message if a user to delete is not selected */
        if (selectedUser == null) {
            Messages.selectionNeeded();
            return;
        } else {
            /** Checks if contact has an appts by calling the noAppointments method */
            if(noAppointments()) {
                /** if user did not have any upcoming appointment, a delete confirmation is displayed */
                boolean deleteConfirm = Messages.deleteConfirmation(selectedUser.getUserName());
                /** if delete is confirmed, delete user, display console message confirming delete */
                if(deleteConfirm) {
                    int userId = selectedUser.getUserId();
                    /** user is deleted from the db */
                    UserDAO.deleteUser(userId);

                    /** Lambda expression -- console message verifying delete */
                    MessageLambdaInterface message = s -> System.out.println(s + " deleted.");
                    message.displayMessage(selectedUser.getUserName());

                    /** the user table is reloaded and the deleted user is removed form the table display */
                    userTableView.setItems(UserDAO.loadAllUsers());
                    userTableView.refresh();
                }
            }
        }
    }

    /**
     * setUsersTableView -- populates user data to appropriate columns in the user table
     */
    public void setUsersTableView() {
        userTableView.setItems(usersList);
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
    }

    /**
     * initialize -- calls the setUsersTableView method to load the users table when page is loaded.
     * @param url -- not used
     * @param resourceBundle -- not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUsersTableView();
    }
}
