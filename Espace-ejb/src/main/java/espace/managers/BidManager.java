package espace.managers;

import espace.entity.Bid;
import espace.template.TemplateManager;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
@Log
public class BidManager extends TemplateManager<Bid> {

    public Bid createBid(Bid bid) {
        return super.add(bid);
    }

    public List<Bid> listLatestBidsByUser(Long userId) {
        List<Bid> result = new ArrayList<>();
        //language=JPAQL
        String hql = "select bid from Bid bid left join bid.user user left join bid.auction auction " +
                     " where user.id = :userId and bid.createdOn = (select max(subBid.createdOn)" +
                     " from Bid subBid left join subBid.auction subBidAuction where subBidAuction = auction) and not exists" +
                     " (select subsubBid from Bid subsubBid left join subsubBid.auction subsubBidAudtion where subsubBidAudtion = auction and subsubBid.createdOn > bid.createdOn)";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        result = listByFilter(hql,params);
        return result;
    }
    @Override
    protected Class getMyClass() {
        return Bid.class;
    }



}
