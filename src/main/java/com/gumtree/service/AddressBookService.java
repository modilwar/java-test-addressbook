package com.gumtree.service;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.domain.Order;

import java.util.List;

public interface AddressBookService {
    int getNumberOfMaleContacts();

    Contact getOldestPerson();

    Contact findContactByName(String name);

    long ageDifferenceBetween(Contact contact1, Contact contact2);

    List<Contact> getContactsByGender(Gender gen);

    List<Contact> getContacts(String orderBy, Order order, int limit);
}
