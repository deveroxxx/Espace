package espace.views.admin;


import espace.entity.User;
import espace.enums.Role;
import espace.managers.GroupRoleManager;
import espace.managers.UserManager;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userListView" )
public class UserListView implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private GroupRoleManager roleManager;

    private List<User> users = new ArrayList<>();

    String name;
    private User selectedUser;

    @PostConstruct
    public void init() {
        users = userManager.listAll();
        name = null;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            if (name.length() < 1) {
                name = null;
            }
        }
        this.name = name;
    }

    public List<User> getUserList() {
        users = userManager.listAll();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isAdmin(User user) {
        return roleManager.getRoles(user.getUserName()).contains(Role.admin);
    }

    public void statusSwiched(User user) {
        if (roleManager.getRoles(user.getUserName()).contains(Role.admin)) {
            userManager.removeUserRole(user, Role.admin);
        } else {
            userManager.addUserRole(user, Role.admin);
        }
    }

    public void redirect(User user) {

        if (user != null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('selectButton').jq.click();");
        }
        //FacesContext.getCurrentInstance().getExternalContext().redirect("/ProductLibrary-web/faces/Product/singleProductView.xhtml?faces-redirect=true");
    }

}
