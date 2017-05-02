package espace.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ITEM")
public class Item extends BaseEntity {

    @NotNull(message = "Item name is requied")
    private String name;

    @NotNull
    @ManyToOne
    private User user;

    @OneToOne
    private Auction auction;

    @ManyToOne
    private ItemCategory category;

    private String picture; // Termékkép elérési url


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' + "} " + super.toString();
    }
}
