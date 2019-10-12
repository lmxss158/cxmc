package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.ShebeiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 设备管理Controller
 * @Author: 李锐平
 * @Date: 2019/8/24 10:01
 * @Version 1.0
 */

@Controller
@RequestMapping("/shebei")
@MenuAcls(value = {"-1516250", "19994"})
public class ShebeiController {

    @Autowired
    @Qualifier("shebeiServiceQs")
    ShebeiService shebeiServiceQs;

    //设备运行总览

    /**
     * 设备管理-设备运行总览-概览-月累计停机时间
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxzl/yljtjsj/gl")
    @ResponseBody
    public JsonData zlGlyljtjsj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getShebeiService(subCode).zlGlyljtjsj());
    }

    /**
     * 设备管理-设备运行总览-日停机时间趋势 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxzl/tjsjqsd/{type}")
    @ResponseBody
    public JsonData zlTjsjqsd(@PathVariable("type") String type,
                              @RequestParam("subCode") String subCode) {
        String typeName = getZlQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).zlTjsjqsd(typeName));
    }

    /**
     * 设备管理-设备运行总览-月停机时间趋势 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxzl/tjsjqsm/{type}")
    @ResponseBody
    public JsonData zlTjsjqsm(@PathVariable("type") String type,
                              @RequestParam("subCode") String subCode) {
        String typeName = getZlQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).zlTjsjqsm(typeName));
    }

    /**
     * 设备管理-设备运行总览-月累计停机原因构成 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxzl/yljtjyygc/{type}")
    @ResponseBody
    public JsonData zlYljtjyygc(@PathVariable("type") String type,
                                @RequestParam("subCode") String subCode) {
        String typeName = getZlQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).zlYljtjyygc(typeName));
    }

    //设备运行明细

    /**
     * 设备管理-设备运行明细-概览-月累计停机时间
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxmx/yljtjsj/gl")
    @ResponseBody
    public JsonData mxGlyljtjsj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getShebeiService(subCode).mxGlyljtjsj());
    }

    /**
     * 设备管理-设备运行明细-日停机时间趋势 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxmx/tjsjqsd/{type}")
    @ResponseBody
    public JsonData mxTjsjqsd(@PathVariable("type") String type,
                              @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).mxTjsjqsd(typeName));
    }

    /**
     * 设备管理-设备运行明细-月停机时间趋势 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxmx/tjsjqsm/{type}")
    @ResponseBody
    public JsonData mxTjsjqsm(@PathVariable("type") String type,
                              @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).mxTjsjqsm(typeName));
    }

    /**
     * 设备管理-设备运行明细-月累计停机原因构成 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxmx/yljtjyygc/{type}")
    @ResponseBody
    public JsonData mxYljtjyygc(@PathVariable("type") String type,
                                @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).mxYljtjyygc(typeName));
    }

    /**
     * 设备管理-设备运行明细-日停机时间趋势 （顺义冷轧）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxmx/tjsjqsd/sl")
    @ResponseBody
    public JsonData mxTjsjqsdSl(@RequestParam("typeCode") String typeCode,
                                @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuSlName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getShebeiService(subCode).mxTjsjqsd(typeName));
    }

    /**
     * 设备管理-设备运行明细-月停机时间趋势 （顺义冷轧）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxmx/tjsjqsm/sl")
    @ResponseBody
    public JsonData mxTjsjqsmSl(@RequestParam("typeCode") String typeCode,
                                @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuSlName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getShebeiService(subCode).mxTjsjqsm(typeName));
    }

    /**
     * 设备管理-设备运行明细-月累计停机原因构成 （顺义冷轧）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/sbyxmx/yljtjyygc/sl")
    @ResponseBody
    public JsonData mxYljtjyygcSl(@RequestParam("typeCode") String typeCode,
                                  @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuSlName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getShebeiService(subCode).mxYljtjyygc(typeName));
    }

    //非计划停机

    /**
     * 设备管理-非计划停机-概览-月累计停机时间
     *
     * @param subCode
     * @return
     */
    @GetMapping("/fjhtj/yljtjsj/gl")
    @ResponseBody
    public JsonData fjhtjGlyljtjsj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getShebeiService(subCode).fjhtjGlyljtjsj());
    }

    /**
     * 设备管理-非计划停机-日停机时间趋势 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/fjhtj/tjsjqsd/{type}")
    @ResponseBody
    public JsonData fjhtjTjsjqsd(@PathVariable("type") String type,
                                 @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).fjhtjTjsjqsd(typeName));
    }

    /**
     * 设备管理-非计划停机-月停机时间趋势 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/fjhtj/tjsjqsm/{type}")
    @ResponseBody
    public JsonData fjhtjTjsjqsm(@PathVariable("type") String type,
                                 @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).fjhtjTjsjqsm(typeName));
    }

    /**
     * 设备管理-非计划停机-月累计停机原因构成 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/fjhtj/yljtjyygc/{type}")
    @ResponseBody
    public JsonData fjhtjYljtjyygc(@PathVariable("type") String type,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getShebeiService(subCode).fjhtjYljtjyygc(typeName));
    }

    /**
     * 设备管理-非计划停机-日停机时间趋势 （顺义冷轧）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/fjhtj/tjsjqsd/sl")
    @ResponseBody
    public JsonData fjhtjTjsjqsdSl(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuSlName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getShebeiService(subCode).fjhtjTjsjqsd(typeName));
    }

    /**
     * 设备管理-非计划停机-月停机时间趋势 （顺义冷轧）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/fjhtj/tjsjqsm/sl")
    @ResponseBody
    public JsonData fjhtjTjsjqsmSl(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuSlName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getShebeiService(subCode).fjhtjTjsjqsm(typeName));
    }

    /**
     * 设备管理-非计划停机-月累计停机原因构成 （顺义冷轧）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/fjhtj/yljtjyygc/sl")
    @ResponseBody
    public JsonData fjhtjYljtjyygcSl(@RequestParam("typeCode") String typeCode,
                                     @RequestParam("subCode") String subCode) {
        String typeName = getMxQuyuSlName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getShebeiService(subCode).fjhtjYljtjyygc(typeName));
    }

    // 机组运行状态

    /**
     * 设备管理-机组运行状态 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/jzyxzt/{type}")
    @ResponseBody
    public JsonData jzyxzt(@PathVariable("type") String type,
                           @RequestParam("subCode") String subCode) {

        return JsonData.success(getShebeiService(subCode).jzyxzt(type));
    }

    //维修成本

    /**
     * 设备管理-维修成本-概览-各单位修理费用
     *
     * @param subCode
     * @return
     */
    @GetMapping("/wxcb/gdwxlfy/gl")
    @ResponseBody
    public JsonData wxcbGdwxlfy(@RequestParam("subCode") String subCode) {
        return JsonData.success(getShebeiService(subCode).wxcbGdwxlfy());
    }

    /**
     * 设备管理-维修成本-修理费月度趋势 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/wxcb/wxfyydqs/{type}")
    @ResponseBody
    public JsonData wxcbWxfyydqs(@PathVariable("type") String type,
                                 @RequestParam("subCode") String subCode) {

        String typeName = getWxcbQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getShebeiService(subCode).wxcbWxfyydqs(typeName));
    }

    /**
     * 设备管理-维修成本-修理费用构成 （分厂区）
     *
     * @param subCode
     * @return
     */
    @GetMapping("/wxcb/xlfygc/{type}")
    @ResponseBody
    public JsonData wxcbXlfygc(@PathVariable("type") String type,
                               @RequestParam("subCode") String subCode) {

        String typeName = getWxcbQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }

        return JsonData.success(getShebeiService(subCode).wxcbXlfygc(typeName));
    }

    // 设备运行总览，区域获取
    private String getZlQuyuName(String typeCode) {

        switch (typeCode) {
//            case "sx":
//                return "酸洗";
//            case "qt":
//                return "球团";
//            case "sj":
//                return "烧结";
            case "lt":
                return "炼铁";
            case "lg":
                return "炼钢";
            case "rz":
                return "热轧";
            case "zx":
                return "智新";
            case "sl":
                return "顺义冷轧";
//            case "qj":
//                return "迁焦";
            default:
                return null;
        }
    }

    // 维修成本区域获取，区域获取
    private String getWxcbQuyuName(String typeCode) {

        switch (typeCode) {
            case "gf":
                return "公辅";
            case "lt":
                return "炼铁";
            case "lg":
                return "炼钢";
            case "rz":
                return "热轧";
            case "sl":
                return "顺义冷轧";
            case "zx":
                return "智新";
            default:
                return null;
        }
    }

    // 设备运行明细，区域获取
    private String getMxQuyuName(String typeCode) {

        switch (typeCode) {
            case "sx":
                return "酸洗";
            case "qt":
                return "球团";
            case "sj":
                return "烧结";
            case "lt":
                return "炼铁";
            case "lg1":
                return "一炼钢";
            case "lg2":
                return "二炼钢";
            case "rz1":
                return "一热轧";
            case "rz2":
                return "二热轧";
            case "zx1":
                return "智新一区";
            case "zx2":
                return "智新二区";
            case "zx3":
                return "智新三区";
//            case "sl":
//                return "顺义冷轧"; // 顺义冷轧因有详细分类，单独获取
//            case "qj":
//                return "迁焦";
            default:
                return null;
        }
    }

    // 设备运行明细，顺义冷轧的明细获取，因系统暂无数据，因此若有数据有逻辑有变更，可能需要对应修改
    private String getMxQuyuSlName(String typeCode) {
        switch (typeCode) {
            case "suanzha":
                return "顺义冷轧";
            case "liantui":
                return "连退";
            case "duxin":
                return "镀锌";
            default:
                return null;
        }
    }

    private ShebeiService getShebeiService(String subCode) {
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return shebeiServiceQs;
            case JT:
                throw new RuntimeException("京唐设备模块暂未开发：" + subCode);
            default:
                throw new RuntimeException("当前接口暂不支持的subCode：" + subCode);
        }
    }
}