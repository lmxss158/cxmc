package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.WuliuGuanliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 上传到git测试2.0
 * @Author 张年禄
 * @Date 2019/10/9 17:52
 * @Version 1.0
 */
@Controller
@RequestMapping("/wuliuguanli")
@MenuAcls(value = "19993")
public class WuliuGuanliController {

    @Autowired
    @Qualifier("wuliuGuanliServiceQs")
    WuliuGuanliService wuliuGuanliServiceQs;

    /**
     * 物流管理-采购到达量
     * @param subCode
     * @return
     */
    @GetMapping("/cgddltst")
    @ResponseBody
    public Object wlglCgddlTst(@RequestParam("subCode") String subCode){

        return JsonData.success (getWuliuGuanliService ( subCode ).getCgddlTst());
    }

    /**
     * 物流管理-日到达量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/rddlqst")
    @ResponseBody
    public Object wlglRddlqst(@RequestParam("subCode") String subCode,
                              @RequestParam("typeCode") String typeCode  ){
        String typeName = getRddlqstTypeName(typeCode);
        if (typeName == null){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getWuliuGuanliService ( subCode ).getRddlQst(typeName));
   }

    /**
     * 物流管理-清洁运输方式构成环形图
     * @param subCode
     * @return
     */
    @GetMapping("/qjysysgchxt")
    @ResponseBody
    public Object wlglQjysysgchxt(@RequestParam("subCode") String subCode,
                              @RequestParam("typeCode") String typeCode  ){
        String typeName = getRddlqstTypeName(typeCode);
        if (typeName == null){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getWuliuGuanliService ( subCode ).getQjysysgchxt(typeName));
    }

    /**
     * 物流管理-成品发运量-总发运量
     * @param subCode
     * @return
     */
    @GetMapping("/cpfyl/zfyl")
    @ResponseBody
    public Object wlglCpfylZfyl(@RequestParam("subCode") String subCode){
        return JsonData.success (getWuliuGuanliService ( subCode ).getCpfylZfyl());
    }
    /**
     * 物流管理-成品发运量-总发运量条形图
     * @param subCode
     * @return
             */
    @GetMapping("/cpfyl/zfyltxt")
    @ResponseBody
    public Object wlglCpfylZfyltxt(@RequestParam("subCode") String subCode){
        return JsonData.success (getWuliuGuanliService ( subCode ).getCpfylZfyltxt());
    }

    /**
     * 物流管理-日发运量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/rfylqst")
    @ResponseBody
    public Object wlglRfylqst(@RequestParam("subCode") String subCode,
                              @RequestParam("typeCode") String typeCode  ){
        String typeName = getRddlqstTypeName(typeCode);
        if (typeName == null){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getWuliuGuanliService ( subCode ).getRfylQst(typeName));
    }

    /**
     * 物流管理-固废发运环形图
     * @param subCode
     * @return
     */
    @GetMapping("/gffyhxt")
    @ResponseBody
    public Object wlglGffyhxt(@RequestParam("subCode") String subCode,
                              @RequestParam("typeCode") String typeCode  ){
        String typeName = getRddlqstTypeName(typeCode);
        if (typeName == null){
            return JsonData.fail ( "参数无效" );
        }
        return JsonData.success (getWuliuGuanliService ( subCode ).getGffyhxt(typeName));
    }

    private String getRddlqstTypeName(String typeCode) {

        switch (typeCode){
            case "qy":
                    return "汽运";
            case "hy":
                return "火运";
            case "ty":
                return "铁运";
            case "pd":
                return "皮带";
            default:
                return null;
        }
    }

    private WuliuGuanliService getWuliuGuanliService(String subCode) {
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf ( subCode );
        switch (subCodeEnum) {
            case QS:
                return wuliuGuanliServiceQs;
            case JT:
                throw new RuntimeException ( "京唐销售模块-产品推进暂未开发：" + subCode );
            default:
                throw new RuntimeException ( "当前接口暂不支持的subCode：" + subCode );
        }
    }

}


