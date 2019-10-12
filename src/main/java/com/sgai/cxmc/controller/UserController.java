package com.sgai.cxmc.controller;

import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.service.MyTeeService;
import com.sgai.cxmc.service.UserService;
import com.sgai.cxmc.vo.AppLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/7/31 9:07
 * @Version 1.0
 */


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public JsonData login(@RequestParam("userName") String userName,
                          @RequestParam("password") String password){

        Object obj = userService.login(userName,password);
        if (obj instanceof String) {
            return JsonData.fail((String) obj);
        } else {
            return JsonData.success(obj);
        }
    }
}
