package com.sgai.cxmc.service;

import com.sgai.cxmc.vo.AppLoginInfo;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/7/31 13:53
 * @Version 1.0
 */

public interface UserService {

    Object addAppLoginInfo(AppLoginInfo appLoginInfo);

    Object login(String userName, String password);
}
