package com.hust.kien.schoolrecsys.controller;

import com.hust.kien.schoolrecsys.dto.SchoolDto;
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
import java.util.List;

@Scope(value = "view")
@Component(value = "searchSchoolBean")
@ELBeanName(value = "searchSchoolBean")
@Join(path = "/danh-sach-truong-nghe", to = "/danh-sach-truong-nghe.xhtml")
public class SearchSchoolBean {
    @Autowired
    private SchoolService schoolService;
    @Getter
    private List<SchoolDto> schools;

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        schools = schoolService.findAll();
    }
}
