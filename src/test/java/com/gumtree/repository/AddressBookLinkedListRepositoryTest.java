package com.gumtree.repository;

import com.gumtree.domain.Contact;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class AddressBookLinkedListRepositoryTest {

    private static final Contact CONTACT_1 = new Contact("Male");
    private static final Contact CONTACT_2 = new Contact("Female");

    private AddressBookRepository repository;

    @Before
    public void setup() {
        repository = new AddressBookLinkedListRepository();
    }

    @Test
    public void getAllContactsTest_returnEmptyList_whenAddressBookContainsNoContacts() {
        List<Contact> allContacts = repository.getAllContacts();

        assertNotNull(allContacts);
        assertEquals(0, allContacts.size());
    }

    @Test
    public void getAllContactsTest_returnOneContact_whenAddressBookContainsOneContact() {
        repository.add(CONTACT_1);
        List<Contact> allContacts = repository.getAllContacts();

        assertNotNull(allContacts);
        assertEquals(1, allContacts.size());
    }

    @Test
    public void getAllContactsTest_returnAllContacts_whenAddressBookContainsManyContacts() {
        repository.add(CONTACT_1);
        repository.add(CONTACT_1);
        List<Contact> allContacts = repository.getAllContacts();

        assertNotNull(allContacts);
        assertEquals(2, allContacts.size());
    }
}