package com.example.inventory_management.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T store(T entity);
    Optional<T> fetchById(ID id);
    List<T> fetchAll();
    boolean eraseById(ID id);
    boolean existsById(ID id);
    long count();
}