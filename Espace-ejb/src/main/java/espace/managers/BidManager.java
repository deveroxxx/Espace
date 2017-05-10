package espace.managers;

import espace.entity.Bid;
import espace.template.TemplateManager;
import espace.utils.Log;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
@Log
public class BidManager extends TemplateManager<Bid> {

    Logger logger = LogManager.getLogger(BidManager.class);

    public Bid createBid(Bid bid) {
        return super.add(bid);
    }

    /**
     * list the latest bids on each auction by user,
     * latesd bid is decided on the bid creation time
     * @param userId the filtered user
     * @return a Bid list
     */
    public List<Bid> listLatestBidsByUser(Long userId, boolean auctionClosed) {
        List<Bid> result = new ArrayList<>();
        //language=JPAQL
        String hql = "select bid from Bid bid left join bid.user user left join bid.auction auction" +
                " where auction.closed = :auctionClosed and bid.createdOn = (select max(subBid.createdOn) from Bid subBid where subBid.auction = bid.auction and subBid.user.id = :userId group by subBid.auction)";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("auctionClosed", auctionClosed);
        logger.debug("Running listLatestBidsByUser-querry: " + hql);
        result = listByFilter(hql, params);
        return result;
    }

    @Override
    protected Class getMyClass() {
        return Bid.class;
    }

}
