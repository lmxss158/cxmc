package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.ZhizaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 制造模块-关键指标Controller
 * @Author: 李锐平
 * @Date: 2019/8/6 21:09
 * @Version 1.0
 */


@Controller
@RequestMapping("/zhizao/zhibiao/gjzb")
@MenuAcls(value = {"-1516269", "19989"})
public class ZhizaoGjzbController {


    @Autowired
    @Qualifier("zhizaoServiceQs")
    ZhizaoService zhizaoServiceQs;

    // ------专业指标-关键指标-----

    private String getFlTypeName(String typeCode) {

        if (StringUtils.isEmpty(typeCode)) {
            return "";
        }

        switch (typeCode) {
            case "lg1":
                return "一炼钢";
            case "lg2":
                return "二炼钢";
            case "rz1":
                return "一热轧";
            case "rz2":
                return "二热轧";
            case "suanzha":
                return "酸轧";
            case "liantui":
                return "连退";
            case "duxin":
                return "镀锌";
            case "chongjuan":
                return "重卷";
            default:
                return null;
        }
    }

    /**
     * 制造管理-专业指标-关键指标-质量直通率-质量直通率趋势
     *
     * @param type    分类编码
     * @param subCode
     * @return
     */

    @GetMapping("/zlztlqs/{type}")
    @ResponseBody
    public JsonData gjzbZlztlqs(@PathVariable("type") String type,
                                @RequestParam("subCode") String subCode,
                                @RequestParam("dateType") String dateType, String typeCode) {
        String typeName = getGjzbQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        String flTypeName = getFlTypeName(typeCode);
        if (flTypeName == null) {
            return JsonData.fail("参数错误");
        }

        String dateTypeName = "m".equalsIgnoreCase(dateType) ? "月" : "日";
        return JsonData.success(getZhizaoService(subCode).gjzbZlztlqs(typeName, flTypeName, dateTypeName));
    }

    /**
     * 制造管理-专业指标-关键指标-质量直通率月累计-智新（其他厂区的在月趋势数据已有）
     *
     * @param subCode
     * @return
     */

