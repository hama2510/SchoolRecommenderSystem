package com.hust.kien.schoolrecsys.controller.admin;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.dto.SubjectDto;
import com.hust.kien.schoolrecsys.service.SubjectService;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@Scope(value = "view")
@Component(value = "addSubjectBean")
@ELBeanName(value = "addSubjectBean")
@Join(path = "/them-mon-hoc", to = "/admin/add-subject.xhtml")
public class AddSubjectBean {
    @Autowired
    private SubjectService subjectService;
    @Getter
    private List<String> groups;
    @Getter
    private List<String> types;
    @Getter
    private SubjectDto subject;

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        subject = new SubjectDto();
        groups = subjectService.getSubjectGroups();
        types = subjectService.getSubjectTypes();
    }

    public void add() {
        subjectService.add(subject);
        FacesContext.getCurrentInstance().addMessage("Thành công",
                new FacesMessage("Thêm môn học thành công"));
        subject = new SubjectDto();
    }
}
