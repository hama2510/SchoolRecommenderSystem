package com.hust.kien.schoolrecsys.service.converter;

import org.springframework.stereotype.Service;
import com.hust.kien.schoolrecsys.dto.SubjectDto;
import com.hust.kien.schoolrecsys.entity.Subject;

@Service
public class SubjectConverter extends Converter<Subject, SubjectDto> {
    @Override
    public SubjectDto toDto(Subject entity) {
        SubjectDto subject = new SubjectDto();
        subject.setId(entity.getId());
        subject.setGroup(entity.getGroup());
        subject.setName(entity.getName());
        subject.setType(entity.getType());
        return subject;
    }

    @Override
    public Subject toEntity(SubjectDto dto) {
        Subject subject = new Subject();
        subject.setId(dto.getId());
        subject.setGroup(dto.getGroup());
        subject.setName(dto.getName());
        subject.setType(dto.getType());
        return subject;
    }
}
