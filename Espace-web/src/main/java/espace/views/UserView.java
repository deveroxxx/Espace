package espace.views;

import espace.entity.Notification;
import espace.entity.User;
import espace.entity.UserRating;
import espace.managers.UserManager;
import espace.managers.UserRatingManager;
import espace.services.UserService;
import espace.utils.Messages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;


@ManagedBean(name = "userView")
@ViewScoped
public class UserView  implements Serializable {


    @Inject
    private UserManager userManager;

    @Inject
    private UserRatingManager userRatingManager;

    @Inject
    private UserService userService;

    private User currentUser;
    private User viewedUser;
    private Long userId;
    private List<UserRating> ratingsList;
    private double avrgRating;
    private int numberOfRatings;


    public void init() {
        try {
            currentUser = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            viewedUser = userManager.select(userId);
            avrgRating = userService.avarageUserRating(viewedUser);
            ratingsList = viewedUser.getMyRaitings();
            numberOfRatings = ratingsList.size();
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened!");
        }
    }

    public String redirect(Long userId) {
        return "/Account/userView.xhtml?faces-redirect=true&userId="+userId;
    }

    public boolean currentUserIsViewedUser() {
        if (currentUser == null) {
            return true;
        } else {
            return currentUser.equals(viewedUser);
        }
    }



    //GETTER - SETTER

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getViewedUser() {
        return viewedUser;
    }

    public void setViewedUser(User viewedUser) {
        this.viewedUser = viewedUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public double getAvrgRating() {
        return avrgRating;
    }

    public void setAvrgRating(double avrgRating) {
        this.avrgRating = avrgRating;
    }

    public List<UserRating> getRatingsList() {
        return ratingsList;
    }

    public void setRatingsList(List<UserRating> ratingsList) {
        this.ratingsList = ratingsList;
    }
}
