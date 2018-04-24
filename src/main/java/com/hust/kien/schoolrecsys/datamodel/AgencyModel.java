package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.SortOrder;
import com.hust.kien.schoolrecsys.dto.SchoolAgencyDto;

import java.util.List;

public class AgencyModel extends AbstractDataModel<SchoolAgencyDto>{
    public AgencyModel(List<SchoolAgencyDto> list) {
        super(list);
    }

    @Override
    protected AbstractSorter getSorter(String sortField, SortOrder sortOrder) {
        return null;
    }
}
