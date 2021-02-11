package com.mokhovav.goodcare_moex_info.rabbitMQ;

import com.mokhovav.goodcare_moex_info.entites.AssetType;
import com.mokhovav.goodcare_moex_info.entites.assetquotation.AssetQuotationService;
import com.mokhovav.goodcare_moex_info.entites.assets.AssetList;
import com.mokhovav.goodcare_moex_info.entites.assets.AssetService;
import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AssetService assetService;
    @Autowired
    private AssetQuotationService assetQuotationService;
    @Autowired
    private Logger logger;


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public void send(GoodCareException message) {
        rabbitTemplate.convertAndSend("MOEXInfoToScheduler", message);
    }

    @RabbitListener(queues = "SchedulerToMOEXInfo")
    public void listen(RMQMessage in) {
        try {
            switch (in.getType()) {
                case UPDATE_SECURITIES:
                    securitiesUpdate();
                    break;
                case UPDATE_QUOTATIONS:
                    quotationHistoryUpdate(in.getArgs()[0]);
                    break;
                default:
                    logger.info("Unknown command");
                    send(new GoodCareException("Unknown command"));
            }
        }catch (GoodCareException exception){
            send(exception);
        }
    }

    private void securitiesUpdate() throws GoodCareException {
        AssetList result = new AssetList();
        AssetList temp;
        Date beginTime = new Date();
        temp = assetService.getIndexList();
        if (temp != null) {
            logger.info("Index list size: " + temp.size());
            result.addAll(temp);
        }
        temp = assetService.getShareList();
        if (temp != null) {
            logger.info("Shares list size: " + temp.size());
            result.addAll(temp);
        }
        temp = assetService.getBondList();
        if (temp != null) {
            logger.info("Bonds list size: " + temp.size());
            result.addAll(temp);
        }

        try {
            logger.debug("The Number of records was updated: " + assetService.saveAssertListToDB(result));
        } catch (GoodCareException e) {
            logger.debug(e.getMessage());
        }

        logger.debug("Spent time (s): " + ((double) (new Date().getTime() - beginTime.getTime())) / 1000);
    }

    private void quotationHistoryUpdate(String from) throws GoodCareException {
        assetQuotationService.saveHistoryOfAllAssets(from);//"2020-09-01");
    }

    private  void indexQuotationsHistoryUpdate(String from) throws GoodCareException {
            logger.debug("updateIndexQuotationsHistoryToDataBase is tested");
            Date beginTime = new Date();
            int count = assetQuotationService.updateHistoryOfAssets(from, AssetType.INDEX);
            logger.debug("Count = " + count + "; Spent time (s): " + ((double) (new Date().getTime() - beginTime.getTime())) / 1000);
    }

    public void bondQuotationsHistoryUpdate(String from) throws GoodCareException {

            logger.debug("updateBondQuotationsHistoryToDataBase is tested");
            Date beginTime = new Date();
            int count = assetQuotationService.updateHistoryOfAssets(from, AssetType.BOND);
            logger.debug("Count = " + count + "; Spent time (s): " + ((double) (new Date().getTime() - beginTime.getTime())) / 1000);
    }

    public void shareQuotationsHistoryUpdate(String from) throws GoodCareException {

            logger.debug("updateShareQuotationsHistoryToDataBase is tested");
            Date beginTime = new Date();
            int count = assetQuotationService.updateHistoryOfAssets(from, AssetType.SHARE);
            logger.debug("Count = " + count + "; Spent time (s): " + ((double) (new Date().getTime() - beginTime.getTime())) / 1000);

    }

}
