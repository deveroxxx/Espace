package espace.managers;

import espace.entity.User;
import espace.enums.Role;
import espace.exceptions.EntityAlreadyExistException;
import espace.template.TemplateManager;
import espace.utils.SHA256Hash;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
public class UserManager extends TemplateManager {

    public UserManager() {
    }

    public User addUser(User user) throws EntityAlreadyExistException {

        String querry = "select user from User user where user.userName = :userName";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", user.getUserName());
        List<User> users = listByFilter(querry, params);
        if (users.isEmpty()) {
            // Hashing password
            String password = user.getPassword();
            user.setPassword(SHA256Hash.sha256(password));
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

    public User getUserByName(String userName) {
        String querry = "select user from User user where user.userName = :userName";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        return (User) getUniqueItemByFilter(querry, params);
    }

    public List<Role> getRoles(String userName) {
        String querry = "select role.groupRole from GroupRole role where role.userName = :userName";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        return listByFilter(querry,params);
    }


    protected Class getMyClass() {
        return User.class;
    }
}
