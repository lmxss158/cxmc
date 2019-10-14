package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.NenghuanGuanliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/10/11 10:36
 * @Version 1.0
 */
@Controller
@RequestMapping("/nenghuanguanli")
@MenuAcls(value = "19986")
public class NenghauanGuanliController {

    @Autowired
    @Qualifier("nenghuanGuanliServiceQs")
    NenghuanGuanliService nenghuanGuanliServiceQs;

    /**
     * 能环管理-环境管理-AQI指数
     * @param subCode
     * @return
     */
    @RequestMapping("/aqizs")
    @ResponseBody
    public Object wlglAqizs(@RequestParam("subCode") String subCode) {

        return JsonData.success (getNenghuanGuanliService ( subCode ).getAqizs());
    }
    /**
     * 能环管理-环境管理-AQI指数-变化趋势图
     * @param subCode
     * @return
     */
    @RequestMapping("/aqizs/bhqst")
    @ResponseBody
    public Object wlglAqizsBhqst(@RequestParam("subCode") String subCode,
                                 @RequestParam("indexCode") String indexCode,
                                 @RequestParam("timeCode") String timeCode) {
        String indexName = getNhglBhqstName(indexCode);
        String timeName = getNhglTimeName(timeCode);
        if (indexName == null || timeName ==null){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getNenghuanGuanliService ( subCode ).getAqizsBhqst(indexName,timeName));
    }

    /**
     * 能环管理-环境管理-监控点信息
     * @param subCode
     * @return
     */
    @RequestMapping("/wryjc/jkdxx")
    @ResponseBody
    public Object wlglWryjcJkdxx(@RequestParam("subCode") String subCode,
                                 @RequestParam("companyCode") String companyCode) {
        String companyName = getNhglCompanyName(companyCode);
        if (companyName == null ){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getNenghuanGuanliService ( subCode ).getWryjcJkdxx(companyName));
    }

    /**
     * 能环管理-环境管理-AQI明细
     * @param subCode
     * @return
     */
    @RequestMapping("/wryjc/aqimx")
    @ResponseBody
    public Object wlglWryjcAqimx(@RequestParam("subCode") String subCode,
                                 @RequestParam("companyCode") String companyCode) {
        String companyName = getNhglCompanyName(companyCode);
        if (companyName == null ){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getNenghuanGuanliService ( subCode ).getWryjcAqimx(companyName));
    }

    /**
     * 能环管理-排污总量-年计划（颗粒物、二氧化硫、氮氧化物）
     * @param subCode
     * @return
     */
    @RequestMapping("/pwzl/kednjh")
    @ResponseBody
    public Object wlglPwzlKednjh(@RequestParam("subCode") String subCode) {
        return JsonData.success (getNenghuanGuanliService ( subCode ).getPwzlKednjh( ));
    }

    /**
     * 能环管理-排污总量-月计划（颗粒物、二氧化硫、氮氧化物）
     * @param subCode
     * @return
     */
    @RequestMapping("/pwzl/kedyjh")
    @ResponseBody
    public Object wlglPwzlKedyjh(@RequestParam("subCode") String subCode) {
        return JsonData.success (getNenghuanGuanliService ( subCode ).getPwzlKedyjh( ));
    }

    /**
     * 能环管理-排污总量-月实际（颗粒物、二氧化硫、氮氧化物）
     * @param subCode
     * @return
     */
    @RequestMapping("/pwzl/kedysj")
    @ResponseBody
    public Object wlglPwzlKedysj(@RequestParam("subCode") String subCode) {
        return JsonData.success (getNenghuanGuanliService ( subCode ).getPwzlKedysj( ));
    }

    /**
     * 能环管理-排污总量-同比及环比（颗粒物、二氧化硫、氮氧化物）
     * @param subCode
     * @return
     */
    @RequestMapping("/pwzl/tbjhb")
    @ResponseBody
    public Object wlglPwzlTbjhb(@RequestParam("subCode") String subCode,
                                @RequestParam("typeCode") String typeCode) {
        String typeName = getNhglPwzlName(typeCode);
        if (typeName == null ){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getNenghuanGuanliService ( subCode ).getPwzlTbjhb( typeName ));
    }

