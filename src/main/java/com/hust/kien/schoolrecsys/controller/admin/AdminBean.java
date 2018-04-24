package com.hust.kien.schoolrecsys.controller.admin;

import lombok.Getter;
import lombok.Setter;
import com.hust.kien.schoolrecsys.datamodel.*;
import com.hust.kien.schoolrecsys.dto.*;
import com.hust.kien.schoolrecsys.service.*;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Scope(value = "view")
@Component(value = "adminBean")
@ELBeanName(value = "adminBean")
@Join(path = "/admin", to = "/admin/trang-chu.xhtml")
public class AdminBean {
    @Autowired
    private JobService jobService;
    @Autowired
    private JobRequirementService jobRequirementService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private RequirementGroupService requirementGroupService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TagService tagService;

    @Getter
    private JobDataModel jobModel;
    @Getter
    @Setter
    private JobDto selectedJob;
    @Getter
    private JobRequirementDataModel requirementModel;
    @Getter
    @Setter
    private RequirementDto selectedRequirement;
    @Getter
    private SchoolDataModel schoolDataModel;
    @Getter
    @Setter
    private SchoolDto selectedSchool;
    @Getter
    private RequirementGroupDataModel groupModel;
    @Getter
    @Setter
    private RequirementGroupDto selectedGroup;
    @Getter
    @Setter
    private SubjectDto selectedSubject;
    @Getter
    private SubjectDataModel subjectModel;
    @Getter
    private TagDataModel tagDataModel;
    @Getter
    @Setter
    private TagDto selectedTag = new TagDto();

    @Getter
    public final int JOB = 1;
    @Getter
    public final int JOB_REQUIREMENT = 2;
    @Getter
    public final int SCHOOL = 3;
    @Getter
    public final int REQUIREMENT_GROUP = 4;
    @Getter
    public final int SUBJECT = 5;
    @Getter
    public final int TAG = 6;
    @Getter
    private int selectedMenu;

    @PostConstruct
    public void init() {
        jobModel = new JobDataModel(jobService.findAll());
        requirementModel = new JobRequirementDataModel(jobRequirementService.findAll());
        schoolDataModel = new SchoolDataModel(schoolService.findAll());
        groupModel = new RequirementGroupDataModel(requirementGroupService.findAll());
        subjectModel = new SubjectDataModel(subjectService.findAll());
        tagDataModel = new TagDataModel(tagService.findAll());

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        Object current = session.getAttribute("currentView");
        if (current == null) {
            selectedMenu = SCHOOL;
        } else {
            selectedMenu = (int) current;
        }
    }

    public void deleteJob() {
        jobService.delete(selectedJob);
        jobModel.remove(selectedJob);
        FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Xóa nghề thành công!"));
    }

    public void deleteJobRequirement() {
        jobRequirementService.delete(selectedRequirement);
        requirementModel.remove(selectedRequirement);
        FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Xóa phẩm chất nghề thành công!"));
    }

    public void deleteSchool() {
        schoolService.delete(selectedSchool);
        schoolDataModel.remove(selectedSchool);
        FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Xóa trường nghề thành công!"));
    }

    public void setView(int menu) {
        this.selectedMenu = menu;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.setAttribute("currentView", menu);
    }

    public void changeToUpdateSchoolPage() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("/admin/edit-school.xhtml?id=" + selectedSchool.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteGroup() {
        if (requirementGroupService.getNoRequirement(selectedGroup.getId()) <= 0) {
            requirementGroupService.delete(selectedGroup);
            groupModel.remove(selectedGroup);
            FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Xóa thành công!"));
        } else {
            FacesContext.getCurrentInstance().addMessage("Lỗi",
                    new FacesMessage(null, "Nhóm đang chứa phẩm chất nghề. Vui lòng xóa toàn bộ phẩm chất nghề" +
                            " trước khi xóa nhóm!"));
        }
    }

    public void changeActiveGroup() {
        selectedGroup.setActive(!selectedGroup.isActive());
        requirementGroupService.edit(selectedGroup);
    }

    public void changeToEditJob() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("/admin/edit-job.xhtml?id=" + selectedJob.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeToEditRequirement() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("/admin/edit-requirement.xhtml?id=" + selectedRequirement.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeToEditGroup() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("/admin/edit-group.xhtml?id=" + selectedGroup.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeHotJob() {
        selectedJob.setBrightOutlook(!selectedJob.isBrightOutlook());
        jobService.editJob(selectedJob);
    }

    public void deleteSubject() {
        subjectService.delete(selectedSubject);
        subjectModel.remove(selectedSubject);
        FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Xóa thành công!"));
    }

    public void deleteTag(){
        tagService.delete(selectedTag);
        tagDataModel.remove(selectedTag);
        FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Xóa thành công!"));
    }
}
