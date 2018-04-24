package com.hust.kien.schoolrecsys.controller;

import lombok.Getter;
import com.hust.kien.schoolrecsys.dto.RecommendedJobDto;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Scope(value = "view")
@Component(value = "jobRecommendationResultBean")
@ELBeanName(value = "jobRecommendationResultBean")
@Join(path = "/ket-qua-tu-van-chon-nghe", to = "/ket-qua-tu-van-chon-nghe.xhtml")
public class JobRecommendationResultBean {
    @Getter
    private List<RecommendedJobDto> recommendedJobs;
    @Getter
    private String message;
    @Getter
    private boolean reDo = false;

    @Deferred
    @RequestAction
    @IgnorePostback
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        recommendedJobs = (List<RecommendedJobDto>) session.getAttribute("recommendedJobs");
        if (recommendedJobs == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("/tu-van-chon-nghe.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            session.setAttribute("recommendedJobs", null);
        } else if (recommendedJobs.size() == 0) {
            message = "Rất tiếc không thể tìm thấy nghề phù hợp với điều kiện của bạn, vui lòng cung cấp thêm một vài thông tin của bạn";
            reDo = true;
        } else {
            message = "Các nghề phù hợp với bạn là";
        }

    }
}
