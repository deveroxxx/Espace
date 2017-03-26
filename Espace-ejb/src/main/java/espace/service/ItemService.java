package espace.service;

import espace.entity.Item;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class ItemService {

    @PersistenceContext(unitName = "EspacePersistance")
    private EntityManager em;

    public Item add(Item item) {
        em.persist(item);
        return item;
    }

}
