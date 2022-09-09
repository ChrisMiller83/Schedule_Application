package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.sql.SQLException;
import java.util.Optional;

public class Messages {

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
            default:
            {
                alert.setContentText("One or more fields may be empty");
                break;
            }
        }
        alert.showAndWait();
    }

    public static void validateAppt(int number) {
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
                alert.setContentText("Appointment date is empty:  Please select an appointment date.");
                break;
            }
            case 6:
            {
                alert.setContentText("Start time is empty:  Please select a start time");
                break;
            }
            case 7:
            {
                alert.setContentText("End time is empty:  Please select an end time.");
                break;
            }
            case 8:
            {
                alert.setContentText("Appointment end time can not be before or equal to the start time");
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
            case 11:
            {
                alert.setContentText("Appointment time conflicts with an existing customer appointment.");
                break;
            }
            case 12:
            {
                alert.setContentText("Appointment times must be within business hours of 08:00 - 22:00 Eastern Standard Time.");
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
        alert.setTitle("!! ERROR CHECK YOUR START TIME");
        alert.setContentText("You end time can not be before your start time.");
        alert.showAndWait();
    }

    public static void selectAnItemToUpdate(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! Missing " + s + " To Update");
        alert.setContentText("Please select a " + s.toLowerCase() + " to update first.");
        alert.showAndWait();
    }



    public static void selectionNeeded() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! Nothing To Delete");
        alert.setContentText("Please make a selection first.");
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

    public static void validateUserError (int number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!! ERROR ALERT !!");
        switch (number){
            case 1:
            {
                alert.setContentText("User Name field is empty.  Please add a user name.");
                break;
            }
            case 2:
            {
                alert.setContentText("Password field is empty.  Please add a password.");
                break;
            }
            default:
            {
                alert.setContentText("ERROR UNKNOWN");
            }
        }
        alert.showAndWait();
    }

    public static void validateContactError (int number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!! ERROR ALERT !!");
        switch (number){
            case 1:
            {
                alert.setContentText("Contact Name field is empty.  Please add a contact name.");
                break;
            }
            case 2:
            {
                alert.setContentText("Email field is empty.  Please add an email address.");
                break;
            }
            case 3:
            {
                alert.setContentText("Please enter a valid email address");
                break;
            }
            default:
            {
                alert.setContentText("ERROR UNKNOWN");
                break;
            }
        }
        alert.showAndWait();
    }


}
