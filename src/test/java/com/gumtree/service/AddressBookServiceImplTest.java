package com.gumtree.service;

import com.gumtree.domain.Contact;
import com.gumtree.repository.AddressBookRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressBookServiceImplTest {

    private static final String JOHN = "John";
    private static final String JACK = "Jack";
    private static final String JANE = "Jane";
    public static final String JOE = "Joe";

    private static final String MALE = "Male";
    private static final String FEMALE = "Female";

    private Contact johnMale150682;
    private Contact jackMale160682;
    private Contact janeFemale081285;
    private Contact joeFemale081285;

    private AddressBookService service;

    private AddressBookRepository repository;

    @Before
    public void setup() throws Exception {
        repository = Mockito.mock(AddressBookRepository.class);
        service = new AddressBookServiceImpl(repository);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        johnMale150682 = new Contact(JOHN, MALE, sdf.parse("15/06/82"));
        jackMale160682 = new Contact(JACK, MALE, sdf.parse("16/06/82"));
        janeFemale081285 = new Contact(JANE, FEMALE, sdf.parse("08/12/85"));
        joeFemale081285 = new Contact(JOE, FEMALE, sdf.parse("08/12/85"));
    }

    @Test
    public void getNumberOfMales_isZero_whenAddressBookIsEmpty() {
        List<Contact> contacts = Collections.emptyList();
        when(repository.getAllContacts()).thenReturn(contacts) ;

        assertEquals(0, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

    @Test
    public void getNumberOfMales_isOne_whenAddressBookContainOnlyOneContactAndHeIsMale() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(johnMale150682);}};

        when(repository.getAllContacts()).thenReturn(contacts) ;

        assertEquals(1, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

    @Test
    public void getNumberOfMales_isZero_whenAddressBookContainOnlyOneContactAndSheIsFemale() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(janeFemale081285);}};

        when(repository.getAllContacts()).thenReturn(contacts) ;

        assertEquals(0, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

    @Test
    public void getNumberOfMales_isOne_whenAddressBookContainOneMaleAndOneFemaleContact() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(johnMale150682);add(janeFemale081285);}};

        when(repository.getAllContacts()).thenReturn(contacts) ;

        assertEquals(1, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

    @Test
    public void getNumberOfMales_isOne_whenAddressBookContainOneFemaleAndOneMaleContact() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(janeFemale081285);add(johnMale150682);}};

        when(repository.getAllContacts()).thenReturn(contacts) ;

        assertEquals(1, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

    @Test
    public void getOldestPerson_returnsNullFromEmptyAddressBook() {
        List<Contact> contacts = new LinkedList<Contact>(){};

        when(repository.getAllContacts()).thenReturn(contacts) ;

        assertNull(service.getOldestPerson());
        verify(repository).getAllContacts();
    }

    @Test
    public void getOldestPerson_returnsOldestPersonFromAddressBookContainingOneContact() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(janeFemale081285);}};
        when(repository.getAllContacts()).thenReturn(contacts) ;

        Contact oldestPerson = service.getOldestPerson();

        assertEquals(janeFemale081285, oldestPerson);
        verify(repository).getAllContacts();
    }

    @Test
    public void getOldestPerson_returnsOldestPersonFromAddressBookContainingManyContacts() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(janeFemale081285);add(johnMale150682);}};
        when(repository.getAllContacts()).thenReturn(contacts) ;

        Contact oldestPerson = service.getOldestPerson();

        assertEquals(johnMale150682, oldestPerson);
        verify(repository).getAllContacts();
    }

    @Test
    public void findContactByName_returnsNullWhenContactWithGivenNameNotFoundInRepository() {
        when(repository.get(JOHN)).thenReturn(null) ;

        assertNull(service.findContactByName(JOHN));
        verify(repository).get(JOHN);
    }

    @Test
    public void findContactByName_returnsContactWhenContactWithGivenNameIsFoundInRepository() {
        when(repository.get(JANE)).thenReturn(janeFemale081285) ;

        Contact contact = service.findContactByName(JANE);

        assertEquals(janeFemale081285, contact);
        verify(repository).get(JANE);
    }

    @Test
    public void ageDifferenceBetween_returnsZero_whenBothContactsHaveSameDOB() {
        Long days = service.ageDifferenceBetween(janeFemale081285, joeFemale081285);

        assertEquals(new Long(0), days);
    }

    @Test
    public void ageDifferenceBetween_returnsPositiveNumber_whenContact1OlderThanContact2() {
        Long days = service.ageDifferenceBetween(johnMale150682, jackMale160682);
        assertEquals(new Long(1), days);
    }

    @Test
    public void ageDifferenceBetween_returnsNegativeNumber_whenContact1YoungerThanContact2() {
        Long days = service.ageDifferenceBetween(jackMale160682, johnMale150682);
        assertEquals(new Long(-1), days);
    }
}