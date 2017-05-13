package espace.controllers;

import espace.entity.User;
import espace.exceptions.BusinessException;
import espace.managers.UserManager;
import espace.services.UserService;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@ManagedBean(name = "userRaitingController")
@RequestScoped
public class UserRaitingController {

    @Inject
    private UserService userService;

    @Inject
    private UserManager userManager;

    private String text;
    private Short rating;
    private User user;


    public void createUserRating(Long auctionId) {
        FacesMessage message = null;
        user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());

        try {
            userService.createUserRating(user, auctionId, rating, text);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Sucessfull commenting");
        } catch (BusinessException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
