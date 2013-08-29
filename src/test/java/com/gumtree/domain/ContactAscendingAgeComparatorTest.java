package com.gumtree.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class ContactAscendingAgeComparatorTest {

    public static final String ANY_NAME = "Name";
    public static final Gender ANY_GENDER = Gender.MALE;
    private Comparator<Contact> comparator;

    private Contact olderContact;
    private Contact youngerContact;

    @Before
    public void setup() throws Exception {
        comparator = new ContactAscendingAgeComparator();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        olderContact = new Contact(ANY_NAME, ANY_GENDER, sdf.parse("13/02/80"));
        youngerContact = new Contact(ANY_NAME, ANY_GENDER, sdf.parse("13/02/85"));
    }

    @Test
    public void dob1IsOlderThanDob2() {
        int comparison = comparator.compare(olderContact, youngerContact);
        Assert.assertEquals(1, comparison);
    }

    @Test
    public void dob2IsOlderThanDob1() {
        int comparison = comparator.compare(youngerContact, olderContact);
        Assert.assertEquals(-1, comparison);
    }

    @Test
    public void dob1IDob2AreTheSame() {
        int comparison = comparator.compare(olderContact, olderContact);
        Assert.assertEquals(0, comparison);
    }
}
