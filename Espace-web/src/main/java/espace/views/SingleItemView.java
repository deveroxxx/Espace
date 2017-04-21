package espace.views;

import espace.entity.Item;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name = "singleItemView")
@RequestScoped
public class SingleItemView implements Serializable {

    @Inject
    private ItemManager itemManager;

    @Inject
    private ItemCategoryManager itemCategoryManager;

    private Item item;

    @PostConstruct
    public void init(Long itemId) {
        item = itemManager.getItemById(itemId);
        if (item == null) {
            //FIXME: new INVALID ID exception (global legyen.)
        }
    }





    // getters - setters

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
