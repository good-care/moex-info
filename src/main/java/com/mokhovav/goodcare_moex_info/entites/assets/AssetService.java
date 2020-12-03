package com.mokhovav.goodcare_moex_info.entites.assets;

import com.mokhovav.goodcare_moex_info.RestRequestsService;
import com.mokhovav.goodcare_moex_info.entites.AssetType;
import com.mokhovav.goodcare_moex_info.entites.Currency;
import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXRequests;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AssetService {

    private final RestRequestsService restRequestsService;
    private final SessionFactory sessionFactory;
    private final Logger logger;

    public AssetService(RestRequestsService restRequestsService, SessionFactory sessionFactory, Logger logger) {
        this.restRequestsService = restRequestsService;
        this.sessionFactory = sessionFactory;
        this.logger = logger;
    }

    public AssetList getIndexList() throws GoodCareException {
        return getAssetList(AssetType.INDEX, MOEXRequests.getStockIndex());
    }

    public AssetList getShareList() throws GoodCareException {
        return getAssetList(AssetType.SHARE, MOEXRequests.getStockShares());
    }

    public AssetList getBondList() throws GoodCareException {
        return getAssetList(AssetType.BOND, MOEXRequests.getStockBonds());
    }

    private AssetList getAssetList(AssetType assetType, String request) throws GoodCareException {
        AssetList assetList = new AssetList();
        Optional<SecurityData> response =
                Optional.ofNullable(
                        (SecurityData) restRequestsService.getPostInJson(request, SecurityData.class)
                );
        response
                .map(r -> r.getSecurities().getData())
                .map(Arrays::stream)
                .map(stream -> {
                    stream.forEach(str -> addToAssertList(str, assetList, assetType));
                    return null;
                });
        return assetList;
    }

    private void addToAssertList(String[] str, AssetList indexList, AssetType assetType) {
        Asset asset = new Asset();
        asset.setSecurityId(str[0]);
        asset.setName(str[1]);
        if (str[2] != null)
            switch (str[2]) {
                case "RUB":
                case "SUR":
                    asset.setCurrency(Currency.RUB);
                    break;
                case "USD":
                    asset.setCurrency(Currency.USD);
                    break;
                case "EUR":
                    asset.setCurrency(Currency.EUR);
                    break;
            }
        asset.setTrade(true);
        asset.setAssetType(assetType);
        indexList.add(asset);
    }

    public int saveAssertListToDB(AssetList assets) {
        int count = 0;
        Session session = sessionFactory.openSession();
        for (Asset asset : assets) {
            try {
                if (findBySecurityId(session, asset.getSecurityId()) == 0) {
                    Transaction transaction = session.beginTransaction();
                    session.save(asset);
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

    public void updateAssertListInDB(ArrayList objectList) {
        Session session = sessionFactory.openSession();
        for (Asset o : (AssetList) objectList) {
            try {
                Transaction transaction = session.beginTransaction();
                o.setId(findBySecurityId(session, o.getSecurityId()));
                session.merge(o);
                transaction.commit();
            } catch (GoodCareException e) {
                logger.error(e.getMessage());
            }
        }
        session.close();
    }

    private Long findBySecurityId(Session session, String securityId) throws GoodCareException {
        Asset asset = null;
        try {
            asset = (Asset) session.createQuery(
                    "from Asset where securityId='" + securityId + "'"
            ).uniqueResult();
            if (asset != null) return asset.getId();
        } catch (Exception e) {
            throw new GoodCareException(this, e.getMessage());
        }
        return 0L;
    }

    public Asset findBySecurityId(String securityId) throws GoodCareException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Asset asset = (Asset) session.createQuery(
                    "from Asset where securityId='" + securityId + "'"
            ).uniqueResult();
            session.close();
            return asset;
        } catch (Exception e) {
            if (session != null) session.close();
            throw new GoodCareException(this, e.getMessage());
        }
    }
}
