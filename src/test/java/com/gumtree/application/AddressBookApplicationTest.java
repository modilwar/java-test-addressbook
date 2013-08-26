package com.gumtree.application;

import com.gumtree.io.FileReader;
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
    public static final String ADDRESS_BOOK_LINE = "addressBookLine";

    private AddressBookApplication application;
    private FileReader fileReader;
    private ContactParser contactParser;

    @Before
    public void setup() {
        LinkedList<String> lines = new LinkedList<>();
        lines.add(ADDRESS_BOOK_LINE);
        fileReader = mock(FileReader.class);
        when(fileReader.readLines(FILE)).thenReturn(lines);

        contactParser = mock(ContactParser.class);

        application = new AddressBookApplication(fileReader, contactParser);
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

}
