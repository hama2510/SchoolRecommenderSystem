package com.hust.kien.schoolrecsys.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NodeEntity
@Getter
@Setter
public class Requirement extends AbstractEntity {
    @Index(unique = true)
    @Property(name = "name")
    private String name;
    @Property(name = "description")
    private String description;
    @Relationship(type = "REQUIRES", direction = Relationship.INCOMING)
    private List<Job> jobs = new ArrayList<>();
    @Relationship(type = "IN")
    private List<RequirementGroup> groups = new ArrayList<>();
}
