package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.SortOrder;
import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;

import java.util.List;

public class RequirementGroupDataModel extends AbstractDataModel<RequirementGroupDto> {
    public RequirementGroupDataModel(List<RequirementGroupDto> list) {
        super(list);
    }

    @Override
    protected AbstractSorter getSorter(String sortField, SortOrder sortOrder) {
        return null;
    }
}
