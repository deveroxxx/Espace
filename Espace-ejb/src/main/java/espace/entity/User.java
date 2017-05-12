package espace.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "USERTABLE")
public class User extends BaseEntity {

    private String realName; //Igazi név

    @NotNull
    private String userName; //Bejelentkező név

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth; //Születési dátum

    private String picture; // Profilkép elérés url

    @NotNull
    private String password; // Jelszó (SHA hashel)

    @OneToMany(mappedBy = "user")
    private List<Item> items; // A felhasználó tárgyai

    @OneToMany(mappedBy = "user")
    private List<Bid> myBids; // A felhasználó licitjei

    @OneToMany(mappedBy = "owner")
    private List<Auction> myAuctions; //A felhasználó aukciói

    @OneToMany(mappedBy = "recipient")
    private List<Notification> notifications; //A felhasználó aukciói

    @OneToMany(mappedBy = "recipient")
    private List<UserRating> myRaitings; //A felhasználó értékelései más felhasználóktól

    @OneToMany(mappedBy = "sender")
    private List<UserRating> witedRaitings; //A felhasználó által írt értékelések

    private String phoneNumber;

    private String emailAdress;

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

    public List<Auction> getMyAuctions() {
        return myAuctions;
    }

    public void setMyAuctions(List<Auction> myAuctions) {
        this.myAuctions = myAuctions;
    }

    public List<Bid> getMyBids() {
        return myBids;
    }

    public void setMyBids(List<Bid> myBids) {
        this.myBids = myBids;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }
}
