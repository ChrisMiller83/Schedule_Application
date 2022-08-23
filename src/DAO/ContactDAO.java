package DAO;

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
    public static final String




}
