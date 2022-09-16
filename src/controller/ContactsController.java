package controller;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import dao.AppointmentDAO;
import dao.ContactDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import model.MessageLambdaInterface;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * ContactsController -- Displays a table contacts, allows contacts to be deleted, and redirects to add/update contacts page
 */
public class ContactsController implements Initializable {

    static ObservableList<Contact> contactList = ContactDAO.loadAllContacts();
    private static Contact selectedContact;

    @FXML private Button addContactBtn;
    @FXML private Button updateContactBtn;
    @FXML private Button deleteContactBtn;
    @FXML private Button mainMenuBtn;

    /** contacts table components */
    @FXML private TableView<Contact> contactsTable;
    @FXML private TableColumn<Contact, Integer> contactIdCol;
    @FXML private TableColumn<Contact, String> contactNameCol;
    @FXML private TableColumn<Contact, String> emailCol;

    /**
     * setContactsTable -- populates contact data to appropriate columns in the contacts table
     */
    public void setContactsTable() {
        contactsTable.setItems(contactList);
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    /**
     * noAppointments -- checks if contact to delete has upcoming appointments.
     * @return -- Returns false if contact has upcoming appointments, returns true if contact does not have an appts scheduled.
     */
    private boolean noAppointments() {
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        int contactId = selectedContact.getContactId();
        ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

        /** loops through the appointment db looking for appointments with the contact id, if an appt is found an
         *  error message is displayed telling the user they must delete the appt before they can delete the contact
        */
        for(Appointment appointment : appointments) {
            if(appointment.getContactId() == contactId) {
                Messages.hasAppointments(selectedContact.getContactName());
                return false;
            }
        }
        return true;
    }


    /**
     * deleteContact -- deletes selected contact if contact does not have upcoming appointments.
     * @param actionEvent -- contact to be deleted is selected and Delete Contact button is clicked.
     */
    public void deleteContact(ActionEvent actionEvent) {
        selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        /** Displays error message if a contact to delete is not selected. */
        if(selectedContact == null) {
            Messages.selectionNeeded();
            return;
        } else {
            /** Checks if contact has an appts by calling the noAppointments method */
            if (noAppointments()) {
                /** if contact did not have any upcoming appointment, a delete confirmation is displayed */
                boolean deleteConfirm = Messages.deleteConfirmation(selectedContact.getContactName());
                /** if delete is confirmed, delete contact, display console message confirming delete */
                if (deleteConfirm) {
                    int contactId = selectedContact.getContactId();
                    /** contact is deleted from the db */
                    ContactDAO.deleteContact(contactId);

                    /** Lambda expression -- console message verifying delete */
                    MessageLambdaInterface message = s -> System.out.println(s + " deleted.");
                    message.displayMessage(selectedContact.getContactName());

                    /** the contacts table is reloaded and the deleted contact is removed from the table display */
                    contactsTable.setItems(ContactDAO.loadAllContacts());
                    contactsTable.refresh();
                }
            }
        }
    }

    /**
     * toAddContacts -- changes the view to AddCustomerView
     * @param actionEvent -- Add Contact button clicked
     * @throws IOException
     */
    public void toAddContacts(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "AddContactView.fxml");
    }

    /**
     * toUpdateContacts -- user selects a contact to update and changes the view to UpdateContactView
     * @param actionEvent -- contact to update is selected and then Update Contact button is clicked
     * @throws IOException
     */
    public void toUpdateContacts(ActionEvent actionEvent) throws IOException{
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        /** if no contact was selected an error message is displayed */
        if (selectedContact == null) {
            Messages.selectAnItemToUpdate("Contact");
            return;
        } else {
            /** the selected contact's data is sent to the update contact controller */
            UpdateContactController.getSelectedContact(selectedContact);
            /** changes the view to the UpdateContactView */
            new ChangeView(actionEvent, "UpdateContactView.fxml");
        }
    }

    /**
     * toMainMenu -- changes the view to the Main Page View
     * @param actionEvent -- Main Menu button clicked
     * @throws IOException
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "MainPageView.fxml");
    }

    /**
     * initialize -- calls the setContactsTable method to load the contacts table when page is loaded.
     * @param url -- not used
     * @param resourceBundle -- not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContactsTable();
    }

}