package com.mokhovav.goodcare_moex_info;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mokhovav.goodcare_moex_info.entites.assets.SecurityData;
import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {GoodCareMOEXInfo.class})
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RestRequestsServiceTests {

    @Autowired
    private Logger logger;
    @Autowired
    private RestRequestsService restRequestsService;

    @Before
    public void contextLoads() {
        logger.info("Start tests");
    }

    @Test
    @Order(1)
    public void getMOEXStockSharesSecurities(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            SecurityData stockSharesList = (SecurityData) restRequestsService.getPostInJson(
                    "https://iss.moex.com/iss/engines/stock/markets/shares/securities.json?iss.meta=off",
                    SecurityData.class);
            logger.info(mapper.writeValueAsString(stockSharesList));
        } catch (GoodCareException e) {
            try {
                String answer = mapper.writeValueAsString(e.getResponse());
                logger.info(answer);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }
}
