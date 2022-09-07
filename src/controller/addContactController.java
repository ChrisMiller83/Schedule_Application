package controller;

import DAO.ContactDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.paint.Stop;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class addContactController implements Initializable {
    private String contactName;
    private String email;
    @FXML private Button cancelBtn;
    @FXML private TextField contactIdTF;
    @FXML private TextField contactNameTF;
    @FXML private TextField emailTF;
    @FXML private Button saveBtn;

    @FXML
    void saveContact(ActionEvent actionEvent) throws IOException {
        if (validateContact()) {
            contactName = contactNameTF.getText();
            email = emailTF.getText();

            boolean addConfirm = Messages.addConfirmation(contactName);

            if (addConfirm) {
                ContactDAO.addContact(contactName, email);
                System.out.println(contactName + " added.");
            } else {
                return;
            }
            new ChangeView(actionEvent, "contactsView.fxml");
        } else {
            return;
        }

    }

    @FXML
    void toContactsView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "contactsView.fxml");
    }


    private boolean validateContact() {
        if (contactNameTF.getText().isEmpty()) {
            Messages.validateContactError(1);
            return false;
        }
        if (emailTF.getText().isEmpty()) {
            Messages.validateContactError(2);
            return false;
        }
        return true;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactDAO.loadAllContacts();

    }
}
