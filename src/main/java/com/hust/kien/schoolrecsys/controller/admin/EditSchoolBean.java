package com.hust.kien.schoolrecsys.controller.admin;

import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.hust.kien.schoolrecsys.datamodel.AgencyModel;
import com.hust.kien.schoolrecsys.datamodel.SchoolSubjectDataModel;
import com.hust.kien.schoolrecsys.dto.*;
import com.hust.kien.schoolrecsys.entity.Province;
import com.hust.kien.schoolrecsys.service.ProvinceService;
import com.hust.kien.schoolrecsys.service.SchoolService;
import com.hust.kien.schoolrecsys.service.SubjectService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.ArrayList;
import java.util.List;

@Scope(value = "view")
@Component(value = "editSchoolBean")
@ELBeanName(value = "editSchoolBean")
@Join(path = "/sua-truong-nghe", to = "/admin/edit-school.xhtml")
public class EditSchoolBean {
    @Getter
    @Setter
    private SchoolDto school;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private SubjectService subjectService;
    @Getter
    private List<Province> provinces;
    @Getter
    @Setter
    private String jobName;
    @Getter
    @Setter
    private SchoolSubjectDto schoolSubjectReg;
    @Getter
    private SchoolSubjectDataModel schoolSubjectDataModel;
    @Getter
    @Setter
    private SchoolAgencyDto agency = new SchoolAgencyDto();
    @Getter
    private AgencyModel agencyModel;
    @Getter
    @Setter
    private SchoolAgencyDto selectedAgency;
    @Getter
    @Setter
    private String selectedType;
    @Getter
    private List<String> subjectTypes;
    @Getter
    @Setter
    String selectedName;
    @Getter
    @Setter
    private SchoolSubjectDto selectedSubject;


    public EditSchoolBean() {
        school = new SchoolDto();
        schoolSubjectReg = new SchoolSubjectDto();
    }

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        try {
            Long id = Long.parseLong(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("id"));
            school = schoolService.find(id);
            if (school.getSubjects() == null) {
                school.setSubjects(new ArrayList<>());
            }
            schoolSubjectDataModel = new SchoolSubjectDataModel(school.getSubjects());
            agencyModel = new AgencyModel(school.getAgencies());
        } catch (NumberFormatException e) {
            school = new SchoolDto();
        }
        provinces = provinceService.findAll();

        subjectTypes = subjectService.getSubjectTypes();
        selectedType = subjectTypes.get(0);
    }

    public void update() {
        if (schoolService.isValid(school)) {
            schoolService.update(school);
            FacesContext.getCurrentInstance()
                    .addMessage("Thành công", new FacesMessage("Cập nhật thành công"));
        } else {
            FacesContext.getCurrentInstance().addMessage("Lỗi",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phải điền đầy đủ thông tin!", null));
        }
    }

    public List<SubjectDto> subjectSuggestion(String schoolName) {
        return subjectService.searchByTypeAndName(selectedType, schoolName);
    }

    public void addSubjectToSchool() {
        if (StringUtils.isEmpty(selectedName)) {
            FacesContext.getCurrentInstance().addMessage("Lỗi",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nhập thiếu nghề", ""));
        } else {
            SubjectDto subject = subjectService.findByTypeAndName(selectedType, selectedName);
            if (subject != null) {
                schoolSubjectReg.setType(selectedType);
                schoolSubjectReg.setSubject(subject);
                if (school.isExistJob(schoolSubjectReg)) {
                    FacesContext.getCurrentInstance().addMessage("Lỗi",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Trùng dữ liệu", ""));
                } else {
                    schoolService.addSubject(school, schoolSubjectReg);
                    schoolSubjectDataModel.add(schoolSubjectReg);
                    FacesContext.getCurrentInstance().addMessage("Thành công",
                            new FacesMessage("Thêm thành công"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("Lỗi",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nhập sai nghề", ""));
            }
        }
    }

    public void addAgency() {
        schoolService.addAgency(school, agency);
        agencyModel.add(agency);
        FacesContext.getCurrentInstance().addMessage("Thành công",
                new FacesMessage("Thêm thành công"));
        agency = new SchoolAgencyDto();
    }

    public void deleteAgency() {
        schoolService.deleteAgency(school, selectedAgency);
        agencyModel.remove(selectedAgency);
        FacesContext.getCurrentInstance().addMessage("Thành công",
                new FacesMessage("Xóa thành công"));
    }

    public void subjectTypeSelectionChanged(final AjaxBehaviorEvent event) {
        selectedType = (String) ((UIOutput) event.getSource()).getValue();
        selectedName = "";
    }

    public void deleteSubject() {
        schoolService.deleteSubject(school, selectedSubject);
        schoolSubjectDataModel.remove(selectedSubject);
        FacesContext.getCurrentInstance().addMessage("Thành công",
                new FacesMessage("Xóa thành công"));
    }

}
