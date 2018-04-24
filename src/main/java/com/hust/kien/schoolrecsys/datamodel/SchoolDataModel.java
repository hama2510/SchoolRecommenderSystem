package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.SortOrder;
import com.hust.kien.schoolrecsys.dto.SchoolDto;

import java.util.List;

public class SchoolDataModel extends AbstractDataModel<SchoolDto> {
    public SchoolDataModel(List<SchoolDto> list) {
        super(list);
    }

    @Override
    protected AbstractSorter getSorter(String sortField, SortOrder sortOrder) {
        return new Sorter(sortField, sortOrder);
    }

    public class Sorter extends AbstractSorter<SchoolDto> {

        public Sorter(String sortField, SortOrder sortOrder) {
            super(sortField, sortOrder);
        }

        @Override
        protected Class<SchoolDto> getSortedClass() {
            return SchoolDto.class;
        }
    }
}
