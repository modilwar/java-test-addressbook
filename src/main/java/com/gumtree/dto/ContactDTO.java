package com.gumtree.dto;

public class ContactDTO {

    private String name;

    private String gender;

    private String dob;

    public ContactDTO() {};

    public ContactDTO(String name, String gender, String dob) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}