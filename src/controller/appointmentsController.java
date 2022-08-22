package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utilities.ChangeView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class appointmentsController implements Initializable {


    public Button scheduleApptBtn;
    public Button updateApptBtn;
    public Button deleteApptBtn;
    public RadioButton allApptRBtn;
    public ToggleGroup selectedView;
    public RadioButton monthlyApptBtn;
    public RadioButton weeklyApptRBtn;
    public TableView appointmentsTable;
    public TableColumn appointmentIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn dateCol;
    public TableColumn startTimeCol;
    public TableColumn endTimeCol;
    public TableColumn customerIdCol;
    public TableColumn userIdCol;
    public Button mainMenuBtn;



    public void toScheduleAppointmentView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/scheduleAppointmentView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void toUpdateAppointmentView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/updateAppointmentView.fxml")));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteAppointment(ActionEvent actionEvent) throws IOException {

    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
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
