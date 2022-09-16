package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.MessageLambdaInterface;
import model.User;
import utilities.ChangeView;
import javafx.event.ActionEvent;
import utilities.Messages;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * AddUserController -- adds users to db only if current user is admin
 */
public class AddUserController {

    private String userName;
    private String password;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    @FXML private TextField userIdTF;
    @FXML private TextField userNameTF;
    @FXML private TextField passwordTF;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;

    /**
     * cancelToUserView -- changes view to UserView
     * @param actionEvent -- cancel button clicked
     * @throws IOException
     */
    public void cancelToUserView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "UsersView.fxml");

    }

    /**
     * saveUser  -- calls validateUser method to run validation checks,
     * if validation checks pass, all info from the fields are gathered
     * and user is added to the db, otherwise error messages are given.
     * @param actionEvent -- save button clicked
     * @throws IOException
     */
    public void saveUser(ActionEvent actionEvent) throws IOException {
        /** validateUser method called to check for errors */
        if (validateUser()) {
            /** if no errors found, gather data from fields */
            userName = userNameTF.getText();
            password = passwordTF.getText();
            createdDate = Timestamp.valueOf(LocalDateTime.now());
            createdBy = User.currentUser.getUserName();
            lastUpdated = Timestamp.valueOf(LocalDateTime.now());
            lastUpdatedBy = User.currentUser.getUserName();
            /** Confirmation message to add user */
            boolean saveConfirm = Messages.addConfirmation(userName);
            /** If confirmation was ok/yes user is added to the db. */
            if (saveConfirm) {
                UserDAO.addUser(userName, password, createdDate, createdBy,lastUpdated,lastUpdatedBy);

                /** Lambda expression -- console message verifying add */
                MessageLambdaInterface message = s -> System.out.println(s + " added.");
                message.displayMessage(userName);

            } else {
                /** If confirmation was no/cancel, returns to addUserView with current add data in the fields */
                return;
            }
            /** ChangeView brings the user back to the UsersView page when a user is added to the db. */
            new ChangeView(actionEvent, "UsersView.fxml");
        } else {
            /** validateUser found an error, error message displayed, and user is returned to addUsers page. */
            return;
        }
    }

    /**
     * validateUser:  Is called in saveUser, it checks for empty fields.
     * @return -- false if one of the checks are found, otherwise returns true and allows saveUser to continue
     */
    private boolean validateUser() {
        /** Checker:  user name text field is empty */
        if (userNameTF.getText().isEmpty()) {
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
}
