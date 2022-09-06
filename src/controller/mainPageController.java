package controller;

import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import utilities.ChangeView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainPageController implements Initializable {

    public Button appointmentsBtn;
    @FXML public Button customersBtn;
    public Button viewReportsBtn;
    public Button logOutBtn;
    @FXML public Button quitBtn;
    public Button contactsBtn;
    public Button usersBtn;


    public void toAppointmentsView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "appointmentsView.fxml");
    }

    public void toCustomersView(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "customerView.fxml");

    }


    public void toReportsView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "reportsView.fxml");
    }

    public void toContactsView(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "contactView.fxml");
    }

    public void toUsersView(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "usersView.fxml");
    }


    public void logOutUserToLoginPage(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "loginView.fxml");
    }

    public void quitApp(ActionEvent actionEvent) throws IOException{
        System.exit(0);
    }

    public void checkUser(){
        if(User.currentUser.getUserName().equals("admin")){
            usersBtn.setDisable(false);
        } else {
            usersBtn.setDisable(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDAO.loadAllUsers();
        checkUser();
    }
}
