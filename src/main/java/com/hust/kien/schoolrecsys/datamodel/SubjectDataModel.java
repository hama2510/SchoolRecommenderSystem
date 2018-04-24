package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.SortOrder;
import com.hust.kien.schoolrecsys.dto.SubjectDto;

import java.util.List;

public class SubjectDataModel extends AbstractDataModel<SubjectDto> {
    public SubjectDataModel(List<SubjectDto> list) {
        super(list);
    }

    @Override
    protected AbstractSorter getSorter(String sortField, SortOrder sortOrder) {
        return null;
    }
}
