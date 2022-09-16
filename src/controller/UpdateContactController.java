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
import model.Contact;
import model.MessageLambdaInterface;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * UpdateContactController -- updates existing contacts in the db.
 */
public class UpdateContactController implements Initializable {
    private static Contact selectedContact;

    private int contactId;
    private String contactName;
    private String email;

    @FXML private TextField contactIdTF;
    @FXML private TextField contactNameTF;
    @FXML private TextField emailTF;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;



    /**
     * updateContact -- calls validateAppt method to run validation checks,
     * if validation checks pass, all info from the fields are gathered and contact is
     * updated in the db otherwise error messages are given.
     * @param actionEvent -- saveBtn was clicked.
     */
    public void updateContact(ActionEvent actionEvent) throws IOException {
        /** validateContact method called to check for errors */
        if(validateContact()) {
            /** if no errors gather data from fields */
            contactName = contactNameTF.getText();
            email = emailTF.getText();
            contactId = Integer.parseInt(contactIdTF.getText());
            /** Confirmation message to update contact */
            boolean updateConfirm = Messages.updateConfirmation(contactName);
            /** If confirmation was ok/yes contact is updated in the db. */
            if (updateConfirm) {
                ContactDAO.updateContact(contactName, email, contactId);

                /** Lambda expression -- console message verifying update */
                MessageLambdaInterface message = s -> System.out.println(s + " updated.");
                message.displayMessage(contactName);

            } else {
                /** If confirmation was no/cancel, returns to updateContactView with current add data in the fields */
                return;
            }
            /** ChangeView brings the user back to the ContactsView page when a contact is updated in the db. */
            new ChangeView(actionEvent, "ContactsView.fxml");
        } else {
            /** validateAppt found an error, error message displayed, and user is returned to updateContact page. */
            return;
        }

    }

    /**
     * setSelectedContact -- gets the contact info from the selectedContact from the ContactsController
     * and populates the fields with the data.
     * @param selectedContact -- contact selected to update in the ContactsController.
     */
    public void setSelectedContact(Contact selectedContact) {
        contactIdTF.setText(Integer.toString(selectedContact.getContactId()));
        contactNameTF.setText(selectedContact.getContactName());
        emailTF.setText(selectedContact.getEmail());
    }

    /**
     * getSelectedContact -- gets the selectedContact from the ContactsController
     * @param contact -- contact selected to update info.
     */
    public static void getSelectedContact(Contact contact) {
        selectedContact = contact;
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
     * validateContact:  Is called in updateContact, it checks for empty fields
     * @return -- false if one of the checks are found, otherwise returns true and allows updateContact to continue
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
     * initialize -- loads and sets the selectedContact's info
     * @param url -- not used
     * @param resourceBundle -- not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSelectedContact(selectedContact);
    }
}