    @GetMapping("/zlztlylj/zx")
    @ResponseBody
    public JsonData gjzbZxZlztlylj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).gjzbZxZlztlylj());
    }


    /**
     * 制造管理-专业指标-关键指标-质量直通率-封闭前五位
     *
     * @param type    分类编码
     * @param subCode
     * @return
     */
    @GetMapping("/fbtop5/{type}")
    @ResponseBody
    public JsonData gjzbZlztlfb(@PathVariable("type") String type,
                                @RequestParam("dateType") String dateType,
                                @RequestParam("subCode") String subCode,
                                String typeCode) {

        String dateTypeName = "m".equalsIgnoreCase(dateType) ? "月" : "日";
        String typeName = getGjzbFlQuyuName(type, typeCode);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getZhizaoService(subCode).gjzbZlztlfb(typeName, dateTypeName));
    }


    /**
     * 制造管理-专业指标-关键指标-带出品率-带出品率趋势
     *
     * @param type    分类编码
     * @param subCode
     * @return
     */

    @GetMapping("/dcplqs/{type}")
    @ResponseBody
    public JsonData gjzbDcplqs(@PathVariable("type") String type,
                               @RequestParam("subCode") String subCode,
                               @RequestParam("dateType") String dateType,
                               String typeCode) {
        String typeName = getGjzbQuyuName(type);
        if (null == typeName || "lg".equals(type)) {
            return JsonData.fail("无效路径");
        }

        String flTypeName = getFlTypeName(typeCode);
        if (flTypeName == null) {
            return JsonData.fail("参数错误");
        }

        String dateTypeName = "m".equalsIgnoreCase(dateType) ? "月" : "日";
        return JsonData.success(getZhizaoService(subCode).gjzbDcplqs(typeName, flTypeName, dateTypeName));
    }

    /**
     * 制造管理-专业指标-关键指标-带出品率月累计-智新（其他厂区的在月趋势数据已有）
     *
     * @param subCode
     * @return
     */

    @GetMapping("/dcplylj/zx")
    @ResponseBody
    public JsonData gjzbZxDcplylj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).gjzbZxDcplylj());
    }


    /**
     * 制造管理-专业指标-关键指标--带出品率-带出品构成
     *
     * @param type    分类编码
     * @param subCode
     * @return
     */

    @GetMapping("/dcpgc/{type}")
    @ResponseBody
    public JsonData gjzbDcpgc(@PathVariable("type") String type,
                              @RequestParam("dateType") String dateType,
                              @RequestParam("subCode") String subCode,
                              String typeCode) {

        String dateTypeName = "m".equalsIgnoreCase(dateType) ? "月" : "日";
        String typeName = getGjzbFlQuyuName(type, typeCode);
        if (null == typeName || "lg".equals(type)) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getZhizaoService(subCode).gjzbDcpgc(typeName, dateTypeName));
    }


    /**
     * 制造管理-专业指标-关键指标-废次降率-废次降率趋势
     *
     * @param type    分类编码
     * @param subCode
     * @return
     */

    @GetMapping("/fcjlqs/{type}")
    @ResponseBody
    public JsonData gjzbFcjlqs(@PathVariable("type") String type,
                               @RequestParam("subCode") String subCode,
                               @RequestParam("dateType") String dateType,
                               String typeCode) {
        String typeName = getGjzbQuyuName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        String flTypeName = getFlTypeName(typeCode);
        if (flTypeName == null) {
            return JsonData.fail("参数错误");
        }

        String dateTypeName = "m".equalsIgnoreCase(dateType) ? "月" : "日";
        return JsonData.success(getZhizaoService(subCode).gjzbFcjlqs(typeName, flTypeName, dateTypeName));
    }

    /**
     * 制造管理-专业指标-关键指标-废次降率-废次降率月累计-智新（其他厂区的在月趋势数据已有）
     *
     * @param subCode
     * @return
     */

    @GetMapping("/fcjlylj/zx")
    @ResponseBody
    public JsonData gjzbZxFcjlylj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).gjzbZxFcjlylj());
    }


    /**
     * 制造管理-专业指标-关键指标-废次降率-废次降构成
     *
     * @param type    分类编码
     * @param subCode
     * @return
     */

    @GetMapping("/fcjgc/{type}")
    @ResponseBody
    public JsonData gjzbFcjgc(@PathVariable("type") String type,
                              @RequestParam("dateType") String dateType,
                              @RequestParam("subCode") String subCode,
                              String typeCode) {

        String dateTypeName = "m".equalsIgnoreCase(dateType) ? "月" : "日";
        String typeName = getGjzbFlQuyuName(type, typeCode);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getZhizaoService(subCode).gjzbFcjgc(typeName, dateTypeName));
    }


    /**
     * 制造管理-专业指标-关键指标-兑现率-整体兑现率月度趋势
     *
     * @param type    分类编码
     * @param subCode
     * @return
     */

    @GetMapping("/dxlydqs/zt/{type}")
    @ResponseBody
    public JsonData gjzbDxlydqsZt(@PathVariable("type") String type,
                                  @RequestParam("subCode") String subCode) {
        String typeName = getGjzbQuyuName(type);
        if (null == typeName || "lg".equals(type)) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getZhizaoService(subCode).gjzbDxlydqs(typeName, "整体"));
    }

    /**
     * 制造管理-专业指标-关键指标-兑现率-整单兑现率月度趋势
     *
     * @param type    分类编码
     * @param subCode
     * @return
     */

    @GetMapping("/dxlydqs/zd/{type}")
    @ResponseBody
    public JsonData gjzbDxlydqsZd(@PathVariable("type") String type,
                                  @RequestParam("subCode") String subCode) {
        String typeName = getGjzbDxlQuyuName(type);
        if (null == typeName || "lg".equals(type)) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getZhizaoService(subCode).gjzbDxlydqs(typeName, "整单"));

    }

    private String getGjzbDxlQuyuName(String type) {
        switch (type) {
            case "sx":
                return "热扎";
            case "lg":
                return "炼钢";
            case "rz":
                return "热轧";
            case "zx":
                return "智新";
            case "sl":
                return "顺义冷轧";
            case "zh":
                return "股份";
            default:
                return null;
        }
    }

    private String getGjzbQuyuName(String type) {
        switch (type) {
            case "sx":
                return "酸洗";
            case "lg":
                return "炼钢";
            case "rz":
                return "热轧";
            case "zx":
                return "智新";
            case "sl":
                return "顺义冷轧";
            case "zh":
                return "股份";
            default:
                return null;
        }
    }


    private String getGjzbFlQuyuName(String type, String typeCode) {
        switch (type) {
            case "sx":
                return "酸洗";
            case "lg":
                if (typeCode == null) {
                    return null;
                }
                switch (typeCode) {
                    case "lg1":
                        return "炼钢";
                    case "lg2":
                        return "二炼钢";
                    default:
                        return null;
                }
            case "rz":
                if (typeCode == null) {
                    return null;
                }
                switch (typeCode) {
                    case "rz1":
                        return "热轧";
                    case "rz2":
                        return "二热轧";
                    default:
                        return null;
                }
            case "zx":
                return "智新";
            case "sl":
                if (typeCode == null) {
                    return null;
                }
                switch (typeCode) {
                    case "suanzha":
                        return "酸轧";
                    case "liantui":
                        return "连退";
                    case "duxin":
                        return "镀锌";
                    case "chongjuan":
                        return "重卷";
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    // ------专业指标-关键指标end-----

    private ZhizaoService getZhizaoService(String subCode) {
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return zhizaoServiceQs;
            case JT:
                throw new RuntimeException("京唐制造模块-关键指标暂未开发：" + subCode);
            default:
                throw new RuntimeException("当前接口暂不支持的subCode：" + subCode);
        }
    }
}


