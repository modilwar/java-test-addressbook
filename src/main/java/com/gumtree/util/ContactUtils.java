package com.gumtree.util;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.dto.ContactDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactUtils {
    private static final String DATE_FORMAT = "dd/MM/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    public Contact createContact(ContactDTO contactDTO) throws ParseException {
        String name = contactDTO.getName();
        Gender gender = Gender.valueOf(contactDTO.getGender().toUpperCase());
        Date dob = sdf.parse(contactDTO.getDob());
        return new Contact(name, gender, dob);
    }

    public ContactDTO createContactDTO(Contact contact) {
        String name = contact.getName();
        String gender = contact.getGender().asString();
        String dob = sdf.format(contact.getDob());
        return new ContactDTO(name, gender, dob);
    }
}
