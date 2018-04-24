package com.hust.kien.schoolrecsys.service.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Converter<E, D> {
    public abstract D toDto(E entity);

    public abstract E toEntity(D dto);

    public List<E> toEntity(Collection<D> ds) {
        List<E> entities = new ArrayList();
        if (ds != null) {
            ds.forEach((d) -> {
                entities.add(toEntity(d));
            });
        }
        return entities;
    }

    public List<D> toDto(Collection<E> entities) {
        List<D> dtos = new ArrayList();
        if (entities != null) {
            entities.forEach((e) -> {
                dtos.add(toDto(e));
            });
        }
        return dtos;
    }
}
