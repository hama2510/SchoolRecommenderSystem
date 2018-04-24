package com.hust.kien.schoolrecsys.controller;

import com.hust.kien.schoolrecsys.dto.RecommendedJobDto;
import com.hust.kien.schoolrecsys.dto.RequirementDto;
import com.hust.kien.schoolrecsys.dto.RequirementGroupDto;
import com.hust.kien.schoolrecsys.service.JobService;
import com.hust.kien.schoolrecsys.service.RequirementGroupService;
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

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Scope(value = "view")
@Component(value = "jobRecommendationBean")
@ELBeanName(value = "jobRecommendationBean")
@Join(path = "/tu-van-chon-nghe", to = "/tu-van-chon-nghe.xhtml")
public class JobRecommendationBean {

    @Autowired
    private JobService jobService;
    @Autowired
    private RequirementGroupService requirementGroupService;

    @Getter
    private List<RequirementGroupDto> group;
    @Getter
    @Setter
    private List<Long> selectedCheckbox;
    @Getter
    @Setter
    private Long selectedRadio;
    @Getter
    @Setter
    private List<List<Long>> selectedList = new ArrayList<>();
    @Getter
    private List<RecommendedJobDto> recommendedJobs = new ArrayList<>();
    @Getter
    @Setter
    private int selectedMenu;
    @Getter
    private List<RequirementDto> requirements = new ArrayList<>();
    @Getter
    private String currentGroup;

    public JobRecommendationBean() {
        selectedMenu = 0;
    }

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        group = requirementGroupService.findActiveGroup();
        requirements = group.get(0).getRequirements();
        currentGroup = group.get(0).getName();
        for (int i = 0; i < group.size(); i++) {
            selectedList.add(new ArrayList<>());
        }
    }

    public void next() {
        selectedList.get(selectedMenu).removeAll(selectedList.get(selectedMenu));
        if (group.get(selectedMenu).getType().equals("checkbox")) {
            selectedList.get(selectedMenu).addAll(selectedCheckbox);
        } else {
            if (selectedRadio != 0) {
                selectedList.get(selectedMenu).add(selectedRadio);
            }
        }
        selectedMenu++;
        requirements = group.get(selectedMenu).getRequirements();
        currentGroup = group.get(selectedMenu).getName();
        if (group.get(selectedMenu).getType().equals("checkbox")) {
            selectedCheckbox = selectedList.get(selectedMenu);
        } else {
            if (selectedList.get(selectedMenu).size() > 0)
                selectedRadio = selectedList.get(selectedMenu).get(0);
            else
                selectedRadio = 0L;
        }
    }

    public void back() {
        selectedList.get(selectedMenu).removeAll(selectedList.get(selectedMenu));
        if (group.get(selectedMenu).getType().equals("checkbox")) {
            selectedList.get(selectedMenu).addAll(selectedCheckbox);
        } else {
            if (selectedRadio != 0) {
                selectedList.get(selectedMenu).add(selectedRadio);
            }
        }
        selectedMenu--;
        requirements = group.get(selectedMenu).getRequirements();
        currentGroup = group.get(selectedMenu).getName();
        if (group.get(selectedMenu).getType().equals("checkbox")) {
            selectedCheckbox = selectedList.get(selectedMenu);
        } else {
            if (selectedList.get(selectedMenu).size() > 0)
                selectedRadio = selectedList.get(selectedMenu).get(0);
            else
                selectedRadio = 0L;
        }
    }

    public void recommend() {
        selectedList.get(selectedMenu).removeAll(selectedList.get(selectedMenu));
        if (group.get(selectedMenu).getType().equals("checkbox")) {
            selectedList.get(selectedMenu).addAll(selectedCheckbox);
        } else {
            if (selectedRadio != 0) {
                selectedList.get(selectedMenu).add(selectedRadio);
            }
        }
        List<Long> recommendedList = new ArrayList<>();
        selectedList.forEach((item) -> {
            recommendedList.addAll(item);
        });
        recommendedJobs = jobService.recommend(recommendedList);
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.getFlash().put("recommendedJobs", recommendedJobs);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.setAttribute("recommendedJobs", recommendedJobs);
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("/ket-qua-tu-van-chon-nghe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
