package com.sgai.cxmc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sgai.cxmc.service.QueryDataService;
import com.sgai.cxmc.service.ZonglanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description: 总览模块接口-迁钢实现
 * @Author: 李锐平
 * @Date: 2019/8/5 16:12
 * @Version 1.0
 */

@Service("zonglanServiceQs")
public class ZonglanServiceQsImpl implements ZonglanService {

    @Autowired
    QueryDataService queryDataService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Object cwxx(String companyCode) {

        String xjjllSql = null; //现金净流量
        String xjjllPreviousSql = null;  //现金净流量上期

        if ("1A10".equals(companyCode) ) {
            //迁钢
            xjjllSql = " SELECT calendar,company_name,zb_name,zb_value/10000 as curvalue , '亿元' as unit_name\n"
                    + " FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + " where company_name = '迁钢'\n"
                    + "  and zb_name = '现金净流量'\n"
                    + "  and calendar = (select max(calendar)\n"
                    + "                  FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + "                  where company_name = '迁钢'\n"
                    + "                    and zb_name = '现金净流量'\n"
                    + "                    and zb_value <> 0)";

            xjjllPreviousSql = "SELECT calendar,company_name,zb_name,zb_value/10000 as curvalue , '亿元' as unit_name\n"
                    + " FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + " where company_name = '迁钢'\n"
                    + "  and zb_name = '现金净流量'\n"
                    + "  and calendar = (SELECT to_char(to_date(MAX(calendar), 'yyyy-mm') - 1 MONTH, 'yyyy-mm')\n"
                    + "                  FROM sgmc.v_mc_zb_fi\n"
                    + "                  WHERE company_code = '1A10') ";

        } else if ("1A20".equals(companyCode)) {
            //顺义
            xjjllSql = "SELECT calendar,company_name,zb_name,zb_value/10000 as curvalue , '亿元' as unit_name\n"
                    + " FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + " where company_name = '顺义' and zb_name ='现金净流量' and calendar= to_char(current date-1 month,'yyyy-mm')";

            xjjllPreviousSql = "SELECT calendar,company_name,zb_name,zb_value/10000 as curvalue , '亿元' as unit_name\n"
                    + " FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + " where company_name = '顺义' and zb_name ='现金净流量' and calendar= to_char(current date-2 month,'yyyy-mm')";

        }

        String thisDataSql = "SELECT calendar,company_name,zb_name,  curvalue,unit_name "
                + " FROM sgmc.V_MC_ZB_FI "
                + " where zb_name in('营业总收入','利润总额','现金净流量','存货--不扣除跌价','应收账款净额') "
                + "  and company_code= '"+companyCode+"' "
                + "  and calendar=(select max(calendar) from sgmc.v_mc_zb_fi where company_code='"+companyCode+"') "
                + " order by calendar desc ";

        String previousDataSql = "SELECT calendar,company_name,zb_name, curvalue,unit_name "
                + "FROM sgmc.V_MC_ZB_FI "
                + " WHERE zb_name IN('营业总收入','利润总额','现金净流量','存货--不扣除跌价','应收账款净额')\n"
                + "  AND calendar =( SELECT to_char(to_date(MAX(calendar),'yyyy-mm')-1 MONTH ,'yyyy-mm')\n"
                + "                  FROM sgmc.v_mc_zb_fi "
                + "                  WHERE company_code='"+companyCode+"') "
                + "  AND company_code='"+companyCode+"' "
                + " ORDER BY calendar DESC";


//        Object thisData = MyStringUtil.listFormatHumpName(jdbcTemplate.queryForList(thisDataSql,companyCode,companyCode));
//        Object previousData = MyStringUtil.listFormatHumpName(jdbcTemplate.queryForList(previousDataSql,companyCode,companyCode));

        if (xjjllSql!=null && xjjllPreviousSql!=null) {
            thisDataSql = "(" + thisDataSql + " ) union all ( " + xjjllSql + " ) ";
            previousDataSql = "(" + previousDataSql + " )  union all ( " + xjjllPreviousSql+ " ) ";
        }

        Object thisData = queryDataService.query(thisDataSql);
        Object previousData = queryDataService.query(previousDataSql);

        JSONObject obj = new JSONObject();
        obj.put("thisData",thisData);
        obj.put("previousData",previousData);

        return obj;
    }


