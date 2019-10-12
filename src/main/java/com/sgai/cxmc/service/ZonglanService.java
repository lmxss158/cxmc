package com.sgai.cxmc.service;

/**
 * @Description: 总览接口
 * @Author: 李锐平
 * @Date: 2019/8/5 16:05
 * @Version 1.0
 */

public interface ZonglanService {

    //财务信息
    Object cwxx(String companyCode);

    //制造信息-日产量
    Object zzRcl();

    //制造信息-在半产品库存
    Object zzZbcpkc();

    //制造信息-在半产品库存分类统计
    Object zzZbcpkcfltj();

    //制造信息-关键指标
    Object zzGjzb();


    //销售信息-日发货量
    Object xsRfhl();

    //销售信息-库存
    Object xsKc();

    //销售信息-次月订单量
    Object xsCyddl();

    //销售信息-月累计销量
    Object xsYljxl();

    //采购信息-采购量
    Object cgCgl();

    //采购信息-库存
    Object cgKc();

}
