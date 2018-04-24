package com.hust.kien.schoolrecsys.repository;

import com.hust.kien.schoolrecsys.entity.School;

public interface SchoolRepository extends BaseRepository<School> {
    void deleteAgency(Long school, Long agency);
    void deleteSubject(Long school, Long subjectRelationship);
}
