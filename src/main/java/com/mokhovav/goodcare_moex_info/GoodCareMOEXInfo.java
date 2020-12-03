package com.mokhovav.goodcare_moex_info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mokhovav.goodcare_moex_info"})
public class GoodCareMOEXInfo {

    private final RestRequestsService restRequestsService;

    public GoodCareMOEXInfo(RestRequestsService restRequestsService) {
        this.restRequestsService = restRequestsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(GoodCareMOEXInfo.class, args);
    }

//    @PostConstruct
//    private void run() throws GoodCareException {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//
//            StockSharesList stockSharesList = (StockSharesList) restRequestsService.getPostInJson(
//                    "https://iss.moex.com/iss/engines/stock/markets/shares/securities.json?iss.meta=off",
//                    StockSharesList.class);
//            System.out.println(mapper.writeValueAsString(stockSharesList));
//        } catch (GoodCareException e) {
//            try {
//                String answer = mapper.writeValueAsString(e.getResponse());
//                System.out.println(answer);
//            } catch (JsonProcessingException ex) {
//                ex.printStackTrace();
//            }
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }
//    }
}
