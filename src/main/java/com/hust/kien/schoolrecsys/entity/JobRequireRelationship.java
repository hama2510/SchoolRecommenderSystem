package com.hust.kien.schoolrecsys.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "REQUIRES")
@Getter
@Setter
public class JobRequireRelationship extends AbstractEntity {
    @StartNode
    private Job job;
    @EndNode
    private Requirement requirement;
}
