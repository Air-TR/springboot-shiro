package com.tr.springboot.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TR
 * @date 2022/7/1 下午5:53
 */
@RestController
public class ShiroController {

    @GetMapping("/login/{username}/{password}")
    public String login(@PathVariable String username, @PathVariable String password) {
        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException e) {
            return "UnknownAccountException: " + e.getMessage();
        } catch (AccountException e) {
            return "AccountException: " + e.getMessage();
        } catch (IncorrectCredentialsException e) {
            return "IncorrectCredentialsException: " + e.getMessage();
        }
        return "登陆成功，准备跳转主页";
    }

    @GetMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "登出成功，准备跳转登录页...";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "登录页";
    }

    @GetMapping("/403")
    public String page403() {
        return "403：没有权限，拒绝访问！（通过 ShiroConfig 配置重定向）";
    }

    @GetMapping("/my403")
    public String pageMy403() {
        return "my403：没有权限，拒绝访问！（经过 ShiroExceptionAdvice 重定向）";
    }

    @RequiresPermissions("perm:view") // @RequiresPermissions 方式不推荐，建议在 ShiroConfig 中配置
    @GetMapping("/requires/perm")
    public String list() {
        return "/requires/perm --> success";
    }

    @RequiresRoles("System") // @RequiresRoles 方式不推荐，建议在 ShiroConfig 中配置
    @GetMapping("/requires/role")
    public String add() {
        return "/requires/role --> success";
    }

}
