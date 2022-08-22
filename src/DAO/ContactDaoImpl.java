package DAO;

import controller.errorMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Contact;
import main.main;

import javax.management.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDaoImpl {
    private String selectAllContacts = "SELECT * FROM contacts";
    Connection conn = main.conn;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @FXML
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();

    public ContactDaoImpl() throws SQLException {
        QueryDatabase.setPreparedStatement(conn, selectAllContacts);
        preparedStatement = QueryDatabase.getPreparedStatement();
        resultSet = preparedStatement.executeQuery();
    }

    public ObservableList<Contact> getAllContacts() {
        int contactId;
        String contactName;
        String email;

        try {
            while (resultSet.next()) {
                contactId = resultSet.getInt("Contact ID");
                contactName = resultSet.getString("Contact Name");
                email = resultSet.getString("Email");
                Contact contact = new Contact(contactId, contactName, email);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            errorMessage.SQLException(e);
        }
        return contacts;
    }

    public Contact getContact(int contactId) throws SQLException {
        String selectContact = "SELECT * FROM contacts WHERE Contact ID=" + String.valueOf(contactId);
        QueryDatabase.setPreparedStatement(conn, selectContact);
        PreparedStatement preparedStatement = QueryDatabase.getPreparedStatement();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int contactId = resultSet.getInt("Contact ID");
            String contactName = resultSet.getString("Contact Name");
            String email = resultSet.getString("Email");
            Contact foundContact = new Contact(contactId, contactName, email);
            return foundContact;
        }
        return null;
    }
}
