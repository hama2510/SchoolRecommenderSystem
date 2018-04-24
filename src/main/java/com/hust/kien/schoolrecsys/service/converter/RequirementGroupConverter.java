package com.hust.kien.schoolrecsys.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;
import com.hust.kien.schoolrecsys.entity.RequirementGroup;

@Service
public class RequirementGroupConverter extends Converter<RequirementGroup, RequirementGroupDto> {
    @Autowired
    JobRequirementConverter jobRequirementConverter;

    @Override
    public RequirementGroupDto toDto(RequirementGroup entity) {
        RequirementGroupDto dto = new RequirementGroupDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setActive(entity.isActive());
        dto.setIndex(entity.getIndex());
        dto.setType(entity.getType());
        dto.setRequirements(jobRequirementConverter.toDto(entity.getRequirements()));
        return dto;
    }

    @Override
    public RequirementGroup toEntity(RequirementGroupDto dto) {
        RequirementGroup group = new RequirementGroup();
        group.setId(dto.getId());
        group.setName(dto.getName());
        group.setActive(dto.isActive());
        group.setIndex(dto.getIndex());
        group.setType(dto.getType());
        return group;
    }
}
