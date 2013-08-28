package com.gumtree.springconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy(@ContextConfiguration(classes = WebAppConfig.class))
public class WebAppConfigTest {

    @Autowired
    private InternalResourceViewResolver viewResolver;

    @Test
    public void testViewResolver() {
        assertThat(viewResolver, is(notNullValue()));
    }
}

