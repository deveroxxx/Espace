package espace.managers;

import espace.entity.User;
import espace.entity.UserRating;
import espace.template.TemplateManager;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
@Log
public class UserRatingManager extends TemplateManager<UserRating> {

    @Override
    protected Class<UserRating> getMyClass() {
        return UserRating.class;
    }
}
