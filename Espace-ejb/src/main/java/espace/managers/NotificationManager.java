package espace.managers;

import espace.entity.Notification;
import espace.entity.User;
import espace.template.TemplateManager;

import javax.persistence.EntityManager;

public class NotificationManager extends TemplateManager<Notification> {




    @Override
    protected Class getMyClass() {
        return Notification.class;
    }
}
