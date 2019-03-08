package com.michalsadel.ecar

import com.michalsadel.ecar.domain.CustomerEntryPoint
import com.michalsadel.ecar.domain.PriceEntryPoint
import com.michalsadel.ecar.domain.ServiceSpec
import com.michalsadel.ecar.dto.CustomerDto
import com.michalsadel.ecar.dto.CustomerTypeDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.ResultActions
import org.springframework.transaction.annotation.Transactional

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = [Application])
@DirtiesContext
@Transactional
@Rollback
class CustomerControllerSpec extends MvcSpec implements ServiceSpec  {

    @Autowired
    private PriceEntryPoint priceService

    @Autowired
    private CustomerEntryPoint customerService

    def "should standard customer be charged 20EUR and VIP customer receive 10% discount"() {
        given: "there is one default 1EUR per minute price in the system"
            priceService.add(createDefaultPrice())
        and: "there is one VIP customer"
            def vip = customerService.add(CustomerDto.builder().customerType(CustomerTypeDto.VIP).build())
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
}