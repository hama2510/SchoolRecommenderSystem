package com.hust.kien.schoolrecsys.service.converter;

import com.hust.kien.schoolrecsys.repository.TagRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hust.kien.schoolrecsys.common.util.StringUtil;
import com.hust.kien.schoolrecsys.dto.JobDto;
import com.hust.kien.schoolrecsys.dto.JobRequirementDto;
import com.hust.kien.schoolrecsys.dto.RequirementDto;
import com.hust.kien.schoolrecsys.dto.SubjectDto;
import com.hust.kien.schoolrecsys.entity.Job;
import com.hust.kien.schoolrecsys.entity.JobRequireRelationship;
import com.hust.kien.schoolrecsys.entity.School;
import com.hust.kien.schoolrecsys.entity.Subject;
import com.hust.kien.schoolrecsys.repository.JobRepository;
import com.hust.kien.schoolrecsys.repository.ProvinceRepository;

import java.util.Iterator;

@Service
public class JobConverter extends Converter<Job, JobDto> {

    @Autowired
    private JobRequirementConverter jobRequirementConverter;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    SubjectConverter subjectConverter;
    @Autowired
    ProvinceRepository provinceRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagConverter tagConverter;

    @Override
    public JobDto toDto(Job entity) {
        if (entity != null) {
            JobDto job = new JobDto();
            job.setId(entity.getId());
            job.setName(entity.getName());
            job.setDescription(entity.getDescription());
            job.setCareer(entity.getCareer());
            job.setBrightOutlook(entity.isBrightOutlook());
            Document document = Jsoup.parse(entity.getDescription());
            String description = document.text();
            if (entity.getDescription() != null) {
                int end = description.indexOf(".");
                if (end > 0) {
                    job.setSummary(description.substring(0, end) + "...");
                } else {
                    job.setSummary(description);
                }
            }
            if (!StringUtil.isBlank(entity.getImageSrc())) {
                job.setImageSrc(entity.getImageSrc());
            } else {
                job.setImageSrc("/resources/images/static/job.jpg");
            }
            entity.getRequirements().forEach((item) -> {
                JobRequirementDto requirementDto = new JobRequirementDto();
                requirementDto.setRequirement(jobRequirementConverter.toDto(item.getRequirement()));
                job.getRequirements().add(jobRequirementConverter.toDto(item.getRequirement()));
            });
            Iterator<Subject> iterator = entity.getSubjects().iterator();
            while (iterator.hasNext()) {
                Subject subject = iterator.next();
                job.getSubject().add(subjectConverter.toDto(subject));
                subject.getSchools().forEach((item) -> {
                    JobDto.School school = new JobDto.School();
                    school.setId(item.getSchool().getId());
                    school.setName(item.getSchool().getName());
                    if (!job.getSchools().contains(school))
                        job.getSchools().add(school);
                });
            }
            entity.getTags().forEach((item) ->
                    job.getTags().add(tagConverter.toDto(item)));
            return job;
        } else {
            return null;
        }
    }

    @Override
    public Job toEntity(JobDto dto) {
        if (dto != null) {
            Job job = null;
            if (dto.getId() != null) {
                job = jobRepository.find(dto.getId());
            }
            if (job == null) {
                job = new Job();
            }
            job.setId(dto.getId());
            job.setName(dto.getName());
            job.setDescription(dto.getDescription());
            job.setCareer(dto.getCareer());
            job.setBrightOutlook(dto.isBrightOutlook());
            for (RequirementDto item : dto.getRequirements()) {
                JobRequireRelationship jobRequireRelationship = new JobRequireRelationship();
                jobRequireRelationship.setJob(job);
                jobRequireRelationship.setRequirement(jobRequirementConverter.toEntity(item));
                job.getRequirements().add(jobRequireRelationship);
            }
            job.setImageSrc(dto.getImageSrc());
            return job;
        } else {
            return null;
        }
    }
}
