package espace.views;

import espace.entity.*;
import espace.enums.Role;
import espace.exceptions.EntityNotFoundException;
import espace.managers.*;
import espace.services.AuctionService;
import espace.services.UserService;
import espace.utils.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
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

    @Inject
    private NotificationManager notificationManager;

    @Inject
    private UserService userService;



    private User user;
    private List<Role> userRoles;
    private List<Auction> myActiveAuctions;
    private List<Auction> myClosedAuctions;
    private List<Item> myItems;
    private Item selectedItem;

    private double avrgRate;
    private int countRate;

    private int page = 0;

    //notifications
    private List<Notification> selectedNotifications;


    @PostConstruct
    public void init() {
        try{
            user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            userRoles = roleManager.getRoles(user.getUserName());
            avrgRate = userService.avarageUserRating(user);
            countRate = user.getMyRaitings().size();
            myItems = itemManager.listItemsByUser(user);
            avrgRate = userService.avarageUserRating(user);
            countRate = user.getMyRaitings().size();
            myActiveAuctions = auctionManager.listAuctionsByUser(user, false);
            selectedNotifications = new ArrayList<>();
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

    public List<Notification> getNotifications(boolean readed) {
        return notificationManager.listByUserWithFilters(user, readed);
    }

    public void deleteSelectedNotifications() {
        if (selectedNotifications != null && selectedNotifications.size() > 0) {
            for (Notification not : selectedNotifications)
                try {
                    notificationManager.deleteById(not.getId());
                } catch (EntityNotFoundException e) {
                    Messages.error("We are sorry!", "Entity is alreadyDeleted! :" + (not.getId()));
                }
        }
        Messages.info("Delete Successfull!", ":)");
    }

    public String discard() {
        return null;
    }

    public void changeActiveTab(TabChangeEvent event) {
        if (event != null) {
            String id = event.getTab().getId();
            if (id.equals("tab0")) { //profile

                page = 0;
            }
            if (id.equals("tab1")) { // edit profile
                page = 1;
            }
            if (id.equals("tab2")) { // auctions
                page = 2;
            }
            if (id.equals("tab3")) { // items
                page = 3;
            }
            if (id.equals("tab4")) { // bids
                page = 4;
            }
            if (id.equals("tab5")) { // notifications
                page = 5;
            }
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

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<Notification> getSelectedNotifications() {
        return selectedNotifications;
    }

    public void setSelectedNotifications(List<Notification> selectedNotifications) {
        this.selectedNotifications = selectedNotifications;
    }

    public double getAvrgRate() {
        return avrgRate;
    }

    public void setAvrgRate(double avrgRate) {
        this.avrgRate = avrgRate;
    }

    public int getCountRate() {
        return countRate;
    }

    public void setCountRate(int countRate) {
        this.countRate = countRate;
    }
}