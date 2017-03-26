package espace.controllers;


import espace.entity.User;
import espace.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

@ManagedBean(name = "userController", eager = true)
@ViewScoped
public class UserController implements Serializable {

    private static final Logger log = Logger.getLogger(UserController.class.getName());

    @Inject
    private UserService userService;

    private User user;

    private String password1;
    private String password2;

    @PostConstruct
    public void init() {
        user = new User();
        password1 = null;
        password2 = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
        if (password1.equals(password2)) {
            user.setPassword(password1);
        }
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;

        if (password1.contentEquals(password2)) {
            user.setPassword(password1);
        }
    }

    public String registerUser() {
        System.out.println("Registering user starting...");
            userService.addUser(user);
        return "/Account/login.xhtml?faces-redirect=true&success=true";
    }

}
