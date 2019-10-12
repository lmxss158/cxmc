package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.McAppInfoService;
import com.sgai.cxmc.service.QueryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/9/16 11:07
 * @Version 1.0
 */
@Service("mcAppInfoService")
public class McAppInfoServiceImpl implements McAppInfoService {
    @Autowired
    QueryDataService queryDataService;

    @Override
    public Object mcAppVersionInfo() {
        String sql = "select APPID,\n"
                + "       APPNAME,\n"
                + "       CURRENT_VERSION,\n"
                + "       CURRENT_CODE,\n"
                + "       VERSION_URL,\n"
                + "       left(RELEASE_DATA, 19),\n"
                + "       VERSION_INFO,\n"
                + "       UPDATE_TYPE\n"
                + "from MC_APP_INFO\n"
                + "where CURRENT_CODE = (select max(CURRENT_CODE) from MC_APP_INFO)";
        return queryDataService.query(sql);
    }
}
