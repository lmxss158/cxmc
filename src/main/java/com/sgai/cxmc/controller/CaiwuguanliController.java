package com.sgai.cxmc.controller;

import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.enums.SubCodeEnum;
import com.sgai.cxmc.service.CaiwuguanliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/9/26 13:08
 * @Version 1.0
 */

@Controller
@RequestMapping("/caiwuguanli")
@MenuAcls(value = "19987")
public class CaiwuguanliController {

    @Autowired
    @Qualifier("caiwuguanliServiceQs")
    CaiwuguanliService caiwuguanliServiceQs;

    /**
     * 财务管理-主要指标-营业收入
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/yysr")
    @ResponseBody
    public Object cwglZyzbYysr(@RequestParam("subCode") String subCode,
                               @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        if (companyName == null) {
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbYysr (companyName) );
    }


    /**
     * 财务管理-主要指标-利润总额
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/lr")
    @ResponseBody
    public Object cwglZyzbLr(@RequestParam("subCode") String subCode,
                             @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        if (companyName == null) {
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbLr ( companyName ) );
    }

    /**
     * 财务管理-主要指标-盈利能力-月实际、年累计
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/ylnl/ysjnlj")
    @ResponseBody
    public Object cwglZyzbYlzczj(@RequestParam("subCode") String subCode,
                                 @RequestParam("typeCode") String typeCode,
                                 @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZyzbYlnlIndexName ( typeCode );
        if (indexName == null) {
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbYlnl ( indexName,companyName ) );
    }

    /**
     * 财务管理-主要指标-盈利能力-环比上月
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/ylnl/hbsy")
    @ResponseBody
    public Object cwglZyzbHbsy(@RequestParam("subCode") String subCode,
                               @RequestParam("typeCode") String typeCode,
                               @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZyzbYlnlIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbHbsy ( indexName,companyName ) );
    }

    /**
     * 财务管理-主要指标-盈利能力-月度同比
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/ylnl/ydtb")
    @ResponseBody
    public Object cwglZyzbYdtb(@RequestParam("subCode") String subCode,
                               @RequestParam("typeCode") String typeCode,
                               @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZyzbYlnlIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbYdtb ( indexName,companyName ) );
    }

    /**
     * 财务管理-主要指标-盈利能力-月趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/ylnl/yqst")
    @ResponseBody
    public Object cwglZyzbYqst(@RequestParam("subCode") String subCode,
                               @RequestParam("typeCode") String typeCode,
                               @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZyzbYlnlIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbYqst ( indexName,companyName ) );
    }

    /**
     * 财务管理-主要指标-资产效率-月实际
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/zcxl/ysj")
    @ResponseBody
    public Object cwglZcxlYsj(@RequestParam("subCode") String subCode,
                              @RequestParam("typeCode") String typeCode,
                              @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZcxlIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getZcxlYsj ( indexName,companyName ) );
    }

    /**
     * 财务管理-主要指标-资产效率-环比上月
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/zcxl/hbsy")
    @ResponseBody
    public Object cwglZcxlHbsy(@RequestParam("subCode") String subCode,
                               @RequestParam("typeCode") String typeCode,
                               @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZcxlIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbZcxlHbsy ( indexName,companyName) );
    }
    /**
     * 财务管理-主要指标-资产效率-月度同比
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/zcxl/ydtb")
    @ResponseBody
    public Object cwglZcxlYdtb(@RequestParam("subCode") String subCode,
                               @RequestParam("typeCode") String typeCode,
                               @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZcxlIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbZcxlYdtb ( indexName,companyName ) );
    }

    /**
     * 财务管理-主要指标-资产效率-月趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/zcxl/yqst")
    @ResponseBody
    public Object cwglZyzbZcxlYqst(@RequestParam("subCode") String subCode,
                                   @RequestParam("typeCode") String typeCode,
                                   @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZcxlIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbZcxlYqst ( indexName,companyName ) );
    }

    /**
     * 财务管理-主要指标-资金管理-月实际、年累计
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/zjgl/ysjnlj")
    @ResponseBody
    public Object cwglZyzbZjglYlzczj(@RequestParam("subCode") String subCode,
                                     @RequestParam("typeCode") String typeCode,
                                     @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyName ( companyCode );
        String indexName = getZyzbZjglIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbZjglYlnl ( indexName,companyName ) );
    }

    /**
     * 财务管理-主要指标-资金管理-月趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/zyzb/zjgl/yqst")
    @ResponseBody
    public Object cwglZyzbZjglYqst(@RequestParam("subCode") String subCode,
                                   @RequestParam("typeCode") String typeCode,
                                   @RequestParam("companyCode") String companyCode) {
        String companyName = getCompanyCName ( companyCode );
        String indexName = getZyzbZjglIndexName ( typeCode );
        if (indexName == null || null == companyName) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getZyzbZjglYqst ( indexName,companyName) );
    }


    /**
     * 财务管理-成本构成-月度成本费(酸洗、炼铁、炼钢、热轧)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/sllrydcbf")
    @ResponseBody
    public Object cwglCbgcYdcbf(@RequestParam("subCode") String subCode,
                                   @RequestParam("areaCode") String areaCode) {
        String areaName = getCbgcAreaName ( areaCode );
        if (areaName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcYdcbf ( areaName ) );
    }

    /**
     * 财务管理-成本构成-加工费构成(酸洗、炼铁、炼钢、热轧)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/sllrjgfgc")
    @ResponseBody
    public Object cwglCbgcJgfgc(@RequestParam("subCode") String subCode,
                                @RequestParam("areaCode") String areaCode) {
        String areaName = getCbgcAreaName ( areaCode );
        if (areaName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcJgfgc ( areaName ) );
    }

    /**
     * 财务管理-成本构成-加工费构成环形图中间取值(酸洗、炼铁、炼钢、热轧)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/sllrjgfgc/midval")
    @ResponseBody
    public Object cwglCbgcJgfgcMidval(@RequestParam("subCode") String subCode,
                                @RequestParam("areaCode") String areaCode) {
        String areaName = getCbgcAreaName ( areaCode );
        if (areaName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcJgfgcMidval ( areaName ) );
    }

    /**
     * 财务管理-成本构成-月度成本费(智新公司)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/zxgsydcbf")
    @ResponseBody
    public Object cwglCbgcZxgsYdcbf(@RequestParam("subCode") String subCode,
                                @RequestParam("typeCode") String typeCode) {
        String tableName = getCbgcZxgsTableName ( typeCode );
        if (tableName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcZxgsYdcbf ( tableName ) );
    }

    /**
     * 财务管理-成本构成-加工费构成(智新公司)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/zxgsjgfgc")
    @ResponseBody
    public Object cwglCbgcZxgsJgfgc(@RequestParam("subCode") String subCode,
                                    @RequestParam("typeCode") String typeCode) {
        String tableName = getCbgcZxgsTableName ( typeCode );
        if (tableName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcZxgsJgfgc ( tableName ) );
    }

    /**
     * 财务管理-成本构成-加工费构成环形图中间取值(智新公司)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/zxgsjgfgc/midval")
    @ResponseBody
    public Object cwglCbgcZxgsJgfgcMidval(@RequestParam("subCode") String subCode,
                                    @RequestParam("typeCode") String typeCode) {
        String areaName = getCbgcZxgsTableName ( typeCode );
        if (areaName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcJgfgcMidval ( areaName ) );
    }

    /**
     * 财务管理-成本构成-月度成本费(顺义冷轧)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/sylzydcbf")
    @ResponseBody
    public Object cwglCbgcSylzYdcbf(@RequestParam("subCode") String subCode,
                                    @RequestParam("typeCode") String typeCode) {
        String tableName = getCbgcSylzTableName ( typeCode );
        if (tableName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcSylzYdcbf ( tableName ) );
    }

    /**
     * 财务管理-成本构成-加工费构成(顺义冷轧)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/sylzjgfgc")
    @ResponseBody
    public Object cwglCbgcSylzJgfgc(@RequestParam("subCode") String subCode,
                                    @RequestParam("typeCode") String typeCode) {
        String tableName = getCbgcSylzTableName ( typeCode );
        if (tableName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcZxgsJgfgc ( tableName ) );
    }

    /**
     * 财务管理-成本构成-加工费构成环形图中间取值(顺义冷轧)
     *
     * @param subCode
     * @return
     */
    @GetMapping("/cbgc/sylzjgfgc/midval")
    @ResponseBody
    public Object cwglCbgcSylzJgfgcMidval(@RequestParam("subCode") String subCode,
                                    @RequestParam("typeCode") String typeCode) {
        String areaName = getCbgcSylzTableName ( typeCode );
        if (areaName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getCbgcJgfgcMidval ( areaName ) );
    }

