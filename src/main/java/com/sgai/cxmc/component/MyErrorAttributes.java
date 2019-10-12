package com.sgai.cxmc.component;


import com.sgai.cxmc.common.JsonData;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @Description: 自定义错误参数封装
 * @Author: 李锐平
 * @Date: 2019/5/20 18:02
 * @Version 1.0
 */

@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);

        //若启用 com.sgai.cxmc.controller.MyExceptionHandler 自定义处理，则message使用下面这种方式获取
        //String msg = (String) webRequest.getAttribute("message", WebRequest.SCOPE_REQUEST);

        String msg = map.get("message") + "";
        Map<String, Object> myMap = JsonData.fail(msg).toMap();
        myMap.put("status",map.get("status"));
        return myMap;

    }
}
