package controller;

import DAO.ContactDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import model.Contact;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class updateContactController implements Initializable {
    private static Contact selectedContact;

    private int contactId;
    private String contactName;
    private String email;

    @FXML private TextField contactIdTF;
    @FXML private TextField contactNameTF;
    @FXML private TextField emailTF;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;

    @FXML
    void cancelToContactsView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "contactsView.fxml");
    }

    @FXML
    void updateContact(ActionEvent actionEvent) throws IOException {
        if(validateContact()) {
            contactName = contactNameTF.getText();
            email = emailTF.getText();
            contactId = Integer.parseInt(contactIdTF.getText());

            boolean updateConfirm = Messages.updateConfirmation(contactName);
            if (updateConfirm) {
                System.out.println(contactName + " updated.");
                ContactDAO.updateContact(contactName, email, contactId);
            } else {
                return;
            }
            new ChangeView(actionEvent, "contactsView.fxml");
        } else {
            return;
        }

    }

    public void setSelectedContact(Contact selectedContact) {
        contactIdTF.setText(Integer.toString(selectedContact.getContactId()));
        contactNameTF.setText(selectedContact.getContactName());
        emailTF.setText(selectedContact.getEmail());
    }


    public static void getSelectedContact(Contact contact) {
        selectedContact = contact;
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
       setSelectedContact(selectedContact);
    }
}
