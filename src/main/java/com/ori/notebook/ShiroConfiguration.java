package com.ori.notebook;

import com.ori.notebook.shiro.JWTFilter;
import com.ori.notebook.shiro.UserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfiguration {

    //1.创建安全管理器
    @Bean
    public SecurityManager getSecurityManager(UserRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    //配置shiro的过滤器工厂
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //1.创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        //2.设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        //3.通用配置（跳转登录页面，未授权跳转的页面）
        filterFactory.setLoginUrl("/authError?code=1");//跳转url地址
        filterFactory.setUnauthorizedUrl("/authError?code=2");//未授权的url
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //用来校验token
        filtersMap.put("jwtAuth", new JWTFilter());
        filterFactory.setFilters(filtersMap);
        //4.设置过滤器集合
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/sys/**","noSessionCreation, anon");
        filterMap.put("/authError","noSessionCreation, anon");
        filterMap.put("/**","noSessionCreation, jwtAuth");
        filterFactory.setFilterChainDefinitionMap(filterMap);
        return filterFactory;
    }

    //开启对shiro注解的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
