package com.gumtree.domain;

import java.util.Date;

public class Contact {
    public static final Gender MALE = Gender.MALE;
    public static final Gender FEMALE = Gender.FEMALE;

    private final Gender gender;
    private final Date dob;
    private final String name;

    public Contact(String name, Gender gender, Date dob) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    public boolean isMale() {
        return MALE.equals(gender) ? true : false;
    }

    public boolean isFemale() {
        return FEMALE.equals(gender) ? true : false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (dob != null ? !dob.equals(contact.dob) : contact.dob != null) return false;
        if (gender != null ? !gender.equals(contact.gender) : contact.gender != null) return false;
        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gender != null ? gender.hashCode() : 0;
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Date getDob() {
        return dob;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }
}