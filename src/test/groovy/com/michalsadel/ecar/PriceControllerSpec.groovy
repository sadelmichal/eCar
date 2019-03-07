package com.michalsadel.ecar

import com.michalsadel.ecar.domain.ChargeService
import com.michalsadel.ecar.domain.ServiceSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = [Application])
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PriceControllerSpec extends MvcSpec implements ServiceSpec {
    @Autowired
    private ChargeService chargeService

    def "should default price be accepted and creation of another default price rejected"() {
        when: "post with default price of 1EUR per minute is made to /price"
            ResultActions firstPrice = mvc.perform(
                    post("/price")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content('{"perMinute": 1}'))
        then: "price is accepted"
            firstPrice.andExpect(status().isAccepted())

        when: "post with another default price of 2EUR per minute is made to /price"
        ResultActions secondPrice = mvc.perform(
                post("/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{"perMinute": 2}'))
        then: "price is rejected"
            secondPrice
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath('$.error', containsString("Price provided overlaps price defined in the system between")))
    }
    def "should not be allowed to partially define time range in price definition"(){
        when: "another price definition is added without finishesAt"
            ResultActions price = mvc.perform(
                post("/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content('{ "perMinute": 1, "effectedIn": {"startsAt": "05:00"} }'))
        then:
            price.andExpect(status().isBadRequest())

    }

}