package utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ChangeView {
    private static final String rootName = "/view/";

    public static void changeView (ActionEvent event, String viewName) throws IOException {
        String fullViewName = rootName + viewName;
        Parent root = FXMLLoader.load(ChangeView.class.getResource(fullViewName));
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
