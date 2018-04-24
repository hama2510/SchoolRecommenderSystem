package com.hust.kien.schoolrecsys.controller;

import com.hust.kien.schoolrecsys.datamodel.JobDataModel;
import com.hust.kien.schoolrecsys.service.JobService;
import com.hust.kien.schoolrecsys.service.SuggestionService;
import lombok.Getter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Scope(value = "view")
@Component(value = "homePageBean")
@ELBeanName(value = "homePageBean")
@Join(path = "/", to = "/trang-chu.xhtml")
public class HomePageBean {
    @Autowired
    private JobService jobService;
    @Autowired
    private SuggestionService suggestionService;
    @Getter
    private JobDataModel jobModel;

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        jobModel = new JobDataModel(jobService.findAll());
    }
}
