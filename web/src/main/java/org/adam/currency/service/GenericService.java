package org.adam.currency.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService {
    /**
     * Finds an entity by id.
     *
     * @param clazz the entity class.
     * @param id    the entity id.
     * @param <T>   generic type.
     * @return instance of entity or null if not found.
     */
    <T> T findById(Class<T> clazz, Serializable id);

    /**
     * Finds an entity by given attribute name and it's value.
     *
     * @param clazz the entity class.
     * @param name  the name of the attribute to be used for finding an entity.
     * @param value the value of the attribute.
     * @param <T>   generic type.
     * @return instance of entity or null if not found.
     */
    <T> T findByName(Class<T> clazz, String name, Object value);

    /**
     * Finds all present entities for a given type and orders them if order is provided.
     *
     * @param clazz   the entity class.
     * @param orderBy the ascending order in which the returned list should be ordered.
     * @param <T>     generic type.
     * @return the list collection with all entities found.
     */
    <T> List<T> findAll(Class<T> clazz, String... orderBy);

    /**
     * Persists a given object.
     *
     * @param obj the object to be persisted
     * @return object id.
     */
    Serializable save(Object obj);
}
