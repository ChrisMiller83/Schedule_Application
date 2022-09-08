package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.ContactDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddContactController implements Initializable {
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
            new ChangeView(actionEvent, "ContactsView.fxml");
        } else {
            return;
        }

    }

    @FXML
    void toContactsView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "ContactsView.fxml");
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
