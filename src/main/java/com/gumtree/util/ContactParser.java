package com.gumtree.util;

import com.gumtree.domain.Contact;
import com.gumtree.exception.BadlyFormattedContactException;

public class ContactParser {

    private static final int VALID_NUMBER_OF_CSV = 3;
    private static final String DELIMITER = ",";

    public Contact parse(String input) {
        throwBadlyFormattedContactExceptionWhenInputIsNull(input);

        String[] contactCSV = input.split(DELIMITER);

        throwBadlyFormattedContactExceptionIfInputHaveCorrectNumberOfCSVs(contactCSV);

        String gender = getGender(contactCSV);

        return new Contact(gender);
    }

    private void throwBadlyFormattedContactExceptionWhenInputIsNull(String contact) {
        if (contact == null) {
            throw new BadlyFormattedContactException();
        }
    }

    private void throwBadlyFormattedContactExceptionIfInputHaveCorrectNumberOfCSVs(String[] contactCSV) {
        if (contactCSV.length != VALID_NUMBER_OF_CSV) {
            throw new BadlyFormattedContactException();
        }
    }

    private String getGender(String[] contactCSV) {
        String gender = contactCSV[1].trim();

        if (!gender.equals("Male") && !gender.equals("Female")) {
            throw new BadlyFormattedContactException();
        }
        return gender;
    }
}
