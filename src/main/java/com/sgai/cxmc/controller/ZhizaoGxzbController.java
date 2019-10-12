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
 * @Description: 制造模块-工序指标Controller
 * @Author: 李锐平
 * @Date: 2019/8/6 21:09
 * @Version 1.0
 */


@Controller
@RequestMapping("/zhizao/zhibiao/gxzb")
@MenuAcls(value = {"-1516268","20012"})
public class ZhizaoGxzbController {

    @Autowired
    @Qualifier("zhizaoServiceQs")
    ZhizaoService zhizaoServiceQs;

    // ------专业指标-工序指标-----

    /**
     * 制造管理-专业指标-工序指标-炼铁
     * @param zbxCode 指标项编码
     * @param typeCode 分类编码
     * @param subCode
     * @return
     */

    @GetMapping("/liantie")
    @ResponseBody
    public JsonData gxzbLiantie(@RequestParam("typeCode") String typeCode,
                             @RequestParam("zbxCode") String zbxCode,
                             @RequestParam("subCode") String subCode) {
        String typeName;
        switch (typeCode) {
            case "zonghe":
                typeName = "炼铁";
                break;
            case "gl1":
                typeName = "一高炉";
                break;
            case "gl2":
                typeName = "二高炉";
                break;
            case "gl3":
                typeName = "三高炉";
                break;
            default:
                return JsonData.fail("无效参数");
        }

        String zbxName;
        switch (zbxCode) {
            case "jiaobi":
                zbxName = "焦比";
                break;
            case "meibi":
                zbxName = "煤比";
                break;
            case "gllyxs":
                if ("zonghe".equals(typeCode)) {
                    zbxName = "高炉利用系数";
                } else {
                    zbxName = "利用系数";
                }
                break;
            default:
                return JsonData.fail("无效参数");
        }

        String queryName = typeName+zbxName;
        return JsonData.success(getZhizaoService(subCode).gxzbLiantie(queryName));

    }

    @GetMapping("/liantie/glfh")
    @ResponseBody
    public JsonData gxzbLiantieGlfh(@RequestParam("typeCode") String typeCode,
                                    @RequestParam("subCode") String subCode) {
        String typeName;
        switch (typeCode) {
            case "zonghe":
                typeName = "综合";
                break;
            case "gl1":
                typeName = "一高炉";
                break;
            case "gl2":
                typeName = "二高炉";
                break;
            case "gl3":
                typeName = "三高炉";
                break;
            default:
                return JsonData.fail("无效参数");
        }
        return JsonData.success(getZhizaoService(subCode).gxzbLiantieGlfh(typeName));

    }

    /**
     * 制造管理-专业指标-工序指标-炼钢
     * @param zbxCode 指标项编码
     * @param typeCode 分类编码
     * @param subCode
     * @return
     */
    @GetMapping("/liangang")
    @ResponseBody
    public JsonData gxzbLiangang(@RequestParam("typeCode") String typeCode,
                             @RequestParam("zbxCode") String zbxCode,
                             @RequestParam("subCode") String subCode) {
        String typeName;
        String zbxName;

        switch (zbxCode) {
            case "tiehao":
                if ("zonghe".equals(typeCode)) {
                    zbxName = "当日铁耗";
                } else {
                    zbxName = "铁耗";
                }
                break;
            case "zlpjcgwd":
                zbxName = "转炉平均出钢温度";
                break;
            case "rhclzq":
                zbxName = "处理周期";
                break;
            case "hlsl":
                zbxName = "_恒拉速率";
                break;
            default:
                return JsonData.fail("无效参数");
        }

        //对处理周期的名称单独处理
        if ("rhclzq".equals(zbxCode)){
            switch (typeCode) {
                case "zonghe":
                    typeName = "RH";
                    break;
                case "lg1":
                    typeName = "RH1";
                    break;
                case "lg2":
                    typeName = "RH2";
                    break;
                default:
                    return JsonData.fail("无效参数");
            }
        } else {
            switch (typeCode) {
                case "zonghe":
                    typeName = "炼钢";
                    break;
                case "lg1":
                    typeName = "一炼钢";
                    break;
                case "lg2":
                    typeName = "二炼钢";
                    break;
                default:
                    return JsonData.fail("无效参数");
            }
        }

        String queryName = typeName+zbxName;
        return JsonData.success(getZhizaoService(subCode).gxzbLiangang(queryName));
    }

