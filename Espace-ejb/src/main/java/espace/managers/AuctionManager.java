package espace.managers;

import espace.entity.Auction;
import espace.entity.User;
import espace.template.TemplateManager;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
@Log
public class AuctionManager extends TemplateManager {


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



    @Override
    protected Class getMyClass() {
        return Auction.class;
    }
}
