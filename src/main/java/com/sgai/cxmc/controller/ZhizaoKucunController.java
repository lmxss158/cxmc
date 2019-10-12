package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.ZhizaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 制造模块-库存Controller
 * @Author: 李锐平
 * @Date: 2019/8/6 21:09
 * @Version 1.0
 */


@Controller
@RequestMapping("/zhizao/kucun")
@MenuAcls(value = {"-1516266","20006"})
public class ZhizaoKucunController {

    @Autowired
    @Qualifier("zhizaoServiceQs")
    ZhizaoService zhizaoServiceQs;

// 库存管理-库存总览

    /**
     * 制造管理-库存管理-库存总览-当日库存、在半产品库存明细
     * @param subCode
     * @return
     */
    @GetMapping("/zbcpkcmx")
    @ResponseBody
    public JsonData kcZbcpkcmx(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).kcZbcpkcmx());
    }

    /**
     * 制造管理-库存管理-库存总览-在半产品库存分布
     * @param subCode
     * @return
     */
    @GetMapping("/zbcpkcfb")
    @ResponseBody
    public JsonData kcZbcpkcfb(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).kcZbcpkcfb());
    }

// 库存管理-库存明细

    //炼钢

    /**
     * 制造管理-库存管理-库存明细-炼钢库存结构
     * @param subCode
     * @return
     */
    @GetMapping("/lgkcjg/{type}")
    @ResponseBody
    public JsonData kcLgkcjg(@PathVariable("type") String type,
                              @RequestParam("subCode") String subCode) {
        String typeName;
        switch (type) {
            case "lg1":
                typeName = "一炼钢";
                break;
            case "lg2":
                typeName = "二炼钢";
                break;
            default:
                return JsonData.fail("无效参数");
        }
        return JsonData.success(getZhizaoService(subCode).kcLgkcjg(typeName));

    }

    /**
     * 制造管理-库存管理-库存明细-炼钢库存趋势-一炼钢
     * @param subCode
     * @return
     */
    @GetMapping("/lgkcqs/lg1")
    @ResponseBody
    public JsonData kcLg1kcqs(@RequestParam("cpkCode") String cpkCode,
                              @RequestParam("subCode") String subCode) {
        String typeName;
        switch (cpkCode) {
            case "bpk":
                typeName = "板坯";
                break;
            case "jqk":
                typeName = "机清";
                break;
            default:
                return JsonData.fail("无效参数");
        }
        return JsonData.success(getZhizaoService(subCode).kcLg1kcqs(typeName));

    }

    /**
     * 制造管理-库存管理-库存明细-炼钢库存趋势-二炼钢板坯库
     * @param subCode
     * @return
     */
    @GetMapping("/lgkcqs/lg2")
    @ResponseBody
    public JsonData kcLg2kcqs(@RequestParam("cpkCode") String cpkCode,
                              @RequestParam("subCode") String subCode) {
        if (!"bpk".equals(cpkCode)) {
            return JsonData.fail("参数不支持");
        }
        return JsonData.success(getZhizaoService(subCode).kcLg2kcqs());

    }

    //热轧

    /**
     * 制造管理-库存管理-库存明细-热轧库存结构
     * @param subCode
     * @return
     */
    @GetMapping("/rzkcjg/{type}")
    @ResponseBody
    public JsonData kcRzkcjg(@PathVariable("type") String type,
                             @RequestParam("subCode") String subCode) {
        String typeName = getRzTypeName(type);
        if (typeName == null) {
            return JsonData.fail("无效请求");
        }
        return JsonData.success(getZhizaoService(subCode).kcRzkcjg(typeName));

    }

    /**
     * 制造管理-库存管理-库存明细-热轧库存趋势
     * /rzkcqs/rz1
     * /rzkcqs/rz2
     * @param subCode
     * @return
     */
    @GetMapping("/rzkcqs/{type}")
    @ResponseBody
    public JsonData kcRzkcqs(@PathVariable("type") String type,
                             @RequestParam("cpkCode") String cpkCode,
                             @RequestParam("subCode") String subCode) {
        String typeName = getRzTypeName(type);
        if (typeName == null) {
            return JsonData.fail("无效参数");
        }

        String kuName = getRzKuName(type,cpkCode);
        if (kuName == null) {
            return JsonData.fail("无效参数");
        }
        String kuTypeName = typeName+"_"+kuName;

        return JsonData.success(getZhizaoService(subCode).kcRzkcqs(kuTypeName));

    }

    private String getRzTypeName(String type){
        switch (type) {
            case "rz1":
                return "一热轧";
            case "rz2":
                return "二热轧";
            default:
                return null;
        }
    }

    private String getRzKuName(String type, String cpkCode){

        if ("rz2".equals(type)) {
            switch (cpkCode) {
                case "ylk":
                    return "原料";
                case "bjk":
                    return "板卷";
                default:
                    return null;
            }
        } else if ("rz1".equals(type)) {
            switch (cpkCode) {
                case "ylk":
                    return "原料";
                case "bjk":
                    return "板卷";
                case "jqylk":
                    return "剪切原料";
                case "jqcpk":
                    return "剪切成品";
                case "hqylk":
                    return "横切原料";
                case "hqcpk":
                    return "横切成品";
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    //酸洗

    /**
     * 制造管理-库存管理-库存明细-酸洗库存结构
     * @param subCode
     * @return
     */
    @GetMapping("/sxkcjg")
    @ResponseBody
    public JsonData kcSxkcjg(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).kcSxkcjg());

    }

    /**
     * 制造管理-库存管理-库存明细-酸洗库存趋势
     * @param subCode
     * @return
     */
    @GetMapping("/sxkcqs")
    @ResponseBody
    public JsonData kcSxkcqs(@RequestParam("cpkCode") String cpkCode,
                              @RequestParam("subCode") String subCode) {
        String typeName;
        switch (cpkCode) {
            case "ylk":
                typeName = "原料";
                break;
            case "cpk":
                typeName = "成品";
                break;
            case "hqcpk":
                typeName = "横切成品";
                break;
            default:
                return JsonData.fail("无效参数");
        }
        return JsonData.success(getZhizaoService(subCode).kcSxkcqs(typeName));
    }

    //顺义冷轧

    /**
     * 制造管理-库存管理-库存明细-顺义冷轧库存结构
     * @param subCode
     * @return
     */
    @GetMapping("/slkcjg")
    @ResponseBody
    public JsonData kcSlkcjg(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).kcSlkcjg());

    }

    /**
     * 制造管理-库存管理-库存明细-顺义冷轧库存趋势
     * @param subCode
     * @return
     */
    @GetMapping("/slkcqs")
    @ResponseBody
    public JsonData kcSlkcqs(@RequestParam("cpkCode") String cpkCode,
                             @RequestParam("subCode") String subCode) {
        String typeName;
        switch (cpkCode) {
            case "ylk":
                typeName = "原料";
                break;
            case "cpk":
                typeName = "成品";
                break;
            case "zhk":
                typeName = "轧后";
                break;
            case "llylk":
                typeName = "落料原料";
                break;
            case "llcpk":
                typeName = "落料成品";
                break;
            default:
                return JsonData.fail("无效参数");
        }
        return JsonData.success(getZhizaoService(subCode).kcSlkcqs(typeName));
    }

    //智新一二三区

    /**
     * 制造管理-库存管理-库存明细-智新一二三区库存结构
     * @param subCode
     * @return
     */
    @GetMapping("/zxkcjg")
    @ResponseBody
    public JsonData kcZxkcjg(@RequestParam("subCode") String subCode,
                             @RequestParam("typeCode") String typeCode) {

        String typeName = getZxTypeName(typeCode);

        return JsonData.success(getZhizaoService(subCode).kcZxkcjg(typeName));

    }

    private String getZxTypeName(String type){
        switch (type) {
            case "zx1":
                return "智新一区";
            case "zx2":
                return "智新二区";
            case "zx3":
                return "智新三区";
            default:
                return null;
        }
    }

    private String getZxKuName(String type,String cpkCode){
        switch (type) {
            case "zx1":
                switch (cpkCode) {
                    case "jqqk1":
                        return "1#剪切前库";
                    case "cpk1":
                        return "1#成品库";
                    case "jqqk2":
                        return "2#剪切前库";
                    case "ylk":
                        return "原料库";
                    case "zhk":
                        return "轧后库";
                    default:
                        return null;
                }

            case "zx2":
                switch (cpkCode) {
                    case "cpk2":
                        return "2#成品库";
                    case "jqqk3":
                        return "3#剪切前库";
                    case "apljpk":
                        return "APL机旁库";
                    case "esgk":
                        return "二十辊库";
                    case "ltqk":
                        return "连退前库";
                    default:
                        return null;
                }
            case "zx3":
                switch (cpkCode) {
                    case "cpk3":
                        return "3#成品库";
                    case "jqqk4":
                        return "4#剪切前库";
                    case "hxlqhk":
                        return "环形炉前后库";
                    case "ttthqk":
                        return "脱碳退火前库";
                    case "ttthhk":
                        return "脱碳退火后库";
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    /**
     * 制造管理-库存管理-库存明细-智新一二三区库存趋势
     * @param subCode
     * @return
     */
    @GetMapping("/zxkcqs")
    @ResponseBody
    public JsonData kcZxkcqs(@RequestParam("cpkCode") String cpkCode,
                             @RequestParam("typeCode") String typeCode,
                             @RequestParam("subCode") String subCode) {

        String typeName = getZxTypeName(typeCode);
        String cpkName = getZxKuName(typeCode,cpkCode);
        String queryName = typeName + "_" + cpkName;
        if (typeName == null || cpkName == null) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getZhizaoService(subCode).kcZxkcqs(queryName));
    }

    // -------制造管理-库存管理end-------------

    private ZhizaoService getZhizaoService(String subCode){
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return zhizaoServiceQs;
            case JT:
                throw new  RuntimeException("京唐制造模块-库存管理暂未开发："+subCode);
            default:
                throw new  RuntimeException("当前接口暂不支持的subCode ："+subCode);
        }
    }
}