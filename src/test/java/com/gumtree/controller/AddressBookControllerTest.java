package com.gumtree.controller;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.service.AddressBookService;
import com.gumtree.springconfig.TestConfig;
import com.gumtree.springconfig.WebAppConfig;
import com.gumtree.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, TestConfig.class})
@WebAppConfiguration
public class AddressBookControllerTest {
    private static final String DATE_FORMAT = "dd/MM/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    private static final String JOHN = "John";
    private static final String JANE = "Jane";
    private static final String JOE = "JOE";
    private static final String JACK = "Jane";

    private static final Gender MALE = Gender.MALE;
    private static final Gender FEMALE = Gender.FEMALE;

    private static final String DOB_150682 = "15/06/82";
    private static final String DOB_150382 = "15/03/82";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AddressBookService addressBookServiceMock;

    private Contact john;
    private Contact jane;
    private Contact jack;
    private Contact joe;
    private LinkedList<Contact> maleContacts;
    private LinkedList<Contact> femalContacts;


    @Before
    public void setup() throws ParseException {
        reset(addressBookServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        john = new Contact(JOHN, MALE, sdf.parse(DOB_150682));
        jane = new Contact(JANE, FEMALE, sdf.parse(DOB_150682));
        jack = new Contact(JACK, MALE, sdf.parse(DOB_150382));
        joe = new Contact(JOE, FEMALE, sdf.parse(DOB_150382));

        maleContacts = new LinkedList<Contact>() {{
            add(jack);
            add(john);
        }};
        femalContacts = new LinkedList<Contact>() {{
            add(jane);
            add(joe);
        }};
    }

    @Test
    public void countContactsByGender_whenContactsOfGivenGenderDontExist_ShouldReturnZero() throws Exception {

        when(addressBookServiceMock.getContactsByGender(Gender.MALE)).thenReturn(Collections.EMPTY_LIST);

        mockMvc.perform(get("/api/contact/count?gender={gender}", "Male"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.count", is(0)))
        ;

        verify(addressBookServiceMock).getContactsByGender(Gender.MALE);
        verifyNoMoreInteractions(addressBookServiceMock);
    }

    @Test
    public void countContactsByGender_whenContactsOfGivenGenderExist_ShouldReturnCountOfGivenContacts() throws Exception {

        when(addressBookServiceMock.getContactsByGender(Gender.MALE)).thenReturn(maleContacts);
        when(addressBookServiceMock.getContactsByGender(Gender.FEMALE)).thenReturn(femalContacts);

        mockMvc.perform(get("/api/contact/count?gender={gender}", "Male"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.count", is(2)))
        ;

        verify(addressBookServiceMock).getContactsByGender(Gender.MALE);

        mockMvc.perform(get("/api/contact/count?gender={gender}", "Female"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.count", is(2)))
        ;

        verify(addressBookServiceMock).getContactsByGender(Gender.FEMALE);
        verifyNoMoreInteractions(addressBookServiceMock);
    }


    @Test
    public void countContactsByGender_incorrectGenderValue_ShouldReturnHttpStatusCode406() throws Exception {

        mockMvc.perform(get("/api/contact/count?gender={gender}", "sxxzc"))
                .andExpect(status().isNotAcceptable());

        verifyNoMoreInteractions(addressBookServiceMock);
    }

}
