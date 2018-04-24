package com.hust.kien.schoolrecsys.repository;

import com.hust.kien.schoolrecsys.entity.Subject;

import java.util.List;

public interface SubjectRepository extends BaseRepository<Subject> {
    List<Subject> searchByName(String name);

    List<Subject> searchByType(String type);

    List<Subject> searchByTypeAndName(String type, String name);

    Subject findByTypeAndName(String type, String name);

    List<String> getAllName();
}
