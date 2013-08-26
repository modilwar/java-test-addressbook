package com.gumtree.domain;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class ContactTest {

    private static final String JOHN = "John";
    private static final String JANE = "Jane";

    public static final Gender MALE = Gender.MALE;
    public static final Gender FEMALE = Gender.FEMALE;

    private Contact johnMale150682;
    private Contact janeFemale081285;

    @Before
    public void setup() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        johnMale150682 = new Contact(JOHN, MALE, sdf.parse("15/06/82"));
        janeFemale081285 = new Contact(JANE, FEMALE, sdf.parse("08/12/85"));
    }

    @Test
    public void isMale() {
        Assert.assertTrue(johnMale150682.isMale());
        Assert.assertFalse(janeFemale081285.isMale());
    }

    @Test
    public void isFemale() {
        Assert.assertFalse(johnMale150682.isFemale());
        Assert.assertTrue(janeFemale081285.isFemale());
    }

}