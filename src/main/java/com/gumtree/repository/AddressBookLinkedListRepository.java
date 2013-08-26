package com.gumtree.repository;

import com.gumtree.domain.Contact;

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
    public void add(Contact contact) {
        contacts.add(contact);
    }

    @Override
    public Contact get(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}