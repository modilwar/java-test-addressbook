package com.gumtree.domain;

public class Contact {
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    private final String gender;
    private final String name;

    public Contact(String gender) {
        this.gender = gender;
        this.name = "";
    }

    public Contact(String name, String gender) {
        this.name = name;
        this.gender = gender;
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

        if (!gender.equals(contact.gender)) return false;
        if (!name.equals(contact.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gender.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}