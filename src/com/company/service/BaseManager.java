package com.company.service;

import java.util.Collection;

public interface BaseManager<T, R> {
    void initData();
    void saveData();

    void saveModel(T t);
    T findById(R r);
    boolean deleteById(R r);
    Collection<T> findAll();
}
