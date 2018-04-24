package com.hust.kien.schoolrecsys.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@Setter
@Getter
public class Subject extends AbstractEntity {
    @Property(name = "name")
    String name;
    @Property(name = "group")
    String group;
    @Property(name = "type")
    private String type;
    @Relationship(type = "EQUIVALENT", direction = INCOMING)
    private List<Job> jobs = new ArrayList<>();
    @Relationship(type = "TEACHES", direction = INCOMING)
    private List<TeachesRelationship> schools = new ArrayList<>();
}
