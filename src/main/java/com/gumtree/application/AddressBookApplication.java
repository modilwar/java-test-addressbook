package com.gumtree.application;


import com.gumtree.io.FileReader;

public class AddressBookApplication {

    static String FILE = "../AddressBook";

    private FileReader fileReader;

    public AddressBookApplication(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public static void main(String[] args) throws Exception {
    }

    public void init() {
        fileReader.readLines(FILE);
    }
}
