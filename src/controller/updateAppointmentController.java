package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class updateAppointmentController implements Initializable {
    public TextField appointIdTF;
    public ChoiceBox customerIdChoiceBox;
    public ChoiceBox userChoiceBox;
    public TextField titleTF;
    public TextField descriptionTF;
    public TextField locationTF;
    public ChoiceBox contactChoiceBox;
    public ChoiceBox typeChoiceBox;
    public DatePicker datePicker;
    public ChoiceBox startTimeChoiceBox;
    public ChoiceBox endTimeChoiceBox;
    public Button cancelBtn;
    public Button saveBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cancelToAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointmentsView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {




        Parent root = FXMLLoader.load(getClass().getResource("/view/appointmentsView.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
