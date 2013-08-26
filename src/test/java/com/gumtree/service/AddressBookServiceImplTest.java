package com.gumtree.service;

import com.gumtree.domain.Contact;
import com.gumtree.repository.AddressBookRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
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
    private static final String JANE = "Jane";

    private static final String MALE = "Male";
    private static final String FEMALE = "Female";

    private Contact johnMale150682;
    private Contact janeFemale081285;

    private AddressBookService service;

    private AddressBookRepository repository;

    @Before
    public void setup() throws Exception {
        repository = Mockito.mock(AddressBookRepository.class);
        service = new AddressBookServiceImpl(repository);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        johnMale150682 = new Contact(JOHN, MALE, sdf.parse("15/06/82"));
        janeFemale081285 = new Contact(JANE, FEMALE, sdf.parse("08/12/85"));
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
}