package com.hust.kien.schoolrecsys.controller.admin;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.common.util.StringUtil;
import com.hust.kien.schoolrecsys.dto.RequirementDto;
import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;
import com.hust.kien.schoolrecsys.service.JobRequirementService;
import com.hust.kien.schoolrecsys.service.RequirementGroupService;
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
@Component(value = "editRequirementBean")
@ELBeanName(value = "editRequirementBean")
@Join(path = "/sua-yeu-cau-nghe", to = "/edit-requirement.xhtml")
public class EditRequirementBean {
    @Autowired
    private JobRequirementService jobRequirementService;
    @Getter
    private RequirementDto requirement;
    @Autowired
    private RequirementGroupService requirementGroupService;
    @Getter
    private List<RequirementGroupDto> requirementGroups;

    public EditRequirementBean() {
        requirement = new RequirementDto();
    }

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        requirementGroups = requirementGroupService.findAll();
        try {
            Long id = Long.parseLong(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("id"));
            requirement = jobRequirementService.find(id);
        } catch (NumberFormatException e) {
            requirement = new RequirementDto();
        }
    }

    public String editJobRequirement() {
        if (StringUtil.isBlank(requirement.getName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Phải nhập phẩm chất nghề!"));
            return "/admin?faces-redirect=true";
        } else {
            jobRequirementService.edit(requirement);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Sửa phẩm chất nghề thành công"));
            return "";
        }
    }
}
