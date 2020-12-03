package com.mokhovav.goodcare_moex_info;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {GoodCareMOEXInfo.class})
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GoodCareMOEXInfoTests {

}
