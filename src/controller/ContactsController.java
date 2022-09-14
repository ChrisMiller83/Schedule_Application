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
     * @return -- Returns false if contact has upcoming appointments, returns true is contact does not have an appts.
     */
    private boolean noAppointments() {
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        int contactId = selectedContact.getContactId();
        ObservableList<Appointment> appointments = AppointmentDAO.loadAllAppts();

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
            if (noAppointments()) {
                /** if contact did not have any upcoming appointment, a delete confirmation is displayed */
                boolean deleteConfirm = Messages.deleteConfirmation(selectedContact.getContactName());
                /** if delete is confirmed, delete contact, display console message confirming delete */
                if (deleteConfirm) {
                    int contactId = selectedContact.getContactId();
                    System.out.println(selectedContact.getContactName() + " deleted");
                    ContactDAO.deleteContact(contactId);
                    contactsTable.setItems(ContactDAO.loadAllContacts());
                    contactsTable.refresh();
                }
            }
        }
    }

    @FXML
    void toAddContacts(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "AddContactView.fxml");
    }

    @FXML
    public void toUpdateContacts(ActionEvent actionEvent) throws IOException{
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Messages.selectAnItemToUpdate("Contact");
            return;
        } else {
            UpdateContactController.getSelectedContact(selectedContact);
            new ChangeView(actionEvent, "UpdateContactView.fxml");
        }
    }

    @FXML
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "MainPageView.fxml");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContactsTable();
    }

}