package controller;

import javafx.fxml.Initializable;
import model.Contact;

import java.net.URL;
import java.util.ResourceBundle;

public class updateContactController implements Initializable {
    private static Contact selectedContact;



    public void setSelectedContact(Contact selectedContact) {

    }
    public static void getSelectedContact(Contact contact) {
        selectedContact = contact;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       setSelectedContact(selectedContact);
    }
}
