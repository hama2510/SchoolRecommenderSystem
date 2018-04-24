package com.hust.kien.schoolrecsys.repository;

import java.util.List;

public interface BaseRepository<T> {
    void save(T t);

    void save(T t, int depth);

    void delete(T t);

    List<T> findAll();

    T find(Long id);

    List<T> findAll(int depth);

    T find(Long id, int depth);
}
