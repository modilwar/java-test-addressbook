package com.gumtree.application;


import com.gumtree.domain.Contact;
import com.gumtree.io.FileReader;
import com.gumtree.repository.AddressBookRepository;
import com.gumtree.service.AddressBookService;
import com.gumtree.util.ContactParser;

import java.util.List;

public class AddressBookApplication {

    static String FILE = "../AddressBook";

    private FileReader fileReader;
    private ContactParser contactParser;
    private AddressBookRepository addressBookRepository;
    private AddressBookService addressBookService;

    public AddressBookApplication(FileReader fileReader, ContactParser contactParser, AddressBookRepository addressBookRepository, AddressBookService addressBookService) {
        this.fileReader = fileReader;
        this.contactParser = contactParser;
        this.addressBookRepository = addressBookRepository;
        this.addressBookService = addressBookService;
    }

    public static void main(String[] args) throws Exception {
    }

    public void init() {
        List<String> lines = fileReader.readLines(FILE);
        for (String line : lines) {
            Contact contact = contactParser.parse(line);
            addressBookRepository.add(contact);
        }

    }

    public int numberOfMaleContacts() {
        return addressBookService.getNumberOfMaleContacts();
    }
}
