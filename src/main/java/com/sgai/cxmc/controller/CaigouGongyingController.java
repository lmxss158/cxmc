package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.CaigouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 采购管理-采购供应Controller
 * @Author: 李锐平
 * @Date: 2019/8/23 10:25
 * @Version 1.0
 */

@Controller
@RequestMapping("/caigou/gongying")
@MenuAcls(value = {"-1516263","20004"})
public class CaigouGongyingController {

    @Autowired
    @Qualifier("caigouServiceQs")
    CaigouService caigouServiceQs;

    /**
     * 采购管理-采购供应-铁矿石-进口块矿进口粉矿-计划量与到达量（铁矿石）
     * @param subCode
     * @return
     */
    @GetMapping("/tks/jkkkfk/jhlydl")
    @ResponseBody
    public JsonData gyJkkkfkJhlydl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJkkkfkJhlydl());
    }

    /**
     * 采购管理-采购供应-铁矿石-国内矿-计划量与到达量
     * @param subCode
     * @return
     */
    @GetMapping("/tks/gnk/jhlydl")  //
    @ResponseBody
    public JsonData gnkJhlydl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gnkJhlydl());
    }

    /**
     * 采购管理-采购供应-铁矿石-计划量与到达量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/tks/jhlydlqst")
    @ResponseBody
    public JsonData gyTksJhlydlqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyTksJhlydlqst());
    }

    /**
     * 采购管理-采购供应-铁矿石-进口矿报支数量
     * @param subCode
     * @return
     */
    @GetMapping("/tks/jkk/bzsl")
    @ResponseBody
    public JsonData gyJkkBzsl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJkkBzsl());
    }

    /**
     * 采购管理-采购供应-铁矿石-国内矿报支数量
     * @param subCode
     * @return
     */
    @GetMapping("/tks/gnk/bzsl")
    @ResponseBody
    public JsonData gyGnkBzsl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyGnkBzsl());
    }

    /**
     * 采购管理-采购供应-铁矿石-进口矿报支金额
     * @param subCode
     * @return
     */
    @GetMapping("/tks/jkk/bzje")
    @ResponseBody
    public JsonData gyJkkBzje(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJkkBzje());
    }

    /**
     * 采购管理-采购供应-铁矿石-国内矿报支金额
     * @param subCode
     * @return
     */
    @GetMapping("/tks/gnk/bzje")
    @ResponseBody
    public JsonData gyGnkBzje(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyGnkBzje());
    }

    /**
     * 采购管理-采购供应-铁矿石-报支金额趋势图
     * @return
     */
    @GetMapping("/tks/bzjeqst")
    @ResponseBody
    public JsonData gyTksBzjeqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyTksBzjeqst());
    }

    /**
     * 采购管理-采购供应-铁矿石-进口块矿粉矿港存库存量库存
     * @param subCode
     * @return
     */
    @GetMapping("/tks/jkfkkk/kcl")
    @ResponseBody
    public JsonData gyJkfkkkKcl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJkfkkkKcl());
    }

    /**
     * 采购管理-采购供应-铁矿石-国内矿矿港存库存量库存（铁矿石）
     * @param subCode
     * @return
     */
    @GetMapping("/tks/gnk/kcl")
    @ResponseBody
    public JsonData gyGnkKcl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyGnkKcl());
    }

    /**
     * 采购管理-采购供应-铁矿石-库存量与资金占用趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/tks/kclzyqst")
    @ResponseBody
    public Object gyTksKclzyqst(@RequestParam("subCode") String subCode,
                                @RequestParam("typeCode") String typeCode) {

        String typeName = getGyJckczyqstTypeName(typeCode);

        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouService(subCode).gyTksKclzyqst(typeName));

    }


    /**
     * 采购管理-采购供应-焦碳-计划量与到达量
     * @param subCode
     * @return
     */
    @GetMapping("/jc/jhlydl")
    @ResponseBody
    public JsonData gyJcJhlydl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJcJhlydl());
    }

    /**
     * 采购管理-采购供应-焦碳-计划量与到达量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/jc/jhlydlqst")
    @ResponseBody
    public JsonData gyJcJhlydlqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJcJhlydlqst());
    }

    /**
     * 采购管理-采购供应-迁焦焦碳-计报支数量与金额
     * @return
     */
    @GetMapping("/jc/qjbzslyje")
    @ResponseBody
    public JsonData gyJcBzslyje(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJcBzslyje());
    }

    /**
     * 采购管理-采购供应-外购焦碳-报支数量与金额
     * @return
     */
    @GetMapping("/jc/wgbzslyje")
    @ResponseBody
    public JsonData gyJcwgBzslyje(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJcwgBzslyje());
    }

    /**
     * 采购管理-采购供应-焦碳-报支金额趋势图
     * @return
     */
    @GetMapping("/jc/bzjeqst")
    @ResponseBody
    public JsonData gyJcBzjeqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJcBzjeqst());
    }

    /**
     * 采购管理-采购供应-迁焦焦碳-库存量与资金占用
     * @return
     */
    @GetMapping("/jc/qjkclyzy")
    @ResponseBody
    public JsonData gyJcQjkclyzy(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJcQjkclyzy());
    }

    /**
     * 采购管理-采购供应-外购焦碳-库存量与资金占用
     * @return
     */
    @GetMapping("/jc/wgkclyzy")
    @ResponseBody
    public JsonData gyJcWgkclyzy(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyJcWgkclyzy());
    }

    /**
     * 采购管理-采购供应-焦碳-库存量与资金占用趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/jc/kclzyqst")
    @ResponseBody
    public Object gyJckczyqst(@RequestParam("subCode") String subCode,
                                   @RequestParam("typeCode") String typeCode) {
        String typeName = getGyJckczyqstTypeName(typeCode);
        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouService(subCode).gyJckczyqst(typeName));

    }

    /**
     * 采购管理-采购供应-合金-计划量与到达量
     * @param subCode
     * @return
     */
    @GetMapping("/hj/jhlydl")
    @ResponseBody
    public JsonData gyHjJhlydl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyHjJhlydl());
    }

    /**
     * 采购管理-采购供应-合金-计划量与到达量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/hj/jhlydlqst")
    @ResponseBody
    public JsonData gyHjJhlydlqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyHjJhlydlqst());
    }

    /**
     * 采购管理-采购供应-合金-报支数量
     * @return
     */
    @GetMapping("/hj/bzsl")
    @ResponseBody
    public JsonData gyHjbzsl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyHjbzsl());
    }

    /**
     * 采购管理-采购供应-合金-报支金额趋势图
     * @return
     */
    @GetMapping("/hj/bzjeqst")
    @ResponseBody
    public JsonData gyHjBzjeqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyHjBzjeqst());
    }

    /**
     * 采购管理-采购供应-合金-库存量与资金占用
     * @return
     */
    @GetMapping("/hj/kclyzy")
    @ResponseBody
    public JsonData gyHjkclyzy(@RequestParam("subCode") String subCode) {

        return JsonData.success(getCaigouService(subCode).gyHjkclyzy());

    }

    /**
     * 采购管理-采购供应-合金-库存量与资金占用趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/hj/kclzyqst")
    @ResponseBody
    public Object gyHjKclzyqst(@RequestParam("subCode") String subCode,
                              @RequestParam("typeCode") String typeCode) {

        String typeName = getGyJckczyqstTypeName(typeCode);

        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouService(subCode).gyHjKclzyqst(typeName));

    }

    /**
     * 采购管理-采购供应-废钢-计划量与到达量
     * @param subCode
     * @return
     */
    @GetMapping("/fg/jhlydl")
    @ResponseBody
    public JsonData gyFgJhlydl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyFgJhlydl());
    }

    /**
     * 采购管理-采购供应-废钢-计划量与到达量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/fg/jhlydlqst")
    @ResponseBody
    public JsonData gyFgJhlydlqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyFgJhlydlqst());
    }

    /**
     * 采购管理-采购供应-废钢-报支数量
     * @return
     */
    @GetMapping("/fg/bzsl")
    @ResponseBody
    public JsonData gyFgbzsl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyFgbzsl());
    }

    /**
     * 采购管理-采购供应-废钢-报支金额趋势图
     * @return
     */
    @GetMapping("/fg/bzjeqst")
    @ResponseBody
    public JsonData gyFgBzjeqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyFgBzjeqst());
    }

    /**
     * 采购管理-采购供应-废钢-库存量与资金占用
     * @return
     */
    @GetMapping("/fg/kclyzy")
    @ResponseBody
    public JsonData gyFgkclyzy(@RequestParam("subCode") String subCode) {

        return JsonData.success(getCaigouService(subCode).gyFgkclyzy());

    }

    /**
     * 采购管理-采购供应-废钢-库存量与资金占用趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/fg/kclzyqst")
    @ResponseBody
    public Object gyFgKclzyqst(@RequestParam("subCode") String subCode,
                               @RequestParam("typeCode") String typeCode) {

        String typeName = getGyJckczyqstTypeName(typeCode);

        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouService(subCode).gyFgKclzyqst(typeName));

    }
    /**
     * 采购管理-采购供应-喷吹煤-计划量与到达量
     * @param subCode
     * @return
     */
    @GetMapping("/pcm/jhlydl")
    @ResponseBody
    public JsonData gyPcmJhlydl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyPcmJhlydl());
    }

    /**
     * 采购管理-采购供应-喷吹煤计划量与到达量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/pcm/jhlydlqst")
    @ResponseBody
    public JsonData gyPcmJhlydlqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyPcmJhlydlqst());
    }

    /**
     * 采购管理-采购供应-喷吹煤-报支数量
     * @return
     */
    @GetMapping("/pcm/bzsl")
    @ResponseBody
    public JsonData getPcmbzsl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyPcmbzsl());
    }

    /**
     * 采购管理-采购供应-喷吹煤-报支金额趋势图
     * @return
     */
    @GetMapping("/pcm/bzjeqst")
    @ResponseBody
    public JsonData gypcmBzjeqst(@RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyPcmBzjeqst());
    }

    /**
     * 采购管理-采购供应-喷吹煤-库存量与资金占用
     * @return
     */
    @GetMapping("/pcm/kclyzy")
    @ResponseBody
    public JsonData gypcmkclyzy(@RequestParam("subCode") String subCode) {

        return JsonData.success(getCaigouService(subCode).gyPcmkclyzy());

    }

    /**
     * 采购管理-采购供应-喷吹煤-库存量与资金占用趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/pcm/kclzyqst")
    @ResponseBody
    public Object gyPcmKclzyqst(@RequestParam("subCode") String subCode,
                               @RequestParam("typeCode") String typeCode) {

        String typeName = getGyJckczyqstTypeName(typeCode);

        if (null == typeCode) {
            return JsonData.fail("参数无效");
        }

        return JsonData.success(getCaigouService(subCode).gyPcmKclzyqst(typeName));

    }

    /**
     * 采购管理-采购供应-资金占用（铁矿石、喷吹煤、焦炭、废钢、合金）
     * @param subCode
     * @return
     */
    @GetMapping("/zjzy/{type}")
    @ResponseBody
    public JsonData gyZjzy(@PathVariable("type") String type,
                             @RequestParam("subCode") String subCode) {
        return JsonData.success(getCaigouService(subCode).gyZjzy(type));
    }

    /**
     * 采购管理-采购供应-采购计划与到达量月数据（资材）
     * @param subCode
     * @return
     */
    @GetMapping("/cgjhydlm/zc")
    @ResponseBody
    public JsonData gyCgjhydlm(@RequestParam("subCode") String subCode) {
        String type = "zc";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        return JsonData.success(getCaigouService(subCode).gyCgjhydlm(typeName));
    }
    /**
     * 采购管理-采购供应-采购计划与到达量采购计划金额、合同签约金额、仓储到货金额、仓储出货金额月数据（备件）
     * @param subCode
     * @return
     */
    @GetMapping("/cgjhydlm/bj")
    @ResponseBody
    public JsonData gyBjCgjhydlm(@RequestParam("subCode") String subCode) {
        String type = "bj";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        return JsonData.success(getCaigouService(subCode).gyBjCgjhydlm(typeName));
    }

    /**
     * 采购管理-采购供应-采购计划与到达量年数据（资材）
     * @param subCode
     * @return
     */
    @GetMapping("/cgjhydly/zc")
    @ResponseBody
    public JsonData gyCgjhydly(@RequestParam("subCode") String subCode) {
        String type = "zc";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getCaigouService(subCode).gyCgjhydly(typeName));
    }

    /**
     * 采购管理-采购供应-采购计划与到达量在途金额数据（备件）
     * @param subCode
     * @return
     */
    @GetMapping("/cgjhydly/bj/ztje")
    @ResponseBody
    public JsonData gyBjZtjeCgjhydly(@RequestParam("subCode") String subCode) {
        String type = "bj";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getCaigouService(subCode).gyBjZtjeCgjhydly(typeName));
    }

    /**
     * 采购管理-采购供应-采购计划与到达量年数据（备件）
     * @param subCode
     * @return
     */
    @GetMapping("/cgjhydly/bj")
    @ResponseBody
    public JsonData gyBjCgjhydly(@RequestParam("subCode") String subCode) {
        String type = "bj";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getCaigouService(subCode).gyBjCgjhydly(typeName));
    }

    /**
     * 采购管理-采购供应-计划金额/采购订单金额累计（资材）
     * @param subCode
     * @return
     */
    @GetMapping("/jhcgddjelj/zc")
    @ResponseBody
    public JsonData gyJhcgddjelj(@RequestParam("subCode") String subCode) {
        String type = "zc";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getCaigouService(subCode).gyJhcgddjelj(typeName));
    }

    /**
     * 采购管理-采购供应-计划金额/采购订单金额累计（备件）
     * @param subCode
     * @return
     */
    @GetMapping("/jhcgddjelj/bj")
    @ResponseBody
    public JsonData gyBjJhcgddjelj(@RequestParam("subCode") String subCode) {
        String type = "bj";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }
        return JsonData.success(getCaigouService(subCode).gyBjJhcgddjelj(typeName));
    }

    /**
     * 采购管理-采购供应-当期吨钢消耗费用（资材、备件）
     * @param subCode
     * @return
     */
    @GetMapping("/dqdgxhfy/{type}")
    @ResponseBody
    public JsonData gyDqdgxhfy(@PathVariable("type") String type,
                             @RequestParam("subCode") String subCode) {
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        return JsonData.success(getCaigouService(subCode).gyDqdgxhfy(typeName));
    }

    /**
     * 采购管理-采购供应-上一期吨钢备件消耗费用统计（资材、备件）
     * @param subCode
     * @return
     */
    @GetMapping("/sqdgbjxhfy/{type}")
    @ResponseBody
    public JsonData gySqdgbjxhfy(@PathVariable("type") String type,
                             @RequestParam("subCode") String subCode) {
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        return JsonData.success(getCaigouService(subCode).gySqdgbjxhfy(typeName));
    }

    /**
     * 采购管理-采购供应-当期库存资金占用（资材、备件）
     * @param subCode
     * @return
     */
    @GetMapping("/dqkczjzy/{type}")
    @ResponseBody
    public JsonData gyDqkczjzy(@PathVariable("type") String type,
                             @RequestParam("subCode") String subCode) {
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        return JsonData.success(getCaigouService(subCode).gyDqkczjzy(typeName));
    }

    /**
     * 采购管理-采购供应-月度库存资金占用统计（资材、备件）
     * @param subCode
     * @return
     */
    @GetMapping("/ydkczjzy/{type}")
    @ResponseBody
    public JsonData gyYdkczjzy(@PathVariable("type") String type,
                             @RequestParam("subCode") String subCode) {
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        return JsonData.success(getCaigouService(subCode).gyYdkczjzy(typeName));
    }

    /**
     * 采购管理-采购供应-各作业部资金占用（备件）
     * @param subCode
     * @return
     */
    @GetMapping("/gzybzjzy/zc")
    @ResponseBody
    public JsonData gyGzybzjzy(@RequestParam("subCode") String subCode) {
        String type = "zc";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        return JsonData.success(getCaigouService(subCode).gyGzybzjzy(typeName));
    }

    /**
     * 采购管理-采购供应-各作业部资金占用（备件）
     * @param subCode
     * @return
     */
    @GetMapping("/gzybzjzy/bj")
    @ResponseBody
    public JsonData gyBjGzybzjzy(@RequestParam("subCode") String subCode) {
        String type = "bj";
        String typeName = getZcbjTypeName(type);
        if (null == typeName) {
            return JsonData.fail("无效路径");
        }

        return JsonData.success(getCaigouService(subCode).gyBjGzybzjzy(typeName));
    }

    private String getGyJckczyqstTypeName(String typeCode) {

        switch (typeCode) {
            case "kcl":
                return "库存量";
            case "zjzy":
                return "资金占用";
            default:
                return null;
        }
    }

    private String getZcbjTypeName(String type){

        switch (type) {
            case "zc":
                return "材料";
            case "bj":
                return "备件";
            default:
                return null;

        }

    }


    private CaigouService getCaigouService(String subCode){
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return caigouServiceQs;
            case JT:
                throw new  RuntimeException("京唐采购模块-采购供应暂未开发："+subCode);
            default:
                throw new  RuntimeException("当前接口暂不支持的subCode："+subCode);
        }
    }
}
