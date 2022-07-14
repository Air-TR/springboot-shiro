package com.tr.springboot.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Objects;
import java.util.Set;

/**
 * @author TR
 * @date 2022/7/7 下午4:15
 */
@Component
public class MyRolesAuthorizationFilter extends RolesAuthorizationFilter {

    /**
     * 自定义 roles[Admin, System, ...] 中 roles 拦截验证逻辑
     *  这里重写后的验证逻辑是：只要具备 roles 中任意一种 role 即可访问（原来需要具备所有 role 才能访问）
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[])mappedValue;
        if (Objects.nonNull(rolesArray) && rolesArray.length > 0) {
            Set<String> roles = CollectionUtils.asSet(rolesArray);
            for (String role : roles) {
                if (subject.hasRole(role)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

}