    /**
     * 制造管理-专业指标-工序指标-热轧
     * @param zbxCode 指标项编码
     * @param typeCode 分类编码
     * @param subCode
     * @return
     */
    @GetMapping("/rezha")
    @ResponseBody
    public JsonData gxzbRezha(@RequestParam("typeCode") String typeCode,
                             @RequestParam("zbxCode") String zbxCode,
                             @RequestParam("subCode") String subCode) {
        String typeName;
        String zbxName;

        switch (zbxCode) {
            case "ccl":
                zbxName = "成材率日指标";
                break;
            case "rzrsl":
                zbxName = "热装热送率";
                break;
            case "xnyjhgl":
                zbxName = "性能一检合格率日指标";
                break;
            case "bpyjhgl":
                zbxName = "_表判一检合格率日指标";
                break;
            default:
                return JsonData.fail("无效参数");
        }

        switch (typeCode) {
            case "zonghe":
                if ("ccl".equals(zbxCode)){
                    //对成材率的名称单独处理
                    typeName = "热轧综合";
                } else {
                    typeName = "热轧";
                }
                break;
            case "rz1":
                typeName = "一热轧";
                break;
            case "rz2":
                typeName = "二热轧";
                break;
            default:
                return JsonData.fail("无效参数");
        }

        String queryName = typeName+zbxName;
        return JsonData.success(getZhizaoService(subCode).gxzbRezha(queryName));
    }

    /**
     * 制造管理-专业指标-工序指标-冷轧
     * @param zbxCode 指标项编码
     * @param typeCode 分类编码
     * @param subCode
     * @return
     */
    @GetMapping("/lengzha")
    @ResponseBody
    public JsonData gxzbLengzha(@RequestParam("typeCode") String typeCode,
                             @RequestParam("zbxCode") String zbxCode,
                             @RequestParam("subCode") String subCode) {
        String typeName;
        String zbxName;

        switch (zbxCode) {
            case "ccl":
                zbxName = "成材率";
                break;
            case "ckhdmzl":
                zbxName = "_出口厚度命中率"; //仅酸轧有
                if (!"suanzha".equals(typeCode)) {
                    return JsonData.fail("参数错误");
                }
                break;
            case "xnyjhgl":
                zbxName = "性能一检合格率"; // 酸轧没有，其他有
                if ("suanzha".equals(typeCode)) {
                    return JsonData.fail("参数错误");
                }
                break;
            case "bpyjhgl":
                zbxName = "_表判一检合格率";
                break;
            default:
                return JsonData.fail("无效参数");
        }

        switch (typeCode) {
            case "zonghe":
                //对不同的指标对应的类型名称单独处理
                if ("ccl".equals(zbxCode)){
                    typeName = "冷轧综合";
                } else if ("bpyjhgl".equals(zbxCode)){
                    typeName = "顺冷";
                } else {
                    typeName = "冷轧";
                }
                break;
            case "suanzha":
                typeName = "酸轧";
                break;
            case "liantui":
                typeName = "连退";
                break;
            case "duxin":
                typeName = "镀锌";
                break;
            case "chongjuan":
                typeName = "重卷";
                break;
            default:
                return JsonData.fail("无效参数");
        }

        String queryName = typeName+zbxName;
        return JsonData.success(getZhizaoService(subCode).gxzbLengzha(queryName));
    }

    /**
     * 制造管理-专业指标-工序指标-酸洗
     * @param zbxCode 指标项编码
     * @param subCode
     * @return
     */
    @GetMapping("/suanxi")
    @ResponseBody
    public JsonData gxzbSuanxi(@RequestParam("zbxCode") String zbxCode,
                              @RequestParam("subCode") String subCode) {
        String zbxName;

        switch (zbxCode) {
            case "ccl":
                zbxName = "成材率";
                break;
            case "xnyjhgl":
                zbxName = "性能一检合格率";
                break;
            case "bpyjhgl":
                zbxName = "_表判一检合格率";
                break;
            default:
                return JsonData.fail("无效参数");
        }

        String queryName = "酸洗"+zbxName;
        return JsonData.success(getZhizaoService(subCode).gxzbSuanxi(queryName));

    }

// ------专业指标-工序指标end-----

    private ZhizaoService getZhizaoService(String subCode){
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return zhizaoServiceQs;
            case JT:
                throw new  RuntimeException("京唐制造模块-工序指标暂未开发："+subCode);
            default:
                throw new  RuntimeException("当前接口暂不支持的subCode："+subCode);
        }
    }
}