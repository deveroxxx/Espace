package espace.managers;

import espace.entity.Notification;
import espace.entity.User;
import espace.template.TemplateManager;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
@LocalBean
@Log
public class NotificationManager extends TemplateManager<Notification> {

    @Override
    protected Class getMyClass() {
        return Notification.class;
    }
}