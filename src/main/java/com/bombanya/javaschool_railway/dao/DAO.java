package com.bombanya.javaschool_railway.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T, ID> {

    void save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();
}
