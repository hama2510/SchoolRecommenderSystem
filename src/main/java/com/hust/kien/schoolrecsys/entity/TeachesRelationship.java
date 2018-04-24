package com.hust.kien.schoolrecsys.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "TEACHES")
@Getter
@Setter
public class TeachesRelationship extends AbstractEntity {
    private Integer cost;
    private Integer period;
    private String type;
    @StartNode
    private School school;
    @EndNode
    private Subject subject;
}
