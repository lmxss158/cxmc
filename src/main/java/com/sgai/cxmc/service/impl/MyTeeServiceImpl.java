package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.MyTeeService;
import com.sgai.cxmc.service.QueryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/7/31 13:54
 * @Version 1.0
 */


@Service
public class MyTeeServiceImpl implements MyTeeService {

    @Autowired
    QueryDataService queryDataService;

    @Override
    public Object queryTeeData() {

        String sql = "SELECT calendar,company_code,company_name,zb_name,\n "
                + "       curvalue,sumyear,unit_name\n"
                + " FROM sgmc.V_MC_ZB_FI\n"
                + " where zb_name in('营业总收入','利润总额','现金净流量','存货--不扣除跌价','应收账款净额')\n"
                + "  and company_code='1A10'\n"
                + "  and calendar=(select max(calendar) from sgmc.v_mc_zb_fi where company_code='1A10')\n"
                + " order by calendar desc";

        return queryDataService.query(sql);
    }
}
