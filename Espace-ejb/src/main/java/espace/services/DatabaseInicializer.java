package espace.services;


import espace.entity.Item;
import espace.entity.ItemCategory;
import espace.entity.User;
import espace.enums.Role;
import espace.exceptions.EntityAlreadyExistException;
import espace.managers.GroupRoleManager;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;
import espace.managers.UserManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
@LocalBean
public class DatabaseInicializer implements Serializable {

    @Inject
    UserManager userManager;

    @Inject
    ItemManager itemManager;

    @Inject
    GroupRoleManager groupRoleManager;

    @Inject
    ItemCategoryManager itemCategoryManager;

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

}
