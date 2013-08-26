package com.gumtree.application;


import com.gumtree.io.FileReader;
import com.gumtree.util.ContactParser;

import java.util.List;

public class AddressBookApplication {

    static String FILE = "../AddressBook";

    private FileReader fileReader;
    private ContactParser contactParser;

    public AddressBookApplication(FileReader fileReader, ContactParser contactParser) {
        this.fileReader = fileReader;
        this.contactParser = contactParser;
    }

    public static void main(String[] args) throws Exception {
    }

    public void init() {
        List<String> lines = fileReader.readLines(FILE);
        for (String line : lines) {
            contactParser.parse(line);
        }

    }
}
