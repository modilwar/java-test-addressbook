package com.gumtree.io;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class FlatFileReaderTest {

    private static final String NON_EXISTING_FILE_NAME = "NON_EXISTING_FILE_NAME";
    private static final String FILE_NAME = "src/test/resources/AddressBook";

    private FileReader reader;

    @Before
    public void setup() {
        reader = new FlatFileReader();
    }

    @Test
    public void readFileLines_returnsZeroLines_whenFileDoesNotExist() throws Exception {
        List<String> lines = reader.readLines(NON_EXISTING_FILE_NAME);
        assertNotNull(lines);
        assertTrue(lines.isEmpty());
    }

    @Test
    public void readFileLines_returnsAllLinesFromExistingFile() throws Exception {
        List<String> lines = reader.readLines(FILE_NAME);
        assertNotNull(lines);
        assertEquals(5, lines.size());
    }


}