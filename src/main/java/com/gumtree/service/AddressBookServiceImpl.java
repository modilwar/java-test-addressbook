package com.gumtree.service;

import com.gumtree.domain.Contact;
import com.gumtree.domain.ContactDescendingAgeComparator;
import com.gumtree.domain.Gender;
import com.gumtree.repository.AddressBookRepository;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class AddressBookServiceImpl implements AddressBookService {
    private final AddressBookRepository repository;
    private ContactDescendingAgeComparator conparator;

    public AddressBookServiceImpl(AddressBookRepository repository) {
        this.repository = repository;
        this.conparator = new ContactDescendingAgeComparator();
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
    public List<Contact> getContactsByGender(Gender gen) {
        List<Contact> contactsOfGender = new LinkedList<>();
        List<Contact> allContacts = repository.getAllContacts();
        for (Contact contact : allContacts) {
            if (contact.getGender() == gen) contactsOfGender.add(contact);
        }
        return contactsOfGender;
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
        return repository.get(name);
    }

    @Override
    public long ageDifferenceBetween(Contact contact1, Contact contact2) {
        Date dob1 = contact1.getDob();
        Date dob2 = contact2.getDob();
        long days = Days.daysBetween(new DateTime(dob1), new DateTime(dob2)).getDays();
        if (dob1.after(dob2)) {
            return days * 1;
        }
        return days;
    }
}