    /**
     * 财务管理-工序成本-铁前成本-各类型数量
     *
     * @param subCode
     * @return
     */
    @GetMapping("/gxcb/tqcb/glxsl")
    @ResponseBody
    public Object cwglGxcbTqcbaglxsl(@RequestParam("subCode") String subCode) {
//        String typeName = getGxcbTqcbglxslTableName ( typeCode );
      /*  if (typeName == null) {
            return JsonData.fail ( "无效参数" );
        }*/

        return JsonData.success ( getCaiwuguanliService ( subCode ).getcwglGxcbTqcbglxsl ( ) );
    }

    /**
     * 财务管理-工序成本-铁前成本-各类型环比上月
     *
     * @param subCode
     * @return
     */
    @GetMapping("/gxcb/tqcb/hbsy")
    @ResponseBody
    public Object cwglGxcbTqcbhbsy(@RequestParam("subCode") String subCode) {
       /* String typeName = getGxcbTqcbglxslTableName ( typeCode );
        if (typeName == null) {
            return JsonData.fail ( "无效参数" );
        }*/

        return JsonData.success ( getCaiwuguanliService ( subCode ).getcwglGxcbTqcbhbsy ( ) );
    }

    /**
     * 财务管理-工序成本-铁前成本-各类型成本月度趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/gxcb/tqcb/cbydqst")
    @ResponseBody
    public Object cwglGxcbTqcbCbydqst(@RequestParam("subCode") String subCode,
                                   @RequestParam("typeCode") String typeCode) {
        String typeName = getGxcbTqcbglxslTableName ( typeCode );
        if (typeName == null) {
            return JsonData.fail ( "无效参数" );
        }

        return JsonData.success ( getCaiwuguanliService ( subCode ).getcwglGxcbTqcbCbydqst ( typeName ) );
    }

    /**
     * 财务管理-工序成本-钢后成本-各类型数量
     *
     * @param subCode
     * @return
     */
    @GetMapping("/gxcb/ghcb/glxsl")
    @ResponseBody
    public Object cwglGxcbGhcbaglxsl(@RequestParam("subCode") String subCode) {
       /* String typeName = getGxcbGhcbglxslTableName ( typeCode );
        if (typeName == null) {
            return JsonData.fail ( "无效参数" );
        }*/

        return JsonData.success ( getCaiwuguanliService ( subCode ).getcwglGxcbGhcbglxsl (  ) );
    }

