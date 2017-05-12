package espace.services;

import espace.managers.*;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
@Log
public class StatisticService {

    @Inject
    private ItemManager itemManager;

    @Inject
    private AuctionManager auctionManager;

    @Inject
    private BidManager bidManager;

    @Inject
    private UserManager userManager;

    @Inject
    private NotificationManager notificationManager;


    public int countOfRegisteredUsers() {
        return userManager.countAll();
    }

    public int countActiveAuctions() {
        return auctionManager.countAuctionsByFilter(false, null);
    }

    public int countClosedAuctionsWithWinner() {
        return auctionManager.countAuctionsByFilter(true, true);
    }

    public int countClosedAuctionsWithoutWinner() {
        return auctionManager.countAuctionsByFilter(true, false);
    }

}
