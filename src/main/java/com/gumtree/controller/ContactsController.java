package com.gumtree.controller;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.dto.ContactDTO;
import com.gumtree.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ContactsController {

    private static final String DATE_FORMAT = "dd/MM/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    private AddressBookRepository addressBookRepository;

    @RequestMapping(value = "/api/contact", method = RequestMethod.POST)
    @ResponseBody
    public ContactDTO add(@Valid @RequestBody ContactDTO contactDTO) throws ParseException {
        Contact contact = createContact(contactDTO);

        Contact added = addressBookRepository.add(contact);
        return createContactDTO(added);
    }

    private Contact createContact(ContactDTO contactDTO) throws ParseException {
        String name = contactDTO.getName();
        Gender gender = Gender.valueOf(contactDTO.getGender().toUpperCase());
        Date dob = sdf.parse(contactDTO.getDob());
        return new Contact(name, gender, dob);
    }

    private ContactDTO createContactDTO(Contact contact) {
        String name = contact.getName();
        String gender = contact.getGender().asString();
        String dob = sdf.format(contact.getDob());
        return new ContactDTO(name, gender, dob);
    }
}
