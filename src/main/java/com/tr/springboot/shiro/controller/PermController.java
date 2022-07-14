package com.tr.springboot.shiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TR
 * @date 2022/7/7 下午4:18
 */
@RestController
public class PermController {

    @GetMapping("/perm/admin")
    public String admin() {
        return "/perm/admin --> success";
    }

    @GetMapping("/perm/admin/1")
    public String admin1() {
        return "/perm/admin/1 --> success";
    }

    @GetMapping("/perm/system")
    public String system() {
        return "/perm/system --> success";
    }

    @GetMapping("/perm/system/1")
    public String system1() {
        return "/perm/system/1 --> success";
    }

    @GetMapping("/perm/visitor")
    public String visitor() {
        return "/perm/visitor --> success";
    }

    @GetMapping("/perm/visitor/1")
    public String visitor1() {
        return "/perm/visitor/1 --> success";
    }

}