    @Override
    public Object zzRcl() {
        String sql = "    SELECT calendar,zb_name,round(zb_value,2) as zb_value,unit_name\n"
                + "    FROM SGMC.MC_SA_ZB_PP_01\n"
                + "    where zb_name in('炼钢产量明细日指标','硅钢商品材日指标','炼铁产量明细日指标','酸洗商品材日指标','顺冷商品材日指标','热轧商品材日指标')\n"
                + "    and CALENDAR=to_char(current date -1,'yyyy-mm-dd')";

        String previousSql = "select calendar, '硅钢计划量日指标' as zb_name, sum(ZB_value) zb_value\n"
                + "from sgmc.MC_SA_ZB_PP_01\n"
                + "where ZB_NAME in ('一冷轧连退计划量日指标', '二冷轧连退计划量日指标', '二冷轧取向计划量日指标')\n"
                + "  and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "group by calendar\n"
                + "union all\n"
                + "select calendar, '冷轧计划量日指标' as zb_name, sum(ZB_value) zb_value\n"
                + "from sgmc.MC_SA_ZB_PP_01\n"
                + "where ZB_NAME in ('顺冷镀锌计划量日指标', '顺冷连退计划量日指标', '顺冷冷硬计划量日指标')\n"
                + "  and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "group by calendar\n"
                + "union all\n"
                + "select calendar, '炼钢计划量日指标' as zb_name, round(sum(ZB_value), 2) zb_value\n"
                + "from sgmc.MC_SA_ZB_PP_01\n"
                + "where ZB_NAME in ('一炼钢计划量日指标', '二炼钢计划量日指标')\n"
                + "  and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "group by calendar\n"
                + "union all\n"
                + "select calendar, '炼铁计划量日指标' as zb_name, zb_value\n"
                + "from sgmc.mc_sa_zb_pp_01\n"
                + "where zb_name = '炼铁高炉计划量日指标'\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "union all\n"
                + "select calendar, '热轧计划量日指标' as zb_name, sum(ZB_value) zb_value\n"
                + "from sgmc.MC_SA_ZB_PP_01\n"
                + "where ZB_NAME in ('一热轧计划量日指标', '二热轧计划量日指标')\n"
                + "  and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "group by calendar\n"
                + "union all\n"
                + "select calendar, '酸洗计划量日指标' as zb_name, zb_value\n"
                + "from sgmc.mc_sa_zb_pp_01\n"
                + "where zb_name like '%酸洗计划%'\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm-dd')";

        Object thisData = queryDataService.query(sql);
        Object previousData = queryDataService.query(previousSql);

        JSONObject obj = new JSONObject();
        obj.put("thisData",thisData);
        obj.put("previousData",previousData);

        return obj;

    }

