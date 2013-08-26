package com.gumtree.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class ContactDescendingAgeComparatorTest {

    public static final String ANY_NAME = "Name";
    public static final String ANY_GENDER = "Male";
    private Comparator<Contact> conparator;

    private Contact olderContact;
    private Contact youngerContact;

    @Before
    public void setup() throws Exception {
        conparator = new ContactDescendingAgeComparator();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        olderContact = new Contact(ANY_NAME, ANY_GENDER, sdf.parse("13/02/80"));
        youngerContact = new Contact(ANY_NAME, ANY_GENDER, sdf.parse("13/02/85"));
    }

    @Test
    public void dob1IsOlderThanDob2() {
        int comparison = conparator.compare(olderContact, youngerContact);
        Assert.assertEquals(-1, comparison);
    }

    @Test
    public void dob2IsOlderThanDob1() {
        int comparison = conparator.compare(youngerContact, olderContact);
        Assert.assertEquals(1, comparison);
    }

    @Test
    public void dob1IDob2AreTheSame() {
        int comparison = conparator.compare(olderContact, olderContact);
        Assert.assertEquals(0, comparison);
    }
}
