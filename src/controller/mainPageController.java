package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainPageController implements Initializable {



    @FXML public Label timeZoneLabel;

    public Button appointmentsBtn;
    @FXML public Button customersBtn;
    public Button viewReportsBtn;
    public Button logOutBtn;
    @FXML public Button quitBtn;





    public void toAppointmentsView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/appointmentsView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toCustomersView(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/customersView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void toReportsView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/reportsView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void logOutUserToLoginPage(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load((getClass().getResource("/view/loginView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void quitApp(ActionEvent actionEvent) throws IOException{
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




}
