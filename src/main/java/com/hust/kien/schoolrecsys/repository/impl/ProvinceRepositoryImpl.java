package com.hust.kien.schoolrecsys.repository.impl;

import org.springframework.stereotype.Repository;
import com.hust.kien.schoolrecsys.entity.Province;
import com.hust.kien.schoolrecsys.repository.ProvinceRepository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProvinceRepositoryImpl extends Neo4jAbstractRepository<Province> implements ProvinceRepository {
    public ProvinceRepositoryImpl() {
        super(Province.class);
    }

    @Override
    public Province findByName(String name) {
        Map values = new HashMap();
        values.put("name", name);
        return super.queryForObject("MATCH(p:Province) WHERE p.name = {name} return p", values);
    }
}
