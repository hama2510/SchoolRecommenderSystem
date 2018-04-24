package com.hust.kien.schoolrecsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hust.kien.schoolrecsys.dto.RequirementDto;
import com.hust.kien.schoolrecsys.repository.RequirementRepository;
import com.hust.kien.schoolrecsys.service.converter.JobRequirementConverter;

import java.util.List;

@Service
public class JobRequirementService {
    @Autowired
    RequirementRepository requirementRepository;
    JobRequirementConverter jobRequirementConverter;

    @Autowired
    public void setJobRequirementConverter(JobRequirementConverter jobRequirementConverter) {
        this.jobRequirementConverter = jobRequirementConverter;
    }

    public void add(RequirementDto jobRequirement) {
        requirementRepository.save(jobRequirementConverter.toEntity(jobRequirement));
    }

    public void edit(RequirementDto jobRequirement) {
        requirementRepository.save(jobRequirementConverter.toEntity(jobRequirement));
    }

    public List<RequirementDto> findAll() {
        return jobRequirementConverter.toDto(requirementRepository.findAll());
    }

    public void delete(RequirementDto requirement) {
        requirementRepository.delete(jobRequirementConverter.toEntity(requirement));
    }

    public List<RequirementDto> findByGroup(String groupName) {
        return jobRequirementConverter.toDto(requirementRepository.findByGroup(groupName));
    }

    public RequirementDto find(Long id){
        return jobRequirementConverter.toDto(requirementRepository.find(id));
    }
}
