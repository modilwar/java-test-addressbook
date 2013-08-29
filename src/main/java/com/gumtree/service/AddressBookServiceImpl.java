package com.gumtree.service;

import com.gumtree.domain.*;
import com.gumtree.dto.ContactDTO;
import com.gumtree.dto.ContactsDTO;
import com.gumtree.repository.AddressBookRepository;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.gumtree.util.ContactUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class AddressBookServiceImpl implements AddressBookService {
    private final AddressBookRepository repository;
    private ContactAscendingAgeComparator asc;
    private ContactDescendingAgeComparator desc;

    public AddressBookServiceImpl(AddressBookRepository repository) {
        this.repository = repository;
        this.asc = new ContactAscendingAgeComparator();
        this.desc = new ContactDescendingAgeComparator();
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
            Collections.sort(allContacts, desc);
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
    public ContactsDTO getContactsOrderedByAge(Order order, int limit) {
        List<ContactDTO> results = new LinkedList<>();
        List<Contact> allContacts = repository.getAllContacts();

        if (!allContacts.isEmpty()) {

            if (Order.ASC.equals(order)) {
                Collections.sort(allContacts, asc);
            }
            else {
                Collections.sort(allContacts, desc);
            }

            for (int i = 0; i < limit; i++) {
                Contact contact = allContacts.get(i);
                ContactDTO contactDTO = new ContactUtils().createContactDTO(contact);
                results.add(contactDTO);
            }
        }
        return new ContactsDTO(results);
    }
}