package DAO;

import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public static final int INDEX_CONTACT_ID = 1;
    public static final int INDEX_CONTACT_NAME = 2;
    public static final int INDEX_CONTACT_EMAIL = 3;

    public static final String QUERY_ALL_CONTACTS = "SELECT * FROM " + TABLE_CONTACTS;


    public static void loadAllContacts() {
        try {
            PreparedStatement loadContacts = DBConnection.getConnection().prepareStatement(QUERY_ALL_CONTACTS);
            ResultSet result = loadContacts.executeQuery();

            while (result.next()) {
                int contactId = result.getInt(COLUMN_CONTACT_ID);
                String contactName = result.getString(COLUMN_CONTACT_NAME);
                String email = result.getString(COLUMN_CONTACT_EMAIL);
                Contact contact = new Contact(contactId, contactName, email);
                Contact.contacts.add(contact);
            }

            // add reports for contacts
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