    /**
     * 能环管理-排污总量-累计（颗粒物、二氧化硫、氮氧化物）
     * @param subCode
     * @return
     */
    @RequestMapping("/pwzl/lj")
    @ResponseBody
    public Object wlglPwzlLj(@RequestParam("subCode") String subCode,
                                @RequestParam("typeCode") String typeCode) {
        String typeName = getNhglPwzlName(typeCode);
        if (typeName == null ){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getNenghuanGuanliService ( subCode ).getPwzlLj( typeName ));
    }
    /**
     * 能环管理-排污总量-月度趋势图（颗粒物、二氧化硫、氮氧化物）
     * @param subCode
     * @return
     */
    @RequestMapping("/pwzl/ydqst")
    @ResponseBody
    public Object wlglPwzlYdqst(@RequestParam("subCode") String subCode,
                                @RequestParam("typeCode") String typeCode) {
        String typeName = getNhglPwzlName(typeCode);
        if (typeName == null ){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getNenghuanGuanliService ( subCode ).getPwzlYdqst( typeName ));
    }

    /**
     * 能环管理-能源管理-月度实际（吨钢综合能耗、吨钢综合电耗、吨钢耗新水）
     * @param subCode
     * @return
     */
    @RequestMapping("/nygl/ydsj")
    @ResponseBody
    public Object wlglNyglYdsj(@RequestParam("subCode") String subCode) {
        return JsonData.success (getNenghuanGuanliService ( subCode ).getNyglYdsj( ));
    }
    /**
     * 能环管理-能源管理-年度实际（吨钢综合能耗、吨钢综合电耗、吨钢耗新水）
     * @param subCode
     * @return
     */
    @RequestMapping("/nygl/ndsj")
    @ResponseBody
    public Object wlglNyglNdsj(@RequestParam("subCode") String subCode) {
        return JsonData.success (getNenghuanGuanliService ( subCode ).getNyglNdsj( ));
    }

    /**
     * 能环管理-能源管理-月度趋势图（吨钢综合能耗、吨钢综合电耗、吨钢耗新水）
     * @param subCode
     * @return
     */
    @RequestMapping("/nygl/ydqst")
    @ResponseBody
    public Object wlglNyglYdqst(@RequestParam("subCode") String subCode,
                                @RequestParam("typeCode") String typeCode) {
        String typeName = getNhglNyglzlName(typeCode);
        if (typeName == null ){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getNenghuanGuanliService ( subCode ).getNyglYdqdt( typeName ));
    }

    private String getNhglNyglzlName(String typeCode) {
        switch (typeCode){
            case "dgzhnh":
                return "吨钢综合能耗";
            case "dgdh":
                return "吨钢电耗";
            case "dghxs":
                return "吨钢耗新水";
            default:
                return null;
        }
    }

    private String getNhglPwzlName(String typeCode) {
        switch (typeCode){
            case "klw":
                return "颗粒物";
            case "eyhl":
                return "二氧化硫";
            case "dyhw":
                return "氮氧化物";
            default:
                return null;
        }
    }

    private String getNhglCompanyName(String companyCode) {
        switch (companyCode){
            case "gf":
                return "能源部";
            case "lt":
                return "炼铁作业部";
            case "lg":
                return "炼钢作业部";
            case "rz":
                return "热轧作业部";
            case "zx":
                return "电磁公司";
            default:
                return null;
        }
    }

    private String getNhglTimeName(String timeCode) {
        switch (timeCode){
            case "xs":
                return "时";
            case "ri":
                return "日";
            case "yue":
                return "月";
            default:
                return null;
        }
    }

    private String getNhglBhqstName(String indexCode) {
        switch (indexCode){
            case "AQI":
                return "AQI";
            case "PM2.5":
                return "PM2.5";
            case "PM10":
                return "PM10";
            case "NO2":
                    return "NO2";
            case "CO":
                return "CO";
            case "O3":
                return "O3";
            case "SO2":
                return "SO2";
            default:
                return null;
        }
    }


    private NenghuanGuanliService getNenghuanGuanliService(String subCode){
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf ( subCode );
        switch (subCodeEnum){
            case QS:
                return nenghuanGuanliServiceQs;
            case JT:
                throw new RuntimeException ( "京唐采购模块-原燃料库存暂未开发：" + subCode );
            default:
                throw new RuntimeException ( "当前接口暂不支持的subCode：" + subCode );
        }
    }

}
