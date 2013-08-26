package com.gumtree.service;

import com.gumtree.domain.Contact;

public interface AddressBookService {
    int getNumberOfMaleContacts();

    Contact getOldestPerson();

    Contact findContactByName(String name);

    long ageDifferenceBetween(Contact contact1, Contact contact2);
}
