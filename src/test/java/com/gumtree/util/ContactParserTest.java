package com.gumtree.util;

import com.gumtree.domain.Contact;
import com.gumtree.exception.BadlyFormattedContactException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ContactParserTest {

    private ContactParser parser;

    @Before
    public void setup() {
        parser = new ContactParser();
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
        String input1 = "a,Male,c";
        Contact contact1 = parser.parse(input1);
        assertNotNull(contact1);
        assertTrue(contact1.isMale());

        String input2 = "a,Female,c";
        Contact contact2 = parser.parse(input2);
        assertNotNull(contact2);
        assertTrue(contact2.isFemale());
    }

    @Test
    public void parsesValidInputWithSpaces() {
        String input1 = " a  , Male,  c  ";
        Contact contact1 = parser.parse(input1);
        assertNotNull(contact1);
        assertTrue(contact1.isMale());

        String input2 = "a, Female, c";
        Contact contact2 = parser.parse(input2);
        assertNotNull(contact2);
        assertTrue(contact2.isFemale());
    }

}
