package com.gumtree.domain;

public enum Gender {
    MALE,
    FEMALE;

    public String asString() {
        return this.equals(MALE) ? "Male" : "Female";
    }
}
