package com.gumtree.repository;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.exception.ContactNotFoundException;
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

    private static final Gender MALE = Gender.MALE;
    private static final Gender FEMALE = Gender.FEMALE;

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
    public void add_addsNewContactTOAddressBookAndReturnsTheAddedContact() {
        Contact added = repository.add(johnMale150682);
        assertEquals(1, repository.getAllContacts().size());
        assertEquals(johnMale150682, added);
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

    @Test(expected = ContactNotFoundException.class)
    public void get_throwContactNotFoundException_whenAddressBookContainsNoContacts() {
        repository.get(JANE);
    }

    @Test(expected = ContactNotFoundException.class)
    public void get_throwContactNotFoundException_whenAddressContactWithGivenNameNotFoundInAddress() {
        repository.add(johnMale150682);
        repository.add(janeFemale081285);
        repository.get("BOB");
    }

    @Test
    public void get_returnContact_whenAddressContactWithGivenNameIsFoundInAddress() {
        repository.add(janeFemale081285);
        repository.add(johnMale150682);
        Contact jane = repository.get(JANE);
        assertNotNull(jane);
    }
}