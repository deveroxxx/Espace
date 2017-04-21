package espace.views.user;

import espace.entity.Auction;
import espace.entity.User;
import espace.managers.AuctionManager;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;
import espace.managers.UserManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name = "auctionView")
@SessionScoped
public class AuctionView implements Serializable {

    @Inject
    private AuctionManager auctionManager;

    @Inject
    private ItemCategoryManager itemCategoryManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private UserManager userManager;


    private Auction auction;
    private User user;


    public String init(Long auctionId) {
        user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        auction = auctionManager.getById(auctionId); //FIXME: if null incalid id
        return "/Auctions/viewAuction.xhtml?faces-redirect=true";
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
