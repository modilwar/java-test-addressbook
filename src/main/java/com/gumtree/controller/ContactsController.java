package com.gumtree.controller;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.dto.ContactDTO;
import com.gumtree.exception.ContactNotFoundException;
import com.gumtree.repository.AddressBookRepository;
import com.gumtree.util.ContactUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ContactsController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @RequestMapping(value = "/api/contact/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ContactDTO findByName(@PathVariable("name") String  name) throws ContactNotFoundException {
        Contact found = addressBookRepository.get(name);
        return new ContactUtils().createContactDTO(found);
    }

    @RequestMapping(value = "/api/contact", method = RequestMethod.POST, consumes="application/json", produces="application/json")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public ContactDTO add(@Valid @RequestBody ContactDTO contactDTO) throws ParseException {
        ContactUtils contactUtils = new ContactUtils();
        Contact contact = contactUtils.createContact(contactDTO);

        Contact added = addressBookRepository.add(contact);
        return contactUtils.createContactDTO(contact);
    }
}
