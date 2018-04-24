package com.hust.kien.schoolrecsys.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NodeEntity
@Getter
@Setter
public class School extends AbstractEntity {
    private String name;
    private String website;
    private String email;
    @Relationship(type = "HAS_AGENCY")
    private List<SchoolAgency> agencies = new ArrayList<>();
    @Relationship(type = "TEACHES")
    private List<TeachesRelationship> subjects = new ArrayList<>();
}