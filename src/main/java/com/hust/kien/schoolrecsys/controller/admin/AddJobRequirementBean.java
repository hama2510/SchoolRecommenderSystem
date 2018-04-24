package com.hust.kien.schoolrecsys.controller.admin;

import com.hust.kien.schoolrecsys.common.util.StringUtil;
import com.hust.kien.schoolrecsys.dto.RequirementDto;
import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;
import com.hust.kien.schoolrecsys.service.JobRequirementService;
import com.hust.kien.schoolrecsys.service.RequirementGroupService;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

@Scope(value = "view")
@Component(value = "addJobRequirementBean")
@ELBeanName(value = "addJobRequirementBean")
@Join(path = "/them-yeu-cau-nghe", to = "/admin/add-job-requirement.xhtml")
public class AddJobRequirementBean {

    @Autowired
    private JobRequirementService jobRequirementService;
    @Autowired
    private RequirementGroupService requirementGroupService;
    @Getter
    private RequirementDto requirement;
    @Getter
    private List<RequirementGroupDto> requirementGroups;

    public AddJobRequirementBean() {
        requirement = new RequirementDto();
    }

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        requirementGroups = requirementGroupService.findAll();
    }

    public String addJobRequirement() {
        if (StringUtil.isBlank(requirement.getName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Phải nhập phẩm chất nghề!"));
        } else {
            jobRequirementService.add(requirement);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Tạo mới phẩm chất nghề thành công"));
            requirement = new RequirementDto();
        }
        return "/admin/trang-chu?faces-redirect=true";
    }

}
