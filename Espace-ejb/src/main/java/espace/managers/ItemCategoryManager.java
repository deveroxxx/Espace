package espace.managers;

import espace.entity.ItemCategory;
import espace.exceptions.EntityAlreadyExistException;
import espace.template.TemplateManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.HashMap;

@Stateless
@LocalBean
public class ItemCategoryManager extends TemplateManager {

    public ItemCategoryManager() {
    }

    public ItemCategory add(ItemCategory itemCategory) throws EntityAlreadyExistException {
        if (getByName(itemCategory.getName()) == null) {
            super.add(itemCategory);
            return itemCategory;
        } else {
            throw new EntityAlreadyExistException("Category already existing with this name: " + itemCategory.getName());
        }

    }

    public ItemCategory getByName(String name) {
        String hql = "select ic from ItemCategory ic where ic.name = :name";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(":name", name);
        return (ItemCategory) getUniqueItemByFilter(hql, params);
    }


    protected Class getMyClass() {
        return this.getClass();
    }
}
