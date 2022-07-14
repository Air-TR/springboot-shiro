package com.tr.springboot.shiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TR
 * @date 2022/7/7 下午4:17
 */
@RestController
public class RoleController {

    @GetMapping("/role/admin")
    public String admin() {
        return "/role/admin --> success";
    }

    @GetMapping("/role/admin/1")
    public String admin1() {
        return "/role/admin/1 --> success";
    }

    @GetMapping("/role/system")
    public String system() {
        return "/role/system --> success";
    }

    @GetMapping("/role/system/1")
    public String system1() {
        return "/role/system/1 --> success";
    }

    @GetMapping("/role/visitor")
    public String visitor() {
        return "/role/visitor --> success";
    }

    @GetMapping("/role/visitor/1")
    public String visitor1() {
        return "/role/visitor/1 --> success";
    }
    
}
