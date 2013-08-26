package com.gumtree.domain;

public class Contact {
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    private final String gender;

    public Contact(String gender) {
        this.gender = gender;
    }

    public boolean isMale() {
        return MALE.equals(gender) ? true : false;
    }

    public boolean isFemale() {
        return FEMALE.equals(gender) ? true : false;
    }
}