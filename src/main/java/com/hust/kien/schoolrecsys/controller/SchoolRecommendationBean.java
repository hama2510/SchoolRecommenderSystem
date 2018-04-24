package com.hust.kien.schoolrecsys.controller;


import com.hust.kien.schoolrecsys.dto.JobDto;
import com.hust.kien.schoolrecsys.entity.Province;
import com.hust.kien.schoolrecsys.service.JobService;
import com.hust.kien.schoolrecsys.service.ProvinceService;
import com.hust.kien.schoolrecsys.service.SchoolService;
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
import javax.faces.context.FacesContext;
import java.util.List;

@Scope(value = "view")
@Component(value = "schoolRecommendationBean")
@ELBeanName(value = "schoolRecommendationBean")
@Join(path = "/tu-van-chon-truong", to = "/tu-van-chon-truong.xhtml")
public class SchoolRecommendationBean {

    @Getter
    private JobDto job;
    @Getter
    private List<Province> provinces;

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private JobService jobService;
    @Autowired
    private ProvinceService provinceService;

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        try {
            Long id = Long.parseLong(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("id"));
            job = jobService.find(id);
        } catch (NumberFormatException e) {
            job = new JobDto();
        }
        provinces = provinceService.findAll();
    }
}
