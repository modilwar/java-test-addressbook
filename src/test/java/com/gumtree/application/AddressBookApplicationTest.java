package com.gumtree.application;

import com.gumtree.domain.Contact;
import com.gumtree.io.FileReader;
import com.gumtree.repository.AddressBookRepository;
import com.gumtree.service.AddressBookService;
import com.gumtree.util.ContactParser;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressBookApplicationTest {

    static String FILE = "AddressBook";
    private static final String ADDRESS_BOOK_LINE = "Mohammed Hussain, Male, 15/06/1982";
    private static final String NAME = "name";
    private static final String GENDER = "Male";

    private AddressBookApplication application;
    private FileReader fileReader;
    private ContactParser contactParser;
    private AddressBookRepository addressBookRepository;
    private AddressBookService addressBookService;

    private Contact contact1;
    private Contact contact2;

    @Before
    public void setup() throws Exception {
        Date today = new DateTime(new Date()).toDate();
        Date tomorrow = new DateTime(new Date()).plusDays(1).toDate();

        contact1 = new Contact(NAME, GENDER, today);
        contact2 = new Contact(NAME, GENDER, tomorrow);

        LinkedList<String> lines = new LinkedList<>();
        lines.add(ADDRESS_BOOK_LINE);
        fileReader = mock(FileReader.class);
        when(fileReader.readLines(FILE)).thenReturn(lines);

        contactParser = mock(ContactParser.class);
        when(contactParser.parse(ADDRESS_BOOK_LINE)).thenReturn(contact1);

        addressBookRepository = mock(AddressBookRepository.class);

        addressBookService = mock(AddressBookService.class);

        application = new AddressBookApplication(fileReader, contactParser, addressBookRepository, addressBookService);
    }

    @Test
    public void initExitsApplicationIfAddressBookFileCanNotBeRead() throws Exception {
        when(fileReader.readLines(FILE)).thenThrow(IOException.class);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        application.init() ;

        assertEquals("ERROR: could not open AddressBook file", output.toString());
    }

    @Test
    public void initReadsAddressBookFile() throws Exception{
        application.init() ;
        verify(fileReader).readLines(FILE);
    }

    @Test
    public void initParsesEachLineForAddressBookFile() {
        application.init();
        verify(contactParser).parse(ADDRESS_BOOK_LINE);
    }

    @Test
    public void initAddsAllContactsFromTheAddressBookFileIntoRepository() {
        application.init();
        verify(addressBookRepository).add(contact1);
    }

    @Test
    public void numberOfMaleContacts() {
        when(addressBookService.getNumberOfMaleContacts()).thenReturn(3);

        application.init();
        int numberOfMaleContacts = application.numberOfMaleContacts();

        verify(addressBookService).getNumberOfMaleContacts();
        assertEquals(3, numberOfMaleContacts);
    }

    @Test
    public void oldestPerson() {
        when(addressBookService.getOldestPerson()).thenReturn(contact1);
        application.init();

        Contact actualOldestContact = application.oldestPerson();

        verify(addressBookService).getOldestPerson();
        assertEquals(contact1, actualOldestContact);
    }

    @Test
    public void contactByName() {
        when(addressBookService.findContactByName(NAME)).thenReturn(contact1);
        application.init();

        Contact contactFoundByName = application.contactByName(NAME);

        verify(addressBookService).findContactByName(NAME);
        assertEquals(contact1, contactFoundByName);
    }

    @Test
    public void ageDifferenceInDays_whenSameDOB() {
        application.init();

        when(addressBookService.ageDifferenceBetween(contact1, contact1)).thenReturn(0L);

        long days = application.getAgeDifferenceInDays(contact1, contact1);

        verify(addressBookService).ageDifferenceBetween(contact1, contact1);
        assertEquals(0L, days);
    }

    @Test
    public void ageDifferenceInDays_whenFirstContactIsOlder() {
        application.init();

        when(addressBookService.ageDifferenceBetween(contact1, contact2)).thenReturn(1L);

        long days = application.getAgeDifferenceInDays(contact1, contact2);

        verify(addressBookService).ageDifferenceBetween(contact1, contact2);
        assertEquals(1L, days);
    }

    @Test
    public void ageDifferenceInDays_whenFirstContactIsYounger() {
        application.init();

        when(addressBookService.ageDifferenceBetween(contact2, contact1)).thenReturn(-1L);

        long days = application.getAgeDifferenceInDays(contact2, contact1);

        verify(addressBookService).ageDifferenceBetween(contact2, contact1);
        assertEquals(-1L, days);
    }



}
