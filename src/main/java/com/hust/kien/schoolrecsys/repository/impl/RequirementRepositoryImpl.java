package com.hust.kien.schoolrecsys.repository.impl;

import org.springframework.stereotype.Repository;
import com.hust.kien.schoolrecsys.entity.Requirement;
import com.hust.kien.schoolrecsys.repository.RequirementRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RequirementRepositoryImpl extends Neo4jAbstractRepository<Requirement> implements RequirementRepository {
    public RequirementRepositoryImpl() {
        super(Requirement.class);
    }

    @Override
    public List<Requirement> findByGroup(String groupName) {
        Map values = new HashMap();
        values.put("name", groupName);
        return super.query("match(n:Requirement)-[i:IN]->(g:RequirementGroup) WHERE g.name={name} return n", values);
    }
}
