package com.hust.kien.schoolrecsys.service;

import com.hust.kien.schoolrecsys.service.converter.TagConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hust.kien.schoolrecsys.dto.*;
import com.hust.kien.schoolrecsys.entity.*;
import com.hust.kien.schoolrecsys.repository.JobRecommendation;
import com.hust.kien.schoolrecsys.repository.JobRepository;
import com.hust.kien.schoolrecsys.repository.RequirementRepository;
import com.hust.kien.schoolrecsys.service.converter.JobConverter;
import com.hust.kien.schoolrecsys.service.converter.JobRequirementConverter;
import com.hust.kien.schoolrecsys.service.converter.SubjectConverter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    RequirementRepository requirementRepository;
    JobConverter jobConverter;
    @Autowired
    JobRecommendation jobRecommendation;
    @Autowired
    JobRequirementConverter jobRequirementConverter;
    @Autowired
    SubjectConverter subjectConverter;
    @Autowired
    TagConverter tagConverter;

    @Autowired
    public void setJobConverter(JobConverter jobConverter) {
        this.jobConverter = jobConverter;
    }

    public void addJob(JobDto job) {
        Job j = jobConverter.toEntity(job);
        jobRepository.save(j);
        job.setId(j.getId());
    }

    public void saveSubject(JobDto jobDto) {
        Job j = jobRepository.find(jobDto.getId());
        j.setSubjects(new ArrayList<>());
        jobDto.getSubject().forEach((item) -> {
            j.getSubjects().add(subjectConverter.toEntity(item));
        });
        jobRepository.save(j);
    }

    public void saveTag(JobDto jobDto) {
        Job j = jobRepository.find(jobDto.getId());
        j.setTags(new ArrayList<>());
        jobDto.getTags().forEach((item) -> {
            j.getTags().add(tagConverter.toEntity(item));
        });
        jobRepository.save(j);
    }

    public void deleteTag(JobDto jobDto, TagDto tagDto) {
        jobRepository.removeTag(jobDto.getId(), tagDto.getId());
    }

    public void deleteSubject(JobDto jobDto, SubjectDto subjectDto) {
        jobRepository.removeSubject(jobDto.getId(), subjectDto.getId());
    }

    public void editJob(JobDto jobDto) {
        Job j = jobRepository.find(jobDto.getId());
        j.setDescription(jobDto.getDescription());
        j.setName(jobDto.getName());
        j.setCareer(jobDto.getCareer());
        if (jobDto.getImageSrc() != null) {
            j.setImageSrc(jobDto.getImageSrc());
        }
        j.setBrightOutlook(jobDto.isBrightOutlook());
        jobDto.getRequirements().forEach((item) -> {
            Requirement r = requirementRepository.find(item.getId());
            JobRequireRelationship requireRelationship = new JobRequireRelationship();
            requireRelationship.setRequirement(r);
            requireRelationship.setJob(j);
            j.getRequirements().add(requireRelationship);
        });
        jobRepository.save(j);
    }

    public List<JobDto> search(JobSearchDto searchJobDto) {
        List<JobDto> result = jobConverter.toDto(jobRepository.search(searchJobDto));
        return result;
    }

    public List<JobDto> searchRelated(List<String> terms) {
        List<JobDto> result = jobConverter.toDto(jobRepository.searchRelated(terms));
        return result;
    }

    public List<JobDto> findAll() {
        return jobConverter.toDto(jobRepository.findAll());
    }

    public void delete(JobDto job) {
        jobRepository.delete(jobConverter.toEntity(job));
    }

    public List<JobDto> searchByName(String name) {
        JobSearchDto searchDto = new JobSearchDto();
        searchDto.setName(name);
        return jobConverter.toDto(jobRepository.search(searchDto));
    }

    public JobDto find(Long id) {
        return jobConverter.toDto(jobRepository.find(id, 2));
    }

    public List<RecommendedJobDto> recommend(List<Long> requirements) {
        List<RecommendedJob> jobs = jobRecommendation.recommend(requirements);
        List<RecommendedJobDto> jobDtos = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");
        jobs.forEach((item) -> {
            RecommendedJobDto job = new RecommendedJobDto();
            job.setJob(jobConverter.toDto(item.getJob()));
            job.setPoint(df.format(100 * item.getPoint()) + "%");
            jobDtos.add(job);
        });
        return jobDtos;
    }
}
