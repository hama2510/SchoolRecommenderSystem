package com.hust.kien.schoolrecsys.controller;

import com.hust.kien.schoolrecsys.dto.SchoolDto;
import com.hust.kien.schoolrecsys.service.SchoolService;
import lombok.Getter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

@Scope(value = "view")
@Component(value = "viewSchoolBean")
@ELBeanName(value = "viewSchoolBean")
@Join(path = "/chi-tiet-truong", to = "/chi-tiet-truong.xhtml")
public class ViewSchoolBean {
    @Autowired
    private SchoolService schoolService;
    @Getter
    private SchoolDto school;

    @PostConstruct
    public void init() {
        try {
            Long id = Long.parseLong(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("id"));
            school = schoolService.find(id);
        } catch (NumberFormatException e) {
            school = new SchoolDto();
        }
    }
}
