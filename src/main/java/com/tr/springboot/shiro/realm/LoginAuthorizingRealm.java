package com.tr.springboot.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author TR
 * @date 2022/7/4 上午8:54
 */
public class LoginAuthorizingRealm extends AuthorizingRealm {

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
        userInfo.put("James", "123");
        userInfo.put("Wade", "456");

        // 这里强转的类型不一定要是 UsernamePasswordToken，具体要看你在登录接口中所传的对象类型
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        if (!StringUtils.hasText(username)){
            throw new AccountException("用户名不能为空");
        }
        // 判断用户是否存在（实际业务操作中，这里是去数据库查询用户数据，用 user 对象去判断，而不是 password String 来判断）
        String password = userInfo.get(username); // 模拟去数据库中查询 user 对象
        if (!StringUtils.hasText(password)) {
            throw new UnknownAccountException("用户不存在");
        }
        // 这里有三个参数，第一个是账号，第二个是密码，第三个是用户名
        // 这里传入的是用户正确的用户名和密码，shiro 拿正确的用户名密码去和传入的用户名密码参数做校验
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, username);
//      SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getAccountNo(), user.getPassword(), user.getUsername());
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

        // 角色
        Set<String> roles = new HashSet<>();
        roles.add("Admin");
        // 权限
        Set<String> permissions = new HashSet<>();
        permissions.add("/list");
        permissions.add("/add");
        // 为当前用户添加角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

}
