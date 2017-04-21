package espace.views.user;

import espace.entity.Auction;
import espace.entity.Item;
import espace.entity.User;
import espace.managers.AuctionManager;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;
import espace.managers.UserManager;
import espace.utils.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name = "createAuctionView")
@SessionScoped
public class CreateAuctionView implements Serializable{

    @Inject
    private AuctionManager auctionManager;

    @Inject
    private ItemCategoryManager itemCategoryManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private UserManager userManager;

    private Auction auction;
    private Item item;
    private User user;

    @PostConstruct
    public void post() {
        auction = new Auction();
    }

    public String init(Long itemId) {
        user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        item = itemManager.getItemById(itemId); //FIXME: throw exception if not exist
        auction.setOwner(user);
        auction.setItem(item);
        return "/Auctions/createAuction.xhtml?faces-redirect=true";
    }

    public String createAuction() {
        try {
            auctionManager.createAuction(auction);
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened. Please try again.");
            return null;
        }
        return null;
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
}
