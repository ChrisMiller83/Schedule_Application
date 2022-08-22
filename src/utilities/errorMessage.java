package utilities;

import javafx.scene.control.Alert;

import java.sql.SQLException;

public class errorMessage {

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
