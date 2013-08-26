package com.gumtree.application;

import org.junit.Ignore;
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

        String expectedOutput = "There are 3 males in the address book\n" +
                                "The oldest person in the address book is Wes Jackson\n" +
                                "Bill is 2862 days older than Paul";
        assertEquals(expectedOutput, output.toString());
    }

}
