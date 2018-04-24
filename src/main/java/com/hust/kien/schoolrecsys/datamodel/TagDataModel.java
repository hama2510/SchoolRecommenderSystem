package com.hust.kien.schoolrecsys.datamodel;

import com.hust.kien.schoolrecsys.dto.TagDto;
import org.primefaces.model.SortOrder;

import java.util.List;

public class TagDataModel extends AbstractDataModel<TagDto> {
    public TagDataModel(List<TagDto> list) {
        super(list);
    }

    @Override
    protected AbstractSorter getSorter(String sortField, SortOrder sortOrder) {
        return null;
    }
}