    @Override
    public Object zzZbcpkc() {

        String sql = "    select calendar,zb_name,round(sum(zb_value),2) as zb_value from(\n"
                + "    select * from (\n"
                + "    select calendar,zb_name,round(zb_value/10000,2) zb_value from (\n"
                + "    select calendar,'在半产品库存' zb_name, sum(zb_value) zb_value from sgmc.MC_SA_ZB_PP_KC_H where ZB_NAME like '%_半成品_库存' group by calendar,'半成品库存'\n"
                + "    union all \n"
                + "    select calendar,'3月以上' zb_name,sum(zb_value) zb_value from sgmc.MC_SA_ZB_PP_KCCL_H where ZB_NAME like '%3月以上_库存超龄' group by calendar,'3月以上')\n"
                + "    union all \n"
                + "    select calendar,'在半产品库存'zb_name,sum(zb_value)/10000 as zb_value from (\n"
                + "    SELECT calendar,SUBSTR(ZB_NAME,'1',INSTR(ZB_NAME,'_')-1) as zb_name,zb_value,unit_name FROM SGMC.V_MC_ZB_PP_ZXKC \n"
                + "    where zb_name like '%智新一区%' and calendar <= current date-1) group by zb_name,calendar\n"
                + "    union all\n"
                + "    select calendar,'在半产品库存'zb_name,sum(zb_value)/10000 as zb_value from (\n"
                + "    SELECT calendar,SUBSTR(ZB_NAME,'1',INSTR(ZB_NAME,'_')-1) as zb_name,zb_value,unit_name FROM SGMC.V_MC_ZB_PP_ZXKC \n"
                + "    where zb_name like '%智新二区%' and calendar  <= current date-1) group by zb_name,calendar\n"
                + "    union all\n"
                + "    select calendar,'在半产品库存'zb_name,sum(zb_value)/10000 as zb_value from (\n"
                + "    SELECT calendar,SUBSTR(ZB_NAME,'1',INSTR(ZB_NAME,'_')-1) as zb_name,zb_value,unit_name FROM SGMC.V_MC_ZB_PP_ZXKC \n"
                + "    where zb_name like '%智新三区%' and calendar  <= current date-1) group by zb_name,calendar\n"
                + "    order by calendar) "
                + " where calendar=to_char(current date-1,'yyyy-mm-dd'))group by calendar,zb_name";
//                + " where calendar>=to_char(current date-30,'yyyy-mm-dd'))group by calendar,zb_name";
        return queryDataService.query(sql);
    }

    @Override
    public Object zzZbcpkcfltj() {

        String sql = "select calendar, zb_name, sum(zb_value) / 10000 as zb_value\n"
                + " from (select calendar,\n"
                + "             SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1) + 1, INSTR(ZB_NAME, '_', -1) - INSTR(ZB_NAME, '_') - 1) as zb_name,\n"
                + "             zb_value\n"
                + "      from sgmc.v_mc_zb_pp_kccl\n"
                + "      where calendar = to_char(current date - 1, 'yyyy-mm-dd'))\n"
                + " group by zb_name, calendar ";

        return queryDataService.query(sql);
    }

    @Override
    public Object zzGjzb() {
        String sql = "select calendar,zb_name,zb_value\n"
                + "from SGMC.MC_SA_ZB_PP_01 where zb_name like '%合同完成量日指标%'\n"
                + "                           and CALENDAR=to_char(current date -1,'yyyy-mm-dd')\n"
                + "union all\n"
                + "select calendar,zb_name,zb_value\n"
                + "from SGMC.MC_SA_ZB_PP_01 where zb_name like '%合同下达量日指标%'\n"
                + "                           and CALENDAR=to_char(current date -1,'yyyy-mm-dd')";
        return queryDataService.query(sql);
    }

    @Override
    public Object xsRfhl() {
        String sql = "select calendar,zb_name,(zb_value/10000) zb_value,etl_freq\n"
                + "from SGMC.MC_SA_ZB_SO_02\n"
                + "where calendar = to_char(current date-1,'yyyy-mm-dd')\n"
                + "  and zb_name = '产销_厂内_发货量'\n"
                + "  and etl_freq = '日'";
        return queryDataService.query(sql);
    }

    @Override
    public Object xsKc() {
        String sql = "select '厂内' zb_name, sum(sum_amt / 10000) zb_value\n"
                + "from sgso.JSC_KCJG_QG_CN\n"
                + "union all\n"
                + "select  '寄售库存国内' zb_name,sum(码单净重差值)/10000 zb_value\n"
                + "from sgso.JSC_KCJG_QG_JSGN\n"
                + "union all\n"
                + "select '寄售库存出口' zb_name, sum(码单净重差值) / 10000 zb_value\n"
                + "from sgso.JSC_KCJG_QG_JSCK";
        return queryDataService.query(sql);
    }

