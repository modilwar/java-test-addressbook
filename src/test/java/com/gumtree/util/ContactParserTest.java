package com.gumtree.util;

import com.gumtree.domain.Contact;
import com.gumtree.exception.BadlyFormattedContactException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ContactParserTest {

    public static final String JOHN = "John";
    public static final String JANE = "Jane";

    private ContactParser parser;

    private Date dob150682;
    private Date dob081285;

    @Before
    public void setup() throws Exception {
        parser = new ContactParser();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        dob150682 = sdf.parse("15/06/82");
        dob081285 = sdf.parse("08/12/85");
    }

    @Test(expected = BadlyFormattedContactException.class)
    public void throwsBadlyFormattedContactException_whenInputIsNull() {
        String input = null;
        parser.parse(input);
    }

    @Test(expected = BadlyFormattedContactException.class)
    public void throwsBadlyFormattedContactException_whenInputIsEmpyString() {
        String input = "";
        parser.parse(input);
    }

    @Test(expected = BadlyFormattedContactException.class)
    public void throwsBadlyFormattedContactException_whenContactHasOneCSV() {
        String input = "a";
        parser.parse(input);
    }

    @Test(expected = BadlyFormattedContactException.class)
    public void throwsBadlyFormattedContactException_whenInputtHasTwoCSV() {
        String input = "a,b";
        parser.parse(input);
    }

    @Test(expected = BadlyFormattedContactException.class)
    public void throwsBadlyFormattedContactException_whenInputHasFourCSV() {
        String input = "a,b,c,d";
        parser.parse(input);
    }

    @Test(expected = BadlyFormattedContactException.class)
    public void throwsBadlyFormattedContactException_when2ndValueIsNotGender() {
        String input = "a,nonGenderValue,c";
        parser.parse(input);
    }

    @Test
    public void parsesValidInputWithNoSpaces() {
        String input1 = "John,Male,15/06/82";
        Contact contact1 = parser.parse(input1);
        assertNotNull(contact1);
        assertEquals(JOHN, contact1.getName());
        assertTrue(contact1.isMale());
        assertEquals(dob150682, contact1.getDob());

        String input2 = "Jane,Female,08/12/85";
        Contact contact2 = parser.parse(input2);
        assertNotNull(contact2);
        assertEquals(JANE, contact2.getName());
        assertTrue(contact2.isFemale());
    }

    @Test
    public void parsesValidInputWithSpaces() {
        String input1 = " John  , Male,  15/06/82  ";
        Contact contact1 = parser.parse(input1);
        assertNotNull(contact1);
        assertEquals(JOHN, contact1.getName());
        assertTrue(contact1.isMale());


        String input2 = "Jane, Female, 08/12/85";
        Contact contact2 = parser.parse(input2);
        assertNotNull(contact2);
        assertEquals(JANE, contact2.getName());
        assertTrue(contact2.isFemale());
    }

    @Test(expected = BadlyFormattedContactException.class)
    public void throwsBadlyFormattedContactException_whenDOBisFormattedIncorrctly() {
        String input = "John,Male,15-06-82";
        Contact contact = parser.parse(input);
        assertNotNull(contact);
        assertEquals(JOHN, contact.getName());
        assertTrue(contact.isMale());
        assertEquals(dob150682, contact.getDob());
    }
}
