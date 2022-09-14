package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.User;
import utilities.ChangeView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MainPageController -- Allows the user to select a specific view to be redirected to.
 */
public class MainPageController implements Initializable {

    public Button appointmentsBtn;
    @FXML public Button customersBtn;
    public Button viewReportsBtn;
    public Button logOutBtn;
    @FXML public Button quitBtn;
    public Button contactsBtn;
    public Button usersBtn;


    /**
     * toAppointmentsView -- changes the view to the ApptsView page
     * @param actionEvent -- Appointments button clicked
     * @throws IOException
     */
    public void toAppointmentsView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "ApptsView.fxml");
    }

    /**
     * toCustomerView -- changes the view to the CustomerView page
     * @param actionEvent -- Customers button clicked
     * @throws IOException
     */
    public void toCustomersView(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "CustomerView.fxml");
    }

    /**
     * toReportsView -- changes the view to the ReportsView page
     * @param actionEvent -- View Reports button clicked
     * @throws IOException
     */
    public void toReportsView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "ReportsView.fxml");
    }

    /**
     * toContactsView -- changes the view to the ContactsView page
     * @param actionEvent -- Contacts button clicked
     * @throws IOException
     */
    public void toContactsView(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "ContactsView.fxml");
    }

    /**
     * toUsersView -- changes the view to the UsersView page
     * @param actionEvent -- Users button clicked
     * @throws IOException
     */
    public void toUsersView(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "UsersView.fxml");
    }

    /**
     * logOutUserToLoginPage -- logs the user out and changes the view to the LoginView page
     * @param actionEvent -- Log Out button clicked
     * @throws IOException
     */
    public void logOutUserToLoginPage(ActionEvent actionEvent) throws IOException{
        new ChangeView(actionEvent, "LoginView.fxml");
    }

    /**
     * quitApp -- Closes and Exits the application
     * @param actionEvent -- Exit button clicked
     * @throws IOException
     */
    public void quitApp(ActionEvent actionEvent) throws IOException{
        System.exit(0);
    }

    /**
     * checkUser -- checks if the current user name is admin, if not it disables the toUsersView button.
     */
    public void checkUser(){
        usersBtn.setDisable(!User.currentUser.getUserName().equals("admin"));
    }

    /**
     * initialize -- gets the current user for the checkUser message when the page loads.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDAO.loadAllUsers();
        checkUser();
    }
}
