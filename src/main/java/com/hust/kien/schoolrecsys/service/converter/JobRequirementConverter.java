package com.hust.kien.schoolrecsys.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hust.kien.schoolrecsys.dto.RequirementDto;
import com.hust.kien.schoolrecsys.entity.Requirement;
import com.hust.kien.schoolrecsys.repository.RequirementGroupRepository;
import com.hust.kien.schoolrecsys.repository.RequirementRepository;

@Service
public class JobRequirementConverter extends Converter<Requirement, RequirementDto> {

    @Autowired
    RequirementRepository requirementRepository;
    @Autowired
    RequirementGroupRepository requirementGroupRepository;

    @Override
    public RequirementDto toDto(Requirement entity) {
        if (entity == null)
            return null;
        else {
            RequirementDto jobRequirement = new RequirementDto();
            jobRequirement.setName(entity.getName());
            jobRequirement.setId(entity.getId());
            jobRequirement.setDescription(entity.getDescription());
            if (entity.getGroups().iterator().hasNext()) {
                jobRequirement.setGroup(entity.getGroups().iterator().next().getName());
            } else {
                jobRequirement.setGroup("");
            }
            return jobRequirement;
        }
    }

    @Override
    public Requirement toEntity(RequirementDto dto) {
        if (dto == null)
            return null;
        else {
            Requirement requirement = null;
            if (dto.getId() != null) {
                requirement = requirementRepository.find(dto.getId());
            }
            if (requirement == null) {
                requirement = new Requirement();
            }
            requirement.setDescription(dto.getDescription());
            requirement.setName(dto.getName());
            requirement.setId(dto.getId());
            requirement.getGroups().add(requirementGroupRepository.findByName(dto.getGroup()));
            return requirement;
        }
    }
}
