package utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * ChangeView -- reduces redundant code. Every time a view needs to be changed, one line of code replaces five lines of code for each view change.
 * EX: new ChangeView(actionEvent, "UsersView.fxml");
 * Replaces:
 *      Parent root = FXMLLoader.load(ChangeView.class.getResource("/view/UsersView.fxml"));
 *      Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
 *      Scene scene = new Scene(root);
 *      stage.setScene(scene);
 *      stage.show();
 */
public class ChangeView {
    private static final String rootName = "/view/";

    public ChangeView(javafx.event.ActionEvent actionEvent, String viewName) throws IOException{
        String fullViewName = rootName + viewName;
        Parent root = FXMLLoader.load(ChangeView.class.getResource(fullViewName));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
