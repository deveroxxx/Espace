package espace.managers;

import espace.data.AuctionData;
import espace.data.AuctionFilters;
import espace.entity.Auction;
import espace.entity.User;
import espace.enums.AuctionSortField;
import espace.template.TemplateManager;
import espace.utils.Log;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
@Log
public class AuctionManager extends TemplateManager<Auction> {

    Logger logger = LogManager.getLogger(AuctionManager.class.getCanonicalName());


    public List<Auction> listAuctionsByUser(User user, boolean closed) {
        //language=JPAQL
        String hql = "select auction from Auction auction " +
                "where auction.owner = :user and auction.closed = :closed and auction.deleted = false";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        params.put("closed", closed);
        return listByFilter(hql, params);
    }

    public List<Auction> listAuctionByUserBids(User user) {
        //language=JPAQL
        String hql = "select distinct bid.auction from Bid bid " +
                "where bid.user = :user and bid.auction.closed = false and bid.auction.deleted = false " +
                "and bid.deleted = false";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        return listByFilter(hql, params);
    }

    public Auction getById(Long id) {
        return super.select(id);
    }

    public List<Auction> listNotClosedExpiredAuctions() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //language=JPAQL
        String hql = "select auction from Auction auction where auction.closed = false and auction.expirationDate < :currentTime";
        params.put("currentTime", Calendar.getInstance().getTime());
        return listByFilter(hql, params);
    }

    public AuctionData refreshListData(AuctionFilters filters, AuctionSortField sortField, int start, int end) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //language=JPAQL
        StringBuilder hql = new StringBuilder();
        StringBuilder criteria = new StringBuilder();
        StringBuilder order = new StringBuilder();
        hql.append("select auction from Auction auction left join auction.item item left join auction.topBider topBider");
        //feltételek felépítése
        criteria.append(" where '1'='1'");
        if (filters.getHeader() != null && !filters.getHeader().isEmpty()) {
            criteria.append(" and auction.header like :header");
            params.put("header", "%"+filters.getHeader()+"%");
        }
        if (filters.getUserName() != null && !filters.getUserName().isEmpty()) {
            criteria.append(" and auction.owner.userName like :owner");
            params.put("owner", "%"+filters.getUserName()+"%");
        }
        if (filters.getItemName() != null && !filters.getItemName().isEmpty()) {
            criteria.append(" and item.name like :itemName");
            params.put("itemName", "%"+filters.getItemName()+"%");
        }
        if (filters.getDescription() != null && !filters.getDescription().isEmpty()) {
            criteria.append(" and auction.description like :description");
            params.put("description", "%"+filters.getDescription()+"%");
        }
        if (filters.getMaxPrice() != null) {
            criteria.append(" and ((topBider is null and auction.minimumBid <= :maxPrice) or" +
                            " (topBider is not null and topBider.bid <= :maxPrice))");
            params.put("maxPrice", filters.getMaxPrice());
        }
        if (filters.getMinPrice() != null) {
            criteria.append(" and ((topBider is null and auction.minimumBid >= :minPrice) or" +
                    " (topBider is not null and topBider.bid >= :minPrice))");
            params.put("minPrice", filters.getMinPrice());
        }

        //TODO: meddig jelenjenek meg a lezártak vagy ilyesmi.

        //rendezés
        if (sortField != null) {
            order.append(" order by");
            switch (sortField) {
                case PRICE_ASC:
                    order.append(" case when exists(select bid from Bid bid where bid.id = topBider.id) then (select bid.bid from Bid bid where bid.id = topBider.id) else auction.minimumBid end asc");
                    break;
                case PRICE_DESC:
                    order.append(" case when exists(select bid from Bid bid where bid.id = topBider.id) then (select bid.bid from Bid bid where bid.id = topBider.id) else auction.minimumBid end desc");
                    break;
                case CLOSE_DATE_ASC:
                    order.append(" auction.expirationDate asc");
                    break;
                case CLOSE_DATE_DESC:
                    order.append(" auction.expirationDate desc");
                    break;
                case NAME_ASC:
                    order.append(" item.name desc");
                    break;
                case NAME_DESC:
                    order.append(" item.name desc");
                    break;
                case NEWEST_FIRST:
                    order.append(" auction.createdOn desc");
                    break;
                case NON_SORTED:
                    order.append(" 1");
                    break;
            }
        }
        AuctionData data = new AuctionData();

        String querryString = hql.toString() + criteria.toString() + order.toString();
        String countString = "select count(auction) from Auction auction left join auction.item item left join auction.topBider topBider" + criteria.toString();

        logger.debug("Running lazy querry: " + querryString);

        Long count = getCountResultByFilter(countString,params);
        data.setCount(count);

        List<Auction> resultList = listByFilter(querryString, params, end - start, start);
        data.setResult(resultList);

        data.setTotalCount(getTotalResultCount());
        return data;
    }

    public long getTotalResultCount() {
        //language=JPAQL
        String hql = "select count(auction) from Auction auction";
        Query query = super.getEntityManager().createQuery(hql);
        return (long) query.getSingleResult();
    }

    @Override
    protected Class getMyClass() {
        return Auction.class;
    }
}
