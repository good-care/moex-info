package com.mokhovav.goodcare_moex_info;

import com.mokhovav.goodcare_moex_info.entites.assetquotation.AssetQuotation;
import com.mokhovav.goodcare_moex_info.entites.assetquotation.AssetQuotationList;
import com.mokhovav.goodcare_moex_info.entites.assetquotation.AssetQuotationService;
import com.mokhovav.goodcare_moex_info.entites.assets.AssetList;
import com.mokhovav.goodcare_moex_info.entites.assets.AssetService;
import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@ContextConfiguration(classes = {GoodCareMOEXInfo.class})
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class EntitiesServicesTests {
    @Autowired
    private Logger logger;
    @Autowired
    private AssetService assetService;
    @Autowired
    private AssetQuotationService assetQuotationService;

    @Test
    @Order(1)
    public void addSecuritiesToDataBase() {
        AssetList result = new AssetList();
        AssetList temp;
        Date beginDate = new Date();
        temp = getIndexList();
        logger.info("Index list size: " + temp.size());
        result.addAll(temp);
        temp = getShareList();
        logger.info("Shares list size: " + temp.size());
        result.addAll(temp);
        temp = getBondList();
        logger.info("Bonds list size: " + temp.size());
        result.addAll(temp);
        logger.info("The Number of records was updated: " + assetService.saveAssertListToDB(result));
        logger.info("Spent time (s): " + ((double) (new Date().getTime() - beginDate.getTime())) / 1000);
    }

    @Test
    @Order(2)
    public void addQuotationsToDataBase() {
        try {
            Date beginDate = new Date();
            AssetQuotationList assetQuotations = assetQuotationService.getStockIndexHistory("AKEUA", "2020-09-01");
            assetQuotationService.saveQuotationListToDB(assetQuotations);
            logger.info("Spent time (s): " + ((double) (new Date().getTime() - beginDate.getTime())) / 1000);
        } catch (GoodCareException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void addQuotationsHistoryToDataBase() {
        try {
            Date beginDate = new Date();
            int count = assetQuotationService.updateHistoryOfAllAssets("2020-09-01");
            logger.info("Count = " + count + "; Spent time (s): " + ((double) (new Date().getTime() - beginDate.getTime())) / 1000);
        } catch (GoodCareException e) {
            logger.error(e.getMessage());
        }
    }


    private AssetList getIndexList() {
        try {
            return assetService.getIndexList();
//            logger.info("Index list size: " + assetService.getIndexList().size());
        } catch (GoodCareException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private AssetList getShareList() {
        try {
            return assetService.getShareList();
//            logger.info("Shares list size: " + assetService.getShareList().size());
        } catch (GoodCareException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private AssetList getBondList() {
        try {
            return assetService.getBondList();
//            logger.info("Bonds list size: " + assetService.getBondList().size());
        } catch (GoodCareException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
