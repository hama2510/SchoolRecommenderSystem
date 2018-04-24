package com.hust.kien.schoolrecsys.repository.impl;

import com.hust.kien.schoolrecsys.common.util.StringUtil;
import com.hust.kien.schoolrecsys.dto.JobSearchDto;
import com.hust.kien.schoolrecsys.entity.Job;
import com.hust.kien.schoolrecsys.repository.JobRepository;
import com.hust.kien.schoolrecsys.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JobRepositoryImpl extends Neo4jAbstractRepository<Job> implements JobRepository {

    @Autowired
    CareerService careerService;

    public JobRepositoryImpl() {
        super(Job.class);
    }

    @Override
    public List<Job> search(JobSearchDto searchObject) {
        Map<String, Object> values = new HashMap();
        String query;
        if (StringUtil.isBlank(searchObject.getName())) {
            values.put("name", ".*.*");
        } else {
            values.put("name", ".*" + searchObject.getName() + ".*");
        }
        if (searchObject.getTags().size() > 0) {
            values.put("tags", searchObject.getTags());
            query = "MATCH(j:Job)-[HAS_TAG]-(t:Tag) WHERE toLower(j.name) =~ toLower({name}) AND ID(t) IN {tags} return j";
        } else {
//            values.put("tags", careerService.findAll());
            query = "MATCH(j:Job) WHERE toLower(j.name) =~ toLower({name}) return j";
        }
        Iterable iterable = getSession().query(Job.class, query, values);
        List<Job> jobs = new ArrayList<>();
        iterable.forEach((item) -> {
            jobs.add((Job) item);
        });
        return jobs;
    }

    @Override
    public List<Job> searchRelated(List<String> terms) {
        StringBuilder query = new StringBuilder("MATCH(j:Job) WHERE ");
        if (terms.size() > 0) {
            Iterator<String> iterator = terms.iterator();
            while (iterator.hasNext()) {
                query.append("toLower(j.name) CONTAINS toLower('").append(iterator.next()).append("')");
                if (iterator.hasNext()) {
                    query.append(" OR ");
                }
            }
        }
        query.append(" return j");
        Iterable iterable = getSession().query(Job.class, query.toString(), new HashMap<>());
        List<Job> jobs = new ArrayList<>();
        iterable.forEach((item) -> {
            jobs.add((Job) item);
        });
        return jobs;
    }

    @Override
    public void removeSubject(Long job, Long subject) {
        Map values = new HashMap();
        values.put("job", job);
        values.put("subject", subject);
        super.query("MATCH(j:Job)-[e:EQUIVALENT]-(s:Subject) WHERE ID(j)={job} AND ID(s)={subject} delete e", values);
    }

    @Override
    public void removeTag(Long job, Long tag) {
        Map values = new HashMap();
        values.put("job", job);
        values.put("tag", tag);
        super.query("MATCH(j:Job)-[h:HAS_TAG]-(t:Tag) WHERE ID(j)={job} AND ID(t)={tag} delete h", values);
    }
}
