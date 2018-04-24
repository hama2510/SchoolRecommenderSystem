package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.datamodel.DataModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RequirementGroupDto implements DataModel {
    private Long id;
    private String name;
    private boolean active;
    private String type;
    private int index;
    private List<RequirementDto> requirements = new ArrayList<>();

    @Override
    public String getKey() {
        return String.valueOf(id);
    }
}
