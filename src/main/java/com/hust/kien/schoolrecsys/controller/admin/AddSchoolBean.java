package com.hust.kien.schoolrecsys.controller.admin;

import com.hust.kien.schoolrecsys.dto.SchoolDto;
import com.hust.kien.schoolrecsys.service.SchoolService;
import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;

@Scope(value = "view")
@Component(value = "addSchoolBean")
@ELBeanName(value = "addSchoolBean")
@Join(path = "/them-truong-nghe", to = "/admin/add-school.xhtml")
public class AddSchoolBean {
    @Autowired
    private SchoolService schoolService;
    @Getter
    @Setter
    private SchoolDto school;

    public AddSchoolBean() {
        school = new SchoolDto();
    }

    public void add() {
        if (!schoolService.isValid(school)) {
            FacesContext.getCurrentInstance().addMessage("Lỗi",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Phải nhập tên, tỉnh thành", null));
        } else {
            schoolService.add(school);
            FacesContext.getCurrentInstance().addMessage("Thành công",
                    new FacesMessage("Thêm mới trường " + school.getName() + " thành công"));
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("/admin/sua-truong-nghe?id=" + school.getId());
            } catch (IOException e) {
                e.printStackTrace();
                school = new SchoolDto();
            }
        }
    }
}
