package com.gumtree.application;

import com.gumtree.domain.Contact;
import com.gumtree.io.FileReader;
import com.gumtree.repository.AddressBookRepository;
import com.gumtree.service.AddressBookService;
import com.gumtree.util.ContactParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressBookApplicationTest {

    static String FILE = "AddressBook";
    public static final String ADDRESS_BOOK_LINE = "Mohammed Hussain, Male, 15/06/1982";

    private AddressBookApplication application;
    private FileReader fileReader;
    private ContactParser contactParser;
    private AddressBookRepository addressBookRepository;
    private AddressBookService addressBookService;

    private Contact contact;

    @Before
    public void setup() throws Exception {
        LinkedList<String> lines = new LinkedList<>();
        lines.add(ADDRESS_BOOK_LINE);
        fileReader = mock(FileReader.class);
        when(fileReader.readLines(FILE)).thenReturn(lines);

        contact = new Contact("Male");
        contactParser = mock(ContactParser.class);
        when(contactParser.parse(ADDRESS_BOOK_LINE)).thenReturn(contact);

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
        verify(addressBookRepository).add(contact);
    }

    @Test
    public void numberOfMaleContacts() {
        when(addressBookService.getNumberOfMaleContacts()).thenReturn(3);

        application.init();
        int numberOfMaleContacts = application.numberOfMaleContacts();

        verify(addressBookService).getNumberOfMaleContacts();
        assertEquals(3, numberOfMaleContacts);
    }



}
