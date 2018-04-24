package com.hust.kien.schoolrecsys.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GraphId;

@Getter
@Setter
public abstract class AbstractEntity {
    @GraphId
    Long id;
}
