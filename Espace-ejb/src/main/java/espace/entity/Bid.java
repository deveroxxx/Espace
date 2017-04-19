package espace.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Bid extends BaseEntity {

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Auction auction;

    @NotNull
    private Double bid;


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

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "user=" + user.getId() +
                ", auction=" + auction.getId() +
                ", bid=" + bid +
                "} " + super.toString();
    }
}
