package com.tr.springboot.shiro.config;

import com.tr.springboot.shiro.realm.LoginAuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author TR
 * @date 2022/7/4 上午8:58
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置realm
     */
    @Bean
    public Realm realm(){
        return  new LoginAuthorizingRealm();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 过滤器规则
        // anon 代表不需要拦截验证
        // authc 需要拦截验证，验证通过了，才能继续下一个页面，如果没有验证通过，那么跳转到 factoryBean.setLoginUrl("/login") 设置的地址
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login/*/*", "anon");
        filterChainDefinitionMap.put("/**", "authc");
//        filterChainDefinitionMap.put("/update", "authz");
        // 这里定义用户未认证时跳转的路径
        shiroFilterFactoryBean.setLoginUrl("/loginPage"); // 这里配置的地址不会被拦截，不用在 filterChainDefinitionMap 单独配置
        // 这里是用户登录成功后跳转的路径
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 这里是用户没有访问权限跳转的路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 将 realm 加入管理器中
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm());
        return defaultWebSecurityManager;
    }

}
