package com.michalsadel.ecar.adapters

import com.michalsadel.ecar.Application
import com.michalsadel.ecar.ServiceSpec
import com.michalsadel.ecar.customer.CustomerFacade
import com.michalsadel.ecar.price.PriceFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.ResultActions
import org.springframework.transaction.annotation.Transactional

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(classes = [Application])
@Transactional
@Rollback
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PriceControllerSpec extends MvcSpec implements ServiceSpec {
    @Autowired
    private CustomerFacade chargeService
    @Autowired
    private PriceFacade priceFacade

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
                    .andExpect(jsonPath('$.error', containsString("Price provided overlaps price defined in the system")))
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

    def "should get prices"(){
        given: "there are one three prices in the system"
            priceFacade.add(createDefaultPrice())
            priceFacade.add(createPrice("21:30", "22:18", 2.0))
            priceFacade.add(createPrice("23:30", "23:35", 3.0))
        when: "ask for prices"
            ResultActions prices = mvc.perform(
                    get("/prices"))
        then: "have three prices"
            prices
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                        [
                            { 
                                "perMinute": 1.0, 
                                "effectedIn": {
                                                "startsAt": "00:00",
                                                "finishesAt": "23:59"
                                              },
                                "defaultInSystem": true
                            },
                            { 
                                "perMinute": 2.0, 
                                "effectedIn": {
                                                "startsAt": "21:30",
                                                "finishesAt": "22:18"
                                              },
                                "defaultInSystem": false
                            },
                            { 
                                "perMinute": 3.0, 
                                "effectedIn": {
                                                "startsAt": "23:30",
                                                "finishesAt": "23:35"
                                              },
                                "defaultInSystem": false
                            }
                        ]
                    """))
    }



}