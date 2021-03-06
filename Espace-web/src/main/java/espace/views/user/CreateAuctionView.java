package espace.views.user;

import espace.entity.Auction;
import espace.entity.Item;
import espace.entity.User;
import espace.managers.AuctionManager;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;
import espace.managers.UserManager;
import espace.services.AuctionService;
import espace.utils.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean( name = "createAuctionView")
@ViewScoped
public class CreateAuctionView implements Serializable{

    @Inject
    private AuctionManager auctionManager;

    @Inject
    private AuctionService auctionService;

    @Inject
    private ItemCategoryManager itemCategoryManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private UserManager userManager;

    private Auction auction;
    private Item item;
    private User user;

    private long itemId;

    public void init() {
        try {
            auction = new Auction();
            user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            item = itemManager.getItemById(itemId); //FIXME: throw exception if not exist
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened!");
        }
    }

    public String redirect(Long itemId) {
        return "/Auctions/createAuction.xhtml?faces-redirect=true&itemId="+itemId;
    }


    public String createAuction() {
        try {
            auctionService.createAuction(auction, item.getId(), user);
            Messages.info("Add", "Auction created...");
        } catch (Exception e) {
            Messages.error("We are sorry!", e.getMessage());
            return null;
        }
        return "/Account/profile.xhtml?faces-redirect=true";
    }


    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
