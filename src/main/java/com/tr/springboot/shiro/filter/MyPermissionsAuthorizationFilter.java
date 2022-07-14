package com.tr.springboot.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Objects;
import java.util.Set;

/**
 * @author TR
 * @date 2022/7/7 下午3:24
 */
@Component
public class MyPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

    /**
     * 自定义 perms[admin:*, test:*] 中 perms 拦截验证逻辑
     *  这里重写后的验证逻辑是：只要具备 perms 中任意一种 perm 即可访问（原来需要具备所有 perm 才能访问）
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = this.getSubject(request, response);
        String[] permsArray = (String[])mappedValue;
        if (Objects.nonNull(permsArray) && permsArray.length > 0) {
            Set<String> perms = CollectionUtils.asSet(permsArray);
            for (String perm : perms) {
                if (subject.isPermitted(perm)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

}
