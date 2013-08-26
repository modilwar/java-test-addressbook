package com.gumtree.application;

import com.gumtree.io.FileReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AddressBookApplicationTest {

    static String FILE = "../AddressBook";

    private AddressBookApplication application;
    private FileReader fileReader;

    @Before
    public void setup() {
        fileReader = mock(FileReader.class);

        application = new AddressBookApplication(fileReader);
        application.init() ;
    }

    @Test
    public void readsAddressBookFile() {
        assertTrue(true);
        verify(fileReader).readLines(FILE);
    }

}
