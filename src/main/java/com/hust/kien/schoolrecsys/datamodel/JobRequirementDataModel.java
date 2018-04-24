package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.SortOrder;
import com.hust.kien.schoolrecsys.dto.RequirementDto;

import java.util.List;

public class JobRequirementDataModel extends AbstractDataModel<RequirementDto> {
    public JobRequirementDataModel(List<RequirementDto> list) {
        super(list);
    }

    @Override
    protected AbstractSorter getSorter(String sortField, SortOrder sortOrder) {
        return new Sorter(sortField, sortOrder);
    }

    public class Sorter extends AbstractSorter<RequirementDto> {

        public Sorter(String sortField, SortOrder sortOrder) {
            super(sortField, sortOrder);
        }

        @Override
        protected Class<RequirementDto> getSortedClass() {
            return RequirementDto.class;
        }
    }
}
