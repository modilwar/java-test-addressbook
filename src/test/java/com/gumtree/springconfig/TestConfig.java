package com.gumtree.springconfig;

import com.gumtree.repository.AddressBookRepository;
import com.gumtree.service.AddressBookService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public AddressBookService addressBookService() {
        return Mockito.mock(AddressBookService.class);
    }

    @Bean
    public AddressBookRepository addressBookRepository() {
        return Mockito.mock(AddressBookRepository.class);
    }
}
