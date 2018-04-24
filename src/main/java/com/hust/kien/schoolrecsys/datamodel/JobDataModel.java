package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.SortOrder;
import com.hust.kien.schoolrecsys.dto.JobDto;

import java.util.List;

public class JobDataModel extends AbstractDataModel<JobDto> {

    public JobDataModel(List<JobDto> list) {
        super(list);
    }

    @Override
    protected AbstractSorter getSorter(String sortField, SortOrder sortOrder) {
        return new JobSorter(sortField, sortOrder);
    }

    public class JobSorter extends AbstractSorter<JobDto> {
        public JobSorter(String sortField, SortOrder sortOrder) {
            super(sortField, sortOrder);
        }

        @Override
        protected Class<JobDto> getSortedClass() {
            return JobDto.class;
        }
    }
}