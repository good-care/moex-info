package com.mokhovav.goodcare_moex_info.entites.assetquotation;

import com.mokhovav.goodcare_moex_info.RestRequestsService;
import com.mokhovav.goodcare_moex_info.entites.AssetType;
import com.mokhovav.goodcare_moex_info.entites.assets.Asset;
import com.mokhovav.goodcare_moex_info.entites.assets.AssetService;
import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXData;
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
import java.util.List;
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

    public AssetQuotationList getStockHistory(String assetName, String date) throws GoodCareException {
        Asset asset = assetService.findBySecurityId(assetName);
        return getStockHistory(asset, date);
    }

    private AssetQuotationList getStockHistory(Asset asset, String date) throws GoodCareException {
        if (asset == null) throw new GoodCareException(this, "The asset isn't found");
        String request;
        switch (asset.getAssetType()) {
            case INDEX:
                request = MOEXRequests.getStockIndexHistory(asset.getSecurityId(), date);
                break;
            case SHARE:
                request = MOEXRequests.getStockShareHistory(asset.getSecurityId(), date);
                break;
            case BOND:
                request = MOEXRequests.getStockBondHistory(asset.getSecurityId(), date);
                break;
            default:
                throw new GoodCareException(this, "Unexpected stock type");
        }
        return getStockAssetHistory(asset, request);
    }

    private AssetQuotationList getStockAssetHistory(Asset asset, String request) throws GoodCareException {
        final AssetQuotationList assetQuotations = new AssetQuotationList();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        AssetQuotationHistoryData historyData = (AssetQuotationHistoryData) restRequestsService.getPostInJson(
                request,
                AssetQuotationHistoryData.class
        );

        List<Integer> cursor =
                Optional.ofNullable(historyData)
                        .map(AssetQuotationHistoryData::getCursor)
                        .map(MOEXData::getData)
                        .map(lists -> lists.get(0))
                        .orElse(null);

        if (cursor == null || cursor.size() != 3) throw new GoodCareException(this, "Bad history.cursor");
        int total = cursor.get(1);
        int pageSize = cursor.get(2);
        int start = 0;

        while (true) {
            Optional.ofNullable(historyData)
                    .map(AssetQuotationHistoryData::getHistory)
                    .map(MOEXData::getData)
                    .get()
                    .stream()
                    .filter(strings -> strings.size() == 3)
                    .forEach(strings -> {
                        AssetQuotation assetQuotation = new AssetQuotation();
                        try {
                            String temp = strings.get(1);
                            if (temp != null)
                                assetQuotation
                                        .setDateAndTime(
                                                new Timestamp(
                                                        dateFormat
                                                                .parse(temp)
                                                                .getTime()
                                                )
                                        );
                            assetQuotation.setMoexAsset(asset);
                            temp = strings.get(2);
                            if (temp != null)
                                assetQuotation
                                        .setQuotation(
                                                new BigDecimal(strings.get(2))
                                        );
                            assetQuotation.setQuotationType(QuotationType.DAILY);
                            assetQuotations.add(assetQuotation);
                        } catch (ParseException e) {
                            logger.error("Bad date format");
                        }
                    });
            start += pageSize;
            if (start > total) break;

            historyData = (AssetQuotationHistoryData) restRequestsService.getPostInJson(
                    request + "&start=" + start,
                    AssetQuotationHistoryData.class
            );

        }
        return assetQuotations;
    }

    public int saveQuotationListToDB(AssetQuotationList quotations) throws GoodCareException {
        int count = 0;
        Session session = sessionFactory.openSession();

        for (AssetQuotation quotation : quotations) {
            if (findByAssetQuotation(quotation, session) == 0) {
                Transaction transaction = session.beginTransaction();
                session.save(quotation);
                transaction.commit();
                count++;
            }
        }
        session.close();
        return count;
    }

    public int saveHistoryOfAllAssets(String date) throws GoodCareException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ScrollableResults itemCursor = session.createQuery("FROM Asset").scroll();
        int count = 0;
        int result = 0;
        while (itemCursor.next()) {
            Asset asset = (Asset) itemCursor.get(0);
            AssetQuotationList assetQuotations = getStockHistory(asset, date);
            for (AssetQuotation quotation : assetQuotations) {
                if (quotation.getQuotation() != null &&
                        findByAssetQuotation(quotation, session) == 0
                ) {
                    session.save(quotation);
                    result++;
                }
            }
            if (++count % 100 == 0) {
                session.flush();
                session.clear();
            }
            logger.debug("Number of assets = " + count +
                    " type: " + asset.getAssetType().name() +
                    " asset: " + asset.getSecurityId());
        }
        transaction.commit();
        session.close();
        return result;
    }

    public int updateHistoryOfAssets(String date, AssetType assetType) throws GoodCareException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ScrollableResults itemCursor = session.createQuery("FROM Asset").scroll();
        int count = 0;
        int result = 0;
        long id;
        while (itemCursor.next()) {
            Asset asset = (Asset) itemCursor.get(0);
            if (asset.getAssetType() != assetType) continue;
            AssetQuotationList assetQuotations = getStockHistory(asset, date);
            for (AssetQuotation quotation : assetQuotations) {
                AssetQuotation dbQuotations = getByAssetQuotation(quotation, session);
                if (dbQuotations != null) {
                    dbQuotations.setQuotation(quotation.getQuotation());
                    dbQuotations.setQuotationType(quotation.getQuotationType());
                    ;
                    session.update(dbQuotations);
                    result++;
                }
            }
            if (++count % 100 == 0) {
                session.flush();
                session.clear();
            }
            logger.debug("Number of assets = " + count +
                    " type: " + asset.getAssetType().name() +
                    " asset: " + asset.getSecurityId());
        }
        transaction.commit();
        session.close();
        return result;
    }

    private Long findByAssetQuotation(AssetQuotation quotation, Session session) throws GoodCareException {
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

    private AssetQuotation getByAssetQuotation(AssetQuotation quotation, Session session) throws GoodCareException {
        try {
            return (AssetQuotation) session.createQuery(
                    "from AssetQuotation where moexAsset='" + quotation.getMoexAsset().getId() + "' and dateAndTime='" + quotation.getDateAndTime() + "'"
            ).uniqueResult();
        } catch (Exception e) {
            throw new GoodCareException(this, e.getMessage());
        }
    }
}
