package com.gumtree.service;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;

import java.util.List;

public interface AddressBookService {
    int getNumberOfMaleContacts();

    Contact getOldestPerson();

    Contact findContactByName(String name);

    long ageDifferenceBetween(Contact contact1, Contact contact2);

    List<Contact> getContactsByGender(Gender gen);
}
