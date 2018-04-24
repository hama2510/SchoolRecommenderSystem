package com.hust.kien.schoolrecsys.repository.impl;

import com.hust.kien.schoolrecsys.repository.BaseRepository;
import org.neo4j.ogm.cypher.query.SortOrder;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Neo4jAbstractRepository<T> implements BaseRepository<T> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> clazz;

    public Neo4jAbstractRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected Session getSession() {
        return sessionFactory.openSession();
    }

    protected T queryForObject(String query, Map<String, ?> params) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, query);
        return getSession().queryForObject(clazz, query, params);
    }

    protected Object queryForObject(String query, Map<String, ?> params, Class clazz) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, query);
        return getSession().queryForObject(clazz, query, params);
    }

    protected List<T> query(String query, Map<String, ?> params) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, query);
        Iterable<T> i = getSession().query(clazz, query, params);
        List<T> list = new ArrayList<>();
        i.forEach((item) -> {
            list.add(item);
        });
        return list;
    }

    public void save(T t) {
        getSession().save(t);
    }

    public void save(T t, int depth) {
        getSession().save(t, depth);
    }

    public void delete(T t) {
        getSession().delete(t);
    }

    public List<T> findAll() {
        return (List<T>) getSession().loadAll(clazz);
    }

    public List<T> findAll(int depth) {
        return (List<T>) getSession().loadAll(clazz, depth);
    }

    public T find(Long id) {
        return (T) getSession().load(clazz, id);
    }

    public T find(Long id, int detph) {
        return (T) getSession().load(clazz, id, detph);
    }
}
