package com.gumtree.service;

import com.gumtree.domain.Contact;
import com.gumtree.domain.ContactDescendingAgeConparator;
import com.gumtree.repository.AddressBookRepository;

import java.util.Collections;
import java.util.List;

public class AddressBookServiceImpl implements AddressBookService {
    private final AddressBookRepository repository;
    private ContactDescendingAgeConparator conparator;

    public AddressBookServiceImpl(AddressBookRepository repository) {
        this.repository = repository;
        this.conparator = new ContactDescendingAgeConparator();
    }

    @Override
    public int getNumberOfMaleContacts() {
        int noOfMales = 0;
        List<Contact> allContacts = repository.getAllContacts();
        for (Contact contact : allContacts) {
            if (contact.isMale()) noOfMales++;
        }
        return noOfMales;
    }

    @Override
    public Contact getOldestPerson() {
        List<Contact> allContacts = repository.getAllContacts();

        if (!allContacts.isEmpty()) {
            Collections.sort(allContacts, conparator);
            return allContacts.get(0);
        }
        return null;
    }

    @Override
    public Contact findContactByName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}