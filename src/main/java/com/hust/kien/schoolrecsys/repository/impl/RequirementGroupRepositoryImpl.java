package com.hust.kien.schoolrecsys.repository.impl;

import org.springframework.stereotype.Repository;
import com.hust.kien.schoolrecsys.entity.RequirementGroup;
import com.hust.kien.schoolrecsys.repository.RequirementGroupRepository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RequirementGroupRepositoryImpl extends Neo4jAbstractRepository<RequirementGroup> implements RequirementGroupRepository {

    protected RequirementGroupRepositoryImpl() {
        super(RequirementGroup.class);
    }

    @Override
    public RequirementGroup findByName(String name) {
        Map values = new HashMap();
        values.put("name", name);
        return super.queryForObject("MATCH(n:RequirementGroup) WHERE n.name = {name} return n", values);
    }

    @Override
    public Integer getMaxIndex() {
        return (Integer) super.queryForObject("MATCH(g:RequirementGroup) return MAX(g.index)", new HashMap<>(), Integer.class);
    }
}
