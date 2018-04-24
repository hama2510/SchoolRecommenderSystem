package com.hust.kien.schoolrecsys.controller.admin;

import com.hust.kien.schoolrecsys.common.util.FileUploadUtil;
import com.hust.kien.schoolrecsys.common.util.StringUtil;
import com.hust.kien.schoolrecsys.dto.JobDto;
import com.hust.kien.schoolrecsys.dto.RequirementDto;
import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;
import com.hust.kien.schoolrecsys.service.CareerService;
import com.hust.kien.schoolrecsys.service.JobRequirementService;
import com.hust.kien.schoolrecsys.service.JobService;
import com.hust.kien.schoolrecsys.service.RequirementGroupService;
import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

@Scope(value = "view")
@Component(value = "addJobBean")
@ELBeanName(value = "addJobBean")
@Join(path = "/them-nghe", to = "/admin/add-job.xhtml")
public class AddJobBean {

    @Autowired
    private JobService jobService;
    @Autowired
    private JobRequirementService jobRequirementService;
    @Autowired
    private CareerService careerService;
    @Autowired
    private RequirementGroupService requirementGroupService;
    @Getter
    private List<String> careers;
    @Getter
    private JobDto job;
    @Getter
    private List<RequirementDto> requirements;
    @Getter
    private List<RequirementGroupDto> groups;
    @Getter
    private TreeNode groupTree;
    @Getter
    @Setter
    private TreeNode[] selectedNodes;

    public AddJobBean() {
        job = new JobDto();
    }

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        requirements = jobRequirementService.findAll();
        careers = careerService.findAll();
        groups = requirementGroupService.findAll();
        initTree();
    }

    private void initTree() {
        groupTree = new CheckboxTreeNode("", null);
        groups.forEach((item) -> {
            TreeNode treeNode = new CheckboxTreeNode(item, groupTree);
            item.getRequirements().forEach((requirement) -> new CheckboxTreeNode(requirement, treeNode));
        });
    }

    public void addJob() {
        if (StringUtil.isBlank(job.getName()) ||
                StringUtil.isBlank(job.getDescription()) ||
                selectedNodes.length <= 0 ||
                StringUtil.isBlank(job.getCareer())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Phải điền đủ các thông tin về tên nghề, mô tả nghề, " +
                            "phẩm chất nghề, nhóm ngành nghề", null));
        } else {
            try {
                for (TreeNode node : selectedNodes) {
                    Object data = node.getData();
                    if (data instanceof RequirementDto)
                        job.getRequirements().add((RequirementDto) data);
                    else if (data instanceof RequirementGroupDto) {
                        job.getRequirements().addAll(((RequirementGroupDto) data).getRequirements());
                    }
                }
                jobService.addJob(job);
                FacesContext.getCurrentInstance().addMessage("Thành công",
                        new FacesMessage("Thêm nghề thành công"));
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect("/admin/edit-job.xhtml?id=" + job.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                    job = new JobDto();
                    initTree();
                }

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


}
