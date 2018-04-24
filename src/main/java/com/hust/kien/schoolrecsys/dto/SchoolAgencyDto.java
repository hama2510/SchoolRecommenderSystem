package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.datamodel.DataModel;

@Getter
@Setter
public class SchoolAgencyDto implements DataModel {
    private Long id;
    private String phone;
    private String address;
    private String province;

    @Override
    public String getKey() {
        return String.valueOf(id);
    }
}
