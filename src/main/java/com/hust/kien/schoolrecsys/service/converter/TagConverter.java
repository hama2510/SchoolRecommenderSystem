package com.hust.kien.schoolrecsys.service.converter;

import com.hust.kien.schoolrecsys.dto.TagDto;
import com.hust.kien.schoolrecsys.entity.Tag;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class TagConverter extends Converter<Tag, TagDto> {
    @Override
    public TagDto toDto(Tag entity) {
        if (entity != null)
            return new TagDto(entity.getId(), entity.getName());
        return null;
    }

    @Override
    public Tag toEntity(TagDto dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }
}
