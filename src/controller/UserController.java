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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.User;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    static ObservableList<User> users;

    private static User userToUpdate;

    @FXML private TableView<User> userTableView;
    @FXML private TableColumn<User, Integer> userIdCol;
    @FXML private TableColumn<User, String> userNameCol;
    @FXML private TableColumn<User, String> passwordCol;
    @FXML private TableColumn<User, Timestamp> createDateCol;
    @FXML private TableColumn<User, String> createdByCol;
    @FXML private TableColumn<User, Timestamp> lastUpdateCol;
    @FXML private TableColumn<User, String> lastUpdatedByCol;



    private boolean noAppointments() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        int userId = selectedUser.getUserId();
        ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

        for(Appointment appointment : appointments) {
            if(appointment.getUserId() == userId) {
                Messages.hasAppointments(selectedUser.getUserName());
                return false;
            }
        }
        return true;
    }
    @FXML
    void deleteUser(ActionEvent actionEvent) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Messages.selectionNeeded();
            return;
        } else {
            if(noAppointments()) {
                int userId = selectedUser.getUserId();
                boolean deleteConfirm = Messages.deleteConfirmation(selectedUser.getUserName());
                if(deleteConfirm) {
                    System.out.println("User deleted: " + selectedUser.getUserName());
                    UserDAO.deleteUser(userId);
                    userTableView.setItems(UserDAO.loadAllUsers());
                    userTableView.refresh();
                }
            }
        }
    }

    @FXML
    void toAddUser(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "AddUserView.fxml");
    }

    @FXML
    void toMainMenu(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "MainPageView.fxml");
    }

    @FXML
    void toUpdateUser(ActionEvent actionEvent) throws IOException {
        userToUpdate = userTableView.getSelectionModel().getSelectedItem();
        if (userToUpdate == null) {
            Messages.selectionNeeded();
            return;
        } else {
            UpdateUserController.getSelectedUser(userToUpdate);
            new ChangeView(actionEvent, "UpdateUserView.fxml");
        }
    }

    public void setUsersTableView(ObservableList<User> users) {
        userTableView.setItems(users);
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUsersTableView(UserDAO.loadAllUsers());

    }
}
