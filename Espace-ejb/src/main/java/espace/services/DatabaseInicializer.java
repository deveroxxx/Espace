package espace.services;


import espace.entity.User;
import espace.exceptions.EntityAlreadyExistException;
import espace.managers.GroupRoleManager;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;
import espace.managers.UserManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class DatabaseInicializer {

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
            userManager.addUser(new User("admin", "admin"));
        } catch (EntityAlreadyExistException e) {
            // initnél nem érdekel ez
        }
    }

    public void createUsers() {
        for (int i=1; i<=5; i++) {
            try {
                userManager.addUser(new User("user_"+i, "user_"+i));
            } catch (EntityAlreadyExistException e) {
                // initnél nem érdekel ez
            }
        }
    }

    public void createCategories() {

    }



}
