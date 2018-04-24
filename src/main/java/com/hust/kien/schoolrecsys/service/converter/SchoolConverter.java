package com.hust.kien.schoolrecsys.service.converter;

import com.hust.kien.schoolrecsys.dto.SchoolAgencyDto;
import com.hust.kien.schoolrecsys.dto.SchoolDto;
import com.hust.kien.schoolrecsys.dto.SchoolSubjectDto;
import com.hust.kien.schoolrecsys.dto.SubjectDto;
import com.hust.kien.schoolrecsys.entity.School;
import com.hust.kien.schoolrecsys.entity.TeachesRelationship;
import com.hust.kien.schoolrecsys.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchoolConverter extends Converter<School, SchoolDto> {

    @Autowired
    private SubjectConverter subjectConverter;
    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public SchoolDto toDto(School entity) {
        SchoolDto school = new SchoolDto();
        if (entity != null) {
            school.setId(entity.getId());
            school.setName(entity.getName());
            school.setEmail(entity.getEmail());
            school.setWebsite(entity.getWebsite());
            entity.getSubjects().forEach((item) -> {
                SchoolSubjectDto subject = new SchoolSubjectDto();
                subject.setId(item.getId());
                subject.setSubject(subjectConverter.toDto(item.getSubject()));
                subject.setCost(item.getCost());
                subject.setPeriod(item.getPeriod());
                subject.setType(item.getType());
                school.getSubjects().add(subject);
            });
            entity.getAgencies().forEach((item) -> {
                SchoolAgencyDto agency = new SchoolAgencyDto();
                agency.setId(item.getId());
                agency.setAddress(item.getAddress());
                agency.setPhone(item.getPhone());
                agency.setProvince(item.getProvinces().iterator().next().getName());
                school.getAgencies().add(agency);
            });
        }
        return school;
    }

    @Override
    public School toEntity(SchoolDto dto) {
        School school = new School();
        if (dto.getId() != null) {
            school = schoolRepository.find(dto.getId());
        }
        school.setName(dto.getName());
        school.setEmail(dto.getEmail());
        school.setWebsite(dto.getWebsite());
        for (SchoolSubjectDto item : dto.getSubjects()) {
            TeachesRelationship job = new TeachesRelationship();
            if (item.getId() != null)
                job.setId(item.getId());
            job.setSubject(subjectConverter.toEntity(item.getSubject()));
            job.setCost(item.getCost());
            job.setPeriod(item.getPeriod());
            job.setType(item.getType());
            job.setSchool(school);
            school.getSubjects().add(job);
        }
        return school;
    }
}