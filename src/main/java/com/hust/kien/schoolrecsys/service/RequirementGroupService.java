package com.hust.kien.schoolrecsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;
import com.hust.kien.schoolrecsys.repository.RequirementGroupRepository;
import com.hust.kien.schoolrecsys.service.converter.RequirementGroupConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RequirementGroupService {
    @Autowired
    RequirementGroupRepository requirementGroupRepository;
    @Autowired
    RequirementGroupConverter requirementGroupConverter;

    public RequirementGroupDto find(Long id){
        return requirementGroupConverter.toDto(requirementGroupRepository.find(id));
    }

    public List<RequirementGroupDto> findAll() {
        List<RequirementGroupDto> groups = requirementGroupConverter.toDto(requirementGroupRepository.findAll());
        Collections.sort(groups, new Comparator<RequirementGroupDto>() {
            @Override
            public int compare(final RequirementGroupDto object1, final RequirementGroupDto object2) {
                return object1.getIndex() - object2.getIndex();
            }
        });
        return groups;
    }

    public List<RequirementGroupDto> findActiveGroup() {
        List<RequirementGroupDto> groups = new ArrayList<>();
        List<RequirementGroupDto> list = findAll();
        list.forEach((item) -> {
            if (item.isActive()) {
                groups.add(item);
            }
        });
        return groups;
    }

    public void delete(RequirementGroupDto group) {
        requirementGroupRepository.delete(requirementGroupConverter.toEntity(group));
    }

    public void add(RequirementGroupDto groupDto) {
        groupDto.setActive(false);
        groupDto.setIndex(requirementGroupRepository.getMaxIndex() + 1);
        requirementGroupRepository.save(requirementGroupConverter.toEntity(groupDto));
    }

    public void edit(RequirementGroupDto groupDto) {
        requirementGroupRepository.save(requirementGroupConverter.toEntity(groupDto));
    }

    public int getNoRequirement(Long id) {
        return requirementGroupRepository.find(id).getRequirements().size();
    }

}
