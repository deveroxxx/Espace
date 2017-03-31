package espace.entity;

import espace.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RoleTable")
public class GroupRole extends BaseEntity {

    @NotNull
    @Column(name = "groupRole")
    @Enumerated(EnumType.STRING)
    private Role groupRole;

    @NotNull
    @Column(name = "userName")
    String userName;

    public GroupRole() {
    }

    public GroupRole(Role groupRole, String userName) {
        this.groupRole = groupRole;
        this.userName = userName;
    }

    public Role getGroupRole() {
        return groupRole;
    }

    public void setGroupRole(Role groupRole) {
        this.groupRole = groupRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userLoginName) {
        this.userName = userLoginName;
    }

    @Override
    public String toString() {
        return "GroupRole{" +
                "groupRole=" + groupRole +
                ", userName='" + userName + '\'' +
                "} " + super.toString();
    }
}
