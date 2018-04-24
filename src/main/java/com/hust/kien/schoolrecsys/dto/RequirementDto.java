package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.datamodel.DataModel;

@Getter
@Setter
public class RequirementDto implements DataModel {
    private Long id;
    private String name;
    private String description;
    private String group;

    @Override
    public String getKey() {
        return String.valueOf(id);
    }
}
