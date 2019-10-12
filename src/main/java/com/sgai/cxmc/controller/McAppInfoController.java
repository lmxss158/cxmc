package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.McAppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 版本更新controller
 * @Author 张年禄
 * @Date 2019/9/16 11:02
 * @Version 1.0
 */

@Controller
@RequestMapping("/mc/app")
public class McAppInfoController {

    @Autowired
    @Qualifier("mcAppInfoService")
    McAppInfoService mcAppInfoService;

    @GetMapping("/version/info")
    @ResponseBody
    public JsonData mcAppVersionInfo(){
        return JsonData.success(mcAppInfoService.mcAppVersionInfo());
    }
}

/*
@Controller
@RequestMapping("/mc/app")
public class McAppInfoController {

    @Autowired
    @Qualifier("mcAppInfoService")
    McAppInfoService mcAppInfoService;


    @ResponseBody
    @PostMapping("/version/info")
    public JsonData mcAppVersionInfo() {


        return JsonData.success(mcAppInfoService.mcAppVersionInfo());
    }
}*/
