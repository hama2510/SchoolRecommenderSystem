package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JobSearchDto {
    private String name;
//    private List<String> careers = new ArrayList<>();
    private List<Long> tags = new ArrayList<>();
//    private List<String> terms = new ArrayList<>();
}
