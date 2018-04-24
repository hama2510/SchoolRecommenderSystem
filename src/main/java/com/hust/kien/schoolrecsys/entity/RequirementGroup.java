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

@Getter
@Setter
@NodeEntity
public class RequirementGroup extends AbstractEntity {
    @Index(unique = true)
    @Property(name = "name")
    private String name;
    @Property(name = "active")
    private boolean active;
    @Property(name = "index")
    private int index;
    @Property(name = "type")
    private String type;
    @Relationship(type = "IN", direction = INCOMING)
    private List<Requirement> requirements = new ArrayList<>();
}
