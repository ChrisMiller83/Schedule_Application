package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.sql.SQLException;
import java.util.Optional;

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

    public static void validateCustomerError(int number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR SOMETHING WENT WRONG");
        switch (number) {
            case 1:
            {
                alert.setContentText("Customer name field is empty: Please add a customer name");
                break;
            }
            case 2:
            {
                alert.setContentText("Customer address field is empty: Please add a customer address");
                break;
            }
            case 3:
            {
                alert.setContentText("Customer postal code field is empty: Please add a postal code");
                break;
            }
            case 4:
            {
                alert.setContentText("Customer phone number field is empty: Please add a phone number");
                break;
            }
            case 5:
            {
                alert.setContentText("Customer country is empty: Please select a country");
                break;
            }
            case 6:
            {
                alert.setContentText("Customer state/province is empty: Please select a state/province");
                break;
            }
            case 7:
            {
                alert.setContentText("Customer postal code must be 5 characters long");
            }
            default:
            {
                alert.setContentText("One or more fields may be empty");
                break;
            }
        }
        alert.showAndWait();
    }

    public static void apptEmptyField(int number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Empty Fields");
        switch (number) {
            case 1:
            {
                alert.setContentText("Title text-field is empty:  Please add a title.");
                break;
            }
            case 2:
            {
                alert.setContentText("Description text-field is empty:  Please add a description.");
                break;
            }
            case 3:
            {
                alert.setContentText("Location text-field is empty:  Please add a location.");
                break;
            }
            case 4:
            {
                alert.setContentText("The type choice-box is empty:  Please select a meeting type");
                break;
            }
            case 5:
            {
                alert.setContentText("Start date is empty:  Please select a start date.");
                break;
            }
            case 6:
            {
                alert.setContentText("Start time is empty:  Please select a start time");
                break;
            }
            case 7:
            {
                alert.setContentText("End date is empty:  Please select an end date.");
                break;
            }
            case 8:
            {
                alert.setContentText("End time is empty:  Please select an end time.");
                break;
            }
            case 9:
            {
                alert.setContentText("The customer choice box is empty:  Please select a customer.");
                break;
            }
            case 10:
            {
                alert.setContentText("The contact choice box is empty:  Please select a contact.");
                break;
            }
            default:
            {
                alert.setContentText("One of more items are empty");
                break;
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
        alert.setTitle("!! ERROR CHECK YOUR START TIME");
        alert.setContentText("You start time can not be after your end time.");
        alert.showAndWait();
    }

    public static void checkEndTime() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! ERROR CHECK YOUR STARt TIME");
        alert.setContentText("You end time can not be before your start time.");
        alert.showAndWait();
    }

    public static void selectACustomerToUpdate() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! Missing Customer TO Update");
        alert.setContentText("Please select a customer to update first.");
        alert.showAndWait();
    }



    public static void selectACustomerToDelete() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! Missing Customer TO Delete");
        alert.setContentText("Please select a customer to delete first.");
        alert.showAndWait();
    }

    public static boolean deleteConfirmation(String itemToDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!! DELETE CONFORMATION !!");
        alert.setContentText("Delete: " + itemToDelete);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static boolean addConfirmation(String itemToAdd) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!! ADD CONFORMATION !!");
        alert.setContentText("Add: " + itemToAdd);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static boolean updateConfirmation(String itemToUpdate) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!! UPDATE CONFORMATION !!");
        alert.setContentText("Update: " + itemToUpdate);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
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
