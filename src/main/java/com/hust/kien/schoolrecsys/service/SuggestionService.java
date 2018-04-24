package com.hust.kien.schoolrecsys.service;

import com.hust.kien.schoolrecsys.dto.SubjectDto;
import com.hust.kien.schoolrecsys.entity.Subject;
import com.hust.kien.schoolrecsys.repository.SubjectRepository;
import com.hust.kien.schoolrecsys.service.converter.SubjectConverter;
import com.hust.kien.schoolrecsys.service.vntokenizer.RDRsegmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SuggestionService {

    @Autowired
    private TfIdfService tfIdfService;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private RDRsegmenter segmenter;
    @Autowired
    private SubjectConverter subjectConverter;
//    private Map<SubjectDto, Set<String>> subjects;
//
//    @PostConstruct
//    private void init() {
//        subjects = new HashMap<>();
//        List<Subject> subjectList = subjectRepository.findAll();
//        subjectList.forEach((item) ->
//                subjects.put(subjectConverter.toDto(item), segmenter.segmentTokenizer(item.getName().toLowerCase()  ))
//        );
//    }

    public List<SubjectDto> suggest(String subjectType, String jobName) {
        Set<String> words = segmenter.segmentTokenizer(jobName);
        List<String> documents = subjectRepository.getAllName();
        Map<String, Double> tfidf = new TreeMap();
        for (String item : words) {
            double idf = tfIdfService.calculateIdf(documents, item);
            double tf = tfIdfService.calculateTf(jobName, item);
            tfidf.put(item.toLowerCase(), tf * idf);
        }
        if (tfidf.size() > 2) {
            tfidf = tfidf.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue()).limit(2)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }
        List<String> names = new ArrayList<>();
        names.addAll(tfidf.keySet());
        List<SubjectDto> subjects = subjectConverter.toDto(subjectRepository.searchByType(subjectType));
        Map<Long, List<String>> subjectTokenList = new HashMap<>();
        subjects.forEach((item) ->
                subjectTokenList.put(item.getId(), Arrays.asList(item.getName().toLowerCase().split(" ")))
        );
        Iterator<SubjectDto> iterator = subjects.iterator();
        while (iterator.hasNext()) {
            SubjectDto subject = iterator.next();
            boolean exist = false;
            List<String> token = subjectTokenList.get(subject.getId());
            for (String word : names) {
                if (word.trim().contains(" ")) {
                    if (subject.getName().toLowerCase().contains(word)) {
                        exist = true;
                        break;
                    }
                } else {
                    if (token.contains(word)) {
                        exist = true;
                    }
                }
            }
            if (!exist) {
                iterator.remove();
            }
        }
//        subjects.forEach((item, tokens) -> {
//            for (String word : names) {
//                if (tokens.contains(word)) {
//                    subjectList.add(item);
//                    break;
//                }
//            }
//        });
        return subjects;
    }
}
