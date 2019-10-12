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
 * @Description: 制造模块-产量管理Controller
 * @Author: 李锐平
 * @Date: 2019/8/6 21:09
 * @Version 1.0
 */


@Controller
@RequestMapping("/zhizao/chanliang")
@MenuAcls(value = {"-1516260","19999"})
public class ZhizaoChanliangController {


    @Autowired
    @Qualifier("zhizaoServiceQs")
    ZhizaoService zhizaoServiceQs;


    /**
     * 制造管理-产量管理-产量总览
     * @param subCode
     * @return
     */
    @GetMapping("/clzl")
    @ResponseBody
    public JsonData clClzl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clClzl());
    }

    /**
     * 制造管理-产量管理-月度趋势图
     * @param subCode
     * @return
     */

    @GetMapping("/ydqst")
    @ResponseBody
    public JsonData clYdqst(@RequestParam("subCode") String subCode,
                            @RequestParam("zbName") String zbName,
                            Integer monthNums) {

        switch (zbName) {
            //炼铁、炼钢、热轧、酸洗、顺冷、硅钢
            case "炼铁":break;
            case "炼钢":break;
            case "热轧":break;
            case "酸洗":break;
            case "顺冷":break;
            case "硅钢":break;
            default:
                return JsonData.fail("无效参数");
        }

        return JsonData.success(getZhizaoService(subCode).clYdqst(zbName,monthNums));
    }

//---公辅---

    /**
     * 制造管理-产量管理-产量明细-公辅发日电量概览；包含日发电、日计划发电；可完成发电量展示、比计划发电数量
     * @param subCode
     * @return
     */
    @GetMapping("/gfrfd")
    @ResponseBody
    public JsonData clGfrfd(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clGfrfd());
    }


    /**
     * 制造管理-产量管理-产量明细-公辅发月电量概览；包含月累计发电、月累计计划发电； 可完成发电量展示、比计划发电数量、进度信息图表
     * @param subCode
     * @return
     */
    @GetMapping("/gfyfd")
    @ResponseBody
    public JsonData clGfyfd(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clGfyfd());
    }

    /**
     * 制造管理-产量管理-产量明细-公辅发电量月度趋势
     * @param subCode
     * @return
     */
    @GetMapping("/gffdlydqs")
    @ResponseBody
    public JsonData clGffdlydqs(@RequestParam("subCode") String subCode, Integer monthNums) {
        return JsonData.success(getZhizaoService(subCode).clGffdlydqs(monthNums));
    }

//---酸洗---

    /**
     * 制造管理-产量管理-产量明细-酸洗日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/sxrcl")
    @ResponseBody
    public JsonData clSxrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clSxrcl());
    }

    /**
     * 制造管理-产量管理-产量明细-酸洗月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/sxycl")
    @ResponseBody
    public JsonData clSxycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clSxycl());
    }

    /**
     * 制造管理-产量管理-产量明细-酸洗月商品材产量结构
     * @param subCode
     * @return
     */
    @GetMapping("/sxyspccljg")
    @ResponseBody
    public JsonData clSxyspccljg(@RequestParam("subCode") String subCode, Integer monthNums) {
        return JsonData.success(getZhizaoService(subCode).clSxyspccljg(monthNums));
    }

//---球团---

    /**
     * 制造管理-产量管理-产量明细-球团日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/qtrcl")
    @ResponseBody
    public JsonData clQtrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clQtrcl());
    }

    /**
     * 制造管理-产量管理-产量明细-球团月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/qtycl")
    @ResponseBody
    public JsonData clQtycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clQtycl());
    }

//---烧结---

    /**
     * 制造管理-产量管理-产量明细-烧结日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/sjrcl")
    @ResponseBody
    public JsonData clSjrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clSjrcl());
    }

    /**
     * 制造管理-产量管理-产量明细-烧结月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/sjycl")
    @ResponseBody
    public JsonData clSjycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clSjycl());
    }

//---炼铁---

    /**
     * 制造管理-产量管理-产量明细-炼铁日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/ltrcl")
    @ResponseBody
    public JsonData clLtrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clLtrcl());
    }

    /**
     * 制造管理-产量管理-产量明细-炼铁月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/ltycl")
    @ResponseBody
    public JsonData clLtycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clLtycl());
    }

    /**
     * 制造管理-产量管理-产量明细-炼铁各高炉日产量
     * @param subCode
     * @return
     */
    @GetMapping("/ltglcl")
    @ResponseBody
    public JsonData clLtglcl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clLtglcl());
    }

