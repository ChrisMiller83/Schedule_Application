package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.User;
import utilities.ChangeView;

import javafx.event.ActionEvent;
import utilities.Messages;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @FXML
    void cancelToUserView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "UsersView.fxml");

    }

    @FXML
    void saveUser(ActionEvent actionEvent) throws IOException {
        if (validateUser()) {
            userName = userNameTF.getText();
            password = passwordTF.getText();
            createdDate = Timestamp.valueOf(LocalDateTime.now());
            createdBy = User.currentUser.getUserName();
            lastUpdated = Timestamp.valueOf(LocalDateTime.now());
            lastUpdatedBy = User.currentUser.getUserName();

            boolean saveConfirm = Messages.addConfirmation(userName);
            if (saveConfirm) {
                UserDAO.addUser(userName, password, createdDate, createdBy,lastUpdated,lastUpdatedBy);
                System.out.println("New user added: " + userName);
            } else {
                return;
            }
            new ChangeView(actionEvent, "UsersView.fxml");
        } else {
            return;
        }
    }

    private boolean validateUser() {
        if (userNameTF.getText().isEmpty()) {
            Messages.validateUserError(1);
            return false;
        }
        if (passwordTF.getText().isEmpty()) {
            Messages.validateUserError(2);
            return false;
        }
        return true;
    }
}
