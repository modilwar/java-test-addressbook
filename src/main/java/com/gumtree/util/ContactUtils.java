package com.gumtree.util;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.dto.ContactDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ContactUtils {
    private static final String DATE_FORMAT = "dd/MM/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    public List<Contact> createContacts(Collection<ContactDTO> contactDTOs) throws ParseException {
        List<Contact> contacts = new LinkedList<>();
        for (ContactDTO contactDTO : contactDTOs) {
            contacts.add(createContact(contactDTO));
        }
        return contacts;
    }

    public Contact createContact(ContactDTO contactDTO) throws ParseException {
        String name = contactDTO.getName();
        Gender gender = Gender.valueOf(contactDTO.getGender().toUpperCase());
        Date dob = sdf.parse(contactDTO.getDob());
        return new Contact(name, gender, dob);
    }

    public List<ContactDTO> createContactDTOs(Collection<Contact> contacts) throws ParseException {
        List<ContactDTO> contactDTOs = new LinkedList<>();
        for (Contact contact : contacts) {
            contactDTOs.add(createContactDTO(contact));
        }
        return contactDTOs;
    }

    public ContactDTO createContactDTO(Contact contact) {
        String name = contact.getName();
        String gender = contact.getGender().asString();
        String dob = sdf.format(contact.getDob());
        return new ContactDTO(name, gender, dob);
    }
}
