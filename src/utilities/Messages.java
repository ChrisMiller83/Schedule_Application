package utilities;

import javafx.scene.control.Alert;
import model.Appointment;

import java.sql.SQLException;

public class Messages {

    public static void emptyField() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Check Fields");
        alert.setContentText("Error: One or more fields may be empty.");
        alert.showAndWait();
    }

    public static void SQLException(SQLException e) {
        Alert alert = new Alert((Alert.AlertType.ERROR));
        alert.setTitle("ERROR");
        alert.setContentText("There was an error: " + e.getMessage());
        alert.showAndWait();
    }

    public static void upcomingAppointment (Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You have an upcoming appointment:");
        alert.setHeaderText("Appointment ID: " + appointment.getApptId() + "\n" +
                "Start Time: " + appointment.getStartDateTime().toLocalDateTime().toLocalDate() + "\n" +
                "With " + appointment.getContactId() + " and " + appointment.getCustomerId());
        alert.showAndWait();
    }

    public static void noUpcomingAppointment () {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You have no upcoming appointments:");
        alert.setHeaderText("No upcoming appointments");
        alert.showAndWait();
    }

    public static void invalidLogin () {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Unable to Login");
        alert.setHeaderText("Please check your username and password.");
        alert.showAndWait();
    }

    public static void errorWindow (int number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!! ERROR ALERT !!");
        switch (number){
            case 1:
            {
                alert.setContentText("Invalid format: Letters are required.");
            }
            case 2:
            {
                alert.setContentText("Zip Code Length:  Zip code must not be longer than 5 numbers.");
            }
            default:
            {
                alert.setContentText("ERROR UNKNOWN");
            }
        }
        alert.showAndWait();
    }
}
