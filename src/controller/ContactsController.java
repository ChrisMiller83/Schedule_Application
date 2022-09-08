package controller;


import dao.ContactDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Contact;
import utilities.ChangeView;
import utilities.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    static ObservableList<Contact> contactList;

    @FXML private Button addContactBtn;
    @FXML private Button updateContactBtn;
    @FXML private Button deleteContactBtn;
    @FXML private Button mainMenuBtn;

    @FXML private TableView<Contact> contactsTable;
    @FXML private TableColumn<Contact, Integer> contactIdCol;
    @FXML private TableColumn<Contact, String> contactNameCol;
    @FXML private TableColumn<Contact, String> emailCol;

    public void setContactsTable() {
        contactList = ContactDAO.loadAllContacts();
        contactsTable.setItems(contactList);
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }


    @FXML
    public void deleteContact(ActionEvent actionEvent) {
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        if(selectedContact == null) {
            Messages.selectionNeeded();
            return;
        } else {

            // TODO: check if contact has any appointments

            int contactId = selectedContact.getContactId();
            boolean deleteConfirm = Messages.deleteConfirmation(selectedContact.getContactName());
            if(deleteConfirm) {
                System.out.println(selectedContact.getContactName() + " deleted");
                ContactDAO.deleteContact(contactId);
                contactsTable.setItems(ContactDAO.loadAllContacts());
                contactsTable.refresh();
            } else {
                return;
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
    void toMainMenu(ActionEvent actionEvent) throws IOException {
        new ChangeView(actionEvent, "MainPageView.fxml");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContactsTable();
    }

}