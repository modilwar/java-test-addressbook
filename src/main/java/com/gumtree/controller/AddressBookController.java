package com.gumtree.controller;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.domain.Order;
import com.gumtree.dto.ContactsDTO;
import com.gumtree.dto.CountDTO;
import com.gumtree.repository.AddressBookRepository;
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

    @Autowired
    private AddressBookRepository  addressBookRepository;

    @RequestMapping(value = "/api/contact/count", method = RequestMethod.GET)
    @ResponseBody
    public CountDTO count(@RequestParam(value = "gender", required = false) String gender) throws ParseException {
        List<Contact> contactsBy = null;
        if(gender != null) {
            Gender gen = Gender.valueOf(gender.toUpperCase());
            contactsBy = addressBookService.getContactsByGender(gen);
        }
        else {
            contactsBy = addressBookRepository.getAllContacts();
        }
        return new CountDTO(contactsBy.size());
    }

    @RequestMapping(value = "/api/contact", method = RequestMethod.GET)
    @ResponseBody
    public ContactsDTO getContacts(@RequestParam(value = "order_by", defaultValue = "age", required = false) String orderBy,
                                   @RequestParam(value = "order_how", defaultValue = "asc", required = false) String orderHow,
                                   @RequestParam(value = "limit", defaultValue = "10", required = false) int limit) throws ParseException {

        throwIllegalArgumentExceptionForIlllegalOrderByParameters(orderBy);

        Order order = Order.valueOf(orderHow.toUpperCase());

        throwIllegalArgumentExceptionForLimitLessThanOne(limit);

        return addressBookService.getContactsOrderedByAge(order, limit);
    }

    private void throwIllegalArgumentExceptionForIlllegalOrderByParameters(String orderBy) {
        if (orderBy != null && !"age".equalsIgnoreCase(orderBy.trim())) {
            throw new IllegalArgumentException();
        }
    }

    private void throwIllegalArgumentExceptionForLimitLessThanOne(int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException();
        }
    }
}
