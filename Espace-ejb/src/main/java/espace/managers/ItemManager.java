package espace.managers;

import espace.data.QueryData;
import espace.entity.Item;
import espace.entity.User;
import espace.exceptions.EntityNotFoundException;
import espace.exceptions.ItemIsAssignedException;
import espace.template.TemplateManager;
import espace.utils.Log;
import espace.utils.LogLevel;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
@Log
public class ItemManager extends TemplateManager<Item> {

    public ItemManager() {
    }

    public Item addItem(Item item) {
        super.add(item);
        item.getUser().getItems().add(item);
        getEntityManager().merge(item.getUser());
        return item;
    }

    public void delete(Item item) throws EntityNotFoundException, ItemIsAssignedException {
        Item lockedItem = selectForUpdate(item.getId());
        if (lockedItem.getAuction() != null) {
            throw new ItemIsAssignedException();
        }
        lockedItem.setDeleted(true);
    }

    public Item updateItem(Item item) {
        Item lockedItem = selectForUpdate(item.getId());
        return super.update(lockedItem);
    }


    @Log(level = LogLevel.TRACE)
    public Item getItemById(Long itemId) {
        return super.select(itemId);
    }

    public List<Item> listItemsByUser(User user) {
        //language=JPAQL
        String hql = "select item from Item item " +
                "where item.user = :user and item.deleted = false";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        return listByFilter(hql, params);
    }


    public void refreshListData(QueryData<Item> data) {
        //TODO: filteres query lekérdezést megcsinálni
        String hql = "select item from Item item";
        data.setResult(list(hql));
        data.setTotalResultCount(data.getResult().size());
    }



    protected Class getMyClass() {
        return Item.class;
    }
}
