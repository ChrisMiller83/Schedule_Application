package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ContactDAO -- used to connect to the database and allow sql queries, create, update, and deletes.
 */
public class ContactDAO {

    /**
     * Instantiates a new contact dao
     */
    public ContactDAO() {}

    /**
     * CONSTANTS used to prevent SQL injection into the contacts table
     */
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_CONTACT_ID = "Contact_ID";
    public static final String COLUMN_CONTACT_NAME = "Contact_Name";
    public static final String COLUMN_CONTACT_EMAIL = "Email";

    /**
     * QUERY CONSTANTS used to prevent SQL injection into the contacts table
     */
    public static final String QUERY_ALL_CONTACTS = "SELECT * FROM " + TABLE_CONTACTS +
            " ORDER BY " + COLUMN_CONTACT_ID;

    public static final String CREATE_CONTACT = "INSERT INTO " + TABLE_CONTACTS + " ( " +
            COLUMN_CONTACT_NAME + ", " + COLUMN_CONTACT_EMAIL + " ) VALUES (?, ?)";

    public static final String UPDATE_CONTACT = "UPDATE " + TABLE_CONTACTS + " SET " +
            COLUMN_CONTACT_NAME + " = ?, " + COLUMN_CONTACT_EMAIL + " = ? WHERE " +
            COLUMN_CONTACT_ID + " = ?";

    public static final String DELETE_CONTACT = "DELETE FROM " + TABLE_CONTACTS +
            " WHERE " + COLUMN_CONTACT_ID + " = ?";

    /**
     * loadAllContacts -- queries the db and gets all contacts from the contacts table
     * @return -- returns an ObservableList of all contacts and their data from the contacts table
     */
    public static ObservableList<Contact> loadAllContacts() {
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();
        try {
            PreparedStatement loadContacts = DBConnection.getConnection().prepareStatement(QUERY_ALL_CONTACTS);
            ResultSet result = loadContacts.executeQuery();

            while (result.next()) {
                int contactId = result.getInt(COLUMN_CONTACT_ID);
                String contactName = result.getString(COLUMN_CONTACT_NAME);
                String email = result.getString(COLUMN_CONTACT_EMAIL);
                Contact contact = new Contact(contactId, contactName, email);
                contactsList.add(contact);
            }
            return contactsList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * addContact -- Creates a new contact/entry in the db contact table
     * @param contactName -- contact name
     * @param email -- contact email
     */
    public static void addContact(String contactName, String email) {
        try {
            PreparedStatement addContacts = DBConnection.getConnection().prepareStatement(CREATE_CONTACT);
            addContacts.setString(1, contactName);
            addContacts.setString(2, email);
            addContacts.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updateContact -- Updates an existing contact/entry in the db contact table that matches the contactId given
     * @param contactName -- contact name
     * @param email -- email
     * @param contactId -- contact id
     */
    public static void updateContact(String contactName, String email, int contactId) {
        try {
            PreparedStatement updateContacts = DBConnection.getConnection().prepareStatement(UPDATE_CONTACT);
            updateContacts.setString(1,contactName);
            updateContacts.setString(2, email);
            updateContacts.setInt(3, contactId);
            updateContacts.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deleteContact -- Deletes a contact from the db contacts tabel that matches the contactId given
     * @param contactId -- contact id uses to delete a specific contact
     */
    public static void deleteContact (int contactId) {
        try {
            PreparedStatement deleteContacts = DBConnection.getConnection().prepareStatement(DELETE_CONTACT);
            deleteContacts.setInt(1, contactId);
            deleteContacts.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
