package espace.views;

import espace.entity.Auction;
import espace.entity.User;
import espace.enums.Role;
import espace.managers.ItemManager;
import espace.managers.UserManager;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "dtProfileView")
@SessionScoped
public class ProfileView implements Serializable {

    @Inject
    UserManager userManager;

    @Inject
    ItemManager itemManager;



    private User user;
    private List<Role> userRoles;
    private List<Auction> myAuctions;
    private List<Auction> myTopBids;


    private int page = 0;
    private User tempUser;
    private String reason;


    @PostConstruct
    public void init() {
        user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        userRoles = userManager.getRoles(user.getUserName());
        page = 0;
        tempUser = new User();
    }

    public void updatePage() {
        user = userManager.getUserByName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        userRoles = userManager.getRoles(user.getUserName());
        tempUser = new User();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempUser) {
        this.tempUser = tempUser;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public String saveChanges() {
        try {
            userManager.update(user);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Operation was successful!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning:", "Unexpeced error!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        return null;
    }


    public String discard() {
        return null;
    }

    public void changeActiveTab(TabChangeEvent event) {
        // TabView tabview= (TabView)event.getComponent();
        // page=tabview.getActiveIndex();
        if (event != null) {
            String id = event.getTab().getId();
            if (id.equals("tab0")) {
                page = 0;
            }
            if (id.equals("tab1")) {
                page = 1;
            }
            if (id.equals("tab2")) {
                page = 2;
            }
            if (id.equals("tab3")) {
                page = 3;
            }
            updatePage();
            //page=((TabView)event.getComponent()).getActiveIndex();
            //System.out.println(page);
        }
    }


    public void click1() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('updateButton1').jq.click();");
    }

    public void click2() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('updateButton2').jq.click();");
    }

}