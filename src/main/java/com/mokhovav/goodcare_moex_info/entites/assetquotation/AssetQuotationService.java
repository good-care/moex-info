package com.mokhovav.goodcare_moex_info.entites.assetquotation;

import com.mokhovav.goodcare_moex_info.RestRequestsService;
import com.mokhovav.goodcare_moex_info.entites.QuotationType;
import com.mokhovav.goodcare_moex_info.entites.assets.Asset;
import com.mokhovav.goodcare_moex_info.entites.assets.AssetService;
import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXRequests;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class AssetQuotationService {

    private final RestRequestsService restRequestsService;
    private final AssetService assetService;
    private final SessionFactory sessionFactory;
    private final Logger logger;

    public AssetQuotationService(
            RestRequestsService restRequestsService,
            AssetService assetService,
            SessionFactory sessionFactory,
            Logger logger) {
        this.restRequestsService = restRequestsService;
        this.assetService = assetService;
        this.sessionFactory = sessionFactory;
        this.logger = logger;
    }

    public AssetQuotationList getStockIndexHistory(String assetName, String data) throws GoodCareException {
        Asset asset = assetService.findBySecurityId(assetName);
        return getStockIndexHistory(asset, data);
    }

    public AssetQuotationList getStockIndexHistory(Asset asset, String data) throws GoodCareException {
        if (asset == null) throw new GoodCareException(this, "The asset isn't found");
        String request = MOEXRequests.getStockIndexHistory(data, asset.getSecurityId());
        return getStockAssetHistory(asset, request);
    }

    public int saveQuotationListToDB(AssetQuotationList quotations) {
        int count = 0;
        Session session = sessionFactory.openSession();
        for (AssetQuotation quotation : quotations) {
            try {
                if (findByAssetAndDate(quotation, session) == 0) {
                    Transaction transaction = session.beginTransaction();
                    session.save(quotation);
                    transaction.commit();
                    count++;
                }
            } catch (GoodCareException e) {
                logger.error(e.getMessage());
            }
        }
        session.close();
        return count;
    }

    public int updateHistoryOfAllAssets(String data) throws GoodCareException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ScrollableResults itemCursor = session.createQuery("FROM Asset").scroll();
        int count = 0;
        int result = 0;
        while (itemCursor.next()){
            Asset asset = (Asset)itemCursor.get(0);
            AssetQuotationList assetQuotations = getStockIndexHistory(asset,data);
            for (AssetQuotation quotation : assetQuotations) {
                try {
                    if (findByAssetAndDate(quotation, session) == 0) {

                        session.save(quotation);

                        result++;
                    }
                } catch (GoodCareException e) {
                    logger.error(e.getMessage());
                }
            }
            if ( ++count % 100 == 0 ) {
                session.flush();
                session.clear();
            }
            System.out.println(count);
            if (count == 1000) break;
        }
        transaction.commit();
        session.close();
        return result;
    }

    private Long findByAssetAndDate(AssetQuotation quotation, Session session) throws GoodCareException {
        try {
            AssetQuotation res = (AssetQuotation) session.createQuery(
                    "from AssetQuotation where moexAsset='" + quotation.getMoexAsset().getId() + "' and dateAndTime='" + quotation.getDateAndTime() + "'"
            ).uniqueResult();
            if (res != null) return res.getId();
        } catch (Exception e) {
            throw new GoodCareException(this, e.getMessage());
        }
        return 0L;
    }

    private AssetQuotationList getStockAssetHistory(Asset asset, String request) throws GoodCareException {
        AssetQuotationList assetQuotations = new AssetQuotationList();
        AssetQuotation assetQuotation;
        long time;
        String[][] data;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Optional<HistoryData> response =
                Optional.ofNullable(
                        (HistoryData) restRequestsService.getPostInJson(request, HistoryData.class)
                );
        int total = response.get().getCursor().getData()[0][1];
        int pageSize = response.get().getCursor().getData()[0][2];
        int start = 0;
        while (true) {
            data = response.get().getHistory().getData();
            for (String[] datum : data) {
                if (datum.length != 3) continue;
                assetQuotation = new AssetQuotation();
                try {
                    assetQuotation.setDateAndTime(
                            new Timestamp(
                                    dateFormat
                                            .parse(datum[1])
                                            .getTime()
                            )
                    );
                    assetQuotation.setMoexAsset(asset);
                    assetQuotation.setQuotation(
                            new BigDecimal(datum[2])
                    );
                    assetQuotation.setQuotationType(QuotationType.DAILY);
                } catch (ParseException e) {
                    throw new GoodCareException(this, e.getMessage());
                }
                assetQuotations.add(assetQuotation);
            }
            start += pageSize;
            if (start > total) break;
            response =
                    Optional.ofNullable(
                            (HistoryData) restRequestsService.getPostInJson(request + "&start=" + start, HistoryData.class)
                    );
        }
        return assetQuotations;
    }


}
