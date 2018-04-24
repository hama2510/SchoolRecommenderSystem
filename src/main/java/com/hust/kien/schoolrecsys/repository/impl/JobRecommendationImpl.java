package com.hust.kien.schoolrecsys.repository.impl;

import org.neo4j.ogm.model.Result;
import org.springframework.stereotype.Repository;
import com.hust.kien.schoolrecsys.entity.Job;
import com.hust.kien.schoolrecsys.entity.RecommendedJob;
import com.hust.kien.schoolrecsys.repository.JobRecommendation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class JobRecommendationImpl extends Neo4jAbstractRepository<RecommendedJob> implements JobRecommendation {
    protected JobRecommendationImpl() {
        super(RecommendedJob.class);
    }

    @Override
    public List<RecommendedJob> recommend(List<Long> requirements) {
        Map values = new HashMap();
        values.put("requirements", requirements);
        String query = "MATCH(j:Job)-[r:REQUIRES]->(re:Requirement) WHERE ID(re) IN {requirements} \n" +
                "WITH j, count(re) AS intersection\n" +
                "MATCH (j:Job)-[:REQUIRES]->(other:Requirement)\n" +
                "WITH j, intersection, SIZE(COLLECT(other.name))  AS othersetsize\n" +
                "WITH j, ((1.0*intersection)/(othersetsize)) as point\n" +
                "return j, point ORDER BY point DESC";
        Result result = super.getSession()
                .query(query, values);
        Logger.getLogger(getClass().getName()).log(Level.INFO, query);
        Iterable<Map<String, Object>> i = result.queryResults();
        List<RecommendedJob> jobs = new ArrayList<>();
        i.forEach((item) -> {
            RecommendedJob job = new RecommendedJob();
            job.setJob((Job) item.get("j"));
            job.setPoint((Double) item.get("point"));
            jobs.add(job);
        });
        return jobs;
    }
}
