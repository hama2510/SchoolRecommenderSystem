package com.hust.kien.schoolrecsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.hust.kien.schoolrecsys.dto.SchoolAgencyDto;
import com.hust.kien.schoolrecsys.dto.SchoolDto;
import com.hust.kien.schoolrecsys.dto.SchoolSubjectDto;
import com.hust.kien.schoolrecsys.entity.Province;
import com.hust.kien.schoolrecsys.entity.School;
import com.hust.kien.schoolrecsys.entity.SchoolAgency;
import com.hust.kien.schoolrecsys.entity.TeachesRelationship;
import com.hust.kien.schoolrecsys.repository.ProvinceRepository;
import com.hust.kien.schoolrecsys.repository.SchoolRepository;
import com.hust.kien.schoolrecsys.repository.SubjectRepository;
import com.hust.kien.schoolrecsys.service.converter.SchoolConverter;

import java.util.List;

@Service
public class SchoolService {

    @Autowired
    private SchoolConverter schoolConverter;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public void add(SchoolDto schoolDto) {
        School school = schoolConverter.toEntity(schoolDto);
        schoolRepository.save(school);
        schoolDto.setId(school.getId());
    }

    public void update(SchoolDto schoolDto) {
        schoolRepository.save(schoolConverter.toEntity(schoolDto));
    }

    public void addSubject(SchoolDto schoolDto, SchoolSubjectDto subject) {
        School school = schoolRepository.find(schoolDto.getId());
        TeachesRelationship relationship = new TeachesRelationship();
        relationship.setCost(subject.getCost());
        relationship.setPeriod(subject.getPeriod());
        relationship.setType(subject.getType());
        relationship.setSubject(subjectRepository.find(subject.getSubject().getId()));
        relationship.setSchool(school);
        school.getSubjects().add(relationship);
        schoolRepository.save(school, 2);
        subject.setId(relationship.getId());
    }

    public void addAgency(SchoolDto schoolDto, SchoolAgencyDto agencyDto) {
        School school = schoolRepository.find(schoolDto.getId());
        SchoolAgency agency = new SchoolAgency();
        agency.setPhone(agencyDto.getPhone());
        agency.setAddress(agencyDto.getAddress());
        Province province = provinceRepository.findByName(agencyDto.getProvince());
        agency.getProvinces().add(province);
        agency.getSchools().add(school);
        school.getAgencies().add(agency);
        schoolRepository.save(school, 3);
        agencyDto.setId(agency.getId());
    }

    public void deleteAgency(SchoolDto schoolDto, SchoolAgencyDto agencyDto) {
        schoolRepository.deleteAgency(schoolDto.getId(), agencyDto.getId());
    }

    public void deleteSubject(SchoolDto schoolDto, SchoolSubjectDto subjectDto) {
        schoolRepository.deleteSubject(schoolDto.getId(), subjectDto.getId());
    }


    public void delete(SchoolDto schoolDto) {
        schoolRepository.delete(schoolConverter.toEntity(schoolDto));
    }

    public SchoolDto find(Long id) {
        return schoolConverter.toDto(schoolRepository.find(id));
    }

    public List<SchoolDto> findAll() {
        return schoolConverter.toDto(schoolRepository.findAll());
    }

    public boolean isValid(SchoolDto school) {
        if (StringUtils.isEmpty(school.getName())) {
            return false;
        } else {
            return true;
        }
    }
}
