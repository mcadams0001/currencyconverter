package org.adam.currency.service;

import org.adam.currency.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("genericService")
public class GenericServiceImpl implements GenericService {
    @Autowired
    private GenericRepository genericRepository;

    @Override
    public <T> T findById(Class<T> clazz, Serializable id) {
        return genericRepository.findById(clazz, id);
    }

    @Override
    public <T> T findByName(Class<T> clazz, String name, Object value) {
        return genericRepository.findByName(clazz, name, value);
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz, String... orderBy) {
        return genericRepository.findAll(clazz, orderBy);
    }

    @Override
    public Serializable save(Object obj) {
        return genericRepository.save(obj);
    }
}
