package espace.managers;

import espace.annotations.ExcludeFromLog;
import espace.entity.GroupRole;
import espace.entity.User;
import espace.enums.Role;
import espace.exceptions.EntityAlreadyExistException;
import espace.template.TemplateManager;
import espace.utils.Log;
import espace.utils.LogLevel;
import espace.utils.SHA256Hash;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
@Log
public class UserManager extends TemplateManager<User> {

    @Inject
    GroupRoleManager groupRoleManager;

    public UserManager() {
    }

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
        if (!groupRoleManager.getRoles(user.getUserName()).contains(role)) {
            groupRoleManager.addRole(new GroupRole(role, user.getUserName()));
        }
    }

    public void removeUserRole(User user, Role role) {
        groupRoleManager.deleteRoleFromUser(user.getUserName(), role);
    }

    @Log(level = LogLevel.DEBUG)
    public User getUserByName(String userName) {
        //language=JPAQL
        String querry = "select user from User user " +
                        "where user.userName = :userName";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        return getUniqueItemByFilter(querry, params);
    }

    @Override
    public List<User> listAll() {
        //language=JPAQL
        String hql = "select user from User user where user.deleted = false";
        return list(hql);
    }

    public void update(User user) {
        if (user != null) {
            super.update(user);
        }
    }




    protected Class getMyClass() {
        return User.class;
    }
}
