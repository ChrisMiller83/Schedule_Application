package controller;

import model.Contact;

public class updateContactController {
    private static Contact selectedContact;



    public static void getSelectedContact(Contact contact) {
        selectedContact = contact;
    }
}
