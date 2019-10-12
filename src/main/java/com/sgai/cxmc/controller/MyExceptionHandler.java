package com.sgai.cxmc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 自定义HTTP状态异常处理器,暂时不用，在com.sgai.cxmc.component.MyErrorAttributes中封装即可。
 *     注解 @ControllerAdvice 表示加入Spring容器并起作用
 * @Author: 李锐平
 * @Date: 2019/7/31 11:43
 * @Version 1.0
 */

@Slf4j
//@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e , HttpServletRequest request) {

//        e.printStackTrace();

        int httpStatus ;
        if (e instanceof org.springframework.web.bind.ServletRequestBindingException) {
            //说明： MissingServletRequestParameterException extends ServletRequestBindingException
            httpStatus = 400;
            log.warn(e.getMessage());
        } else if (e instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            httpStatus = 405;
            log.warn(e.getMessage());
        } else if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            httpStatus = 404;
            log.warn(e.getMessage());
        } else {
            httpStatus = 500;
            log.error(e.getMessage());
        }

        request.setAttribute("javax.servlet.error.status_code",httpStatus);

        request.setAttribute("message",e.getMessage());

        return "forward:/error";
    }
}
