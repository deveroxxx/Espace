package espace.converters;

import espace.entity.User;
import espace.managers.UserManager;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

@ApplicationScoped
@ManagedBean(name = "userConverter")
public class UserConverter implements Converter {

    @Inject
    UserManager userManager;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            return userManager.getUserByName(value);
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return ((User) object).getUserName();
        } else {
            return null;
        }
    }
}