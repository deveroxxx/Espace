package espace.views;

import espace.entity.Auction;
import espace.entity.Bid;
import espace.entity.Item;
import espace.entity.User;
import espace.enums.Role;
import espace.managers.*;
import espace.services.AuctionService;
import espace.utils.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dtProfileView")
@RequestScoped
public class ProfileView implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    GroupRoleManager roleManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private AuctionManager auctionManager;

    @Inject
    private BidManager bidManager;



    private User user;
    private List<Role> userRoles;
    private List<Auction> myActiveAuctions;
    private List<Auction> myClosedAuctions;
    private List<Auction> myBids;
    private List<Item> myItems;
    private Item selectedItem;


    private int page = 0;
    private User tempUser;
    private String reason;


    @PostConstruct
    public void init() {
        try{
            user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            userRoles = roleManager.getRoles(user.getUserName());
            page = 0;
            tempUser = new User();

            myActiveAuctions = auctionManager.listAuctionsByUser(user, false);
            myBids = auctionManager.listAuctionByUserBids(user);
            myItems = itemManager.listItemsByUser(user);
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened!");
        }
    }

    public void updatePage(int page) {
        try{
            user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            userRoles = roleManager.getRoles(user.getUserName());
            tempUser = new User();
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened!");
        }
    }
    
    public String saveChanges() {
        try {
            userManager.update(user);
            Messages.info("Save", "Operation was successful!");
            return null;
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened!");
        }
        return null;
    }

    public List<Bid> myLatestBids() {
        List<Bid> result = new ArrayList<>();
        result = bidManager.listLatestBidsByUser(user.getId(), false);
        return result;
    }

    public List<Bid> closedBids() {
        List<Bid> result = new ArrayList<>();
        result = bidManager.listLatestBidsByUser(user.getId(), true);
        return result;
    }

    public boolean isUserTopBider(Bid bid) {
        if (bid.getAuction().getTopBider() != null && bid.getAuction().getTopBider().equals(bid)) {
            return true;
        } else {
            return false;
        }
    }

    public String discard() {
        return null;
    }

    public void changeActiveTab(TabChangeEvent event) {
        // TabView tabview= (TabView)event.getComponent();
        // page=tabview.getActiveIndex();
        if (event != null) {
            String id = event.getTab().getId();
            if (id.equals("tab0")) {
                page = 0;
            }
            if (id.equals("tab1")) {
                page = 1;
            }
            if (id.equals("tab2")) {
                page = 2;
            }
            if (id.equals("tab3")) {
                page = 3;
            }
            if (id.equals("tab4")) {
                page = 4;
            }
            updatePage(page);
            //page=((TabView)event.getComponent()).getActiveIndex();
            //System.out.println(page);
        }
    }
    
    public void click1() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('updateButton1').jq.click();");
    }

    public void click2() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('updateButton2').jq.click();");
    }




    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public List<Auction> getMyActiveAuctions() {
        return myActiveAuctions;
    }

    public void setMyActiveAuctions(List<Auction> myActiveAuctions) {
        this.myActiveAuctions = myActiveAuctions;
    }

    public List<Auction> getMyClosedAuctions() {
        return myClosedAuctions;
    }

    public void setMyClosedAuctions(List<Auction> myClosedAuctions) {
        this.myClosedAuctions = myClosedAuctions;
    }

    public List<Item> getMyItems() {
        return myItems;
    }

    public void setMyItems(List<Item> myItems) {
        this.myItems = myItems;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempUser) {
        this.tempUser = tempUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Auction> getMyBids() {
        return myBids;
    }

    public void setMyBids(List<Auction> myBids) {
        this.myBids = myBids;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }
}