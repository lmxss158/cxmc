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
 * @Description: 销售管理-产品推进Controller
 * @Author: 李锐平
 * @Date: 2019/8/10 17:10
 * @Version 1.0
 */

@Controller
@RequestMapping("/xiaoshou/chanpin")
@MenuAcls(value = {"-1516262","19988"})
public class XiaoshouChanpinController {

    @Autowired
    @Qualifier("xiaoshouServiceQs")
    XiaoshouService xiaoshouServiceQs;



    // 销售管理-产品推进

    /**
     * 销售管理-产品推进-产品结构-高端领先产品及其它重点产品准发量月累计进度（除无取向硅钢计划、实际、集装箱、马口铁计划）
     * @param subCode
     * @return
     */
    @GetMapping("/gdlxcpyljjd")
    @ResponseBody
    public JsonData cptjGdlxcpyljjd(@RequestParam("subCode") String subCode) {
        return JsonData.success(getXiaoshouService(subCode).cptjGdlxcpyljjd());

    }

    /**
     * 销售管理-产品推进-产品结构-其他重点产品月累计进度--汽车酸洗实际计划值
     * @param subCode
     * @return
     */
    @GetMapping("/qtzdcpyljjd/qcsxsj")
    @ResponseBody
    public JsonData cptjQtzdcpyljjdQcsxsj(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getXiaoshouService ( subCode ).cptjQtzdcpyljjdQcsxsj ( ) );

    }

    /**
     * 销售管理-产品推进-产品结构-其他重点产品月累计进度--汽车镀锌，连退，无取向硅钢实际计划值
     * @param subCode
     * @return
     */
    @GetMapping("/qtzdcpyljjd/dlwsj")
    @ResponseBody
    public JsonData cptjQtzdcpyljjdDlwsj(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getXiaoshouService ( subCode ).cptjQtzdcpyljjdDlwsj ( ) );

    }

    /**
     * 销售管理-产品推进-产品结构-其他重点产品月累计进度--汽车家电酸洗，集装箱，马口铁实际值
     * @param subCode
     * @return
     */
    @GetMapping("/qtzdcpyljjd/jjmshj")
    @ResponseBody
    public JsonData cptjQtzdcpyljjdJjmshj(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getXiaoshouService ( subCode ).cptjQtzdcpyljjdJjmshj ( ) );

    }

    /**
     * 销售管理-产品推进-产品结构-其他重点产品月累计进度--汽车家电酸洗，集装箱，马口铁计划值
     * @param subCode
     * @return
     */
    @GetMapping("/qtzdcpyljjd/jjmjh")
    @ResponseBody
    public JsonData cptjQtzdcpyljjdJjmjh(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getXiaoshouService ( subCode ).cptjQtzdcpyljjdJjmjh ( ) );

    }

    /**
     * 销售管理-产品推进-产品结构-当月战略产品月准发量累计进度--汽车板
     * @param subCode
     * @return
     */
    @GetMapping("/cpyzflljjd/qcb")
    @ResponseBody
    public JsonData cpyzflLjjdQcb(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getXiaoshouService ( subCode ).cpyzflLjjdQcb ( ) );

    }

    /**
     * 销售管理-产品推进-产品结构-当月战略产品月准发量累计进度--硅钢
     * @param subCode
     * @return
     */
    @GetMapping("/cpyzflljjd/gg")
    @ResponseBody
    public JsonData cpyzflLjjdGg(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getXiaoshouService ( subCode ).cpyzflLjjdGg ( ) );

    }

    /**
     * 销售管理-产品推进-产品结构-当月战略产品月准发量累计进度--汽车板录入计划值
     * @param subCode
     * @return
     */
    @GetMapping("/cpyzflljjd/lrjhz")
    @ResponseBody
    public JsonData cpyzflLjjdLrjhz(@RequestParam("subCode") String subCode) {
        return JsonData.success ( getXiaoshouService ( subCode ).cpyzflLjjdLrjhz ( ) );

    }

    /**
     * 销售管理-产品推进-产品开发与认证-汽车板证认证
     * @param subCode
     * @return
     */
    @GetMapping("/cpkfyrztj")
    @ResponseBody
    public JsonData cpKfyrztj(@RequestParam("subCode") String subCode,
                              @RequestParam("subCode") String approveCode) {
        String approveName = getKfrzApproveName(approveCode);
        return JsonData.success(getXiaoshouService(subCode).cpKfyrztj(approveName));
    }

    /**
     * 销售管理-产品推进-产品开发与认证-认证情况分类统计
     * @param subCode
     * @return
     */
    @GetMapping("/rzqkfltj")
    @ResponseBody
    public JsonData cpRzqkfltj(@RequestParam("typeCode") String typeCode,@RequestParam("subCode") String subCode) {

        String typeName = getKfrzTypeName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }

        return JsonData.success(getXiaoshouService(subCode).cpRzqkfltj(typeName));
    }

    /**
     * 销售管理-产品推进-产品开发与认证-认证情况月度趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/rzqkydqs")
    @ResponseBody
    public JsonData cpRzqkydqs(@RequestParam("typeCode") String typeCode,@RequestParam("subCode") String subCode) {
        String typeName = getKfrzTableName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).cpRzqkydqs(typeName));
    }

    /**
     * 销售管理-产品推进-产品开发与认证-集团合计EVI供货量-EVI项目数
     * @param subCode
     * @return
     */
    @GetMapping("/jthjghl/evixms")
    @ResponseBody
    public JsonData cpJthjghlEvixms(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpJthjghlEvixms());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-集团合计EVI供货量-年度计划
     * @param subCode
     * @return
     */
    @GetMapping("/jthjghl/ndjh")
    @ResponseBody
    public JsonData cpJthjghlNdjh(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpJthjghlNdjh());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-集团合计EVI供货量-月度完成
     * @param subCode
     * @return
     */
    @GetMapping("/jthjghl/ydwc")
    @ResponseBody
    public JsonData cpJthjghlYdwc(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpJthjghlYdwc());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-集团合计EVI供货量-年度累计完成
     * @param subCode
     * @return
     */
    @GetMapping("/jthjghl/ndljwc")
    @ResponseBody
    public JsonData cpJthjghlNdljwc(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpJthjghlNdljwc());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-集团合计EVI供货量-年度累计同比
     * @param subCode
     * @return
     */
    @GetMapping("/jthjghl/ndljtb")
    @ResponseBody
    public JsonData cpJthjghlNdljtb(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpJthjghlNdljtb());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-集团合计月度供货量及新试产品供货量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/jthjghl/ydghjxsghqst")
    @ResponseBody
    public JsonData cpJthjghlYdghjxsghqst(@RequestParam("subCode") String subCode,
                                 @RequestParam("typeCode") String typeCode) {

        String typeName = getGhlqstTypeName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).cpJthjghlYdghjxsghqst(typeName));
    }

    /**
     * 销售管理-产品推进-产品开发与认证-股份合计月度供货量及新试产品供货量趋势图
     * @param subCode
     * @return
     */
    @GetMapping("/gfhjghl/ydghjxsghqst")
    @ResponseBody
    public JsonData cpGfhjghlYdghjxsghqst(@RequestParam("subCode") String subCode,
                                          @RequestParam("typeCode") String typeCode) {

        String typeName = getGhlqstTypeName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).cpGfhjghlYdghjxsghqst(typeName));
    }

    /**
     * 销售管理-产品推进-产品开发与认证-月度供货量及新试产品供货量趋势图(热系、汽车板、酸洗、电工钢)
     * @param subCode
     * @return
     */
    @GetMapping("/rqslghl/ydghjxsghqst")
    @ResponseBody
    public JsonData cpRqslghlYdghjxsghqst(@RequestParam("subCode") String subCode,
                                          @RequestParam("tableCode") String tableCode,
                                          @RequestParam("productCode") String productCode) {

        String tabName = getKfrzTypeName(tableCode);
        String productName = getGhlqstTypeName(productCode);
        if (null == tabName && null == productName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).cpRqslghlYdghjxsghqst(tabName,productName));
    }


    private String getGhlqstTypeName(String typeCode) {
        switch (typeCode){
            case "evighl":
                return "EVI供货量";
            case "xscpxl":
                return "新试产品销量";
            default:
                return null;
        }
    }

    /**
     * 销售管理-产品推进-产品开发与认证-股份合计EVI供货量-EVI项目数
     * @param subCode
     * @return
     */
    @GetMapping("/gfhjghl/evixms")
    @ResponseBody
    public JsonData cpGfhjghlEvixms(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpGfhjghlEvixms());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-股份合计EVI供货量-年度计划
     * @param subCode
     * @return
     */
    @GetMapping("/gfhjghl/ndjh")
    @ResponseBody
    public JsonData cpGfhjghlNdjh(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpGfhjghlNdjh());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-股份合计EVI供货量-月度完成
     * @param subCode
     * @return
     */
    @GetMapping("/gfhjghl/ydwc")
    @ResponseBody
    public JsonData cpGfhjghlYdjh(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpGfhjghlYdjh());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-股份合计EVI供货量-年度累计完成
     * @param subCode
     * @return
     */
    @GetMapping("/gfhjghl/ndljwc")
    @ResponseBody
    public JsonData cpGfhjghlNdljwc(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpGfhjghlNdljwc());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-股份合计EVI供货量-年度累计同比
     * @param subCode
     * @return
     */
    @GetMapping("/gfhjghl/ndljtb")
    @ResponseBody
    public JsonData cpGfhjghlNdljtb(@RequestParam("subCode") String subCode) {

        return JsonData.success(getXiaoshouService(subCode).cpGfhjghlNdljtb());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-EVI供货量-EVI项目数(热轧卷板、汽车板、酸洗、电工钢)
     * @param subCode
     * @return
     */
    @GetMapping("/ghl/rqslxms")
    @ResponseBody
    public JsonData cpGhlRqslxms(@RequestParam("subCode") String subCode,
                                 @RequestParam("typeCode") String typeCode) {
        String typeName = getKfrzTypeName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).cpGhlRqslxms(typeName));
    }

    /**
     * 销售管理-产品推进-产品开发与认证-EVI供货量-年度计划与月度完成(热轧卷板、汽车板、酸洗、电工钢)
     * @param subCode
     * @return
     */
    @GetMapping("/ghl/rqsljhwc")
    @ResponseBody
    public JsonData cpGfhjghlRqslJhwc(@RequestParam("subCode") String subCode,
                                      @RequestParam("typeCode") String typeCode) {
        String typeName = getKfrzTypeName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).cpGfhjghlRqslJhwc(typeName));
    }
    /**
     * 销售管理-产品推进-产品开发与认证-EVI供货量-年度累计完成与年度累计同比热轧卷板(热轧卷板、汽车板、酸洗、电工钢)
     * @param subCode
     * @return
     */
    @GetMapping("/ghl/rqslwctb")
    @ResponseBody
    public JsonData cpGfhjghlRqslWctb(@RequestParam("subCode") String subCode,
                                      @RequestParam("typeCode") String typeCode) {
        String typeName = getKfrzTypeName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).cpGfhjghlRqslWctb(typeName));
    }


    /**
     * 销售管理-产品推进-产品开发与认证-实现供货EVI项目数
     * @param subCode
     * @return
     */
    @GetMapping("/sxghevixms")
    @ResponseBody
    public JsonData cpSxghevixms(@RequestParam("typeCode") String typeCode,@RequestParam("subCode") String subCode) {
        String typeName = getKfrzTypeName(typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
 //       return JsonData.success(getXiaoshouService(subCode).cpSxghevixms(typeName));
        return null;
    }

    /**
     * 销售管理-产品推进-产品开发与认证-新试产品销量年累计
     * @param subCode
     * @return
     */
    @GetMapping("/xscpxlnlj")
    @ResponseBody
    public JsonData cpXscpxlnlj(@RequestParam("subCode") String subCode) {
        return JsonData.success(getXiaoshouService(subCode).cpXscpxlnlj());
    }

    /**
     * 销售管理-产品推进-产品开发与认证-新试产品月度销量趋势
     * @param subCode
     * @return
     */
    @GetMapping("/xscpydxlqs")
    @ResponseBody
    public JsonData cpXscpydxlqs(@RequestParam("typeCode") String typeCode,@RequestParam("subCode") String subCode) {
        String typeName = getKfrzTypeName(typeCode);
        String tableName = getKfrzTableName (typeCode);
        if (null == typeName) {
            return JsonData.fail("无效参数");
        }
        return JsonData.success(getXiaoshouService(subCode).cpXscpydxlqs(typeName));
    }

    //产品开发与认证分类
    private String getKfrzTypeName(String typeCode){

        switch (typeCode) {
            case "jthj":
                return "集团合计";
            case "gfhj":
                return "股份合计";
            case "rzjb":
                return "热轧卷板";
            case "rx":
                return "热系";
            case "sx":
                return "酸洗";
            case "qcb":
                return "汽车板";
            case "dgg":
                return "电工钢";
            default:
                return null;
        }

    }

    //产品开发与认证分类标签页名称
    private String getKfrzTableName(String typeCode) {
        switch (typeCode) {
            case "blzsl":
                return "备料准时率";
            case "tgl":
                return "通过率";
            default:
                return null;
        }
    }

    private String getKfrzApproveName(String approveCode) {
        switch (approveCode) {
            case "drrzjh":
                return "认证机会获得";
            case "ljrzjh":
                return "当月认证转化订单";
            case "qinrzzhl":
                return "前年认证转化率";
            case "qunrzzhl":
                return "去年认证转化率";
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
                throw new  RuntimeException("京唐销售模块-产品推进暂未开发："+subCode);
            default:
                throw new  RuntimeException("当前接口暂不支持的subCode："+subCode);
        }
    }
}
