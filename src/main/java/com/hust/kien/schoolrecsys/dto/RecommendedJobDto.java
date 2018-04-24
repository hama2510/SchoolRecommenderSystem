package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendedJobDto {
    private JobDto job;
    private String point;
}
