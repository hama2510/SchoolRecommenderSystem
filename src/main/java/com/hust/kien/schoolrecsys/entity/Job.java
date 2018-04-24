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

import static org.neo4j.ogm.annotation.Relationship.INCOMING;
import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

@NodeEntity
@Getter
@Setter
public class Job extends AbstractEntity {

    @Index(unique = true)
    @Property(name = "name")
    private String name;
    @Index(unique = true)
    @Property(name = "description")
    private String description;
    @Relationship(type = "REQUIRES")
    private List<JobRequireRelationship> requirements = new ArrayList<>();
    @Property(name = "career")
    private String career;
    @Property(name = "image_src")
    private String imageSrc;
    @Property(name = "bright_outlook")
    private boolean brightOutlook;
    @Relationship(type = "EQUIVALENT")
    private List<Subject> subjects = new ArrayList<>();
    @Relationship(type = "HAS_TAG")
    private List<Tag> tags = new ArrayList<>();
}
