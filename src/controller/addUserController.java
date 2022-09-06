package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utilities.ChangeView;

import javafx.event.ActionEvent;
import java.io.IOException;

public class addUserController {
    @FXML private Button cancelBtn;
    @FXML private TextField passwordTF;
    @FXML private Button saveBtn;
    @FXML private TextField userIdTF;
    @FXML private TextField userNameTF;

    @FXML
    void cancelToUserView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "usersView.fxml");

    }

    @FXML
    void saveUser(ActionEvent actionEvent) {

    }
}
