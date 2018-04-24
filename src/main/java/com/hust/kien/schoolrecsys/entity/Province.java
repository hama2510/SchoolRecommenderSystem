package com.hust.kien.schoolrecsys.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
@Getter
@Setter
public class Province extends AbstractEntity{
    private String name;
    @Relationship(type = "LOCATE_AT", direction = INCOMING)
    private List<SchoolAgency> agencies = new ArrayList<>();

    public Province() {
    }

    public Province(String name) {
        this.name = name;
    }
}
