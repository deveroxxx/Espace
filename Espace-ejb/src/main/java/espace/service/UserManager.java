package espace.service;

import espace.entity.User;
import espace.exceptions.EntityAlreadyExistException;
import espace.template.TemplateManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
public class UserManager extends TemplateManager {

    public User addUser(User user) throws EntityAlreadyExistException {

        String querry = "select user from User user where user.userName = :userName";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", user.getUserName());
        List<User> users = listByFilter(querry, params);
        if (users.isEmpty()) {
            // set default profile picture
            if (user.getPicture() == null || user.getPicture() == "") {
                user.setPicture("/Content/images/defaultUser.jpg");
            }


            super.add(user);
            return user;
        } else {
            throw new EntityAlreadyExistException("User is already exist");
        }
    }

    protected Class getMyClass() {
        return User.class;
    }
}
