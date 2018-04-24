package com.hust.kien.schoolrecsys.controller;

import com.hust.kien.schoolrecsys.dto.JobDto;
import com.hust.kien.schoolrecsys.dto.JobSearchDto;
import com.hust.kien.schoolrecsys.dto.TagDto;
import com.hust.kien.schoolrecsys.service.CareerService;
import com.hust.kien.schoolrecsys.service.JobService;
import com.hust.kien.schoolrecsys.service.TagService;
import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Scope(value = "view")
@Component(value = "searchJobBean")
@ELBeanName(value = "searchJobBean")
@Join(path = "/danh-sach-nghe", to = "/danh-sach-nghe.xhtml")
public class SearchJobBean {
    @Autowired
    private JobService jobService;
//    @Autowired
//    private CareerService careerService;
    @Autowired
    private TagService tagService;
    @Getter
    private List<JobDto> jobs;
    @Getter
    @Setter
    private JobSearchDto searchJob = new JobSearchDto();
//    @Getter
//    private List<String> jobCareers;
    @Getter
    private List<TagDto> tags;
    @Getter
    private String message;

    //    @Deferred
//    @RequestAction
//    @IgnorePostback
    @PostConstruct
    public void init() {
        jobs = jobService.findAll();
//        jobCareers = careerService.findAll();
        tags = tagService.findAll();
    }

    public void search() {
        message = "";
        jobs = jobService.search(searchJob);
        message = "Kết quả tìm kiếm";
        if (jobs.size() == 0) {
            String[] arr = searchJob.getName().split(" ");
            List<String> terms = new ArrayList<>();
            for (String item : arr) {
                terms.add(item);
            }
            jobs = jobService.searchRelated(terms);
            if (jobs.size() == 0) {
                message = "Không tìm thấy nghề bạn đang tìm kiếm. Hệ thống sẽ cố gắng cập nhật sau.";
            } else {
                message = "Không tìm thấy nghề bạn đang tìm kiếm. Có thể bạn đang quan tâm đến những nghề sau.";
            }
        }
    }

    public void clear() {
        searchJob = new JobSearchDto();
    }
}
