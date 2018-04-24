package com.hust.kien.schoolrecsys.datamodel;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.*;

public abstract class AbstractDataModel<T extends DataModel> extends LazyDataModel<T> {
    private List<T> list;

    public AbstractDataModel(List<T> list) {
        this.list = list;
    }

    @Override
    public Object getRowKey(T p) {
        return p.getKey();
    }

    @Override
    public T getRowData(String key) {
        for (T item : list) {
            if (item.getKey().equals(key)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<T> data = filteredList(filters);

        if (sortField != null) {
            sort(sortField, data, sortOrder);
        }

        int dataSize = data.size();
        this.setRowCount(dataSize);

        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }

    }

    protected List<T> filteredList(Map<String, Object> filters) {
        List<T> data = new ArrayList<>();
        for (T item : list) {
            boolean match = true;
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field f = item.getClass().getDeclaredField(filterProperty);
                        f.setAccessible(true);
                        String fieldValue = String.valueOf(f.get(item));
                        if (filterValue == null ||
                                fieldValue.toLowerCase().contains(filterValue.toString().toLowerCase())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }
            if (match) {
                data.add(item);
            }
        }
        return data;
    }

    private void sort(String sortField, List<T> data, SortOrder sortOrder) {
        AbstractSorter sorter = getSorter(sortField, sortOrder);
        if (sorter != null) {
            Collections.sort(data, sorter);
        }
    }

    protected abstract AbstractSorter getSorter(String sortField, SortOrder sortOrder);

    public void remove(T obj) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).getKey().equals(obj.getKey())) {
                list.remove(i);
                break;
            }
        }
    }

    public void add(T obj) {
        list.add(obj);
    }
}
