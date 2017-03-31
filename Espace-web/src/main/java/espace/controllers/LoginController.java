package espace.controllers;


import espace.utils.Messages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "loginController", eager = true)
@ViewScoped
public class LoginController implements Serializable {

    private static final Logger log = Logger.getLogger(LoginController.class.getName());

    private String username;

    private String password;

    public String logout() {
        String result = "/Account/login.xhtml?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            result = "/loginError.xhtml?faces-redirect=true";
        }
        return result;
    }

    public String login() {
        // Ez tényleg kell?
        logout();

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.login(username, password);
           // loginCounterService.addLoginCounter();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to login user!", e);
            Messages.warn("Login failed!", "Invalid name or password!");
            return null;
        }

        return "/Product/listProducts.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
