package espace.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Auction extends BaseEntity {

    @NotNull(message = "Header is requied!")
    private String header;

    @NotNull
    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.REMOVE)
    private List<Bid> bids;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Bid topBider;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Item item;

    private Date startDate;

    @NotNull(message = "Expiration date is requied")
    private Date expirationDate;

    @NotNull
    private Boolean closed = false;

    @NotNull
    @Min(0)
    private Double minimumBid;

    private String description; //leírás az aukcióhoz

    /**
     * Visszaadja a legnagyobb licitet
     * @return Bid or Null
     */
    @Transient
    private Bid getTopBid() {
        double maxBidPrice = -1;
        Bid maxBid = null;
        if (bids != null && !bids.isEmpty()) {
            for (Bid bid : bids) {
                if (bid.getBid() > maxBidPrice) {
                    maxBid = bid;
                    maxBidPrice = bid.getBid();
                }
            }
        }
        return maxBid;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Double getMinimumBid() {
        return minimumBid;
    }

    public void setMinimumBid(Double minimumBid) {
        this.minimumBid = minimumBid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bid getTopBider() {
        return topBider;
    }

    public void setTopBider(Bid topBider) {
        this.topBider = topBider;
    }

}

