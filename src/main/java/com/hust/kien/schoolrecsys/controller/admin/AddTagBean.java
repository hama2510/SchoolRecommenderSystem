package com.hust.kien.schoolrecsys.controller.admin;

import com.hust.kien.schoolrecsys.common.util.StringUtil;
import com.hust.kien.schoolrecsys.entity.Tag;
import com.hust.kien.schoolrecsys.repository.TagRepository;
import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Scope(value = "view")
@Component(value = "addTagBean")
@ELBeanName(value = "addTagBean")
@Join(path = "/admin/them-tag", to = "/admin/add-tag.xhtml")
public class AddTagBean {

    @Autowired
    private TagRepository tagRepository;
    @Getter
    @Setter
    private Tag tag = new Tag();

    public void addTag() {
        if (StringUtil.isBlank(tag.getName())) {
            FacesContext.getCurrentInstance().addMessage("Lỗi", new FacesMessage("Phải nhập tên tag"));
        } else {
            tagRepository.save(tag);
            tag = new Tag();
            FacesContext.getCurrentInstance().addMessage("Thành công", new FacesMessage("Thêm mới tag thành công"));
        }
    }
}
