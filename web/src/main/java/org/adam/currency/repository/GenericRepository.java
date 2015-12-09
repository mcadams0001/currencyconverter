package org.adam.currency.repository;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Repository.
 */
@Repository("genericRepository")
public class GenericRepository {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Finds an entity by id.
     *
     * @param clazz the entity class.
     * @param id    the entity id.
     * @param <T>   generic type.
     * @return instance of entity or null if not found.
     */
    public <T> T findById(Class<T> clazz, Serializable id) {
        return sessionFactory.getCurrentSession().get(clazz, id);
    }

    /**
     * Finds an entity by given attribute name and it's value.
     *
     * @param clazz the entity class.
     * @param name  the name of the attribute to be used for finding an entity.
     * @param value the value of the attribute.
     * @param <T>   generic type.
     * @return instance of entity or null if not found.
     */
    @SuppressWarnings("unchecked")
    public <T> T findByName(Class<T> clazz, String name, Object value) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(name, value));
        return (T) criteria.uniqueResult();
    }

    /**
     * Finds all present entities for a given type and orders them if order is provided.
     *
     * @param clazz   the entity class.
     * @param orderBy the ascending order in which the returned list should be ordered.
     * @param <T>     generic type.
     * @return the list collection with all entities found.
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> clazz, String... orderBy) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        if (orderBy != null) {
            for (String order : orderBy) {
                criteria.addOrder(Order.asc(order));
            }
        }
        return criteria.list();
    }

    /**
     * Persists a given object.
     *
     * @param obj the object to be persisted
     * @return object id.
     */
    public Serializable save(Object obj) {
        return sessionFactory.getCurrentSession().save(obj);
    }
}
