package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.datamodel.DataModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SchoolDto implements DataModel {
    private Long id;
    private String name;
    private String website;
    private String email;
    private List<SchoolAgencyDto> agencies = new ArrayList<>();
    private List<SchoolSubjectDto> subjects = new ArrayList<>();

    @Override
    public String getKey() {
        return String.valueOf(id);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isExistJob(SchoolSubjectDto subjectDto) {
        return subjects.contains(subjectDto);
    }
}
