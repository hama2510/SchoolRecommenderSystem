package com.hust.kien.schoolrecsys.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TfIdfService {
    public double calculateIdf(List<String> documents, String word) {
        int size = documents.size();
        int count = 1;
        for (String item : documents) {
            if (item.contains(word)) {
                count++;
            }
        }
        return Math.log(1.0 * size / count);
    }

    public double calculateTf(String sentence, String word) {
        return StringUtils.countOccurrencesOf(sentence, word);
    }

}
