package com.sgai.cxmc.controller;

import com.alibaba.fastjson.JSONObject;
import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.CaigouYrlkcService;
import com.sgai.cxmc.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Description: 采购管理-原燃料库存Controller
 * @Author: 张年禄
 * @Date: 2019/9/6 11:00
 * @Version 1.0
 */

@Controller
@RequestMapping("/caigou/yrlkc")
@MenuAcls(value = {"-1516258","19997"})
public class CaigouYrlkcController {

    @Autowired
    @Qualifier("caigouYrlkcServiceQs")
    CaigouYrlkcService caigouYrlkcServiceQs;

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心-进口块矿、粉矿计划量
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/cgzx/kkfkjhl")
    @ResponseBody
    public Object yrlTksCgzxJhl(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlTksCgzxJhla(selectDate));
    }

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心-进口块矿、粉矿港口库存量
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/cgzx/kkfkgckcl")
    @ResponseBody
    public Object yrlTksCgzxGckcl(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);
        return JsonData.success(getCaigouYrlkcService(subCode).yrlTksCgzxGckcla(selectDate));
    }

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心-进口块矿、粉矿厂内入库
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/cgzx/kgfkcnrk")
    @ResponseBody
    public Object yrlTksCgzxKgfkcnrk(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);
        return JsonData.success(getCaigouYrlkcService(subCode).yrlTksCgzxKgfkcnrk(selectDate));
    }

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心-进口块矿、粉矿厂内出库
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/cgzx/kgfkcnck")
    @ResponseBody
    public Object yrlTksCgzxKgfkcnck(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);
        return JsonData.success(getCaigouYrlkcService(subCode).yrlTksCgzxKgfkcnck(selectDate));
    }

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心-进口块矿、粉矿厂内库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/cgzx/kkfkcnkc")
    @ResponseBody
    public Object yrlTksCgzxCnkc(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);
        return JsonData.success(getCaigouYrlkcService(subCode).yrlTksCgzxCnkc(selectDate));
    }

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心-国内矿-计划量
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/cgzx/gnkjhl")
    @ResponseBody
    public Object yrlTksCgzxGnkJhl(@RequestParam("subCode") String subCode) {

        return JsonData.success(getCaigouYrlkcService(subCode).yrlTksCgzxGnkJhl());
    }

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心-国内矿厂内入库，出库，库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/cgzx/gnkcrkckkc")
    @ResponseBody
    public Object yrlTksCgzxCrkckkc(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);
        return JsonData.success(getCaigouYrlkcService(subCode).yrlTksCgzxCrkckkc(selectDate));
    }

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心
     *
     * @param subCode
     * @return

    @GetMapping("/tks/cgzxkc")
    @ResponseBody
    public Object yrlTksCgzx(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);
        Object jkkkfkjhlList = getCaigouYrlkcService(subCode).yrlTksCgzxJhla(selectDate);
        Object kkfkgkkclList = getCaigouYrlkcService(subCode).yrlTksCgzxGckcla(selectDate);
        Object jkkkfkcnrkList = getCaigouYrlkcService(subCode).yrlTksCgzxKgfkcnrk(selectDate);
        Object jkkkfkcnckList = getCaigouYrlkcService(subCode).yrlTksCgzxKgfkcnck(selectDate);
        Object jkkffkcnkcList = getCaigouYrlkcService(subCode).yrlTksCgzxCnkc(selectDate);
        Object gnkjhlList = getCaigouYrlkcService(subCode).yrlTksCgzxGnkJhl();
        Object gnkckrkkcList = getCaigouYrlkcService(subCode).yrlTksCgzxCrkckkc(selectDate);

        JSONObject object = new JSONObject();
        object.put("jkkkfkjhlList",jkkkfkjhlList);
        object.put("kkfkgkkclList",kkfkgkkclList);
        object.put("jkkkfkcnrkList",jkkkfkcnrkList);
        object.put("jkkkfkcnckList",jkkkfkcnckList);
        object.put("jkkffkcnkcList",jkkffkcnkcList);
        object.put("gnkjhlList", gnkjhlList);
        object.put("gnkckrkkcList",gnkckrkkcList);

        return object;
    }
     */

    /**
     * 采购管理-原燃料库存-铁矿石-采购中心-近三十日库存趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/cgzx/jssrkcqst")
    @ResponseBody
    public Object yrlTksCgzxJssrkcqst(@RequestParam("subCode") String subCode,
                                      @RequestParam("typeCode") String typeCode) {
        String typeName = getTksJssrkcqstTypeName(typeCode);
        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouYrlkcService(subCode).yrlTksCgzxJssrkcqst(typeName));

    }

    /**
     * 采购管理-原燃料库存-喷吹煤-采购中心
     *
     * @param subCode
     * @return
     */
    @GetMapping("/pcm/cgzx")
    @ResponseBody
    public Object yrlPcmCgzx(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlPcmCgzx(selectDate));
    }

    /**
     * 采购管理-原燃料库存-喷吹煤-计划量
     *
     * @param subCode
     * @return
     */
    @GetMapping("/pcm/cgzx/jhl")
    @ResponseBody
    public Object yrlPcmCgzxJhl(@RequestParam("subCode") String subCode) {

        return JsonData.success(getCaigouYrlkcService(subCode).yrlPcmCgzxJhl());
    }

    /**
     * 采购管理-原燃料库存-喷吹煤-当日库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("/pcm/drkc")
    @ResponseBody
    public Object yrlPcmDrkc(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlPcmDrkc(selectDate));
    }

    /**
     * 采购管理-原燃料库存-喷吹煤-近5日消耗及预计可用天数
     *
     * @param subCode
     * @return
     */
    @GetMapping("/pcm/jwrxhjkyts")
    @ResponseBody
    public Object yrlPcmJwrxhjkyts(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlPcmJwrxhjkyts(selectDate));
    }

    /**
     * 采购管理-原燃料库存-喷吹煤-近三十日库存趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/pcm/jssrkcqst")
    @ResponseBody
    public Object yrlPcmJssrkcqst(@RequestParam("subCode") String subCode,
                                  @RequestParam("typeCode") String typeCode) {
        String typeName = getPcmJssrkcqstTypeName(typeCode);
        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouYrlkcService(subCode).yrlPcmJssrkcqst(typeName));
    }

    /**
     * 采购管理-原燃料库存-焦碳-采购中心
     *
     * @param subCode
     * @return
     */
    @GetMapping("/jc/cgzx")
    @ResponseBody
    public Object yrlJcCgzx(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlJcCgzx(selectDate));
    }

    /**
     * 采购管理-原燃料库存-焦碳-计划量
     *
     * @param subCode
     * @return
     */
    @GetMapping("/jc/cgzx/jhl")
    @ResponseBody
    public Object yrlJcCgzxJhl(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlJcCgzxJhl(selectDate));
    }

    /**
     * 采购管理-原燃料库存-焦碳-当日库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("/jc/drkc")
    @ResponseBody
    public Object yrlJcDrkc(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlJcDrkc(selectDate));
    }

    /**
     * 采购管理-原燃料库存-焦碳-近三十日库存趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/jc/jssrkcqst")
    @ResponseBody
    public Object yrlPcmJssrkcqst(@RequestParam("subCode") String subCode) {

        return JsonData.success(getCaigouYrlkcService(subCode).yrlJcJssrkcqst());
    }

    /**
     * 采购管理-原燃料库存-外购废钢-采购中心
     *
     * @param subCode
     * @return
     */
    @GetMapping("/wgfg/cgzx")
    @ResponseBody
    public Object yrlWgfgCgzx(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlWgfgCgzx(selectDate));
    }

    /**
     * 采购管理-原燃料库存-外购废钢-采购中心-计划量
     *
     * @param subCode
     * @return
     */
    @GetMapping("/wgfg/cgzx/jhl")
    @ResponseBody
    public Object yrlWgfgCgzxJhl(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlWgfgCgzxJhl());
    }

    /**
     * 采购管理-原燃料库存-外购废钢-当日库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("/wgfg/drkc")
    @ResponseBody
    public Object yrlWgfgDrkc(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlWgfgDrkc(selectDate));
    }

    /**
     * 采购管理-原燃料库存-外购废钢-近三十日库存趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/wgfg/jssrkcqst")
    @ResponseBody
    public Object yrlWgfgJssrkcqst(@RequestParam("subCode") String subCode,
                                   @RequestParam("typeCode") String typeCode) {
        String typeName = getWgfgJssrkcqstTypeName(typeCode);
        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouYrlkcService(subCode).yrlWgfgJssrkcqstP(typeName));

    }

    /**
     * 采购管理-原燃料库存-铁合金-采购中心
     *
     * @param subCode
     * @return
     */
    @GetMapping("/thj/cgzx")
    @ResponseBody
    public Object yrlThjCgzx(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlThjCgzx(selectDate));
    }

    /**
     * 采购管理-原燃料库存-铁合金-采购中心-计划量
     *
     * @param subCode
     * @return
     */
    @GetMapping("thj/cgzx/jhl")
    @ResponseBody
    public Object yrlThjCgzxJhl(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlThjCgzxJhl());
    }

    /**
     * 采购管理-原燃料库存-铁合金-当日库存
     *
     * @param subCode
     * @return
     */
    @GetMapping("/thj/drkc")
    @ResponseBody
    public Object yrlThjDrkc(@RequestParam("subCode") String subCode) {

        String selectDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        return JsonData.success(getCaigouYrlkcService(subCode).yrlThjDrkc(selectDate));
    }


    /**
     * 采购管理-原燃料库存-铁合金-近三十日库存趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/thj/jssrkcqst")
    @ResponseBody
    public Object yrlThjJssrkcqst(@RequestParam("subCode") String subCode,
                                  @RequestParam("typeCode") String typeCode) {
        String typeName = getThjJssrkcqstTypeName(typeCode);
        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouYrlkcService(subCode).yrlThjJssrkcqst(typeName));

    }

    private CaigouYrlkcService getCaigouYrlkcService(String subCode) {
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return caigouYrlkcServiceQs;
            case JT:
                throw new RuntimeException("京唐采购模块-原燃料库存暂未开发：" + subCode);
            default:
                throw new RuntimeException("当前接口暂不支持的subCode：" + subCode);
        }
    }

    private String getTksJssrkcqstTypeName(String typeCode) {

        switch (typeCode) {
            case "jkkk":
                return "进口块矿";
            case "jkfk":
                return "进口粉矿";
            case "gnk":
                return "国内矿";
            default:
                return null;
        }

    }

    private String getPcmJssrkcqstTypeName(String typeCode) {

        switch (typeCode) {
            case "shm":
                return "神华煤";
            case "lam":
                return "潞安煤";
            case "yqm":
                return "阳泉煤";
            case "qtpcm":
                return "其他喷吹煤";
            default:
                return null;
        }
    }

    private String getWgfgJssrkcqstTypeName(String typeCode) {

        switch (typeCode) {
            case "wgptfg":
                return "外购普通废钢";
            case "wgdlfg":
                return "外购低硫废钢";
            case "st":
                return "生铁";
            default:
                return null;
        }
    }

    private String getThjJssrkcqstTypeName(String typeCode) {

        switch (typeCode) {
            case "pthj":
                return "普通合金";
            case "yshj":
                return "有色合金";
            case "tzhj":
                return "特种合金";
            case "fhhj":
                return "复合合金";
            default:
                return null;
        }
    }

}
