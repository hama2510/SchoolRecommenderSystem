package com.hust.kien.schoolrecsys.dto;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.datamodel.DataModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JobDto implements DataModel {
    private Long id;
    private String name;
    private String description;
    private List<RequirementDto> requirements = new ArrayList();
    private String career;
    private String summary;
    private String imageSrc;
    private boolean brightOutlook;
    private List<School> schools = new ArrayList<>();
    private List<SubjectDto> subject = new ArrayList<>();
    private List<TagDto> tags = new ArrayList<>();

    @Override
    public String getKey() {
        return String.valueOf(id);
    }

    @Override
    public String toString() {
        return name;
    }

    @Getter
    @Setter
    public static class School {
        private Long id;
        private String name;

        @Override
        public boolean equals(Object obj) {
            return id.equals(((School) obj).id);
        }
    }
}
