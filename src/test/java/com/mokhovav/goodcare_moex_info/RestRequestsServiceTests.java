package com.mokhovav.goodcare_moex_info;

import com.mokhovav.goodcare_moex_info.entites.assetquotation.AssetQuotationHistoryData;
import com.mokhovav.goodcare_moex_info.entites.assets.AssetData;
import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXData;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXRequests;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

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
    public void getMOEXStockIndexes() {
        checkAssetRequestAnswerSize(MOEXRequests.getStockIndex(), AssetData.class);
    }

    @Test
    @Order(2)
    public void getMOEXStockShares() {
        checkAssetRequestAnswerSize(MOEXRequests.getStockShares(), AssetData.class);
    }

    @Test
    @Order(3)
    public void getMOEXStockBonds() {
        checkAssetRequestAnswerSize(MOEXRequests.getStockBonds(), AssetData.class);
    }

    @Test
    @Order(4)
    public void getMOEXStockIndexHistory() {
        checkAssetHistoryRequestAnswerSize(MOEXRequests.getStockIndexHistory("AKEUA", "2020-12-01"), AssetQuotationHistoryData.class);
    }

    @Test
    @Order(5)
    public void getMOEXStockShareHistory() {
        checkAssetHistoryRequestAnswerSize(MOEXRequests.getStockShareHistory("ABRD", "2020-12-01"), AssetQuotationHistoryData.class);
    }

    @Test
    @Order(6)
    public void getMOEXStockBondHistory() {
        checkAssetHistoryRequestAnswerSize(MOEXRequests.getStockBondHistory("RU000A101NG2", "2020-12-01"), AssetQuotationHistoryData.class);
    }

    private void checkAssetRequestAnswerSize(String request, Class c) {
        try {
            AssetData assetData = (AssetData) restRequestsService.getPostInJson(
                    request,
                    c
            );
            Assertions.assertTrue(
                    Optional.ofNullable(assetData)
                            .map(AssetData::getSecurities)
                            .map(MOEXData::getData)
                            .map(List::size)
                            .orElse(0) > 0
            );
        } catch (GoodCareException e) {
            Assertions.assertTrue(false);
            logger.error(e.getMessage());
        }
    }

    private void checkAssetHistoryRequestAnswerSize(String request, Class c) {
        try {
            AssetQuotationHistoryData historyData = (AssetQuotationHistoryData) restRequestsService.getPostInJson(
                    request,
                    c
            );
            Assertions.assertTrue(
                    Optional.ofNullable(historyData)
                            .map(AssetQuotationHistoryData::getHistory)
                            .map(MOEXData::getData)
                            .map(List::size)
                            .orElse(0) > 0
            );
            Assertions.assertTrue(
                    Optional.ofNullable(historyData)
                            .map(AssetQuotationHistoryData::getCursor)
                            .map(MOEXData::getData)
                            .map(List::size)
                            .orElse(0) > 0
            );
        } catch (GoodCareException e) {
            Assertions.assertTrue(false);
            logger.error(e.getMessage());
        }
    }

}
