package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.datamodel.DataModel;

@Getter
@Setter
public class SchoolSubjectDto implements DataModel {
    private Long id;
    private SubjectDto subject;
    private Integer cost;
    private Integer period;
    private String type;

    @Override
    public String getKey() {
        return subject.getKey();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SchoolSubjectDto) {
            SchoolSubjectDto schoolSubjectDto = (SchoolSubjectDto) obj;
            if (schoolSubjectDto.subject.getId().equals(subject.getId())) {
                return true;
            }
        }
        return false;
    }
}
