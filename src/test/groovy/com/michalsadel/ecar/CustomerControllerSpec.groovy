package com.michalsadel.ecar

import com.michalsadel.ecar.domain.ChargeService
import com.michalsadel.ecar.domain.ServiceSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = [Application])
@DirtiesContext
class CustomerControllerSpec extends MvcSpec implements ServiceSpec  {

    @Autowired
    private ChargeService chargeService

    def "should standard customer be charged 20EUR and VIP customer receive 10% discount"() {
        given: "there is one default 1EUR per minute price in the system"
            chargeService.add(createDefaultPrice())
        when: "post with 20 minute charge time is made to /customer/{customerId}/charge"
            ResultActions charge = mvc.perform(
                    post("/customer/{customerId}/charge", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content('{"start": "2019-01-01T00:00", "finish":"2019-01-01T00:20"}')
            )
        then: "charge is 20EUR"
            charge
                    .andExpect(status().isOk())
                    .andExpect(content().json('{"charge": 20}'))

        when: "post with 20 minute charge time is made to /customer/42/charge"
        ResultActions vipCharge = mvc.perform(
                post("/customer/{customerId}/charge", 42)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{"start": "2019-01-01T00:00", "finish":"2019-01-01T00:20"}')
        )
        then: "charge is 18EUR"
            vipCharge
                .andExpect(status().isOk())
                .andExpect(content().json('{"charge": 18}'))
    }
}