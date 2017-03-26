package espace.service;

import espace.entity.User;
import espace.facade.UserFacade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class UserService {

    @Inject
    UserFacade userFacade;

    public void addUser(User user) {
        System.out.println("Trying to create a fucking user");

        userFacade.create(user);
    }

}
