package com.gumtree.service;

import com.gumtree.domain.Contact;

public interface AddressBookService {
    int getNumberOfMaleContacts();

    Contact getOldestPerson();
}
