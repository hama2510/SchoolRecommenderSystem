package com.hust.kien.schoolrecsys.repository;

import com.hust.kien.schoolrecsys.entity.Tag;

import java.util.List;

public interface TagRepository extends BaseRepository<Tag> {
    Tag findByName(String name);

    List<Tag> searchByName(String name);
}
