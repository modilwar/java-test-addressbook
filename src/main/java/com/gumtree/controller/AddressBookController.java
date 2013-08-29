package com.gumtree.controller;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.dto.CountDTO;
import com.gumtree.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class AddressBookController {

    private static final String DATE_FORMAT = "dd/MM/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    private AddressBookService addressBookService;

    @RequestMapping(value = "/api/contact/count", method = RequestMethod.GET)
    @ResponseBody
    public CountDTO countContactsByGender(@RequestParam("gender") String gender) throws ParseException {
        Gender gen = Gender.valueOf(gender.toUpperCase());
        List<Contact> contactsBy = addressBookService.getContactsByGender(gen);
        return new CountDTO(contactsBy.size());
    }
}