//---炼钢---

    /**
     * 制造管理-产量管理-产量明细-炼钢日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/lgrcl")
    @ResponseBody
    public JsonData clLgrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clLgrcl());
    }

    /**
     * 制造管理-产量管理-产量明细-炼钢月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/lgycl")
    @ResponseBody
    public JsonData clLgycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clLgycl());
    }

    /**
     * 制造管理-产量管理-产量明细-炼钢各产线产量
     * @param subCode
     * @return
     */
    @GetMapping("/lgcxcl")
    @ResponseBody
    public JsonData clLgcxcl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clLgcxcl());
    }

    /**
     * 制造管理-产量管理-产量明细-炼钢日冶炼炉数
     * @param subCode
     * @return
     */
    @GetMapping("/lgrlgls")
    @ResponseBody
    public JsonData clLgrlgls(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clLgrlgls());
    }

//---热轧---

    /**
     * 制造管理-产量管理-产量明细-热轧日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/rzrcl")
    @ResponseBody
    public JsonData clRzrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clRzrcl());
    }

    /**
     * 制造管理-产量管理-产量明细-热轧月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/rzycl")
    @ResponseBody
    public JsonData clRzycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clRzycl());
    }

    /**
     * 制造管理-产量管理-产量明细-热轧各产线产量
     * @param subCode
     * @return
     */
    @GetMapping("/rzcxcl")
    @ResponseBody
    public JsonData clRzcxcl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clRzcxcl());
    }

    /**
     * 制造管理-产量管理-产量明细-热轧1月商品材产量结构
     * @param subCode
     * @return
     */
    @GetMapping("/rzyspccljg")
    @ResponseBody
    public JsonData clRzyspccljg(@RequestParam("subCode") String subCode,@RequestParam("cxName") String cxName) {
        return JsonData.success(getZhizaoService(subCode).clRzyspccljg(cxName));
    }

//---智新公司---

    /**
     * 制造管理-产量管理-产量明细-智新日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/zxrcl")
    @ResponseBody
    public JsonData clZxrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clZxrcl());
    }


    /**
     * 制造管理-产量管理-产量明细-智新月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/zxycl")
    @ResponseBody
    public JsonData clZxycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clZxycl());
    }


    /**
     * 制造管理-产量管理-产量明细-智新月商品材产量结构
     * @param subCode
     * @return
     */
    @GetMapping("/zxyspccljg")
    @ResponseBody
    public JsonData clZxyspccljg(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clZxyspccljg());
    }

//---顺义冷轧---

    /**
     * 制造管理-产量管理-产量明细-顺义冷轧日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/slrcl")
    @ResponseBody
    public JsonData clSlrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clSlrcl());
    }

    /**
     * 制造管理-产量管理-产量明细-顺义冷轧月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/slycl")
    @ResponseBody
    public JsonData clSlycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clSlycl());
    }

    /**
     * 制造管理-产量管理-产量明细-顺义冷轧月产量比计划；
     * @param subCode
     * @return
     */
    @GetMapping("/slycl/bjh")
    @ResponseBody
    public JsonData clSlyclbjh(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clSlyclbjh());
    }

    /**
     * 制造管理-产量管理-产量明细-顺义冷轧月商品材产量结构
     * @param subCode
     * @return
     */
    @GetMapping("/slyspccljg")
    @ResponseBody
    public JsonData clSlyspccljg(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clSlyspccljg());
    }

    /**
     * 制造管理-产量管理-产量明细-顺义冷轧月商品材产量结构：冷轧商品材
     * @param subCode
     * @return
     */
    @GetMapping("/slyspccljglz")
    @ResponseBody
    public JsonData clSlyspccljglz(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clSlyspccljglz());
    }

//---迁焦---

    /**
     * 制造管理-产量管理-产量明细-迁焦日产量概览
     * @param subCode
     * @return
     */
    @GetMapping("/qjrcl")
    @ResponseBody
    public JsonData clQjrcl(@RequestParam("subCode") String subCode ) {
        return JsonData.success(getZhizaoService(subCode).clQjrcl());
    }

    /**
     * 制造管理-产量管理-产量明细-迁焦月产量概览；
     * @param subCode
     * @return
     */
    @GetMapping("/qjycl")
    @ResponseBody
    public JsonData clQjycl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clQjycl());
    }

    /**
     * 制造管理-产量管理-产量明细-迁焦月干湿焦量
     * @param subCode
     * @return
     */
    @GetMapping("/qjygsjl")
    @ResponseBody
    public JsonData clQjygsjl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getZhizaoService(subCode).clQjygsjl());
    }

    private ZhizaoService getZhizaoService(String subCode){
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return zhizaoServiceQs;
            case JT:
                throw new  RuntimeException("京唐制造模块-产量管理暂未开发 ："+subCode);
            default:
                throw new  RuntimeException("当前接口暂不支持的subCode："+subCode);
        }
    }

}


