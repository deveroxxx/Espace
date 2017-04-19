package espace.managers;

import espace.annotations.ExcludeFromLog;
import espace.entity.GroupRole;
import espace.enums.Role;
import espace.exceptions.EntityNotFoundException;
import espace.template.TemplateManager;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@LocalBean
@Log
public class GroupRoleManager extends TemplateManager<GroupRole> {
    Logger logger = Logger.getLogger(GroupRoleManager.class.getCanonicalName());


    public GroupRoleManager() {
    }

    public GroupRole addRole(GroupRole role) {
        super.add(role);
        return role;
    }

    public void deleteRole(GroupRole role) throws EntityNotFoundException {
        try {
            super.deleteById(role.getId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("The role not exist: " + role.toString());
        }
    }

    public void deleteRoleFromUser(String userName, Role role) {
        GroupRole gr = getGroupRoleByUserAndRole(userName, role);
        if (gr != null) {
            try {
                deleteRole(gr);
            } catch (EntityNotFoundException e) {
                logger.warning("Role: " + role + ", not Existing for user: " + userName);
            }
        }
    }

    @ExcludeFromLog
    public List<Role> getRoles(String userName) {
        //language=JPAQL
        String querry = "select role from GroupRole role " +
                "where role.userName = :userName";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);

        List<GroupRole> grList = listByFilter(querry,params);
        List<Role> roles = new ArrayList<Role>();
        for (GroupRole gr : grList) {
            roles.add(gr.getGroupRole());
        }
        return roles;
    }

    public GroupRole getGroupRoleByUserAndRole(String userName, Role role) {
        //language=JPAQL
        String querry = "select role from GroupRole role " +
                        "where role.userName = :userName and role.groupRole = :role";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        params.put("role", role);
        return (GroupRole) getUniqueItemByFilter(querry,params);
    }

    protected Class getMyClass() {
        return GroupRole.class;
    }
}
