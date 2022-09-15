package utilities;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Messages class -- custom messages built here.
 */
public class Messages {

    /**
     * validateCustomerError -- displays error message if an error is found during validation of customer data.
     * @param number switch case number for specific message display.
     */
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

    /**
     * validateAppt -- displays error message if an error is found during validation of appointment data.
     * @param number switch case number for specific message display.
     */
    public static void validateAppt(int number) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
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
            case 13:
            {
                alert.setContentText("The user choice box is empty:  Please select a user.");
                break;
            }
            case 14:
            {
                alert.setContentText("Appointment date issue.  Appointments cannot be made on past dates." +
                        "\nCurrent Date:  " + LocalDate.now());
                break;
            }
            case 15:
            {
                alert.setContentText("Appointment time issue.  Start time is before current time." +
                        "\nCurrent Time:  " + LocalTime.now());
                break;
            }
            case 16:
            {
                alert.setContentText("Appointment time issue.  End time is before current time." +
                        "\nCurrent Time:  " + LocalTime.now());
                break;
            }
            case 17:
            {
                alert.setContentText("Description length is too long.  Max description length is 50 characters.");
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

    /**
     * selectAnItemToUpdate -- error message used if an appointment, customer, contact, or user to update was not selected.
     * @param s string parameter used to display if message is for Appointment, Customer, Contact, or User needing update selection.
     */
    public static void selectAnItemToUpdate(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! Missing " + s + " To Update");
        alert.setContentText("Please select a " + s.toLowerCase() + " to update first.");
        alert.showAndWait();
    }

    /**
     * selectionNeeded -- used to display message when an item to delete has not been selected.
     */
    public static void selectionNeeded() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! No Item Selected");
        alert.setContentText("Please make a selection first.");
        alert.showAndWait();
    }

    /**
     * deleteConfirmation -- displays a delete Confirmation message.
     * @param itemToDelete -- displays the item being deleted.
     * @return -- verifies OK or cancel was selected.
     */
    public static boolean deleteConfirmation(String itemToDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!! DELETE CONFORMATION !!");
        alert.setContentText("Delete: " + itemToDelete);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * addConfirmation -- displays an add Confirmation message.
     * @param itemToAdd -- displays title/name of item being added.
     * @return -- verifies OK or cancel was selected.
     */
    public static boolean addConfirmation(String itemToAdd) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!! ADD CONFORMATION !!");
        alert.setContentText("Add: " + itemToAdd);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * updateConfirmation -- displays an update Confirmation message.
     * @param itemToUpdate -- displays title/name of item being updated.
     * @return -- verifies OK or cancel was selected.
     */
    public static boolean updateConfirmation(String itemToUpdate) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!! UPDATE CONFORMATION !!");
        alert.setContentText("Update: " + itemToUpdate);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * validateUserError -- displays error message if an error is found during validation of user data
     * @param number switch case number for specific message display.
     */
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

    /**
     * validateContactAppt -- displays error message if an error is found during validation of contact data.
     * @param number switch case number for specific message display.
     */
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

    /**
     * overlappingAppts -- displays error message if an overlapping customer appointment was found during appointment validation
     * @param apptId -- apptId of booked/saved appt in overlapping conflict.
     * @param start -- start date time of booked/save appt in overlapping conflict.
     * @param end -- end date time of booked/save appt in overlapping conflict.
     */
    public static void overlappingAppts(int apptId, LocalDateTime start, LocalDateTime end) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("!! Overlapping Appointments");
        alert.setContentText("The Start/End times are in conflict with another appointment" +
                "\nAppt ID:  " + apptId +
                "\nStart:  " + start.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm")) +
                "\nEnd:  " + end.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm")) +
                "\n\nPlease adjust your appointment times.");
        alert.showAndWait();
    }

    /**
     * hasAppointments -- warning message displayed if user, customer, or contact have upcoming appointments
     * @param name -- user name, customer name or contact name to be deleted.
     */
    public static void hasAppointments(String name) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(name + " has upcoming appointments");
        alert.setContentText(name + " has upcoming appointments." +
            "\nPlease delete all appointments before deleting " + name);
        alert.showAndWait();
    }
}
