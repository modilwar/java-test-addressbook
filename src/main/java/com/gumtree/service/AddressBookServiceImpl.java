package com.gumtree.service;

import com.gumtree.domain.Contact;
import com.gumtree.repository.AddressBookRepository;

import java.util.List;

public class AddressBookServiceImpl implements AddressBookService {
    private final AddressBookRepository repository;

    public AddressBookServiceImpl(AddressBookRepository repository) {
        this.repository = repository;
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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}