package espace.views;

import espace.annotations.ExcludeFromLog;
import espace.entity.Auction;
import espace.entity.User;
import espace.exceptions.InvalidBidException;
import espace.managers.AuctionManager;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;
import espace.managers.UserManager;
import espace.services.AuctionService;
import espace.utils.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name = "auctionView")
@ViewScoped
public class AuctionView implements Serializable {

    @Inject
    private AuctionManager auctionManager;

    @Inject
    private ItemCategoryManager itemCategoryManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private UserManager userManager;

    @Inject
    private AuctionService auctionService;


    private Auction auction;
    private User user;
    private Double bidValue;

    private long auctionId;


    public void init() {
        try {
            user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            auction = auctionManager.getById(auctionId);
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened!");
        }
    }

    public String redirect(Long auctionId) {
        return "/Auctions/viewAuction.xhtml?faces-redirect=true&auctionId="+auctionId;

    }

    public String bidOnAuction(double bidValue) {
        try {
            auctionService.bidOnAuction(auction.getId(), bidValue, user);
            Messages.info("Bid", "You are the fist...");
        } catch (InvalidBidException e) {
            Messages.error("We are sorry!", e.getMessage());
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened!");
        }
        return null;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Double getBidValue() {
        return bidValue;
    }

    public void setBidValue(Double bidValue) {
        this.bidValue = bidValue;
    }

    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }
}
