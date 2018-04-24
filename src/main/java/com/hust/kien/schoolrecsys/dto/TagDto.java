package com.hust.kien.schoolrecsys.dto;

import com.hust.kien.schoolrecsys.datamodel.DataModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto implements DataModel {
    private Long id;
    private String name;

    public TagDto() {
    }

    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getKey() {
        return String.valueOf(id);
    }
}
