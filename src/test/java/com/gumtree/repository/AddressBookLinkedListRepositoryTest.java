package com.gumtree.repository;

import com.gumtree.domain.Contact;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class AddressBookLinkedListRepositoryTest {

    private static final String JOHN = "John";
    private static final String JANE = "Jane";

    private static final String MALE = "Male";
    private static final String FEMALE = "Female";

    private Contact johnMale150682;
    private Contact janeFemale081285;

    private AddressBookRepository repository;

    @Before
    public void setup() throws Exception {
        repository = new AddressBookLinkedListRepository();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        johnMale150682 = new Contact(JOHN, MALE, sdf.parse("15/06/82"));
        janeFemale081285 = new Contact(JANE, FEMALE, sdf.parse("08/12/85"));
    }

    @Test
    public void getAllContactsTest_returnEmptyList_whenAddressBookContainsNoContacts() {
        List<Contact> allContacts = repository.getAllContacts();

        assertNotNull(allContacts);
        assertEquals(0, allContacts.size());
    }

    @Test
    public void getAllContactsTest_returnOneContact_whenAddressBookContainsOneContact() {
        repository.add(johnMale150682);
        List<Contact> allContacts = repository.getAllContacts();

        assertNotNull(allContacts);
        assertEquals(1, allContacts.size());
    }

    @Test
    public void getAllContactsTest_returnAllContacts_whenAddressBookContainsManyContacts() {
        repository.add(johnMale150682);
        repository.add(johnMale150682);
        List<Contact> allContacts = repository.getAllContacts();

        assertNotNull(allContacts);
        assertEquals(2, allContacts.size());
    }

    @Test
    public void get_returnNull_whenAddressBookContainsNoContacts() {
        assertNull(repository.get(JANE));
    }

    @Test
    public void get_returnNull_whenAddressContactWithGivenNameNotFoundInAddress() {
        repository.add(johnMale150682);
        repository.add(janeFemale081285);
        assertNull(repository.get("BOB"));
    }

    @Test
    public void get_returnContact_whenAddressContactWithGivenNameIsFoundInAddress() {
        repository.add(janeFemale081285);
        repository.add(johnMale150682);
        Contact jane = repository.get(JANE);
        assertNotNull(jane);
    }
}