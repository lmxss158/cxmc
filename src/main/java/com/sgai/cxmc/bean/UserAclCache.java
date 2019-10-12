package com.sgai.cxmc.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/9/5 17:14
 * @Version 1.0
 */

public class UserAclCache {

    public static Map<String,String> userAclMap = new HashMap<>();

    public static Map<String, String> getUserAclMap() {
        return userAclMap;
    }
}
