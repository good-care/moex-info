package com.mokhovav.goodcare_moex_info.entites.assets;

import com.mokhovav.goodcare_moex_info.RestRequestsService;
import com.mokhovav.goodcare_moex_info.entites.AssetType;
import com.mokhovav.goodcare_moex_info.entites.Currency;
import com.mokhovav.goodcare_moex_info.exceptions.GoodCareException;
import com.mokhovav.goodcare_moex_info.logging.Logger;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXData;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXRequests;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        return getAssetList(MOEXRequests.getStockIndex(), AssetType.INDEX);
    }

    public AssetList getShareList() throws GoodCareException {
        return getAssetList(MOEXRequests.getStockShares(), AssetType.SHARE);
    }

    public AssetList getBondList() throws GoodCareException {
        return getAssetList(MOEXRequests.getStockBonds(), AssetType.BOND);
    }

    private AssetList getAssetList(String request, AssetType assetType) throws GoodCareException {
        AssetList assetList = new AssetList();

        AssetData assetData = (AssetData) restRequestsService.getPostInJson(
                request,
                AssetData.class
        );

        Optional.ofNullable(assetData)
                .map(AssetData::getSecurities)
                .map(MOEXData::getData)
                .get()
                .stream()
                .forEach(strings -> addToAssertList(strings, assetList, assetType));

        return assetList;
    }

    private void addToAssertList(List<String> str, AssetList indexList, AssetType assetType) {
        if (str.size() == 3) {
            Asset asset = new Asset();
            asset.setSecurityId(str.get(0));
            asset.setName(str.get(1));
            if (str.get(2) != null)
                switch (str.get(2)) {
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
    }

    public int saveAssertListToDB(AssetList assets) throws GoodCareException {
        int count = 0;
        Session session = sessionFactory.openSession();
        for (Asset asset : assets) {
            if (findBySecurityId(session, asset.getSecurityId()) == 0) {
                Transaction transaction = session.beginTransaction();
                session.save(asset);
                transaction.commit();
                count++;
            }
        }
        session.close();
        return count;
    }

    public void updateAssertListInDB(ArrayList objectList) throws GoodCareException {
        Session session = sessionFactory.openSession();
        for (Asset o : (AssetList) objectList) {
            Transaction transaction = session.beginTransaction();
            o.setId(findBySecurityId(session, o.getSecurityId()));
            session.merge(o);
            transaction.commit();
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
