package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class contactsController implements Initializable {



    @FXML private Button addContactBtn;
    @FXML private Button updateContactBtn;
    @FXML private Button deleteContactBtn;
    @FXML private Button mainMenuBtn;

    @FXML private TableView<?> contactsTable;
    @FXML private TableColumn<?, ?> contactIdCol;
    @FXML private TableColumn<?, ?> contactNameCol;
    @FXML private TableColumn<?, ?> emailCol;


    @FXML
    void deleteContact(ActionEvent event) {

    }

    @FXML
    void toAddContacts(ActionEvent event) {

    }

    @FXML
    void toMainMenu(ActionEvent event) {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}