package com.hust.kien.schoolrecsys;

import com.hust.kien.schoolrecsys.config.SecurityConfig;
import com.hust.kien.schoolrecsys.config.ViewScope;
import com.hust.kien.schoolrecsys.service.vntokenizer.RDRsegmenter;
import org.neo4j.ogm.session.SessionFactory;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.primefaces.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.faces.application.ProjectStage;
import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
@Import({SecurityConfig.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan({"com.hust.kien.schoolrecsys"})
public class Application {

    @Value("${neo4j.uri}")
    private String neo4jUri;
    @Value("${neo4j.username}")
    private String neo4jUsername;
    @Value("${neo4j.password}")
    private String neo4jPassword;
    @Value("${vntokenizer.model}")
    private String modelFilePath;
    @Value("${vntokenizer.vocal}")
    private String vocalFilePath;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.xhtml");
    }

    @Bean
    public FilterRegistrationBean rewriteFilter() {
        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
                DispatcherType.ASYNC, DispatcherType.ERROR));
        rwFilter.addUrlPatterns("/*");
        return rwFilter;
    }

    @Bean
    public ServletContextInitializer servletContextCustomizer() {
        return sc -> {
            sc.setInitParameter(Constants.ContextParams.THEME, "bootstrap");
            sc.setInitParameter(Constants.ContextParams.FONT_AWESOME, "true");
            sc.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ProjectStage.Development.name());
        };
    }

    @Bean
    public SessionFactory getSessionFactory() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri(neo4jUri)
                .credentials(neo4jUsername, neo4jPassword)
                .build();
        return new SessionFactory(configuration, "com.hust.kien.schoolrecsys.entity");
    }

    @Bean
    public static CustomScopeConfigurer viewScope() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        Map scopes = new HashMap<>();
        scopes.put("view", new ViewScope());
        configurer.setScopes(scopes);
        return configurer;
    }

    @Bean
    public RDRsegmenter getRdrSegmenter() {
        return new RDRsegmenter(modelFilePath, vocalFilePath);
    }
}
