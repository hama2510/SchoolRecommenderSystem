package com.hust.kien.schoolrecsys.controller.admin;

import com.hust.kien.schoolrecsys.datamodel.TagDataModel;
import com.hust.kien.schoolrecsys.dto.*;
import com.hust.kien.schoolrecsys.entity.Tag;
import com.hust.kien.schoolrecsys.repository.JobRepository;
import com.hust.kien.schoolrecsys.repository.TagRepository;
import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import com.hust.kien.schoolrecsys.common.util.FileUploadUtil;
import com.hust.kien.schoolrecsys.common.util.StringUtil;
import com.hust.kien.schoolrecsys.datamodel.SubjectDataModel;
import com.hust.kien.schoolrecsys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Scope(value = "view")
@Component(value = "editJobBean")
@ELBeanName(value = "editJobBean")
@Join(path = "/sua-nghe", to = "/admin/edit-job.xhtml")
public class EditJobBean {
    @Autowired
    private JobService jobService;
    @Autowired
    private CareerService careerService;
    @Autowired
    private RequirementGroupService requirementGroupService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    TagService tagService;

    @Getter
    private List<String> careers;
    @Getter
    private JobDto job;
    @Getter
    private List<RequirementGroupDto> groups;
    @Getter
    private TreeNode groupTree;
    @Getter
    @Setter
    private TreeNode[] selectedNodes;
    @Getter
    @Setter
    private String selectedType;
    @Getter
    @Setter
    String selectedName;
    @Getter
    private SubjectDataModel subjectDataModel;
    @Getter
    private List<String> subjectTypes;
    @Getter
    @Setter
    private SubjectDto selectedSubject;
    @Getter
    private SubjectDataModel suggestionSubjects;
    @Getter
    @Setter
    private SubjectDto selectedSuggestionSubject;
    @Getter
    @Setter
    private String selectedTagName;
    @Getter
    private TagDataModel tagDataModel;
    @Setter
    @Getter
    private TagDto selectedTag = new TagDto();

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
        careers = careerService.findAll();
        groups = requirementGroupService.findAll();
        groupTree = new CheckboxTreeNode("", null);
        groups.forEach((item) -> {
            TreeNode treeNode = new CheckboxTreeNode(item, groupTree);
            item.getRequirements().forEach((requirement) -> {
                TreeNode node = new CheckboxTreeNode(requirement, treeNode);
                job.getRequirements().forEach((jobRequirement) -> {
                    if (requirement.getId().equals(jobRequirement.getId())) {
                        node.setSelected(true);
                    }
                });
            });
        });
        subjectDataModel = new SubjectDataModel(job.getSubject());
        subjectTypes = subjectService.getSubjectTypes();
        selectedType = subjectTypes.get(0);
        suggestionSubjects = new SubjectDataModel(getSuggestionSubject(selectedType, job.getName()));
        tagDataModel = new TagDataModel(job.getTags());
    }

    private List<SubjectDto> getSuggestionSubject(String subjectType, String jobName) {
        List<SubjectDto> subjects = suggestionService.suggest(selectedType, job.getName());
        Iterator<SubjectDto> iterator = subjects.iterator();
        while (iterator.hasNext()) {
            SubjectDto subject = iterator.next();
            for (SubjectDto item : job.getSubject()) {
                if (item.getId().equals(subject.getId())) {
                    iterator.remove();
                    break;
                }
            }
        }
        return subjects;
    }

    public void editJob() {
        if (StringUtil.isBlank(job.getName()) ||
                StringUtil.isBlank(job.getDescription()) ||
                StringUtil.isBlank(job.getCareer())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Phải điền đủ các thông tin về tên nghề, mô tả nghề, " +
                            "phẩm chất nghề, nhóm ngành nghề", null));
        } else {
            try {
                job.setRequirements(new ArrayList<>());
                for (TreeNode node : selectedNodes) {
                    Object data = node.getData();
                    if (data instanceof RequirementDto)
                        job.getRequirements().add((RequirementDto) data);
                    else if (data instanceof RequirementGroupDto) {
                        job.getRequirements().addAll(((RequirementGroupDto) data).getRequirements());
                    }
                }
                jobService.editJob(job);
                FacesContext.getCurrentInstance().addMessage("Thành công",
                        new FacesMessage("Sửa nghề thành công"));
            } catch (IllegalArgumentException ex) {
                FacesContext.getCurrentInstance().addMessage("Lỗi", new FacesMessage(ex.getMessage()));
            }
        }
    }

    public void uploadImage(FileUploadEvent event) {
        String imgSrc = FileUploadUtil.uploadImage(event);
        if (imgSrc != null) {
            job.setImageSrc(imgSrc);
        }
    }

    public List<SubjectDto> subjectSuggestion(String schoolName) {
        return subjectService.searchByTypeAndName(selectedType, schoolName);
    }

    private void addSubject(SubjectDto subject) {
        boolean existed = false;
        for (int i = 0; i < job.getSubject().size(); i++) {
            if (job.getSubject().get(i).getId().equals(subject.getId())) {
                existed = true;
                break;
            }
        }
        if (!existed) {
            job.getSubject().add(subject);
            jobService.saveSubject(job);
            selectedName = "";
        } else {
            FacesContext.getCurrentInstance().addMessage("Lỗi", new FacesMessage("Trùng dữ liệu"));
        }
    }

    public void mapSubject() {
        SubjectDto subject = subjectService.findByTypeAndName(selectedType, selectedName);
        if (subject != null) {
            addSubject(subject);
            FacesContext.getCurrentInstance().addMessage("Thành công",
                    new FacesMessage("Ánh xạ môn học thành công"));
        } else {
            FacesContext.getCurrentInstance().addMessage("Lỗi",
                    new FacesMessage("Môn học không tồn tại, vui lòng chọn môn học hiện ra trong hộp gợi ý"));
        }
    }

    public void subjectTypeSelectionChanged(final AjaxBehaviorEvent event) {
        selectedType = (String) ((UIOutput) event.getSource()).getValue();
        selectedName = "";
        suggestionSubjects = new SubjectDataModel(getSuggestionSubject(selectedType, job.getName()));
    }

    public void deleteSubject() {
        jobService.deleteSubject(job, selectedSubject);
        for (int i = 0; i < job.getSubject().size(); i++) {
            if (job.getSubject().get(i).getId().equals(selectedSubject.getId())) {
                job.getSubject().remove(i);
                break;
            }
        }
        FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Xóa môn học thành công"));
    }

    public void quickAdd() {
        addSubject(selectedSuggestionSubject);
        suggestionSubjects.remove(selectedSuggestionSubject);
    }

    public void getAutoSuggestion() {
        suggestionSubjects = new SubjectDataModel(getSuggestionSubject(selectedType, job.getName()));
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('add-subject').show();");
    }

    public List<TagDto> tagSuggestion(String tagName) {
        return tagService.searchByName(tagName);
    }

    public void mapTag() {
        TagDto tag = tagService.findByName(selectedTagName);
        if (tag != null) {
            boolean existed = false;
            for (int i = 0; i < job.getTags().size(); i++) {
                if (job.getTags().get(i).getId().equals(tag.getId())) {
                    existed = true;
                    break;
                }
            }
            if (!existed) {
                job.getTags().add(tag);
                jobService.saveTag(job);
                selectedTagName = "";
                FacesContext.getCurrentInstance().addMessage("Thành công",
                        new FacesMessage("Gắn tag thành công"));
            } else {
                FacesContext.getCurrentInstance().addMessage("Lỗi", new FacesMessage("Trùng dữ liệu"));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage("Lỗi",
                    new FacesMessage("Tag không tồn tại, vui lòng chọn tag hiện ra trong hộp gợi ý"));
        }
    }

    public void deleteTag() {
        jobService.deleteTag(job, selectedTag);
        for (int i = 0; i < job.getSubject().size(); i++) {
            if (job.getTags().get(i).getId().equals(selectedTag.getId())) {
                job.getTags().remove(i);
                break;
            }
        }
        FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Xóa tag thành công"));

    }
}
