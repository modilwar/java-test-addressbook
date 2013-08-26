package com.gumtree.service;

import com.gumtree.domain.Contact;
import com.gumtree.repository.AddressBookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressBookServiceImplTest {

    public static final Contact MALE_CONTACT = new Contact("Male");
    public static final Contact FEMALE_CONTACT = new Contact("Female");
    private AddressBookService service;

    private AddressBookRepository repository;

    @Before
    public void setup() {
        repository = Mockito.mock(AddressBookRepository.class);
        service = new AddressBookServiceImpl(repository);
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
        List<Contact> contacts = new LinkedList<Contact>(){{add(MALE_CONTACT);}};

        when(repository.getAllContacts()).thenReturn(contacts) ;

        assertEquals(1, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

    @Test
    public void getNumberOfMales_isZero_whenAddressBookContainOnlyOneContactAndSheIsFemale() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(FEMALE_CONTACT);}};

        when(repository.getAllContacts()).thenReturn(contacts) ;

        assertEquals(0, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

    @Test
    public void getNumberOfMales_isOne_whenAddressBookContainOneMaleAndOneFemaleContact() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(MALE_CONTACT);add(FEMALE_CONTACT);}};
        when(repository.getAllContacts()).thenReturn(contacts) ;
        assertEquals(1, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

    @Test
    public void getNumberOfMales_isOne_whenAddressBookContainOneFemaleAndOneMaleContact() {
        List<Contact> contacts = new LinkedList<Contact>(){{add(FEMALE_CONTACT);add(MALE_CONTACT);}};
        when(repository.getAllContacts()).thenReturn(contacts) ;
        assertEquals(1, service.getNumberOfMaleContacts());
        verify(repository).getAllContacts();
    }

}