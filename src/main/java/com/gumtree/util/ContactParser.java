package com.gumtree.util;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
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
        Gender gender = getGender(contactCSV);
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

    private Gender getGender(String[] contactCSV) {
        String gender = contactCSV[1].trim().toUpperCase();

        if (Gender.MALE.name().equals(gender)){
            return Gender.MALE;
        }
        else if (Gender.FEMALE.name().equals(gender)) {
            return Gender.FEMALE;
        }
        else {
            throw new BadlyFormattedContactException();
        }
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
