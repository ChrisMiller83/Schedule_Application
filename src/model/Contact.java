package model;

/**
 * @author Christopher Miller - Schedule Application - WGU C195 PA
 */

import java.util.ArrayList;
import java.util.List;

/** Contact model class. */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    /** Empty default constructor */
    public Contact() {};

    /**
     * Contact constructor.
     * @param contactId int contactId
     * @param contactName String contactName
     * @param email String email
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /** contactId setter */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** contactId getter */
    public int getContactId() {
        return contactId;
    }

    /** contactName setter */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** contactName getter */
    public String getContactName() {
        return contactName;
    }

    /** email setter */
    public void setEmail(String email) {
        this.email = email;
    }

    /** email getter */
    public String getEmail() {
        return email;
    }


    /**
     * toString
     * @return Overrides the default toString method and returns the contact name instead.
     */
    @Override
    public String toString() {
        return this.getContactName();
    }
}
