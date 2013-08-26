package com.gumtree.util;

import com.gumtree.domain.Contact;
import com.gumtree.exception.BadlyFormattedContactException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactParser {

    private static final int VALID_NUMBER_OF_CSV = 3;
    private static final String DELIMITER = ",";

    public Contact parse(String input) {
        throwBadlyFormattedContactExceptionWhenInputIsNull(input);

        String[] contactCSV = input.split(DELIMITER);

        throwBadlyFormattedContactExceptionIfInputHaveCorrectNumberOfCSVs(contactCSV);

        String name = getName(contactCSV);
        String gender = getGender(contactCSV);
        Date dob = getDOB(contactCSV);

        return new Contact(name, gender, dob);
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

    private String getName(String[] contactCSV) {
        return contactCSV[0].trim();
    }

    private String getGender(String[] contactCSV) {
        String gender = contactCSV[1].trim();

        if (!gender.equals("Male") && !gender.equals("Female")) {
            throw new BadlyFormattedContactException();
        }
        return gender;
    }

    private Date getDOB(String[] contactCSV) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dob = contactCSV[2].trim();

        try {
            return sdf.parse(dob);
        } catch (ParseException e) {
            throw new BadlyFormattedContactException();
        }
    }
}
