package com.gumtree.application;

import com.gumtree.domain.Contact;
import com.gumtree.io.FileReader;
import com.gumtree.repository.AddressBookRepository;
import com.gumtree.util.ContactParser;import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressBookApplicationTest {

    static String FILE = "../AddressBook";
    public static final String ADDRESS_BOOK_LINE = "Mohammed Hussain, Male, 15/06/1982";

    private AddressBookApplication application;
    private FileReader fileReader;
    private ContactParser contactParser;
    private AddressBookRepository addressBookRepository;

    private Contact contact;

    @Before
    public void setup() {
        LinkedList<String> lines = new LinkedList<>();
        lines.add(ADDRESS_BOOK_LINE);
        fileReader = mock(FileReader.class);
        when(fileReader.readLines(FILE)).thenReturn(lines);

        contact = new Contact("Male");
        contactParser = mock(ContactParser.class);
        when(contactParser.parse(ADDRESS_BOOK_LINE)).thenReturn(contact);

        addressBookRepository = mock(AddressBookRepository.class);

        application = new AddressBookApplication(fileReader, contactParser, addressBookRepository);
    }

    @Test
    public void initReadsAddressBookFile() {
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

}
