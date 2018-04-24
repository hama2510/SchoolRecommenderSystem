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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@Scope(value = "view")
@Component(value = "addGroupBean")
@ELBeanName(value = "addGroupBean")
@Join(path = "/admin/them-nhom-yeu-cau", to = "/admin/add-requirement-group.xhtml")
public class AddGroupBean {
    @Getter
    @Setter
    private RequirementGroupDto group;
    @Autowired
    private RequirementGroupService requirementGroupService;
    @Getter
    private List<GroupTypeDto> typeList;

    public AddGroupBean() {
        group = new RequirementGroupDto();
        typeList = new ArrayList<>();
        typeList.add(new GroupTypeDto("Lựa chọn nhiều", "checkbox"));
        typeList.add(new GroupTypeDto("Lựa chọn một", "radio"));
    }

    public void addGroup() {
        if (StringUtil.isBlank(group.getName())) {
            FacesContext.getCurrentInstance().addMessage("Lỗi", new FacesMessage("Phải nhập tên nhóm yêu cầu"));
        } else {
            requirementGroupService.add(group);
        }
    }
}
