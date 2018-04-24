package com.hust.kien.schoolrecsys.repository;

import com.hust.kien.schoolrecsys.entity.RequirementGroup;

public interface RequirementGroupRepository extends BaseRepository<RequirementGroup> {

    RequirementGroup findByName(String name);

    Integer getMaxIndex();
}
