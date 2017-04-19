package espace.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Auction extends BaseEntity {

    @NotNull
    private String header;

    @NotNull
    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "auction")
    private List<Bid> bids;

    @OneToOne
    private Item item;

    private Double currentBid;

    private Date startDate;

    @NotNull
    private Date expirationDate;

    @NotNull
    private Boolean closed = false;

    @NotNull
    @Min(0)
    private Double minimumBid;

    /**
     * Visszaadja a legtöbbet licitáló user-t, vagy null-t
     * @return User or Null
     */
    @Transient
    private User getTopBidder() {
        double maxBid = -1;
        User maxBidUser = null;
        if (bids != null && !bids.isEmpty()) {
            for (Bid bid : bids) {
                if (bid.getBid() > maxBid) {
                    maxBidUser = bid.getUser();
                    maxBid = bid.getBid();
                }
            }
        }
        return maxBidUser;
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

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
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

    @Override
    public String toString() {
        return "Auction{" +
                " owner=" + owner.getId() +
                ", item=" + item +
                ", currentBid=" + currentBid +
                ", startDate=" + startDate +
                ", expirationDate=" + expirationDate +
                ", closed=" + closed +
                ", minimumBid=" + minimumBid +
                "} " + super.toString();
    }
}

