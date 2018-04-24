package com.hust.kien.schoolrecsys.controller.admin;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.common.util.StringUtil;
import com.hust.kien.schoolrecsys.dto.GroupTypeDto;
import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;
import com.hust.kien.schoolrecsys.service.RequirementGroupService;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Scope(value = "view")
@Component(value = "editGroupBean")
@ELBeanName(value = "editGroupBean")
@Join(path = "/sua-nhom-yeu-cau", to = "/admin/edit-group.xhtml")
public class EditGroupBean {
    @Setter
    @Getter
    private RequirementGroupDto group = new RequirementGroupDto();
    @Autowired
    private RequirementGroupService requirementGroupService;
    @Getter
    private List<GroupTypeDto> typeList;

    public EditGroupBean() {
        typeList = new ArrayList<>();
        typeList.add(new GroupTypeDto("Lựa chọn nhiều", "checkbox"));
        typeList.add(new GroupTypeDto("Lựa chọn một", "radio"));
    }

    @PostConstruct
    public void init() {
        try {
            Long id = Long.parseLong(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("id"));
            group = requirementGroupService.find(id);
        } catch (NumberFormatException e) {
            group = new RequirementGroupDto();
        }
    }

    public void edit() {
        if (StringUtil.isBlank(group.getName())) {
            FacesContext.getCurrentInstance().addMessage("Lỗi", new FacesMessage("Phải nhập tên nhóm yêu cầu"));
        } else {
            requirementGroupService.edit(group);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/trang-chu.xhtml?redirect=true");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}