package com.gumtree.springconfig;

import com.gumtree.repository.AddressBookLinkedListRepository;
import com.gumtree.repository.AddressBookRepository;
import com.gumtree.service.AddressBookService;
import com.gumtree.service.AddressBookServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public AddressBookService addressBookService() {
        return new AddressBookServiceImpl(addressBookRepository());
    }

    @Bean
    public AddressBookRepository addressBookRepository() {
        return new AddressBookLinkedListRepository();
    }
}
