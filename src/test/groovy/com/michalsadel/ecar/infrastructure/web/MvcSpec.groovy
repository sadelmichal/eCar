package com.michalsadel.ecar.infrastructure.web

import com.michalsadel.ecar.ServiceSpec
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

abstract class MvcSpec extends Specification implements ServiceSpec {
    @Autowired
    private WebApplicationContext webApplicationContext

    protected MockMvc mvc

    @Before
    void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }
}
