package com.michalsadel.ecar.adapters

import com.michalsadel.ecar.Application
import com.michalsadel.ecar.ServiceSpec
import com.michalsadel.ecar.customer.CustomerFacade
import com.michalsadel.ecar.customer.dto.CustomerDto
import com.michalsadel.ecar.customer.dto.CustomerTypeDto
import com.michalsadel.ecar.price.PriceFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.ResultActions
import org.springframework.transaction.annotation.Transactional

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = [Application])
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class CustomerControllerSpec extends MvcSpec implements ServiceSpec  {

    @Autowired
    private PriceFacade priceService

    @Autowired
    private CustomerFacade customerFacade

    def "should standard customer be charged 20EUR and VIP customer receive 10% discount"() {
        given: "there is one default 1EUR per minute price in the system"
            priceService.add(createDefaultPrice())
        and: "there is one VIP customer"
            def vip = customerFacade.add(CustomerDto.builder().customerType(CustomerTypeDto.VIP).build())
        when: "post with 20 minute charge time is made to /customer/{customerId}/charge"
            ResultActions charge = mvc.perform(
                    post("/customer/{customerId}/charge", 0)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content('{"start": "2019-01-01T00:00", "finish":"2019-01-01T00:20"}')
            )
        then: "charge is 20EUR"
            charge
                    .andExpect(status().isOk())
                    .andExpect(content().json('{"charge": 20}'))

        when: "post with 20 minute charge time is made to /customer/42/charge"
        ResultActions vipCharge = mvc.perform(
                post("/customer/{customerId}/charge", vip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{"start": "2019-01-01T00:00", "finish":"2019-01-01T00:20"}')
        )
        then: "charge is 18EUR"
            vipCharge
                .andExpect(status().isOk())
                .andExpect(content().json('{"charge": 18}'))
    }

    def "should get customers"() {
        given: "there are two customers in the system one of them is VIP"
            customerFacade.add(CustomerDto.builder().customerType(CustomerTypeDto.DEFAULT).build())
            customerFacade.add(CustomerDto.builder().customerType(CustomerTypeDto.VIP).build())
        when: "ask for customers"
            ResultActions customers = mvc.perform(
                    get("/customers"))
        then: "have two customers which one of them is VIP"
            customers
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            { 
                                "id": 1, 
                                "customerType": "DEFAULT"
                            },
                            { 
                                "id": 2, 
                                "customerType": "VIP"
                            }
                        ]
                    """))
    }

}