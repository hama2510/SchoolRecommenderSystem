package com.hust.kien.schoolrecsys.repository.impl;

import com.hust.kien.schoolrecsys.entity.Tag;
import com.hust.kien.schoolrecsys.repository.TagRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagRepositoryImpl extends Neo4jAbstractRepository<Tag> implements TagRepository {
    public TagRepositoryImpl() {
        super(Tag.class);
    }

    @Override
    public Tag findByName(String name) {
        Map<String, String> values = new HashMap<>();
        values.put("name", name);
        return super.queryForObject("MATCH (t:Tag) WHERE t.name = {name} return t", values);
    }

    @Override
    public List<Tag> searchByName(String name) {
        Map<String, String> values = new HashMap<>();
        values.put("name", ".*" + name + ".*");
        return super.query("MATCH(t:Tag) WHERE toLower(t.name) =~ toLower({name}) return t", values);

    }
}
