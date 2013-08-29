package com.gumtree.repository;

import com.gumtree.domain.Contact;

import java.util.List;

public interface AddressBookRepository {
    Contact add(Contact contact);

    List<Contact> getAllContacts();

    Contact get(String name);
}
