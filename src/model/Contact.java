package model;

/**
 * Contact model class.
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

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

    /**
     * setContactId
     * @param contactId set the contact Id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * getContactId
     * @return returns the contact Id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * setContactName
     * @param contactName sets the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * getContactName
     * @return returns the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * setEmail
     * @param email sets the contact email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getEmail
     * @return returns the contact's email
     */
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
