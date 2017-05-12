package espace.template;

import espace.exceptions.EntityNotFoundException;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public abstract class TemplateManagerBase<T> {
   // private static final Logger logger = Logger.getLogger(TemplateManagerBase.class);

    public T add(T t) {
        getEntityManager().persist(t);
        return t;
    }

    public T get(Long id) {
        return getEntityManager().getReference(getMyClass(), id);
    }

    public T select(Long id) {
        return getEntityManager().find(getMyClass(), id);
    }

    public T selectForUpdate(Long id) {
        EntityManager em = getEntityManager();
        T t = em.find(getMyClass(), id, LockModeType.PESSIMISTIC_WRITE);
        em.flush();
        if (t != null) {
            em.refresh(t);
        }
        return t;
    }

    public void readLock(T t) {
        getEntityManager().lock(t, LockModeType.PESSIMISTIC_READ);
    }

    public void writeLock(T t) {
        getEntityManager().lock(t, LockModeType.PESSIMISTIC_WRITE);
    }

    public List<T> listAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getMyClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> list(String query) {
        return listByFilter(query, null);
    }

    public List<T> listByFilter(String query, Map<String, Object> params) {
        TypedQuery<T> qry = getEntityManager().createQuery(query, getMyClass());
        setParameters(qry, params);
        List<T> objList = qry.getResultList();
        return objList;
    }

    public List<T> listByFilter(String query, Map<String, Object> params, int maxResult, int firstResult) {
        TypedQuery<T> qry = getEntityManager().createQuery(query, getMyClass());
        qry.setMaxResults(maxResult);
        qry.setFirstResult(firstResult);
        setParameters(qry, params);
        return qry.getResultList();
    }

    public T getUniqueItem(String query) {
        return getUniqueItemByFilter(query, null);
    }

    public T getUniqueItemByFilter(String query, Map<String, Object> params) {
        TypedQuery<T> qry = getEntityManager().createQuery(query, getMyClass());
        setParameters(qry, params);
        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Long getCountResultByFilter(String countQuery, Map<String, Object> params) {
        //logger.trace("Count query.");
        TypedQuery<Long> qry = getEntityManager().createQuery(countQuery, Long.class);
        setParameters(qry, params);
        return qry.getSingleResult();
    }

    public void setParameters(Query qry, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                qry.setParameter(key, params.get(key));
            }
        }
    }

    public T update(T entity) {
        //logger.trace("Updating object: " + entity);
        getEntityManager().merge(entity);
        return entity;
    }

    public void deleteById(Long id) throws EntityNotFoundException {
        //logger.trace("Deleting object by id: " + id);
        T entity = select(id);
        if (entity == null) {
            //logger.warn("Entity does not exist with id: " + id);
            throw new EntityNotFoundException("Entity does not exist with id: " + id);
        }
        getEntityManager().remove(entity);
    }

    public int countAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(getMyClass());
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    abstract protected EntityManager getEntityManager();

    abstract protected Class<T> getMyClass();
}
