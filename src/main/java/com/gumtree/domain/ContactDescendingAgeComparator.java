package com.gumtree.domain;

import java.util.Comparator;
import java.util.Date;

public class ContactDescendingAgeComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact1, Contact contact2) {
        Date dob1 = contact1.getDob();
        Date dob2 = contact2.getDob();
        return dob1.compareTo(dob2);
    }
}
