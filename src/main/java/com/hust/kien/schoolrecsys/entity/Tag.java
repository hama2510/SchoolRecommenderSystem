package com.hust.kien.schoolrecsys.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@Getter
@Setter
public class Tag extends AbstractEntity {
    private String name;
    @Relationship(type = "HAS_TAG", direction = INCOMING)
    private List<Job> jobs = new ArrayList<>();
}
