package com.gumtree.springconfig;

import com.gumtree.repository.AddressBookRepository;
import com.gumtree.service.AddressBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class ApplicationConfigTest {

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    public void testAddressBookService() {
        assertThat(addressBookService, is(notNullValue()));
    }

    @Test
    public void testAddressBookRepository() {
        assertThat(addressBookRepository, is(notNullValue()));
    }

}
