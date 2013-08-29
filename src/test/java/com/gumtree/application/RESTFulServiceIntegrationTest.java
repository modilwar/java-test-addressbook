package com.gumtree.application;

import com.gumtree.domain.Contact;
import com.gumtree.dto.ContactDTO;
import com.gumtree.io.FileReader;
import com.gumtree.io.FlatFileReader;
import com.gumtree.springconfig.ApplicationConfig;
import com.gumtree.springconfig.WebAppConfig;
import com.gumtree.util.ContactParser;
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

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppConfig.class, ApplicationConfig.class})
@WebAppConfiguration
public class RESTFulServiceIntegrationTest {
    private static String FILE = "AddressBook";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private FileReader fileReader;

    private ContactParser contactParser;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        fileReader = new FlatFileReader();
        contactParser = new ContactParser();

        // Read the AddressBook file and populate the REST service with contacts
        try {
            List<String> lines = fileReader.readLines(FILE);
            for (String line : lines) {
                ContactUtils contactUtils = new ContactUtils();
                Contact contact = contactParser.parse(line);
                ContactDTO contactDTO = contactUtils.createContactDTO(contact);
                mockMvc.perform(post("/api/contact")
                        .accept(TestUtil.APPLICATION_JSON_UTF8)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(contactDTO))
                );
            }
        } catch (IOException e) {
            System.out.print("ERROR: could not open AddressBook file");
        }
    }

    @Test
    public void thereShouldBeThreeMaleContactsInTheAddressBook() throws Exception {
        mockMvc.perform(get("/api/contact/count?gender=male"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.count.", is(3)))
        ;
    }

    @Test
    public void theOldestContactShouldBeWesJackson() throws Exception {
        mockMvc.perform(get("/api/contact?order_by=age&order_how=desc&limit=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.count.", is(1)))
                .andExpect(jsonPath("$.contacts.", hasSize(1)))
                .andExpect(jsonPath("$.contacts[0].name", is("Wes Jackson")))
                .andExpect(jsonPath("$.contacts[0].gender", is("Male")))
                .andExpect(jsonPath("$.contacts[0].dob", is("14/08/74")))
        ;
    }



}
