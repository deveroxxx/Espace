package espace.services;

import espace.entity.*;
import espace.exceptions.BusinessException;
import espace.exceptions.InvalidBidException;
import espace.managers.AuctionManager;
import espace.managers.BidManager;
import espace.managers.ItemManager;
import espace.managers.NotificationManager;
import espace.utils.Log;
import org.apache.log4j.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;

@Stateless
@LocalBean
@Log
public class AuctionService {

    private static final Logger logger = Logger.getLogger(AuctionService.class);

    @Inject
    private ItemManager itemManager;

    @Inject
    private AuctionManager auctionManager;

    @Inject
    private BidManager bidManager;

    @Inject
    private NotificationManager notificationManager;


    public Auction createAuction(Auction auction, Long itemId, User user) throws BusinessException {
        Item item = itemManager.getItemById(itemId);
        if (item.getAuction() != null) {
            throw new BusinessException("Item has already been assigned to an auction!");
        }
        if (auction.getStartDate() == null) {
            auction.setStartDate(Calendar.getInstance().getTime());
        }

        if (auction.getExpirationDate().before(auction.getStartDate())) {
            throw new BusinessException("StartDate cannot be after ExpireDate!");
        }

        auction.setItem(item);
        auction.setOwner(user);
        Auction result = auctionManager.add(auction);
        item.setAuction(result);
        return result;
    }

    public void bidOnAuction(long auctionId, double bidValue, User user) throws InvalidBidException {
        Auction auction = auctionManager.selectForUpdate(auctionId);
        //lejárt az aukció
        if (auction.getExpirationDate().before(Calendar.getInstance().getTime()) || auction.getClosed()) {
            throw new InvalidBidException("This auction is already closed!");
        }
        if (auction.getStartDate().after(Calendar.getInstance().getTime())) {
            throw new InvalidBidException("This auction is not started yet!");
        }

        if (user == null) {
            throw new InvalidBidException("Please log in for bidding");
        }
        if (user.equals(auction.getOwner())) {
            throw new InvalidBidException("You cannot bid on your own auction");
        }
        // ha mi vagyunk az első licitálók és megfelelő a licit VAGY van licit de fölémentünk
        if ((auction.getTopBider() == null && auction.getMinimumBid() <= bidValue) ||
                (auction.getTopBider() != null && auction.getTopBider().getBid() < bidValue)) {
            Bid bid = new Bid();
            bid.setAuction(auction);
            bid.setUser(user);
            bid.setBid(bidValue);
            Bid resultBid = bidManager.createBid(bid);
            auction.setTopBider(resultBid);
        } else {
            throw new InvalidBidException("You cannot bid under the currend bid or the minimum price!");
        }
    }

    public void closeExpiredAuctions() {
        List<Auction> auctionList = auctionManager.listNotClosedExpiredAuctions();
        for (Auction auction : auctionList) {
            auction.setClosed(true);
            generateNotificationFromExpiredAuction(auction);
        }
    }

    private void generateNotificationFromExpiredAuction(Auction auction) {
        String sender = "ESPACE noreply";
        //értékesítőnek szóló értesítés
        Notification notificationForOwner = new Notification();
        notificationForOwner.setSender(sender);
        notificationForOwner.setTitle("Auction Expired");
        notificationForOwner.setRecipient(auction.getOwner());
        if (auction.getTopBider() != null) {
            notificationForOwner.setContent("Your auction expired (id: " + auction.getId() + "! The winner is: "
                                             + auction.getTopBider().getUser().getUserName());
        } else {
            notificationForOwner.setContent("Your auction expired without bids! id: " + auction.getId());
        }
        notificationManager.createNotification(notificationForOwner);

        //nyertes felhasználónak szókó értesítése
        if (auction.getTopBider() != null) {
            Notification notification = new Notification();
            notification.setSender(sender);
            notification.setTitle("Auction Won");
            notification.setRecipient(auction.getTopBider().getUser());
            notification.setContent("You have won this auction: " + auction.getId() + ", please contact with the seller!");
            notificationManager.createNotification(notification);
            logger.info("Auction: " + auction.getId() + " closed with winner: " + auction.getTopBider().getUser().getUserName());
        } else {
            logger.info("Auction: " + auction.getId() + " closed without bidder!");
        }
    }
}
