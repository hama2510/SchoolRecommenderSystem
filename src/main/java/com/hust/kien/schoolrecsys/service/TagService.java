package com.hust.kien.schoolrecsys.service;

import com.hust.kien.schoolrecsys.dto.TagDto;
import com.hust.kien.schoolrecsys.entity.Tag;
import com.hust.kien.schoolrecsys.repository.TagRepository;
import com.hust.kien.schoolrecsys.service.converter.TagConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagConverter tagConverter;

    public List<TagDto> findAll() {
        List<TagDto> tags = new ArrayList<>();
        tagRepository.findAll().forEach((item) ->
                tags.add(tagConverter.toDto(item))
        );
        return tags;
    }

    public List<TagDto> searchByName(String name) {
        List<TagDto> tags = new ArrayList<>();
        tagRepository.searchByName(name).forEach((item) ->
                tags.add(tagConverter.toDto(item))
        );
        return tags;
    }

    public void delete(TagDto tag) {
        Tag t = new Tag();
        t.setName(tag.getName());
        t.setId(tag.getId());
        tagRepository.delete(t);
    }

    public TagDto findByName(String name) {
        return tagConverter.toDto(tagRepository.findByName(name));
    }

}
