package com.gumtree.repository;

import com.gumtree.domain.Contact;
import com.gumtree.exception.ContactNotFoundException;

import java.util.LinkedList;
import java.util.List;

public class AddressBookLinkedListRepository implements AddressBookRepository {

    private List<Contact> contacts;

    public AddressBookLinkedListRepository() {
        contacts = new LinkedList<Contact>();
    }

    @Override
    public List<Contact> getAllContacts() {
        return contacts;
    }

    @Override
    public Contact add(Contact contact) {
        contacts.add(contact);
        return contact;
    }

    @Override
    public Contact get(String name) {
        for (Contact contact : contacts) {
            if (name.endsWith(contact.getName())) return contact;
        }
        throw new ContactNotFoundException();
    }
}