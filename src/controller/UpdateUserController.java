package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.User;
import utilities.ChangeView;
import utilities.Messages;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * UpdateUserController -- updates selected user - only if current user is admin
 */
public class UpdateUserController implements Initializable {
    private static User selectedUser;

    private Integer userId;
    private String userName;
    private String password;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;
    @FXML private TextField userIdTF;
    @FXML private TextField userNameTF;
    @FXML private TextField passwordTF;

    /**
     * setSelectedUser -- populates all the data fields from the selected user that was
     * selected in the UserController page.
     * @param selectedUser -- user selected to update data.
     */
    public void setSelectedUser(User selectedUser) {
        userIdTF.setText(Integer.toString(selectedUser.getUserId()));
        userNameTF.setText(selectedUser.getUserName());
        passwordTF.setText(selectedUser.getPassword());
    }

    /**
     * getSelectedUser -- gets the data from the selected user from the UserController
     * @param user -- selected user from the UserController
     */
    public static void getSelectedUser(User user) {
        selectedUser = user;
    }

    /**
     * cancelToUserView -- changes view to UserView
     * @param actionEvent -- cancel button clicked
     * @throws IOException
     */
    public void cancelToUserView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "UsersView.fxml");
    }

    /**
     * updateUser  -- calls validateUser method to run validation checks,
     * if validation checks pass, all info from the fields are gathered
     * and user is updated in the db, otherwise error messages are given.
     * @param actionEvent -- save button clicked
     * @throws IOException
     */
    public void updateUser(ActionEvent actionEvent) throws IOException {
        /** validateUser method called to check for errors */
        if(validateUser()) {
            /** if no errors found, gather data from fields */
            userName = userNameTF.getText();
            password = passwordTF.getText();
            lastUpdated = Timestamp.valueOf(LocalDateTime.now());
            lastUpdatedBy = User.currentUser.getUserName();
            userId = Integer.parseInt(userIdTF.getText());
            /** Confirmation message to add user */
            boolean updateConfirm = Messages.updateConfirmation(userNameTF.getText());
            /** If confirmation was ok/yes user is added to the db. */
            if(updateConfirm) {
                UserDAO.updateUser(userName, password, lastUpdated, lastUpdatedBy, userId);
                /** console message verifying add */
                System.out.println("User updated: " + userName);
            } else {
                /** If confirmation was no/cancel, returns to updateUserView with current update data in the fields */
                return;
            }
            /** ChangeView brings the user back to the UsersView page when a user is updated in the db. */
            new ChangeView(actionEvent, "UsersView.fxml");
        } else {
            /** validateUser found an error, error message displayed, and user is returned to updateUsers page. */
            return;
        }
    }

    /**
     * validateUser:  Is called in updateUser, it checks for empty fields.
     * @return -- false if one of the checks are found, otherwise returns true and allows updateUser to continue
     */
    private boolean validateUser() {
        /** Checker:  user name text field is empty */
        if(userNameTF.getText().isEmpty()) {
            Messages.validateUserError(1);
            return false;
        }
        /** Checker:  password text field is empty */
        if (passwordTF.getText().isEmpty()) {
            Messages.validateUserError(2);
            return false;
        }
        return true;
    }

    /**
     * initialize -- sets selected customer data in appropriate fields when page is loaded
     * @param url -- not used
     * @param resourceBundle -- not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSelectedUser(selectedUser);
    }
}