    /**
     * 财务管理-工序成本-钢后成本-各类型环比上月
     *
     * @param subCode
     * @return
     */
    @GetMapping("/gxcb/ghcb/hbsy")
    @ResponseBody
    public Object cwglGxcbGhcbaHbsy(@RequestParam("subCode") String subCode) {
       /* String typeName = getGxcbGhcbglxslTableName ( typeCode );
        if (typeName == null) {
            return JsonData.fail ( "无效参数" );
        }*/

        return JsonData.success ( getCaiwuguanliService ( subCode ).getcwglGxcbGhcbHbsy (  ) );
    }

    /**
     * 财务管理-工序成本-钢后成本-各类型成本月度趋势图
     *
     * @param subCode
     * @return
     */
    @GetMapping("/gxcb/ghcb/cbydqst")
    @ResponseBody
    public Object cwglGxcbGhcbaCbydqst(@RequestParam("subCode") String subCode,
                                    @RequestParam("typeCode") String typeCode) {
        String typeName = getGxcbGhcbglxslTableName ( typeCode );
        if (typeName == null) {
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success ( getCaiwuguanliService ( subCode ).getcwglGxcbGhcbCbydqst ( typeName ) );
    }

    @GetMapping("/cpylnl/mllrzyzb")
    @ResponseBody
    public Object cwglCpylnlMllrzyzb(@RequestParam("subCode") String subCode,
                                     @RequestParam("companyCode") String companyCode){
        String companyName = getCompanyCName ( companyCode );
        if (companyName == null){
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success (getCaiwuguanliService ( subCode ).getcwglCpylnlMllrzyzb(companyName));
    }

    @GetMapping("/cpylnl/xsmlqst")
    @ResponseBody
    public Object cwglCpylnlXsmlqst(@RequestParam("subCode") String subCode,
                                     @RequestParam("companyCode") String companyCode){
        String companyName = getCompanyCName ( companyCode );
        if (companyName == null){
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success (getCaiwuguanliService ( subCode ).getcwglCpylnlXsmlqst(companyName));
    }

    @GetMapping("/cpylnl/bjlrqst")
    @ResponseBody
    public Object cwglCpylnlBjlrqst(@RequestParam("subCode") String subCode,
                                    @RequestParam("companyCode") String companyCode){
        String companyName = getCompanyCName ( companyCode );
        if (companyName == null){
            return JsonData.fail ( "无效参数" );
        }
        return JsonData.success (getCaiwuguanliService ( subCode ).getcwglCpylnlBjlrqst(companyName));
    }

    private String getGxcbGhcbglxslTableName(String typeCode) {
        switch (typeCode) {
            case "bp":
                return "炼钢单成";
            case "lt":
                return "连退单成";
            case "wqx":
                return "无取向单成";
            case "sx":
                return "酸洗单成";
            case "rj":
                return "热轧单成";
            case "dx":
                return "镀锌单成";
            case "qx":
                return "取向单成";
            default:
                return null;
        }
    }

    private String getGxcbTqcbglxslTableName(String typeCode) {
        switch (typeCode) {
            case "ak":
                return "澳块";
            case "nfk":
                return "南非块";
            case "yhq":
                return "氧化球";
            case "ltdc":
                return "炼铁单成";
            case "jt":
                return "焦炭";
            case "pcm":
                return "喷吹煤";
            case "kssjk":
                return "矿山烧结矿";
            case "wgsjk":
                return "外购烧结矿";
            case "fg":
                return "外购废钢";
            default:
                return null;
        }
    }

    private String getCbgcSylzTableName(String typeCode) {
        switch (typeCode) {
            case "sz":
                return "顺义冷轧";
            case "lt":
                return "连退";
            case "dx":
                return "镀锌";
            default:
                return null;
        }
    }

    private String getCbgcZxgsTableName(String typeCode) {
        switch (typeCode) {
            case "qx":
                return "取向";
            case "wqx":
                return "无取向";
            default:
                return null;
        }

    }

    private String getCbgcAreaName(String areaCode) {
        switch (areaCode){
            case "gf":
                return "公辅";
            case "qt":
                return "球团";
            case "sj":
                return "烧结";
            case "sx":
                return "酸洗";
            case "lt":
                return "炼铁";
            case "lg":
                return "炼钢";
            case "rz":
                return "热轧";
            case "qj":
                return "迁焦";
            default:
                return null;
        }
    }

    private String getCompanyCName(String companyCode) {
        switch (companyCode) {
            case "qs":
                return "迁顺";
            case "qg":
                return "迁钢";
            case "sy":
                return "顺义";
            default:
                return null;
        }

    }
    private String getZyzbZjglIndexName(String typeCode) {
        switch (typeCode) {
            case "lxzc":
                return "利息支出";
            case "xjjll":
                return "现金净流量";
            case "jyxjlje":
                return "经营现金流净额";
            default:
                return null;
        }

    }
    private String getZcxlIndexName(String typeCode) {
        switch (typeCode) {
            case "yszkje":
                return "应收账款净额";
            case "yszkzzl":
                return "应收账款周转率";
            case "yszkzzts":
                return "应收账款周转天数";
            case "chbkcdj":
                return "存货--不扣除跌价";
            case "chzzl":
                return "存货周转率";
            case "chzzts":
                return "存货周转天数";
            case "yfzkje":
                return "应付帐款净额";
            case "ldzczzl":
                return "流动资产周转率";
            case "zzczzl":
                return "总资产周转率";
            case "yyzqts":
                return "营运周期（周转天数）";
            default:
                return null;
        }
    }
    private String getZyzbYlnlIndexName(String typeCode) {

        switch (typeCode) {
            case "yyzsr":
                return "营业总收入";
            case "yycb":
                return "营业成本";
            case "lrze":
                return "利润总额";
            case "jll":
                return "净利率";
            case "zzcbcl":
                return "总资产报酬率";
            default:
                return null;
        }
    }

    private String getCompanyName(String companyCode) {
        switch (companyCode) {
            case "qg":
                return "1A10";
            case "sy":
                return "1A20";
            default:
                return null;
        }
    }

    private CaiwuguanliService getCaiwuguanliService(String subCode) {
        SubCodeEnum subCodeEnum = SubCodeEnum.valueOf ( subCode );
        switch (subCodeEnum) {
            case QS:
                return caiwuguanliServiceQs;
            case JT:
                throw new RuntimeException ( "京唐采购模块-原燃料库存暂未开发：" + subCode );
            default:
                throw new RuntimeException ( "当前接口暂不支持的subCode：" + subCode );
        }
    }
}

