package com.gumtree.repository;

import com.gumtree.domain.Contact;

import java.util.List;

public interface AddressBookRepository {
    void add(Contact contact);

    List<Contact> getAllContacts();
}
