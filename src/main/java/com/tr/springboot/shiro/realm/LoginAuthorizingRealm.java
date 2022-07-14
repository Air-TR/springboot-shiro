package com.tr.springboot.shiro.realm;

import com.tr.springboot.shiro.properties.ShiroProperties;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author TR
 * @date 2022/7/4 上午8:55
 */
public class LoginAuthorizingRealm extends AuthorizingRealm {

    @Resource
    ShiroProperties shiroProperties;

    /**
     * 实现用户的登录行为
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 模拟数据库中用户数据
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("admin", "2c1980287463d99758e0200114b412f1e0b2c7c5b49a17bba6851e51b18190f7"); // 密码明文：123456
        userInfo.put("system", "e18ec552bf6a1c9ac6a789aabc6fd136c04cefbde1eed3a5024a449e7f427ace"); // 密码明文：000000
        userInfo.put("visitor", "2f5740b9080c4d12da10e61ba3903d4350bda6e8510f5644200589453a821671"); // 密码明文：123

        // 这里强转的类型不一定要是 UsernamePasswordToken，具体要看你在登录接口中所传的对象类型
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        if (!StringUtils.hasText(username)) {
            throw new AccountException("用户名不能为空");
        }
        // 判断用户是否存在（实际业务操作中，这里是去数据库查询用户数据，用 user 对象去判断，而不是 password String 来判断）
        String password = userInfo.get(username); // 模拟去数据库中查询 user 对象
        if (!StringUtils.hasText(password)) {
            throw new UnknownAccountException("用户不存在");
        }
        // 这里传入的是用户正确的用户名和密码，shiro 拿正确的用户名密码去和传入的用户名密码参数做校验
        ByteSource salt = ByteSource.Util.bytes(shiroProperties.getSalt());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, salt, getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 返回用户的角色权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 这里 username 没有被使用，实际业务中可以用该 username 获取用户的 role 和 permission 进行赋值
        String username = (String) getAvailablePrincipal(principalCollection);

        // 角色（手动模拟赋值）
        Set<String> roles = new HashSet<>();
        if ("admin".equals(username)) {
            roles.add("Admin");
            roles.add("System");
            roles.add("Visitor");
        }
        if ("system".equals(username)) {
            roles.add("System");
            roles.add("Visitor");
        }
        if ("visitor".equals(username)) {
            roles.add("Visitor");
        }
        // 权限（手动模拟赋值）
        Set<String> perms = new HashSet<>();
        if (roles.contains("Admin")) {
            perms.add("admin:*");
        }
        if (roles.contains("System")) {
            perms.add("system:*");
        }
        if (roles.contains("Visitor")) {
            perms.add("visitor:*");
        }

        // 为当前用户添加角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(perms);
        return info;
    }

}
