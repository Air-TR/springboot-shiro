package com.tr.springboot.shiro.config;

import com.tr.springboot.shiro.properties.ShiroProperties;
import com.tr.springboot.shiro.realm.LoginAuthorizingRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author TR
 * @date 2022/7/4 上午8:58
 */
@Configuration
public class ShiroConfig {

    @Resource
    ShiroProperties shiroProperties;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * 过滤器规则
         *  anon 代表不需要拦截验证
         *  authc 需要拦截验证，验证通过了，才能继续下一个页面，如果没有验证通过，那么跳转到 factoryBean.setLoginUrl("/login") 设置的地址
         *  注：anon 配置在最前，authc 配置在最后，其他配置在两者中间，否则（顺序的错乱）可能导致配置生效情况异常
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login/*/*", "anon"); // anon 配置在最前
//        filterChainDefinitionMap.put("/test/**", "roles[Admin, System]"); // 配置的 role 需要同时具备才能访问
//        filterChainDefinitionMap.put("/test/**", "perms[/add, /test]");   // 配置的 perm 需要同时具备才能访问
        filterChainDefinitionMap.put("/**", "authc");       // authc 配置在最后

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
        defaultWebSecurityManager.setRealm(realm(getHashedCredentialsMatcher()));
        return defaultWebSecurityManager;
    }

    /**
     * 配置realm
     */
    @Bean
    public Realm realm(HashedCredentialsMatcher matcher) {
        LoginAuthorizingRealm realm = new LoginAuthorizingRealm();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        // matcher 就是用来指定加密规则
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 加密方式（SHA-256、MD5...）
        matcher.setHashAlgorithmName(shiroProperties.getHashAlgorithmName());
        // Hash次数，这里的 Hash 次数要与存储时加密的 Hash 次数保持一致
        matcher.setHashIterations(shiroProperties.getHashIterations()); // 默认是 1，为 1 时可以不配置
        return matcher;
    }

}
