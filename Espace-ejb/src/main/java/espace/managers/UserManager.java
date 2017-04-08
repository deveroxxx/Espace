package espace.managers;

import espace.entity.Auction;
import espace.entity.GroupRole;
import espace.entity.User;
import espace.enums.Role;
import espace.exceptions.EntityAlreadyExistException;
import espace.template.TemplateManager;
import espace.utils.Log;
import espace.utils.LoggingInterceptor;
import espace.utils.SHA256Hash;
import org.apache.log4j.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
@Log
@Interceptors(LoggingInterceptor.class)
public class UserManager extends TemplateManager {

    @Inject
    GroupRoleManager groupRoleManager;

    public UserManager() {
    }

    //FIXME: enged reggelni valamiért létezőt is (debug majd)
    public User addUser(User user) throws EntityAlreadyExistException {
        if (getUserByName(user.getUserName()) == null) {
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

    public void addUserRole(User user, Role role) {
        if (!getRoles(user.getUserName()).contains(role)) {
            groupRoleManager.addRole(new GroupRole(role, user.getUserName()));
        }
    }

    public User getUserByName(String userName) {
        //language=JPAQL
        String querry = "select user from User user " +
                        "where user.userName = :userName";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        return (User) getUniqueItemByFilter(querry, params);
    }

    public List<Role> getRoles(String userName) {
        //language=JPAQL
        String querry = "select role.groupRole from GroupRole role " +
                        "where role.userName = :userName";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        return listByFilter(querry,params);
    }




    protected Class getMyClass() {
        return User.class;
    }
}
