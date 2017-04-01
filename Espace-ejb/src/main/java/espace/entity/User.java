package espace.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "USERTABLE")
public class User extends BaseEntity {

    private String realName; //Igazi név

    @NotNull
    private String userName; //Bejelentkező név

    private Date dateOfBirth; //Születési dátum

    private String picture; // Profilkép elérés url

    @NotNull
    private String password; // Jelszó (SHA hashel)

    @OneToMany(mappedBy = "user")
    private List<Item> items; // A felhasználó tárgyai

    @OneToMany(mappedBy = "topBidder")
    private List<Auction> topBids; // A felhasználó licitjei ahol ő vezet

    @OneToMany(mappedBy = "owner")
    private List<Auction> myAuctions; //A felhasználó aukciói

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Auction> getTopBids() {
        return topBids;
    }

    public void setTopBids(List<Auction> topBids) {
        this.topBids = topBids;
    }

    public List<Auction> getMyAuctions() {
        return myAuctions;
    }

    public void setMyAuctions(List<Auction> myAuctions) {
        this.myAuctions = myAuctions;
    }
}
