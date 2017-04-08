package espace.managers;

import espace.data.ItemQueryData;
import espace.entity.Item;
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
public class ItemManager extends TemplateManager {

    public ItemManager() {
    }

    public Item add(Item item) {
        super.add(item);
        item.getUser().getItems().add(item);
        getEntityManager().merge(item.getUser());
        return item;
    }

    public void delete(Item item) throws EntityNotFoundException {
        super.deleteById(item.getId());
    }

    public List<Item> listItemsByUser(User user) {
        //language=JPAQL
        String hql = "select item from Item item " +
                "where item.user = :user and item.deleted = false";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        return (List<Item>) listByFilter(hql, params);
    }


    public void refreshListData(ItemQueryData data) {
        //TODO: filteres query lekérdezést megcsinálni
        String hql = "select item from Item item";
        data.setResult((List<Item>) list(hql));
        data.setTotalResultCount(data.getResult().size());
    }



    protected Class getMyClass() {
        return Item.class;
    }
}
