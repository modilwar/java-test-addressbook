package com.gumtree.controller;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.domain.Order;
import com.gumtree.dto.ContactDTO;
import com.gumtree.dto.ContactsDTO;
import com.gumtree.service.AddressBookService;
import com.gumtree.springconfig.TestConfig;
import com.gumtree.springconfig.WebAppConfig;
import com.gumtree.util.ContactUtils;
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
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
    private static final String DOB_160382 = "16/06/82";
    private static final String DOB_170382 = "17/06/82";
    private static final String DOB_180382 = "18/06/82";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AddressBookService addressBookServiceMock;

    private Contact john;
    private Contact jane;
    private Contact jack;
    private Contact joe;
    private List<Contact> maleContacts;
    private List<Contact> femaleContacts;
    private List<Contact> allContacts;

    @Before
    public void setup() throws ParseException {
        reset(addressBookServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        john = new Contact(JOHN, MALE, sdf.parse(DOB_150682));
        jane = new Contact(JANE, FEMALE, sdf.parse(DOB_160382));
        jack = new Contact(JACK, MALE, sdf.parse(DOB_170382));
        joe = new Contact(JOE, FEMALE, sdf.parse(DOB_180382));

        maleContacts = new LinkedList<Contact>() {{
            add(jack);
            add(john);
        }};
        femaleContacts = new LinkedList<Contact>() {{
            add(jane);
            add(joe);
        }};

        allContacts = new LinkedList<Contact>() {{
            add(jack);
            add(john);
            add(jane);
            add(joe);
        }};
    }

    @Test
    public void countContactsByGender_whenContactsOfGivenGenderDoNotExist_shouldReturnZero() throws Exception {

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
    public void countContactsByGender_whenContactsOfGivenGenderExist_shouldReturnCountOfGivenContacts() throws Exception {

        when(addressBookServiceMock.getContactsByGender(Gender.MALE)).thenReturn(maleContacts);
        when(addressBookServiceMock.getContactsByGender(Gender.FEMALE)).thenReturn(femaleContacts);

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

    @Test
    public void getContacts_incorrectOrderBy_shouldReturnHttpStatusCode406() throws Exception {

        mockMvc.perform(get("/api/contact?order_by={order_by}", "sxxzc"))
                .andExpect(status().isNotAcceptable());

        verifyNoMoreInteractions(addressBookServiceMock);
    }

    @Test
    public void getContacts_incorrectOrderHow_shouldReturnHttpStatusCode200() throws Exception {

        mockMvc.perform(get("/api/contact?order_how={order_how}", "sxxzc"))
                .andExpect(status().isNotAcceptable());

        verifyNoMoreInteractions(addressBookServiceMock);
    }

    @Test
    public void getContacts_limitZeo_shouldReturnHttpStatusCode200() throws Exception {

        mockMvc.perform(get("/api/contact?limit={limit}", "0"))
                .andExpect(status().isNotAcceptable());

        verifyNoMoreInteractions(addressBookServiceMock);
    }

    @Test
    public void getContacts_negativeLimit_shouldReturnHttpStatusCode200() throws Exception {

        mockMvc.perform(get("/api/contact?limit={limit}", "-1"))
                .andExpect(status().isNotAcceptable());

        verifyNoMoreInteractions(addressBookServiceMock);
    }

    @Test
    public void getContacts_withNoParameters_shouldReturnHttpStatusCode200() throws Exception {

        List<ContactDTO> contactDTOs = new ContactUtils().createContactDTOs(allContacts);
        ContactsDTO orderedContactsDTO = new ContactsDTO(contactDTOs);

        when(addressBookServiceMock.getContactsOrderedByDob(Order.ASC, 10)).thenReturn(orderedContactsDTO);

        mockMvc.perform(get("/api/contact"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.count.", is(contactDTOs.size())))
                .andExpect(jsonPath("$.contacts.", hasSize(contactDTOs.size())))
                .andExpect(jsonPath("$.contacts[0].name", is(contactDTOs.get(0).getName())))
                .andExpect(jsonPath("$.contacts[0].gender", is(contactDTOs.get(0).getGender())))
                .andExpect(jsonPath("$.contacts[0].dob", is(contactDTOs.get(0).getDob())))
                .andExpect(jsonPath("$.contacts[1].name", is(contactDTOs.get(1).getName())))
                .andExpect(jsonPath("$.contacts[1].gender", is(contactDTOs.get(1).getGender())))
                .andExpect(jsonPath("$.contacts[1].dob", is(contactDTOs.get(1).getDob())))
                .andExpect(jsonPath("$.contacts[2].name", is(contactDTOs.get(2).getName())))
                .andExpect(jsonPath("$.contacts[2].gender", is(contactDTOs.get(2).getGender())))
                .andExpect(jsonPath("$.contacts[2].dob", is(contactDTOs.get(2).getDob())))
                .andExpect(jsonPath("$.contacts[3].name", is(contactDTOs.get(3).getName())))
                .andExpect(jsonPath("$.contacts[3].gender", is(contactDTOs.get(3).getGender())))
                .andExpect(jsonPath("$.contacts[3].dob", is(contactDTOs.get(3).getDob())))
        ;

        verify(addressBookServiceMock).getContactsOrderedByDob(Order.ASC, 10);
        verifyNoMoreInteractions(addressBookServiceMock);
    }

    @Test
    public void getContacts_withValidParameters_shouldReturnHttpStatusCode200() throws Exception {

        List<ContactDTO> contactDTOs = new ContactUtils().createContactDTOs(allContacts);
        ContactsDTO orderedContactsDTO = new ContactsDTO(contactDTOs);

        when(addressBookServiceMock.getContactsOrderedByDob(Order.DESC, 4)).thenReturn(orderedContactsDTO);

        mockMvc.perform(get("/api/contact?order_by={order_by}&order_how={order_how}&limit={limit}", "age", "desc", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.count.", is(contactDTOs.size())))
                .andExpect(jsonPath("$.contacts.", hasSize(contactDTOs.size())))
                .andExpect(jsonPath("$.contacts[0].name", is(contactDTOs.get(0).getName())))
                .andExpect(jsonPath("$.contacts[0].gender", is(contactDTOs.get(0).getGender())))
                .andExpect(jsonPath("$.contacts[0].dob", is(contactDTOs.get(0).getDob())))
                .andExpect(jsonPath("$.contacts[1].name", is(contactDTOs.get(1).getName())))
                .andExpect(jsonPath("$.contacts[1].gender", is(contactDTOs.get(1).getGender())))
                .andExpect(jsonPath("$.contacts[1].dob", is(contactDTOs.get(1).getDob())))
                .andExpect(jsonPath("$.contacts[2].name", is(contactDTOs.get(2).getName())))
                .andExpect(jsonPath("$.contacts[2].gender", is(contactDTOs.get(2).getGender())))
                .andExpect(jsonPath("$.contacts[2].dob", is(contactDTOs.get(2).getDob())))
                .andExpect(jsonPath("$.contacts[3].name", is(contactDTOs.get(3).getName())))
                .andExpect(jsonPath("$.contacts[3].gender", is(contactDTOs.get(3).getGender())))
                .andExpect(jsonPath("$.contacts[3].dob", is(contactDTOs.get(3).getDob())))
        ;

        verify(addressBookServiceMock).getContactsOrderedByDob(Order.DESC, 4);
        verifyNoMoreInteractions(addressBookServiceMock);
    }

}
