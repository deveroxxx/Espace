package espace.managers;

import espace.entity.Notification;
import espace.entity.User;
import espace.exceptions.EntityNotFoundException;
import espace.template.TemplateManager;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
@Log
public class NotificationManager extends TemplateManager<Notification> {

    public List<Notification> listByUserWithFilters(User user, Boolean readed) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //language=JPAQL
        StringBuilder querry = new StringBuilder();
        querry.append("select notification from Notification notification where notification.recipient = :user");
        params.put("user", user);
        if (readed != null) {
            querry.append(" and notification.readed = :readed");
            params.put("readed", readed);
        }
        List<Notification> result = listByFilter(querry.toString(), params);
        return result;
    }

    public void markAsReded(Long notificationId) throws EntityNotFoundException {
        Notification notification = select(notificationId);
        if (notification != null) {
            notification.setReaded(true);
            update(notification);
        } else {
            throw new EntityNotFoundException("Notification not exist with the given id: " + notificationId);
        }
    }

    public void deleteById(Long notificationId) throws EntityNotFoundException {
        if (notificationId != null && select(notificationId) != null) {
            Notification notification = select(notificationId);
            notification.setDeleted(true);
            update(notification);
        } else {
            throw new EntityNotFoundException("Notification not exist with the given id: " + notificationId);
        }
    }


    @Override
    protected Class getMyClass() {
        return Notification.class;
    }
}
