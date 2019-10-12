package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.service.MyTeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/7/31 9:07
 * @Version 1.0
 */


@Controller
@RequestMapping(value = {"/test","/abcdes"})
@MenuAcls(value = {"abcd","1234"})
public class TestController {

    @Autowired
    MyTeeService myTeeService;

    @GetMapping("/hello")
    @ResponseBody
    public JsonData hello(String userName){
        //throw new RuntimeException("aaa");
        return JsonData.success("Hello World! " + userName);
    }

    @PostMapping("/helloPost")
    @ResponseBody
    public JsonData helloPost(String userName){
        //throw new RuntimeException("aaa");
        return JsonData.success("Hello Post! "+ userName);
    }

    @GetMapping("/queryData")
    @ResponseBody
    public JsonData queryData(){
        return JsonData.success(myTeeService.queryTeeData());
    }

}
