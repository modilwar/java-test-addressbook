package com.gumtree.domain;

import junit.framework.Assert;
import org.junit.Test;

public class ContactTest {

    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    @Test
    public void isMale() {
        Contact maleContact = new Contact(MALE);
        Contact femaleContact = new Contact(FEMALE);

        Assert.assertTrue(maleContact.isMale());
        Assert.assertFalse(femaleContact.isMale());
    }

    @Test
    public void isFemale() {
        Contact maleContact = new Contact(MALE);
        Contact femaleContact = new Contact(FEMALE);

        Assert.assertFalse(maleContact.isFemale());
        Assert.assertTrue(femaleContact.isFemale());
    }

}