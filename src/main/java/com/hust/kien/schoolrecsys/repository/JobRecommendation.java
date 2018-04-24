package com.hust.kien.schoolrecsys.repository;

import com.hust.kien.schoolrecsys.entity.RecommendedJob;

import java.util.List;

public interface JobRecommendation {
    List<RecommendedJob> recommend(List<Long> requirements);
}
