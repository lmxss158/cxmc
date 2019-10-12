package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.XiaoshouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 销售管理Controller
 * @Author: 李锐平
 * @Date: 2019/8/10 17:10
 * @Version 1.0
 */

@Controller
@RequestMapping("/xiaoshou/kefu")
@MenuAcls(value = {"-1516265","19988"})
public class XiaoshoukefuController {

    @Autowired
    @Qualifier("xiaoshouServiceQs")
    XiaoshouService xiaoshouServiceQs;

    private String getGqslggTypeName(String typeCode){
        switch (typeCode) {
            case "gfhj":
                return "1A10'"+",'1A20";
            case "qg":
                return "1A10";
            case "sl":
                return "1A20";
            case "gg":
                return "硅钢";
            default:
                return null;
        }
    }

    // 客户服务
    /**
     * 销售管理-客户服务-质量异议-月累计
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/ylj")
    @ResponseBody
    public JsonData kfZlyyYlj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYlj());
    }

    /**
     * 销售管理-客户服务-质量异议-年累计
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/nlj")
    @ResponseBody
    public JsonData kfZlyyNlj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getXiaoshouService(subCode).kfZlyyNlj());
    }

    /**
     * 销售管理-客户服务-质量异议-异议件数分类统计
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/yyjsfltj")
    @ResponseBody
    public JsonData kfZlyyYyjsfltj(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getGqslggTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYyjsfltj(typeName));
    }

    /**
     * 销售管理-客户服务-质量异议-异议件数年累计统计
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/yyjsnljtj")
    @ResponseBody
    public JsonData kfZlyyYyjsnljtj(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getGqslggTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYyjsnljtj(typeName));
    }

    /**
     * 销售管理-客户服务-质量异议-异议件数月度趋势
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/yyjsydqs")
    @ResponseBody
    public JsonData kfZlyyYyjsydqs(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getGqslggTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYyjsydqs(typeName));
    }

    /**
     * 销售管理-客户服务-质量异议-异议缺陷年统计
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/yyqxntj")
    @ResponseBody
    public JsonData kfZlyyYyqxntj(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getGqslggNljTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYyqxntj(typeName));
    }

    /**
     * 销售管理-客户服务-质量异议-异议缺陷月统计
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/yyqxytj")
    @ResponseBody
    public JsonData kfZlyyYyqxytj(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getYyqxTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYyqxytj(typeName));
    }

    /**
     * 销售管理-客户服务-质量异议-异议缺陷分类年统计
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/yyqxflntj")
    @ResponseBody
    public JsonData kfZlyyYyqxflntj(@RequestParam("typeCode") String typeCode,
                                    @RequestParam("subCode") String subCode) {
        String typeName = getGqslggFlNljTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYyqxflntj(typeName));
    }

    /**
     * 销售管理-客户服务-质量异议-异议缺陷分类月统计
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/yyqxflytj")
    @ResponseBody
    public JsonData kfZlyyYyqxflytj(@RequestParam("typeCode") String typeCode,
                                    @RequestParam("subCode") String subCode) {
        String typeName = getYyqxFlytjTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYyqxflytj(typeName));
    }

    /**
     * 销售管理-客户服务-质量异议-异议缺陷前十位排名
     * @param subCode
     * @return
     */
    @GetMapping("/zlyy/yyqxtopten")
    @ResponseBody
    public JsonData kfZlyyYyqxjs(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getYyqxTopTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfZlyyYyqxjs(typeName));
    }

    //销售管理-客户服务-服务效率

    private String getFwxlTypeName(String typeCode){

        switch (typeCode) {
            case "zj":
                return "总计";
            case "qcb":
                return "汽车板";
            case "rzb":
                return "热轧板";
            case "sxb":
                return "酸洗板";
            case "lzfqcb":
                return "冷轧非汽车板";
            case "all":
                return "";
            default:
                return null;
        }
    }

    private String getCustomerTypeName(String typeCode) {
        switch (typeCode) {
            case "zj":
                return "总计";
            case "zlyh":
                return "战略用户";
            case "qzzlyh":
                return "潜在战略用户";
            case "zdyh":
                return "重点用户";
            case "ybyh":
                return "一般用户";
            case "all":
                return "";
            default:
                return null;
        }
    }

    /**
     * 销售管理-客户服务-服务效率-质量异议处理周期月度趋势
     * @param subCode
     * @return
     */
    @GetMapping("/fwxl/zlyyclzqydqs")
    @ResponseBody
    public JsonData kfFwxlZlyyclzqydqs(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getFwxlTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfFwxlZlyyclzqydqs(typeName));
    }

    /**
     * 销售管理-客户服务-服务效率-客户满意度总分
     * @param subCode
     * @return
     */
    @GetMapping("/fwxl/zlyyzgzq")
    @ResponseBody
    public JsonData kfFwxlZlyyZgzq(@RequestParam("typeCode") String typeCode,
                                       @RequestParam("subCode") String subCode) {
        String typeName = getFwxlTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfFwxlZlyyZgzq(typeName));
    }

    /**
     * 销售管理-客户服务-服务效率-诉求抱怨问题解决率月度趋势
     * @param subCode
     * @return
     */
    @GetMapping("/fwxl/sqbywtjjlydqs")
    @ResponseBody
    public JsonData kfFwxlSqbywtjjlydqs(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getFwxlTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfFwxlSqbywtjjlydqs(typeName));
    }

    /**
     * 销售管理-客户服务-服务效率-技术咨询应答周期月度趋势
     * @param subCode
     * @return
     */
    @GetMapping("/fwxl/jszxydzqydqs")
    @ResponseBody
    public JsonData kfFwxlJszxydzqydqs(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getFwxlTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfFwxlJszxydzqydqs(typeName));
    }

    /**
     * 销售管理-客户服务-服务效率-客户走访件数月度趋势
     * @param subCode
     * @return
     */
    @GetMapping("/fwxl/khzfjsydqs")
    @ResponseBody
    public JsonData kfFwxlKhzfjsydqs(@RequestParam("typeCode") String typeCode,
                                   @RequestParam("subCode") String subCode) {
        String typeName = getCustomerTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).kfFwxlKhzfjsydqs(typeName));
    }

    //销售管理-客户服务-汽车板质量PPM

    private String getCustomerName(String customerCode){

        switch (customerCode) {
            case "bm":
                return "宝马";
            case "cc":
                return "长城";
            case "dz":
                return "大众";
            case "sl":
                return "神龙";
            default:
                return null;
        }
    }

    private String getQcbppmTypeName(String typeCode){

        switch (typeCode) {
            case "tsppm":
                return "投诉PPM";
            case "yyppm":
                return "异议PPM";
            default:
                return null;
        }
    }

    /**
     * 销售管理-客户服务-汽车板质量PPM-当月交付量
     * @param subCode
     * @return
     */
    @GetMapping("/qcbppm/dyjfl")
    @ResponseBody
    public JsonData kfQcbppmDyjfl(@RequestParam("subCode") String subCode) {
//        String customerName = getCustomerName(customerCode) ;
//        if (customerName == null ) {
//            return JsonData.fail("无效参数");
//        }
        return JsonData.success(getXiaoshouService(subCode).kfQcbppmDyjfl());
    }

    /**
     * 销售管理-客户服务-汽车板质量PPM-异议量
     * @param subCode
     * @return
     */
    @GetMapping("/qcbppm/yyl")
    @ResponseBody
    public JsonData kfQcbppmyyl(@RequestParam("subCode") String subCode) {
//        String customerName = getCustomerName(customerCode) ;
//        if (customerName == null ) {
//            return JsonData.fail("无效参数");
//        }
        return JsonData.success(getXiaoshouService(subCode).kfQcbppmyyl());
    }


    /**
     * 销售管理-客户服务-汽车板质量PPM-月度趋势
     * @param subCode
     * @return
     */
    @GetMapping("/qcbppm/ydqs")
    @ResponseBody
    public JsonData kfQcbppmydqs(@RequestParam("customerCode") String customerCode,
                                @RequestParam("subCode") String subCode) {
        String customerName = getCustomerName(customerCode) ;
        if (customerName == null ) {
            return JsonData.fail("无效参数customerCode");
        }

//        String typeName = getQcbppmTypeName(typeCode) ;
       /* if (typeName == null ) {
            return JsonData.fail("无效参数typeCode");
        }*/
        return JsonData.success(getXiaoshouService(subCode).kfQcbppmydqs(customerName));
    }



    private String getYyqxTypeName(String typeCode){

        switch (typeCode) {
            case "rz":
                return "SGSO.YYBT_DYRZ";
            case "sx":
                return "SGSO.YYBT_DYSX";
            case "qcb":
                return "SGSO.YYBT_DYCAR";
            case "lzfqc":
                return "SGSO.YYBT_DYLZ";
            default:
                return null;
        }
    }

    private String getGqslggNljTypeName(String typeCode) {

        switch (typeCode) {
            case "rz":
                return "SGSO.YYBT_YEARRZ";
            case "sx":
                return "SGSO.YYBT_YEARSX";
            case "qcb":
                return "SGSO.YYBT_YEARCAR";
            case "lzfqc":
                return "SGSO.YYBT_YEARLZ";
            default:
                return null;
        }
    }

    private String getYyqxFlytjTypeName(String typeCode) {

        switch (typeCode) {
            case "rz":
                return "SGSO.YYBT_DYRZ_DL";
            case "sx":
                return "SGSO.YYBT_DYSX_DL";
            case "qcb":
                return "SGSO.YYBT_DYCAR_DL";
            case "lzfqc":
                return "SGSO.YYBT_DYLZ_DL";
            default:
                return null;
        }
    }

    private String getGqslggFlNljTypeName(String typeCode) {
        switch (typeCode) {
            case "rz":
                return "SGSO.YYBT_YEARRZ_DL";
            case "sx":
                return "SGSO.YYBT_YEARSX_DL";
            case "qcb":
                return "SGSO.YYBT_YEARCAR_DL";
            case "lzfqc":
                return "SGSO.YYBT_YEARLZ_DL";
            default:
                return null;
        }
    }

    private String getYyqxTopTypeName(String typeCode) {
        switch (typeCode) {
            case "rz":
                return "SGSO.YYPM_RZ";
            case "sx":
                return "SGSO.YYPM_SX";
            case "qcb":
                return "SGSO.YYPM_CAR";
            case "lzfqc":
                return "SGSO.YYPM_LZ";
            default:
                return null;
        }
    }

    private XiaoshouService getXiaoshouService(String subCode){
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return xiaoshouServiceQs;
            case JT:
                throw new  RuntimeException("京唐销售模块暂未开发："+subCode);
            default:
                throw new  RuntimeException("当前接口暂不支持的subCode："+subCode);
        }
    }
}
