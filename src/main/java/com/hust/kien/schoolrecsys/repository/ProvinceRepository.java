package com.hust.kien.schoolrecsys.repository;

import com.hust.kien.schoolrecsys.entity.Province;

public interface ProvinceRepository extends BaseRepository<Province> {
    Province findByName(String name);
}
