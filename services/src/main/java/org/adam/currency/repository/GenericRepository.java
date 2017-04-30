package org.adam.currency.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    public <T> T findByName(Class<T> clazz, String name, Object value) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.where(criteriaBuilder.equal(root.get(name), value));
        return sessionFactory.getCurrentSession().createQuery(query).getSingleResult();
    }

    /**
     * Finds all present entities for a given type and orders them if order is provided.
     *
     * @param clazz   the entity class.
     * @param orderBy the ascending order in which the returned list should be ordered.
     * @param <T>     generic type.
     * @return the list collection with all entities found.
     */
    public <T> List<T> findAll(Class<T> clazz, String... orderBy) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        if (orderBy.length > 0) {
            List<String> orders = Arrays.asList(orderBy);
            List<javax.persistence.criteria.Order> orderList = orders.stream().map(o -> criteriaBuilder.asc(root.get(o))).collect(toList());
            query.orderBy(orderList);
        }
        return sessionFactory.getCurrentSession().createQuery(query).getResultList();
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
