package com.gumtree.dto;

import java.util.List;

public class ContactsDTO {

    private List<ContactDTO> contacts;

    public ContactsDTO() {};

    public ContactsDTO(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public int getCount() {
        return contacts != null ? contacts.size() : 0;
    }
}
