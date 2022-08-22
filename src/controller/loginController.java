package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    public Label zoneID;
    public TextField userNameTF;
    public TextField passwordTF;
    public Button clearTextFieldsBtn;
    public Button loginBtn;

    public void clearTextFieldsBtn(ActionEvent actionEvent) {
        userNameTF.clear();
        userNameTF.setPromptText("Username");
        passwordTF.clear();
        passwordTF.setPromptText("Password");
    }

    public void loginToMainPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/mainPageView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
