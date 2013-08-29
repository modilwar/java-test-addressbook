package com.gumtree.controller;

import com.gumtree.domain.Contact;
import com.gumtree.domain.Gender;
import com.gumtree.dto.ContactDTO;
import com.gumtree.repository.AddressBookRepository;
import com.gumtree.springconfig.TestConfig;
import com.gumtree.springconfig.WebAppConfig;
import com.gumtree.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, TestConfig.class})
@WebAppConfiguration
public class ContactsControllerTest {
    private static final String DATE_FORMAT = "dd/MM/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    public static final String JOHN = "John";
    public static final String MALE = "Male";
    public static final String DOB_150682 = "15/06/82";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AddressBookRepository addressBookRepositoryMock;

    @Before
    public void setup() {
        reset(addressBookRepositoryMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddNewContact() throws Exception {


        ContactDTO johnDTO = new ContactDTO(JOHN, MALE, DOB_150682);
        Contact john = createContact(johnDTO);

        when(addressBookRepositoryMock.add(john)).thenReturn(john);

        mockMvc.perform(post("/api/contact")
                .accept(TestUtil.APPLICATION_JSON_UTF8)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(johnDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(JOHN)))
                .andExpect(jsonPath("$.gender", is(notNullValue())))
                .andExpect(jsonPath("$.dob", is(DOB_150682)))
        ;

        ArgumentCaptor<Contact> captor = ArgumentCaptor.forClass(Contact.class);
        verify(addressBookRepositoryMock, times(1)).add(captor.capture());
        verifyNoMoreInteractions(addressBookRepositoryMock);

        Contact argument = captor.getValue();
        assertThat(argument.getName(), is(JOHN));
        assertThat(argument.getGender(), is(Gender.MALE));
        assertThat(argument.getDob(), is(sdf.parse(DOB_150682)));

    }

    private Contact createContact(ContactDTO contactDTO) throws ParseException {
        String name = contactDTO.getName();
        Gender gender = Gender.valueOf(contactDTO.getGender().toUpperCase());
        Date dob = sdf.parse(contactDTO.getDob());
        return new Contact(name, gender, dob);
    }
}
