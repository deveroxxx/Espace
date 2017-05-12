package espace.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class UserRating extends BaseEntity {

    @NotNull
    @ManyToOne
    private User sender; // azkit értékelünk

    @NotNull
    @ManyToOne
    private User recipient; // az értékelt felhasználó

    @Min(0)
    @Max(5)
    @NotNull
    private short rating; //0-5

    @OneToOne
    private Auction auction; // az aukció amivel kapcsolatos az értékelés

    private String text;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short raiting) {
        this.rating = raiting;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

}
