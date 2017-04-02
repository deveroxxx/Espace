package espace.controllers;

import espace.entity.Item;
import espace.entity.ItemCategory;
import espace.entity.User;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;
import espace.managers.UserManager;
import espace.utils.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "itemController", eager = true)
@ViewScoped
public class ItemController implements Serializable {

    @Inject
    private ItemCategoryManager itemCategoryManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private UserManager userManager;


    private User user;
    private Item item;
    private List<ItemCategory> categoryList;


    @PostConstruct
    public void init() {
        user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        categoryList = itemCategoryManager.listAll();

        item = new Item();
        item.setUser(user);
        item.setPicture("/Content/images/defaultProduct.jpg");
    }

    public String addItem(Item item) {
        itemManager.add(item);
        Messages.warn("Add", "Item added...");
        return null;
    }

    public String navigate() {
        return "/Item/addItem.xhtml?faces-redirect=true";
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<ItemCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ItemCategory> categoryList) {
        this.categoryList = categoryList;
    }
}