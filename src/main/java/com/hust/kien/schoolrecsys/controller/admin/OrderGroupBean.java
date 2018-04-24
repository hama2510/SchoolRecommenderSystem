package com.hust.kien.schoolrecsys.controller.admin;

import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;
import com.hust.kien.schoolrecsys.service.RequirementGroupService;
import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Scope(value = "view")
@Component(value = "orderGroupBean")
@ELBeanName(value = "orderGroupBean")
@Join(path = "/sap-xep-thu-tu-hien-thi-yeu-cau-nghe", to = "/admin/order-group.xhtml")
public class OrderGroupBean {
    @Getter
    @Setter
    private List groups = new ArrayList<>();
    @Autowired
    private RequirementGroupService requirementGroupService;

    @PostConstruct
    public void init() {
        groups = requirementGroupService.findAll();
    }

    public void onReorder() {
        for (int i = 0; i < groups.size(); i++) {
            long id = Long.parseLong(groups.get(i).toString());
            RequirementGroupDto group = requirementGroupService.find(id);
            group.setIndex(i);
            requirementGroupService.edit(group);
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
