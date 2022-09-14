package main;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dao.DBConnection;
import java.io.IOException;
import java.sql.Connection;

/**
 * Main -- Main Class launches the Application, establishes/closes connection to db and opens first window LoginView.
 */
public class Main extends Application {

    public static Connection conn;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        DBConnection.makeConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
