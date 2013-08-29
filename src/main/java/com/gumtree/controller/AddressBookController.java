package com.gumtree.controller;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.domain.Order;
import com.gumtree.dto.ContactsDTO;
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

    @RequestMapping(value = "/api/contact", method = RequestMethod.GET)
    @ResponseBody
    public ContactsDTO countContactsByGender(@RequestParam(value = "order_by", required = false) String orderBy,
                                             @RequestParam(value = "order_how", defaultValue = "asc", required = false) String orderHow,
                                             @RequestParam(value = "limit", defaultValue = "0", required = false) int limit) throws ParseException {

        throwIllegalArgumentExceptionForIlllegalOrderByParameters(orderBy);

        Order order = Order.valueOf(orderHow.toUpperCase());

        List<Contact> contactsBy = addressBookService.getContacts(orderBy, order, limit);
        return null;
    }

    private void throwIllegalArgumentExceptionForIlllegalOrderByParameters(String orderBy) {
        if (orderBy != null && !"dob".equalsIgnoreCase(orderBy.trim())) {
            throw new IllegalArgumentException();
        }
    }
}
