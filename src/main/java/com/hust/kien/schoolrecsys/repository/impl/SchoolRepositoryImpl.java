package com.hust.kien.schoolrecsys.repository.impl;

import org.springframework.stereotype.Repository;
import com.hust.kien.schoolrecsys.entity.School;
import com.hust.kien.schoolrecsys.repository.SchoolRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SchoolRepositoryImpl extends Neo4jAbstractRepository<School> implements SchoolRepository {
    public SchoolRepositoryImpl() {
        super(School.class);
    }

    @Override
    public School find(Long id) {
        return super.find(id, 2);
    }

    @Override
    public List<School> findAll() {
        return super.findAll(2);
    }

    @Override
    public void deleteAgency(Long school, Long agency) {
        Map values = new HashMap();
        values.put("school", school);
        values.put("agency", agency);
        super.query("MATCH(s:School)-[h:HAS_AGENCY]-(a:SchoolAgency) WHERE ID(s)={school} AND ID(a)={agency} DETACH DELETE a", values);
    }

    @Override
    public void deleteSubject(Long school, Long subjectRelationship) {
        Map values = new HashMap();
        values.put("school", school);
        values.put("relationship", subjectRelationship);
        super.query("MATCH(s:School)-[r:TEACHES]-(sb:Subject) WHERE ID(s)={school} AND ID(r)={relationship} DETACH DELETE r", values);

    }
}
