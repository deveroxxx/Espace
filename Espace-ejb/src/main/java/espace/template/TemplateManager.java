package espace.template;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class TemplateManager<T> extends TemplateManagerBase<T> {

    @PersistenceContext(unitName="EspacePersistance")
    protected EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
