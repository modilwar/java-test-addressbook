package com.gumtree.application;


import com.gumtree.domain.Contact;
import com.gumtree.io.FileReader;
import com.gumtree.io.FlatFileReader;
import com.gumtree.repository.AddressBookLinkedListRepository;
import com.gumtree.repository.AddressBookRepository;
import com.gumtree.service.AddressBookService;
import com.gumtree.service.AddressBookServiceImpl;
import com.gumtree.util.ContactParser;

import java.io.IOException;
import java.util.List;

public class AddressBookApplication {

    static String FILE = "AddressBook";

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
        FlatFileReader fileReader = new FlatFileReader();
        ContactParser contactParser = new ContactParser();
        AddressBookRepository addressBookRepository = new AddressBookLinkedListRepository();
        AddressBookService addressBookService = new AddressBookServiceImpl(addressBookRepository);
        AddressBookApplication application = new AddressBookApplication(fileReader, contactParser, addressBookRepository, addressBookService);
        application.init();

        int numberOfMaleCustomers = application.numberOfMaleContacts();
        System.out.println("There are " + numberOfMaleCustomers + " males in the address book");

        Contact oldestPerson = application.oldestPerson();
        System.out.println("The oldest person in the address book is " + oldestPerson.getName());

        Contact bill = application.contactByName("Bill McKnight");
        Contact paul = application.contactByName("Paul Robinson");
        long days = application.getAgeDifferenceInDays(bill, paul);
        System.out.print("Bill is " + days + " days older than Paul");

    }

    public void init() {
        List<String> lines = null;
        try {
            lines = fileReader.readLines(FILE);
            for (String line : lines) {
                Contact contact = contactParser.parse(line);
                addressBookRepository.add(contact);
            }
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.print("ERROR: could not open AddressBook file");
        }
    }

    public int numberOfMaleContacts() {
        return addressBookService.getNumberOfMaleContacts();
    }

    public Contact oldestPerson() {
        return addressBookService.getOldestPerson();
    }

    public Contact contactByName(String name) {
        return addressBookService.findContactByName(name);
    }

    public long getAgeDifferenceInDays(Contact contact1, Contact contact2) {
        return addressBookService.ageDifferenceBetween(contact1, contact2);
    }
}
