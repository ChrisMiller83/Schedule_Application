package DAO;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Contact;

import java.sql.SQLException;

public interface ContactDao {

    @FXML
    public ObservableList<Contact> getAllContacts();

    @FXML
    public Contact getContact(int contactId) throws SQLException {

    }

}
