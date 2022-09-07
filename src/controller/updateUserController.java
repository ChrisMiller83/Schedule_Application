package controller;

import DAO.UserDAO;
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

public class updateUserController implements Initializable {
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

        public void setSelectedUser(User selectedUser) {
                userIdTF.setText(Integer.toString(selectedUser.getUserId()));
                userNameTF.setText(selectedUser.getUserName());
                passwordTF.setText(selectedUser.getPassword());
        }

        public static void getSelectedUser(User user) {
                selectedUser = user;
        }

        @FXML
        public void cancelToUserView(ActionEvent actionEvent) throws IOException {
            new ChangeView(actionEvent, "usersView.fxml");
        }

        @FXML
        void updateUser(ActionEvent actionEvent) {
                if(validateUser()) {
                        userName = userNameTF.getText();
                        password = passwordTF.getText();
                        lastUpdated = Timestamp.valueOf(LocalDateTime.now());
                        lastUpdatedBy = User.currentUser.getUserName();
                        userId = Integer.parseInt(userIdTF.getText());
                        boolean updateConfirm = Messages.updateConfirmation(userNameTF.getText());
                        if(updateConfirm) {
                                UserDAO.updateUser(userName, password, lastUpdated, lastUpdatedBy, userId);
                        } else {
                                return;
                        }
                }

        }

        private boolean validateUser() {
                if(userNameTF.getText().isEmpty()) {
                        Messages.validateUserError(1);
                        return false;
                }
                if (passwordTF.getText().isEmpty()) {
                        Messages.validateUserError(2);
                        return false;
                }
                return true;
        }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                setSelectedUser(selectedUser);

        }
        }


