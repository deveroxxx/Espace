package espace.service;

import espace.entity.Item;
import espace.exceptions.EntityNotFoundException;
import espace.template.TemplateManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    protected Class getMyClass() {
        return Item.class;
    }
}
