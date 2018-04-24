package com.hust.kien.schoolrecsys.repository;

import com.hust.kien.schoolrecsys.dto.JobSearchDto;
import com.hust.kien.schoolrecsys.entity.Job;

import java.util.List;

public interface JobRepository extends BaseRepository<Job> {
    List<Job> search(JobSearchDto searchObject);

    List<Job> searchRelated(List<String> terms);

    void removeSubject(Long job, Long subject);

    void removeTag(Long job, Long tag);
}
