package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupTypeDto {
    private String name;
    private String value;

    public GroupTypeDto() {
    }

    public GroupTypeDto(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