    @Override
    public Object xsCyddl() {
        String sql = "select 'QS_GG_CYJDLJH' as zb_code,\n"
                + "       '硅钢计划量'         as zb_name,\n"
                + "       (case\n"
                + "            when to_char(current date - 1, 'mm') = '01' then JAN\n"
                + "            when to_char(current date - 1, 'mm') = '02' then FEB\n"
                + "            when to_char(current date - 1, 'mm') = '03' then MAR\n"
                + "            when to_char(current date - 1, 'mm') = '04' then APR\n"
                + "            when to_char(current date - 1, 'mm') = '05' then MAY\n"
                + "            when to_char(current date - 1, 'mm') = '06' then JUN\n"
                + "            when to_char(current date - 1, 'mm') = '07' then JUL\n"
                + "            when to_char(current date - 1, 'mm') = '08' then AUG\n"
                + "            when to_char(current date - 1, 'mm') = '09' then SEP\n"
                + "            when to_char(current date - 1, 'mm') = '10' then OCT\n"
                + "            when to_char(current date - 1, 'mm') = '11' then NOV\n"
                + "            when to_char(current date - 1, 'mm') = '12' then DEC\n"
                + "           end)        as zb_value\n"
                + "from SGMC.MC_WH_ZB_ZXTB\n"
                + "where (\n"
                + "          case\n"
                + "              when calendar = to_char(current date - 1, 'dd') < 10 then calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "              else calendar = to_char(current date + 1 month, 'yyyy-mm') end)\n"
                + "union all\n"
                + "select zb_code,\n"
                + "       decode(zb_name, '总计_次月接单量', '迁顺_总计_次月接单量计划', '汽车板_次月接单量', '冷轧汽车板实际量', '汽车酸洗_次月接单量', '汽车酸洗实际量', '迁顺_汽车酸洗_次月接单量计划',\n"
                + "              '汽车酸洗计划量',\n"
                + "              '迁顺_冷轧汽车板_次月接单量计划', '冷轧汽车板计划量', '硅钢_次月接单量', '硅钢实际量', zb_name) zb_name,\n"
                + "       round(zb_value / 10000, 2) as                                        zb_value\n"
                + "from sgmc.MC_SA_ZB_SO_01\n"
                + "where zb_name in\n"
                + "      ('总计_次月接单量', '迁顺_总计_次月接单量计划', '汽车板_次月接单量', '汽车酸洗_次月接单量', '迁顺_汽车酸洗_次月接单量计划', '迁顺_冷轧汽车板_次月接单量计划', '硅钢_次月接单量')\n"
                + "  and (\n"
                + "    case\n"
                + "        when calendar = to_char(current date - 1, 'dd') < 10 then calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "        else calendar = to_char(current date + 1 month, 'yyyy-mm') end)";
        return queryDataService.query(sql);
    }

    @Override
    public Object xsYljxl() {
        String sql = "select calendar,zb_name,(zb_value/10000) zb_value,etl_freq\n"
                + "from SGMC.MC_SA_ZB_SO_02\n"
                + "where calendar = to_char(current date-1,'yyyy-mm')\n"
                + "  and zb_name in ('产销_累计销量','产销_冷轧汽车板_累计销量','产销_汽车酸洗_累计销量')\n"
                + "union all\n"
                + "select calendar, zb_name, (zb_value / 10000) zb_value, etl_freq\n"
                + "from SGMC.MC_SA_ZB_SO_02\n"
                + "where calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "  and zb_name in ('硅钢_取向_累计销量', '硅钢_无取向_累计销量')";
        return queryDataService.query(sql);
    }

    @Override
    public Object cgCgl() {
        String sql = "select calendar, zb_name, round((zb_value / 10000), 2) as zb_value\n"
                + "from (select calendar, '喷吹煤' as zb_name, sum(zb_value) as zb_value\n"
                + "      from SGMC.MC_SA_ZB_PO_01\n"
                + "      where zb_name in ('首钢迁钢_喷吹煤_总计_入库数量', '首钢冷轧_喷吹煤_总计_入库数量')\n"
                + "        and calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "      group by calendar\n"
                + "      union all\n"
                + "      select calendar, '合金' as zb_name, sum(zb_value) as zb_value\n"
                + "      from SGMC.MC_SA_ZB_PO_01\n"
                + "      where zb_name in ('首钢迁钢_合金_总计_入库数量', '首钢冷轧_合金_总计_入库数量')\n"
                + "        and calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "      group by calendar\n"
                + "      union all\n"
                + "      select calendar, '外购废钢' as zb_name, sum(zb_value) as zb_value\n"
                + "      from SGMC.MC_SA_ZB_PO_01\n"
                + "      where zb_name in ('首钢迁钢_外购废钢_总计_入库数量', '首钢冷轧_外购废钢_总计_入库数量')\n"
                + "        and calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "      group by calendar\n"
                + "      union all\n"
                + "      select calendar, '铁矿石' as zb_name, sum(zb_value) as zb_value\n"
                + "      from SGMC.MC_SA_ZB_PO_02\n"
                + "      where zb_name in ('矿_合计_到达量')\n"
                + "        and calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "      group by calendar\n"
                + "      union all\n"
                + "      select calendar, '焦炭' as zb_name, round(sum(zb_value), 2) as zb_value\n"
                + "      from (\n"
                + "               select left(calendar, 7) calendar, zb_name, zb_value\n"
                + "               from sgmc.mc_sa_zb_po_01\n"
                + "               where left(calendar, 7) = to_char(current date - 1, 'yyyy-mm')\n"
                + "                 and zb_name in ('首钢迁钢_迁焦焦炭_(原燃料)入库', '首钢迁钢_外购焦炭_(原燃料)入库'))\n"
                + "      group by calendar)\n"
                + "order by zb_value";
        return queryDataService.query(sql);
    }

    @Override
    public Object cgKc() {
        String sql = "select '铁矿石' as wlmc, calendar as month, zb_value / 10000 as qmkcs\n"
                + "from SGMC.MC_SA_ZB_PO_02\n"
                + "where zb_name = '矿_合计_库存量'\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "union all\n"
                + "select '喷吹煤' as wlmc, month, sum(qmkcs) / 10000 qmkcs\n"
                + "from (\n"
                + "         SELECT company_code, month, wlmc, sum(qmkcs) qmkcs\n"
                + "         FROM SGIM.IM_SR_GLJSC\n"
                + "         where company_code in ('1A10', '1A20')\n"
                + "           and MONTH = to_char(current date - 1, 'yyyymmdd')\n"
                + "         group by wlmc, company_code, month\n"
                + "     )\n"
                + "where wlmc in ('潞安煤', '阳泉煤', '神华煤', '其他喷吹煤')\n"
                + "group by month\n"
                + "union all\n"
                + "select '焦炭' as wlmc, month, sum(qmkcs) / 10000 qmkcs\n"
                + "from (\n"
                + "         SELECT company_code, month, wlmc, sum(qmkcs) qmkcs\n"
                + "         FROM SGIM.IM_SR_GLJSC\n"
                + "         where company_code in ('1A10', '1A20')\n"
                + "           and MONTH = to_char(current date - 1, 'yyyymmdd')\n"
                + "         group by wlmc, company_code, month\n"
                + "     )\n"
                + "where wlmc in ('迁焦焦炭', '外购焦炭')\n"
                + "group by month\n"
                + "union all\n"
                + "select '外购废钢' as wlmc, month, sum(qmkcs) / 10000 qmkcs\n"
                + "from (\n"
                + "         SELECT company_code, month, wlmc, sum(qmkcs) qmkcs\n"
                + "         FROM SGIM.IM_SR_GLJSC\n"
                + "         where company_code in ('1A10', '1A20')\n"
                + "           and MONTH = to_char(current date - 1, 'yyyymmdd')\n"
                + "         group by wlmc, company_code, month\n"
                + "     )\n"
                + "where wlmc in ('外购普通废钢', '外购低硫废钢', '生铁')\n"
                + "group by month\n"
                + "union all\n"
                + "select '合金' as wlmc, month, sum(qmkcs) / 10000 qmkcs\n"
                + "from (\n"
                + "         SELECT company_code, month, wlmc, sum(qmkcs) qmkcs\n"
                + "         FROM SGIM.IM_SR_GLJSC\n"
                + "         where company_code in ('1A10', '1A20')\n"
                + "           and MONTH = to_char(current date - 1, 'yyyymmdd')\n"
                + "         group by wlmc, company_code, month\n"
                + "     )\n"
                + "where wlmc in ('有色合金', '复合合金', '普通合金', '特种合金')\n"
                + "group by month";

        return queryDataService.query(sql);
    }

}
