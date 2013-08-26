package com.gumtree.application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AddressBookApplicationIntegrationTest {

    @Test
    public void testMainPrintsOutAnswers() throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        String[] args = new String[]{};
        AddressBookApplication.main(args);

        String expectedOutput = "There are 3 males in the address book";
        assertEquals(expectedOutput, output.toString());
    }

}
