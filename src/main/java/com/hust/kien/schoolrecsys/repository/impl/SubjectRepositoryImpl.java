package com.hust.kien.schoolrecsys.repository.impl;

import com.hust.kien.schoolrecsys.entity.Subject;
import com.hust.kien.schoolrecsys.repository.SubjectRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectRepositoryImpl extends Neo4jAbstractRepository<Subject> implements SubjectRepository {
    public SubjectRepositoryImpl() {
        super(Subject.class);
    }

    @Override
    public List<Subject> searchByName(String name) {
        Map<String, String> values = new HashMap<>();
        values.put("name", ".*" + name + ".*");
        return super.query("MATCH(s:Subject) WHERE toLower(s.name) =~ toLower({name}) return s", values);
    }

    @Override
    public List<Subject> searchByType(String type) {
        Map<String, String> values = new HashMap<>();
        values.put("type", type);
        return super.query("MATCH(s:Subject) WHERE s.type = {type} return s", values);
    }

    @Override
    public List<Subject> searchByTypeAndName(String type, String name) {
        Map<String, String> values = new HashMap<>();
        values.put("name", ".*" + name + ".*");
        values.put("type", type);
        return super.query("MATCH(s:Subject) WHERE toLower(s.name) =~ toLower({name}) AND s.type = {type} return s", values);
    }

//    @Override
//    public List<Subject> searchByTypeAndToken(String type, List<String> names) {
//        Map values = new HashMap();
//        values.put("names", names);
//        values.put("type", type);
//        return super.query("MATCH(s:Subject) WHERE ANY(item IN split(s.name, ' ') WHERE toLower(item) IN {names})" +
//                " AND s.type = {type} return s", values);
//    }

    @Override
    public Subject findByTypeAndName(String type, String name) {
        Map<String, String> values = new HashMap<>();
        values.put("name", name);
        values.put("type", type);
        return super.queryForObject("MATCH(s:Subject) WHERE s.name = {name} AND s.type = {type} return s", values);
    }

    @Override
    public List<String> getAllName() {
        Iterable<String> iterable = super.getSession().query(String.class, "MATCH(s:Subject) return s.name", new HashMap<>());
        List<String> names = new ArrayList<>();
        iterable.forEach((item) -> names.add(item));
        return names;
    }
}
