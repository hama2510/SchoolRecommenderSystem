package com.hust.kien.schoolrecsys.controller;

import com.hust.kien.schoolrecsys.dto.JobDto;
import com.hust.kien.schoolrecsys.service.JobService;
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

@Scope(value = "view")
@Component(value = "viewJobBean")
@ELBeanName(value = "viewJobBean")
@Join(path = "/chi-tiet-nghe", to = "/chi-tiet-nghe.xhtml")
public class ViewJobBean {
    @Autowired
    private JobService jobService;
    @Getter
    private JobDto job;

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
    }
}
