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
import model.MessageLambdaInterface;
import utilities.ChangeView;
import utilities.Messages;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddContactController -- adds contacts to the db.
 */
public class AddContactController implements Initializable {
    private String contactName;
    private String email;
    @FXML private Button cancelBtn;
    @FXML private TextField contactIdTF;
    @FXML private TextField contactNameTF;
    @FXML private TextField emailTF;
    @FXML private Button saveBtn;

    /**
     * saveContact -- calls validateAppt method to run validation checks,
     * if validation checks pass, all info from the fields are gathered and contact is
     * saved in the db otherwise error messages are given
     * -- A Lambda expression is used for a console message verifying contact add.
     * @param actionEvent -- saveBtn was clicked.
     */
    public void saveContact(ActionEvent actionEvent) throws IOException {
        /** validateAppt method called to check for errors */
        if (validateContact()) {
            /** if no errors gather data from fields */
            contactName = contactNameTF.getText();
            email = emailTF.getText();
            /** Confirmation message to add contact */
            boolean addConfirm = Messages.addConfirmation(contactName);
            /** If confirmation was ok/yes contact is added to the db. */
            if (addConfirm) {
                ContactDAO.addContact(contactName, email);
                /** Lambda expression -- console message verifying add */
                MessageLambdaInterface message = s -> System.out.println(s + " added.");
                message.displayMessage(contactName);
            } else {
                /** If confirmation was no/cancel, returns to addContactView with current add data in the fields */
                return;
            }
            /** ChangeView brings the user back to the ContactsView page when a contact is added to the db. */
            new ChangeView(actionEvent, "ContactsView.fxml");
        } else {
            /** validateAppt found an error, error message displayed, and user is returned to addContact page. */
            return;
        }
    }

    /**
     * cancelToContactsView -- changes view to ContactsView
     * @param actionEvent -- cancel button clicked
     * @throws IOException
     */
    public void cancelToContactsView(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "ContactsView.fxml");
    }


    /**
     * validateContact:  Is called in saveContact, it checks for empty fields
     * @return false if one of the checks are found, otherwise returns true and allows saveContact to continue
     */
    private boolean validateContact() {
        /** Checker:  contact name text field is empty */
        if (contactNameTF.getText().isEmpty()) {
            Messages.validateContactError(1);
            return false;
        }
        /** Checker:  email text field is empty */
        if (emailTF.getText().isEmpty()) {
            Messages.validateContactError(2);
            return false;
        }
        return true;
    }


    /**
     * initialize -- loads contacts when page is opened.
     * @param url -- not used
     * @param resourceBundle -- not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactDAO.loadAllContacts();

    }
}
