package com.hust.kien.schoolrecsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hust.kien.schoolrecsys.dto.SubjectDto;
import com.hust.kien.schoolrecsys.repository.SubjectRepository;
import com.hust.kien.schoolrecsys.service.converter.SubjectConverter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectConverter subjectConverter;

    public List<SubjectDto> findAll() {
        return subjectConverter.toDto(subjectRepository.findAll());
    }

    public void add(SubjectDto subjectDto) {
        subjectRepository.save(subjectConverter.toEntity(subjectDto));
    }

    public void delete(SubjectDto subjectDto) {
        subjectRepository.delete(subjectConverter.toEntity(subjectDto));
    }

    public SubjectDto findByTypeAndName(String type, String name) {
        return subjectConverter.toDto(subjectRepository.findByTypeAndName(type, name));
    }


    public List<SubjectDto> searchByName(String name) {
        List<SubjectDto> subjects = subjectConverter.toDto(subjectRepository.searchByName(name));
        return subjects;
    }

    public List<SubjectDto> searchByTypeAndName(String type, String name) {
        List<SubjectDto> subjects = subjectConverter.toDto(subjectRepository.searchByTypeAndName(type, name));
        return subjects;
    }

    public List<String> getSubjectGroups() {
        List<String> groups = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("subject-groups.txt");
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        while (scanner.hasNextLine()) {
            groups.add(scanner.nextLine());
        }
        return groups;
    }

    public List<String> getSubjectTypes() {
        List<String> types = new ArrayList<>();
        types.add("Trung cấp");
        types.add("Sơ cấp");
        return types;
    }
}
