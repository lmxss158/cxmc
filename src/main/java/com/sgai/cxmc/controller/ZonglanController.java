package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.ZonglanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 总览模块Controller
 * @Author: 李锐平
 * @Date: 2019/8/5 16:02
 * @Version 1.0
 */


@Controller
@RequestMapping("/zonglan")
@MenuAcls(value = {"-1516245", "19990"})
public class ZonglanController {

    @Autowired
    @Qualifier("zonglanServiceQs")
    ZonglanService zonglanServiceQs;

    /**
     * 财务信息
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cwxx")
    @ResponseBody
    public JsonData cwxx(@RequestParam("subCode") String subCode,
                         @RequestParam("companyCode") String companyCode) {

        if ("1A10".equals ( companyCode ) || "1A20".equals ( companyCode )) {
            return JsonData.success ( getZonglanService ( subCode ).cwxx ( companyCode ) );
        } else {
            return JsonData.fail ( "无效参数" );
        }
    }

    /**
     * 制造信息-日产量
     *
     * @param subCode
     * @return
     */
    @GetMapping("zzxx/rcl")
    @ResponseBody
    public JsonData zzRcl(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).zzRcl ( ) );
    }

    /**
     * 制造信息-在半产品库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("zzxx/zbcpkc")
    @ResponseBody
    public JsonData zzZbcpkc(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).zzZbcpkc ( ) );
    }

    /**
     * 制造信息-在半产品库存分类统计
     *
     * @param subCode
     * @return
     */
    @GetMapping("zzxx/zbcpkcfltj")
    @ResponseBody
    public JsonData zzZbcpkcfltj(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).zzZbcpkcfltj ( ) );
    }

    /**
     * 制造信息-关键指标
     *
     * @param subCode
     * @return
     */
    @GetMapping("zzxx/gjzb")
    @ResponseBody
    public JsonData zzGjzb(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).zzGjzb ( ) );
    }

    /**
     * 销售信息-日发货量
     *
     * @param subCode
     * @return
     */
    @GetMapping("xsxx/rfhl")
    @ResponseBody
    public JsonData xsRfhl(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).xsRfhl ( ) );
    }

    /**
     * 销售信息-库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("xsxx/kc")
    @ResponseBody
    public JsonData xsKc(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).xsKc ( ) );
    }

    /**
     * 销售信息-次月订单量、其中重点产品
     *
     * @param subCode
     * @return
     */
    @GetMapping("xsxx/cyddl")
    @ResponseBody
    public JsonData xsCyddl(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).xsCyddl ( ) );
    }

    /**
     * 销售信息-月累计销量
     *
     * @param subCode
     * @return
     */
    @GetMapping("xsxx/yljxl")
    @ResponseBody
    public JsonData xsYljxl(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).xsYljxl ( ) );
    }

    /**
     * 采购信息-采购量
     *
     * @param subCode
     * @return
     */
    @GetMapping("cgxx/cgl")
    @ResponseBody
    public JsonData cgCgl(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).cgCgl ( ) );
    }

    /**
     * 采购信息-库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("cgxx/kc")
    @ResponseBody
    public JsonData cgKc(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getZonglanService ( subCode ).cgKc ( ) );
    }

    private ZonglanService getZonglanService(String subCode) {

        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf ( subCode );
        switch (subCodeEnum) {
            case QS:
                return zonglanServiceQs;
            case JT:
                throw new RuntimeException ( "京唐总览模块暂未开发：" + subCode );
            default:
                throw new RuntimeException ( "当前接口暂不支持的subCode：" + subCode );
        }
    }
}