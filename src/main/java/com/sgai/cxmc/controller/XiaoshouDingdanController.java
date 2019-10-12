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
 * @Description: 销售管理-订单Controller
 * @Author: 李锐平
 * @Date: 2019/8/10 17:10
 * @Version 1.0
 */

@Controller
@RequestMapping("/xiaoshou/dingdan")
@MenuAcls(value = {"-1516264","20001"})
public class XiaoshouDingdanController {

    @Autowired
    @Qualifier("xiaoshouServiceQs")
    XiaoshouService xiaoshouServiceQs;

    /**
     * 销售管理-订单管理-订单执行-订单量、发货量、准发量
     * @param typeCode 分类编码
     * @param subCode
     * @return
     */
    @GetMapping("/ddzx/ddl")
    @ResponseBody
    public JsonData ddzxDdl(@RequestParam("typeCode") String typeCode,
                                @RequestParam("subCode") String subCode) {

        String typeName;
        switch (typeCode) {
            case "ddl":
                typeName = "_订单量";
                break;
            case "zfl":
                typeName = "_准发量";
                break;
            case "fhl":
                typeName = "_发货量";
                break;
            default:
                return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).ddzxDdl(typeName));
    }

    /**
     * 销售管理-订单管理-订单执行-重点产品完成进度
     * @param subCode
     * @return
     */
    @GetMapping("/ddzx/zdcpwcjd")
    @ResponseBody
    public JsonData ddzxZdcpwcjd(@RequestParam("subCode") String subCode) {
        return JsonData.success(getXiaoshouService(subCode).ddzxZdcpwcjd());

    }

    /**
     * 销售管理-订单管理-订单执行-重点客户完成进度
     * @param subCode
     * @return
     */
    @GetMapping("/ddzx/zdkhwcjd")
    @ResponseBody
    public JsonData ddzxZdkhwcjd(@RequestParam("subCode") String subCode) {
        return JsonData.success(getXiaoshouService(subCode).ddzxZdkhwcjd());

    }

    /**
     * 销售管理-订单管理-订单执行-库存结构
     * @param subCode
     * @return
     */
    @GetMapping("/ddzx/kcjg")
    @ResponseBody
    public JsonData ddzxKcjg(@RequestParam("subCode") String subCode,
                            @RequestParam("typeCode") String typeCode) {

        String typeName = getKcjgTypeName(typeCode);
        if (typeName == null){
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).ddzxKcjg(typeName));
    }

    /**
     * 销售管理-订单管理-订单执行-库存结构-寄存、期货饼图
     * @param subCode
     * @return
     */
    @GetMapping("/ddzx/kcjg/jcqhxhqst")
    @ResponseBody
    public JsonData ddzxKcjgJcqhqst(@RequestParam("subCode") String subCode,
                             @RequestParam("typeCode") String typeCode) {

        String typeName = getKcjgTypeName(typeCode);
        if (typeName == null){
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).ddzxKcjgJcqhqst(typeName));
    }

    /**
     * 销售管理-订单管理-订单执行-库存结构-可编计划、已释放、汽火可交、准发接收饼图
     * @param subCode
     * @return
     */
    @GetMapping("/ddzx/kcjg/kyqzqst")
    @ResponseBody
    public JsonData ddzxKcjgKyqzqst(@RequestParam("subCode") String subCode,
                                    @RequestParam("typeCode") String typeCode) {

        String typeName = getKcjgTypeName(typeCode);
        if (typeName == null){
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).ddzxKcjgKyqzqst(typeName));
    }

    private String getKcjgTypeName(String typeCode){

        switch (typeCode) {
            case "qg":
                return "迁钢";
            case "sl":
                return "顺义";
            case "gg":
                return "硅钢";
            default:
                return null;
        }
    }

    /**
     * 销售管理-订单管理-订单执行-库存结构分类统计图
     * @param typeCode 分类编码
     * @param subCode
     * @return
     */
    @GetMapping("/ddzx/kcjgfl")
    @ResponseBody
    public JsonData ddzxKcjgfl(@RequestParam("typeCode") String typeCode,
                            @RequestParam("subCode") String subCode) {

        String typeName = getGqslggTypeName(typeCode) ;
        if (typeName == null ) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).ddzxKcjgfl(typeName));
    }

    private String getGqslggTypeName(String typeCode){

        switch (typeCode) {
            case "qg":
                return "迁钢";
            case "sl":
                return "冷轧";
            case "gg":
                return "硅钢";
            default:
                return null;
        }
    }

    /**
     * 销售管理-订单管理-订单执行-兑现率月度趋势图
     * @param typeCode 分类编码
     * @param subCode
     * @return
     */
    @GetMapping("/ddzx/dxlydqs")
    @ResponseBody
    public JsonData ddzxDxlydqs(@RequestParam("typeCode") String typeCode,
                            @RequestParam("subCode") String subCode) {

        String typeName;
        switch (typeCode) {
            case "zt":
                typeName = "整体";
                break;
            case "zd":
                typeName = "整单";
                break;
            default:
                return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).ddzxDxlydqs(typeName));
    }


    /**
     * 销售管理-订单管理-订单组织-次月订单接单量
     * @param subCode
     * @return
     */
    @GetMapping("/ddzz/cyddjdl")
    @ResponseBody
    public JsonData ddzzCyddjdl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getXiaoshouService(subCode).ddzzCyddjdl());
    }

    /**
     * 销售管理-订单管理-订单组织-完成率月度趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/ddzz/wclydqs")
    @ResponseBody
    public JsonData ddzzWclydqs(@RequestParam("typeCode") String typeCode,@RequestParam("subCode") String subCode) {
        String typeName;
        switch (typeCode) {
            case "xsjh":
                typeName = "销售计划";
                break;
            case "jd":
                typeName = "节点";
                break;
            default:
                return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).ddzzWclydqs(typeName));
    }

    /**
     * 销售管理-订单管理-订单组织-分销售组织销售计划完成率
     * @param subCode
     * @return
     */
    @GetMapping("/ddzz/fzzxsjhwcl")
    @ResponseBody
    public JsonData ddzzFzzxsjhwcl(@RequestParam("subCode") String subCode) {
        return JsonData.success(getXiaoshouService(subCode).ddzzFzzxsjhwcl());

    }

    private XiaoshouService getXiaoshouService(String subCode){
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf(subCode);
        switch (subCodeEnum) {
            case QS:
                return xiaoshouServiceQs;
            case JT:
                throw new  RuntimeException("京唐销售模块-订单管理暂未开发："+subCode);
            default:
                throw new  RuntimeException("当前接口暂不支持的subCode："+subCode);
        }
    }
}