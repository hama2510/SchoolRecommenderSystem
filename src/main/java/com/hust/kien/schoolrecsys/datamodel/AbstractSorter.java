package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.Comparator;

public abstract class AbstractSorter<T> implements Comparator<T> {
    private final String sortField;
    private final SortOrder sortOrder;

    public AbstractSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    protected abstract Class<T> getSortedClass();

    @Override
    public int compare(T o1, T o2) {
        try {
            Field f = getSortedClass().getDeclaredField(this.sortField);
            f.setAccessible(true);
            Object value1 = f.get(o1);
            Object value2 = f.get(o2);
            int value = 0;
            if (value1 != null && value2 != null)
                value = ((Comparable) value1).compareTo(value2);
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
