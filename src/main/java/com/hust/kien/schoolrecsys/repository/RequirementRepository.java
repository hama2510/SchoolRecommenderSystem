package com.hust.kien.schoolrecsys.repository;

import com.hust.kien.schoolrecsys.entity.Requirement;

import java.util.List;

public interface RequirementRepository extends BaseRepository<Requirement> {
    List<Requirement> findByGroup(String groupName);
}
