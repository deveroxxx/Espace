package espace.managers;

import espace.data.ItemQueryData;
import espace.entity.Item;
import espace.exceptions.EntityNotFoundException;
import espace.template.TemplateManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ItemManager extends TemplateManager {

    public ItemManager() {
    }

    public Item add(Item item) {
        super.add(item);
        return item;
    }

    public void delete(Item item) throws EntityNotFoundException {
        super.deleteById(item.getId());
    }


    public void refreshListData(ItemQueryData data) {
        //TODO: filteres query lekérdezést megcsinálni
        String hql = "select item from Item item";
        data.setResult(list(hql));
        data.setTotalResultCount(data.getResult().size());
    }



    protected Class getMyClass() {
        return Item.class;
    }
}
