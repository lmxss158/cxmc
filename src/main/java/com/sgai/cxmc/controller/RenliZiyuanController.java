package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.RenliZiyuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 人力资源
 * @Author 张年禄
 * @Date 2019/10/16 13:46
 * @Version 1.0
 */
@Controller
@RequestMapping("/renliziyuan")
@MenuAcls(value = "19991")
public class RenliZiyuanController {

    @Autowired
    @Qualifier("renliZiyuanServiceQs")
    RenliZiyuanService renliZiyuanServiceQs;

    /**
     * 人力资源管理-人力资源管理-劳产率、吨钢人工费-劳产信息及实物(转炉钢)劳产率趋势图
     * @param subCode
     * @return
     */
    @RequestMapping("/lclrgf/lcxxjqst")
    @ResponseBody
    public Object rlzyLclrgfLcxxjqst(@RequestParam("subCode") String subCode) {

        return JsonData.success (getRenliZiyuanService ( subCode ).getLclrgfLcxxjqst());
    }
    /**
     * 人力资源管理-人力资源管理-劳产率、吨钢人工费-上月人工费及比去年
     * @param subCode
     * @return
     */
    @RequestMapping("/lclrgf/rgfjbqn")
    @ResponseBody
    public Object rlzyLclrgfRgfjbqn(@RequestParam("subCode") String subCode) {

        return JsonData.success (getRenliZiyuanService ( subCode ).getLclrgfRgfjbqn());
    }
    /**
     * 人力资源管理-人力资源管理-劳产率、吨钢人工费-当月人工费
     * @param subCode
     * @return
     */
    @RequestMapping("/lclrgf/rgfdy")
    @ResponseBody
    public Object rlzyLclrgfRgfdy(@RequestParam("subCode") String subCode) {

        return JsonData.success (getRenliZiyuanService ( subCode ).getLclrgfRgfdy());
    }


    /**
     * 人力资源管理-人力资源管理-劳产率、吨钢人工费-人工费月度趋势图
     * @param subCode
     * @return
     */
    @RequestMapping("/lclrgf/rgfydqst")
    @ResponseBody
    public Object rlzyLclrgfRgfydqst(@RequestParam("subCode") String subCode) {

        return JsonData.success (getRenliZiyuanService ( subCode ).getLclrgfRgfydqst());
    }

    /**
     * 人力资源管理-人力资源管理-劳产率、吨钢人工费-投入产出年度占比
     * @param subCode
     * @return
     */
    @RequestMapping("/lclrgf/trccndzb")
    @ResponseBody
    public Object rlzyLclrgfTrccndzb(@RequestParam("subCode") String subCode) {

        return JsonData.success (getRenliZiyuanService ( subCode ).getLclrgfTrccndzb());
    }

    /**
     * 人力资源管理-人力资源管理-期末人数-在册人数月度趋势图
     * @param subCode
     * @return
     */
    @RequestMapping("/qmrs/zxrsydqst")
    @ResponseBody
    public Object rlzyQmrsZxrsydqst(@RequestParam("subCode") String subCode) {

        return JsonData.success (getRenliZiyuanService ( subCode ).getQmrsZxrsydqst());
    }

    /**
     * 人力资源管理-人力资源管理-期末人数-从业人员月度趋势图-当期人数、比上月、上年年末
     * @param subCode
     * @return
     */
    @RequestMapping("/qmrs/dqbsynm")
    @ResponseBody
    public Object rlzyQmrsDqrs(@RequestParam("subCode") String subCode) {

        return JsonData.success (getRenliZiyuanService ( subCode ).getQmrsDqrs());
    }

    /**
     * 人力资源管理-人力资源管理-期末人数-从业人员月度趋势图-从业人员条形趋势图
     * @param subCode
     * @return
     */
    @RequestMapping("/qmrs/cyryydtxt")
    @ResponseBody
    public Object rlzyQmrsCyryydtxt(@RequestParam("subCode") String subCode) {

        return JsonData.success (getRenliZiyuanService ( subCode ).getQmrsCyryydtxt());
    }
    private RenliZiyuanService getRenliZiyuanService(String subCode) {
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf ( subCode );
        switch (subCodeEnum) {
            case QS:
                return renliZiyuanServiceQs;
            case JT:
                throw new RuntimeException ( "京唐采购模块-原燃料库存暂未开发：" + subCode );
            default:
                throw new RuntimeException ( "当前接口暂不支持的subCode：" + subCode );
        }
    }

}
