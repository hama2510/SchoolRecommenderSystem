package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.datamodel.DataModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SubjectDto implements DataModel {
    private Long id;
    private String name;
    private String group;
    private String type;
    private List<SchoolDto> schools = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getKey() {
        return String.valueOf(id);
    }
}
