package espace.managers;

import espace.entity.GroupRole;
import espace.exceptions.EntityNotFoundException;
import espace.template.TemplateManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class GroupRoleManager extends TemplateManager {

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

    protected Class getMyClass() {
        return this.getClass();
    }
}
