package utilities;

import javafx.scene.control.Alert;
import model.Appointment;

import java.sql.SQLException;

public class Messages {

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

    public static void emptyField(int number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Empty Fields");
        switch (number) {
            case 1:
            {
                alert.setContentText("Title text-field is empty:  Please add a title.");
            }
            case 2:
            {
                alert.setContentText("Description text-field is empty:  Please add a description.");
            }
            case 3:
            {
                alert.setContentText("Location text-field is empty:  Please add a location.");
            }
            case 4:
            {
                alert.setContentText("The type choice-box is empty:  Please select a meeting type");
            }
            case 5:
            {
                alert.setContentText("Start date is empty:  Please select a start date.");
            }
            case 6:
            {
                alert.setContentText("Start time is empty:  Please select a start time");
            }
            case 7:
            {
                alert.setContentText("End date is empty:  Please select an end date.");
            }
            case 8:
            {
                alert.setContentText("End time is empty:  Please select an end time.");
            }
            case 9:
            {
                alert.setContentText("The customer choice box is empty:  Please select a customer.");
            }
            case 10:
            {
                alert.setContentText("The contact choice box is empty:  Please select a contact.");
            }
            default:
            {
                alert.setContentText("One of more items are empty");
            }

        }
        alert.showAndWait();
    }

    public static void checkApptDates() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! ERROR CHECK YOUR DATES AND TIME");
        alert.setContentText("You can not make an appointment in the past, please check your dates and time");
        alert.showAndWait();
    }

    public static void checkStartTime() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! ERROR CHECK YOUR STARt TIME");
        alert.setContentText("You start time can not be after your end time.");
        alert.showAndWait();
    }

    public static void checkEndTime() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! ERROR CHECK YOUR STARt TIME");
        alert.setContentText("You end time can not be before your start time.");
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
