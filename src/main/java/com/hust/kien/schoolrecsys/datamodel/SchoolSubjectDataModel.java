package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.SortOrder;
import com.hust.kien.schoolrecsys.dto.SchoolSubjectDto;

import java.util.List;

public class SchoolSubjectDataModel extends AbstractDataModel<SchoolSubjectDto> {
    public SchoolSubjectDataModel(List<SchoolSubjectDto> list) {
        super(list);
    }

    @Override
    protected AbstractSorter getSorter(String sortField, SortOrder sortOrder) {
        return null;
    }
}
