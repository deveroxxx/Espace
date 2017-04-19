package espace.template;

import espace.exceptions.EntityNotFoundException;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public abstract class TemplateManagerBase<T> {
   // private static final Logger logger = Logger.getLogger(TemplateManagerBase.class);


    protected void add(T t) {
        getEntityManager().persist(t);
    }

    protected T get(Long id) {
        return getEntityManager().getReference(getMyClass(), id);
    }

    protected T select(Long id) {
        return getEntityManager().find(getMyClass(), id);
    }

    protected T selectForUpdate(Long id) {
        EntityManager em = getEntityManager();
        T t = em.find(getMyClass(), id, LockModeType.PESSIMISTIC_WRITE);
        em.flush();
        if (t != null) {
            em.refresh(t);
        }
        return t;
    }

    protected void readLock(T t) {
        getEntityManager().lock(t, LockModeType.PESSIMISTIC_READ);
    }

    protected void writeLock(T t) {
        getEntityManager().lock(t, LockModeType.PESSIMISTIC_WRITE);
    }

    protected List<T> listAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getMyClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }

    protected List<T> list(String query) {
        return listByFilter(query, null);
    }

    protected List<T> listByFilter(String query, Map<String, Object> params) {
        TypedQuery<T> qry = getEntityManager().createQuery(query, getMyClass());
        setParameters(qry, params);
        List<T> objList = qry.getResultList();
        return objList;
    }

    protected List<T> listByFilter(String query, Map<String, Object> params, int maxResult) {
        TypedQuery<T> qry = getEntityManager().createQuery(query, getMyClass());
        qry.setMaxResults(maxResult);
        setParameters(qry, params);
        return qry.getResultList();
    }

    protected T getUniqueItem(String query) {
        return getUniqueItemByFilter(query, null);
    }

    protected T getUniqueItemByFilter(String query, Map<String, Object> params) {
        TypedQuery<T> qry = getEntityManager().createQuery(query, getMyClass());
        setParameters(qry, params);
        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected Long getCountResultByFilter(String countQuery, Map<String, Object> params) {
        //logger.trace("Count query.");
        TypedQuery<Long> qry = getEntityManager().createQuery(countQuery, Long.class);
        setParameters(qry, params);
        return qry.getSingleResult();
    }

    protected void setParameters(Query qry, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                qry.setParameter(key, params.get(key));
            }
        }
    }

    protected void update(T entity) {
        //logger.trace("Updating object: " + entity);
        getEntityManager().merge(entity);
    }

    protected void deleteById(Long id) throws EntityNotFoundException {
        //logger.trace("Deleting object by id: " + id);
        T entity = select(id);
        if (entity == null) {
            //logger.warn("Entity does not exist with id: " + id);
            throw new EntityNotFoundException("Entity does not exist with id: " + id);
        }
        getEntityManager().remove(entity);
    }

    abstract protected EntityManager getEntityManager();

    abstract protected Class<T> getMyClass();
}
