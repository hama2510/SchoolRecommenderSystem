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
public class SchoolAgency extends AbstractEntity {
    private String phone;
    private String address;
    @Relationship(type = "LOCATE_AT")
    private List<Province> provinces = new ArrayList<>();
    @Relationship(type = "HAS_AGENCY", direction = INCOMING)
    private List<School> schools = new ArrayList<>();
}
