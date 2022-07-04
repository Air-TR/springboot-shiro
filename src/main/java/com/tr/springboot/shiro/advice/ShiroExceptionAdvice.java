package com.tr.springboot.shiro.advice;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author TR
 * @date 2022/7/4 下午2:27
 */
@RestControllerAdvice
public class ShiroExceptionAdvice {

    @ExceptionHandler(AuthorizationException.class)
    public ModelAndView unAuth(AuthorizationException e){
        e.printStackTrace();
        return new ModelAndView("/403");
    }

}
