package espace.views.user;

import espace.entity.Notification;
import espace.entity.User;
import espace.managers.NotificationManager;
import espace.managers.UserManager;
import espace.utils.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;


@ManagedBean( name = "notificationView")
@ViewScoped
public class NotificationView implements Serializable {

    @Inject
    NotificationManager notificationManager;

    @Inject
    private UserManager userManager;

    private Notification notification;
    private User user;
    private Long notificationId;
    
    public void init() {
        try {
            user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            notification = notificationManager.select(notificationId);
        } catch (Exception e) {
            Messages.error("We are sorry!", "Unexpected error happened!");
        }
    }

    public String redirect(Long notificationId) {
        return "/Account/viewNotification.xhtml?faces-redirect=true&notificationId="+notificationId;
    }


    //GETTER - SETTER

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
}
