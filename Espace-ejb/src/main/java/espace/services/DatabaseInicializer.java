package espace.services;


import espace.entity.*;
import espace.enums.Role;
import espace.exceptions.EntityAlreadyExistException;
import espace.managers.*;
import org.apache.log4j.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class DatabaseInicializer implements Serializable {

    private static final Logger logger = Logger.getLogger(DatabaseInicializer.class);

    @Inject
    UserManager userManager;

    @Inject
    ItemManager itemManager;

    @Inject
    GroupRoleManager groupRoleManager;

    @Inject
    ItemCategoryManager itemCategoryManager;

    @Inject
    DatabaseInicializer self;

    @Inject
    AuctionService auctionService;

    @Inject
    BidManager bidManager;

    @Inject
    AuctionManager auctionManager;

    @Inject
    NotificationManager notificationManager;

    public void createAdminUser() {
        try {
            User user = new User("admin", "admin");
            userManager.addUser(user);
            userManager.addUserRole(user, Role.admin);
            userManager.addUserRole(user, Role.user);
        } catch (EntityAlreadyExistException e) {
            // initnél nem érdekel ez
        }
    }

    public void createUsers() {
        for (int i=1; i<=5; i++) {
            try {
                User user = new User("user_"+i, "user_"+i);
                userManager.addUser(user);
                userManager.addUserRole(user, Role.user);
            } catch (EntityAlreadyExistException e) {
                // initnél nem érdekel ez
            }

        }
    }

    public void createCategories() {
        for (int i=1; i<=5; i++) {
            try {
                itemCategoryManager.addCategory(new ItemCategory("item_category_"+i));
            } catch (EntityAlreadyExistException e) {
                // initnél nem érdekel ez
            }
        }
    }

    public void createItems() {
        for (int i=1; i<=5; i++) {
            Item item = new Item();
            item.setName("Item_"+i);
            //FIXME: ez elbaszódhat ha nem létezik a user
            item.setUser(userManager.getUserByName("user_" + i));
            itemManager.addItem(item);
        }
    }


    public void createAdminNotifications() {
        try {
            User user = userManager.getUserByName("admin");
            for (int i = 1; i<3; i++) {
                Notification notification = new Notification();
                notification.setReaded(false);
                notification.setDeleted(false);
                notification.setTitle("This is a title with timepam " + Calendar.getInstance().getTime());
                notification.setContent("This is a test content for admin user notifications, time: " + Calendar.getInstance().getTime());
                notification.setSender("Demo sender");
                notification.setRecipient(user);
                notificationManager.add(notification);
            }
        } catch (Throwable th) {
            logger.error(th);
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void createDumyDatas() {
        try {
            self.createDumyUsers();
        } catch (Throwable th) {
            logger.error(th);
        }
        try {
            self.createDumyItems();
        } catch (Throwable th) {
            logger.error(th);
        }
        try {
            self.createDumyAuctions();
        } catch (Throwable th) {
            logger.error(th);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createDumyUsers() {
        //User készítés
        for (int i = 1; i <= 10; i++) {
            try {
                User user = new User("user_" + i, "user_" + i);
                userManager.addUser(user);
                userManager.addUserRole(user, Role.user);
            } catch (EntityAlreadyExistException e) {
                // initnél nem érdekel ez
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createDumyItems() {
      //  ItemCategory ic_1 = itemCategoryManager.getByName("item_category_1");
      //  ItemCategory ic_2 = itemCategoryManager.getByName("item_category_2");
      //  ItemCategory ic_3 = itemCategoryManager.getByName("item_category_3");
        //User készítés
        for (int u = 1; u <= 10; u++) {
            for (int i = 1; i <= 3; i++) {
                try {
                    Item item = new Item();
                    item.setName("user_" + u + "_item_" + i);
                    item.setUser(userManager.getUserByName("user_" + u));
                    item.setCategory(itemCategoryManager.getByName("item_category_" + i));
                    itemManager.addItem(item);
                } catch (Exception e) {
                    // initnél nem érdekel ez
                }
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createDumyAuctions() {
        for (int u = 1; u <= 10; u++) {
            User user = userManager.getUserByName("user_" + u);
            List<Item> items = user.getItems();
            for (Item item : items) {
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.set(2018,11,10);
                    Date expirationDate = cal.getTime();
                    double minBid = 20 + u;

                    Auction auction = new Auction();
                    auction.setDescription("Auction description for " + item.getName());
                    auction.setMinimumBid(minBid);
                    auction.setExpirationDate(expirationDate);
                    auction.setHeader("Auction header user: " + user.getUserName());
                    auctionService.createAuction(auction, item.getId(), user);
                } catch (Exception e) {
                    // initnél nem érdekel ez
                }
            }
        }
    }

}
