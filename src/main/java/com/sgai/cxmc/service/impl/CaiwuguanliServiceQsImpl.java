package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.CaiwuguanliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/9/26 13:30
 * @Version 1.0
 */

@Service("caiwuguanliServiceQs")
public class CaiwuguanliServiceQsImpl implements CaiwuguanliService {
    @Autowired
    QueryDataServiceImpl queryDataService;


    @Override
    public Object getZyzbYysr(String companyName) {
        String sql = "select  calendar,\n"
                + "       company_code,\n"
                + "       company_name,\n"
                + "       zblb,\n"
                + "       zb_name,\n"
                + "       round(sumyear, 2)  sumyear,\n"
                + "       unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE = '"+companyName+"'\n"
                + "and ZB_NAME='营业总收入'\n"
                + "order by calendar desc\n"
                + "fetch first 1 rows only";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbLr(String companyName) {
        String sql = "select  calendar,\n"
                + "        company_code,\n"
                + "        company_name,\n"
                + "        zblb,\n"
                + "        zb_name,\n"
                + "        round(sumyear, 2)  sumyear,\n"
                + "        unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE = '"+companyName+"'\n"
                + "  and ZB_NAME='利润总额'\n"
                + "order by calendar desc\n"
                + "fetch first 1 rows only";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbYlnl(String indexName,String companyName) {
        String sql = "select calendar,\n"
                + "       company_code,\n"
                + "       company_name,\n"
                + "       zblb,\n"
                + "       zb_name,\n"
                + "       round(curvalue, 2) curvalue,\n"
                + "       round(sumyear, 2)  sumyear,\n"
                + "       unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE = '"+companyName+"'\n"
                + "  and ZB_NAME = '"+indexName+"'\n"
                + "\n"
                + "order by zb_name, calendar desc\n"
                + "fetch first 1 rows only";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbYqst(String indexName,String companyName) {
        String sql = "select calendar,company_code,company_name,zblb,zb_name,round(curvalue,2) curvalue,round(sumyear,2) sumyear,unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE='"+companyName+"' and ZB_NAME='"+indexName+"'\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbHbsy(String indexName,String companyName) {
        String sql = "(select calendar,company_code,company_name,zblb,zb_name,round(curvalue,2) curvalue,unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE='"+companyName+"'\n"
                + "  and calendar = (select max(CALENDAR) calendar\n"
                + "    from sgmc.V_MC_ZB_FI\n"
                + "    where company_code = '"+companyName+"')\n"
                + "and ZB_NAME='"+indexName+"'\n"
                + "order by calendar)\n"
                + "union all\n"
                + "(select calendar,company_code,company_name,zblb,zb_name,round(curvalue,2) curvalue,unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE='"+companyName+"'\n"
                + "  and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 1 month, 'yyyy-mm') calendar\n"
                + "                  from sgmc.V_MC_ZB_FI\n"
                + "                  where company_code = '"+companyName+"')\n"
                + "  and ZB_NAME='"+indexName+"'\n"
                + "order by calendar)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbYdtb(String indexName,String companyName) {
        String sql = "(select calendar,company_code,company_name,zblb,zb_name,round(curvalue,2) curvalue,unit_name\n"
                + " from SGMC.V_MC_ZB_FI\n"
                + " where COMPANY_CODE='"+companyName+"'\n"
                + "   and calendar = (select max(CALENDAR) calendar\n"
                + "                   from sgmc.V_MC_ZB_FI\n"
                + "                   where company_code = '"+companyName+"')\n"
                + "   and ZB_NAME='"+indexName+"'\n"
                + " order by calendar)\n"
                + " union all\n"
                + "(select calendar, company_code, company_name, zblb, zb_name, nvl(round(curvalue, 2),0) curvalue, unit_name\n"
                + " from SGMC.V_MC_ZB_FI\n"
                + " where COMPANY_CODE = '"+companyName+"'\n"
                + "   and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 13 month, 'yyyy-mm') calendar\n"
                + "                   from sgmc.V_MC_ZB_FI\n"
                + "                   where company_code = '"+companyName+"')\n"
                + "   and ZB_NAME = '"+indexName+"'\n"
                + " order by calendar)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZcxlYsj(String indexName,String companyName) {
        String sql = "select calendar,\n"
                + "       company_code,\n"
                + "       company_name,\n"
                + "       zblb,\n"
                + "       zb_name,\n"
                + "       round(curvalue, 2) curvalue,\n"
                + "       unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE = '"+companyName+"'\n"
                + "  and ZB_NAME = '"+indexName+"'\n"
                + "order by zb_name, calendar desc\n"
                + "fetch first 1 rows only";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbZcxlHbsy(String indexName,String companyName) {
        String sql = "(select calendar, company_code, company_name, zblb, zb_name, round(curvalue, 2) curvalue, unit_name from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE='"+companyName+"'\n"
                + "  and calendar = (select max(CALENDAR) calendar\n"
                + "    from sgmc.V_MC_ZB_FI\n"
                + "    where company_code = '"+companyName+"')\n"
                + "and ZB_NAME='"+indexName+"'\n"
                + "order by calendar) union all\n"
                + "(select calendar,company_code,company_name,zblb,zb_name,round(curvalue,2) curvalue,unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE='"+companyName+"'\n"
                + "  and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 1 month, 'yyyy-mm') calendar\n"
                + "                  from sgmc.V_MC_ZB_FI\n"
                + "                  where company_code = '"+companyName+"')\n"
                + "  and ZB_NAME='"+indexName+"'\n"
                + "order by calendar)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbZcxlYdtb(String indexName,String companyName) {
        String sql = "            (select calendar,company_code,company_name,zblb,zb_name,round(curvalue,2) curvalue,unit_name\n"
                + "            from SGMC.V_MC_ZB_FI\n"
                + " where COMPANY_CODE='"+companyName+"'\n"
                + "   and calendar = (select max(CALENDAR) calendar\n"
                + "                   from sgmc.V_MC_ZB_FI\n"
                + "                   where company_code = '"+companyName+"')\n"
                + "   and ZB_NAME='"+indexName+"'\n"
                + " order by calendar)\n"
                + "    union all\n"
                + "(select calendar, company_code, company_name, zblb, zb_name, nvl(round(curvalue, 2),0) curvalue, unit_name\n"
                + " from SGMC.V_MC_ZB_FI\n"
                + " where COMPANY_CODE = '"+companyName+"'\n"
                + "   and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 13 month, 'yyyy-mm') calendar\n"
                + "                   from sgmc.V_MC_ZB_FI\n"
                + "                   where company_code = '"+companyName+"')\n"
                + "   and ZB_NAME = '"+indexName+"'\n"
                + " order by calendar)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbZcxlYqst(String indexName,String companyName) {
        String sql = "select calendar,\n"
                + "       company_code,\n"
                + "       company_name,\n"
                + "       zblb,\n"
                + "       zb_name,\n"
                + "       round(curvalue, 2) curvalue,\n"
                + "       unit_name\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where COMPANY_CODE = '"+companyName+"'\n"
                + "  and ZB_NAME = '"+indexName+"'\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getZyzbZjglYlnl(String indexName,String companyName) {
        if (companyName.equals ( "1A10" )){
            String companyNameC = "迁钢";
            String sql = "select '月实际' as fl, zb_name, zb_value\n"
                    + "from (\n"
                    + "         SELECT calendar, company_name, zb_name, zb_value / 10000 zb_value\n"
                    + "         FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + "         where company_name = '"+companyNameC+"'\n"
                    + "           and calendar = (select max(CALENDAR) calendar\n"
                    + "                           from sgmc.V_MC_ZB_FI\n"
                    + "                           where company_code = '"+companyName+"')\n"
                    + "           and ZB_NAME = '"+indexName+"'\n"
                    + "         order by calendar\n"
                    + "     )\n"
                    + "union all\n"
                    + "(SELECT '年累计' as fl, zb_name, sum(zb_value) / 10000 zb_value\n"
                    + " FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + " WHERE company_name = '"+companyNameC+"'\n"
                    + "   AND calendar >= TO_CHAR(CURRENT DATE - 1, 'yyyy')\n"
                    + "   and ZB_NAME = '"+indexName+"'\n"
                    + " GROUP BY zb_name)";
            return queryDataService.query ( sql);
        }else {
            String sql = "select '月实际' as fl, zb_name, zb_value\n"
                    + "from (\n"
                    + "         SELECT calendar, company_name, zb_name, zb_value / 10000 zb_value\n"
                    + "         FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + "         where company_name = '顺义'\n"
                    + "           and calendar = (select max(CALENDAR) calendar\n"
                    + "                           from sgmc.V_MC_ZB_FI\n"
                    + "                           where company_code = '" + companyName + "')\n"
                    + "           and ZB_NAME = '" + indexName + "'\n"
                    + "         order by calendar\n"
                    + "     )\n"
                    + "union all\n"
                    + "(SELECT '年累计' as fl, zb_name, sum(zb_value) / 10000 zb_value\n"
                    + " FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                    + " WHERE company_name = '顺义'\n"
                    + "   AND calendar >= TO_CHAR(CURRENT DATE - 1, 'yyyy')\n"
                    + "   and ZB_NAME = '" + indexName + "'\n"
                    + " GROUP BY zb_name)";
            return queryDataService.query ( sql );
        }
    }

    @Override
    public Object getZyzbZjglYqst(String indexName,String companyName) {
        String sql = "SELECT calendar, company_name, zb_name, zb_value / 10000 zb_value\n"
                + "FROM SGMC.V_MC_ZB_FI_ZYZB\n"
                + "where company_name = '"+companyName+"'\n"
                + "  and ZB_NAME = '"+indexName+"'\n"
                + "  and calendar >= to_char(current date - 6 month, 'yyyy-mm')\n"
                + "  and calendar < to_char(current date, 'yyyy-mm')\n";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getCbgcYdcbf(String areaName) {
        String sql = "select *\n"
                + "from ((select CALENDAR, COMPANY_CODE, ZB_NAME, ZB_VALUE\n"
                + "    from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%'\n"
                + "    and calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%' and zb_value <>0) and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%' and zb_value <>0)\n"
                + "    order by CALENDAR )\n"
                + "    union all\n"
                + "    select calendar, company_code, decode(zb_name, '炼铁单成', '炼铁成本费', zb_name) as zb_name, zb_value from sgmc.v_mc_zb_ac_gxcb where zb_name like '%炼铁单成%'\n"
                + "    and calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.v_mc_zb_ac_gxcb where zb_name like '%炼铁单成%')\n"
                + "    and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB where zb_name like '%炼铁单成%' and zb_value <>0)\n"
                + "    union all\n"
                + "    (select CALENDAR, COMPANY_CODE, decode(zb_name, '冷硬加工费', '顺义冷轧加工费', zb_name) zb_name, sum(ZB_VALUE) as zb_value\n"
                + "    from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A20' and\n"
                + "    calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.v_mc_zb_ac_gxcb where ZB_NAME like '%加工费%' and company_code ='1A20') and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A20' and zb_value <> 0) group by zb_name, calendar, company_code order by CALENDAR ))\n"
                + "where zb_name like '"+areaName+"%费'\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getCbgcZxgsYdcbf(String tableName) {
        String sql = "select *\n"
                + "from ((select CALENDAR, COMPANY_CODE, ZB_NAME, ZB_VALUE\n"
                + "    from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%'\n"
                + "    and calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%' and zb_value <>0) and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%' and zb_value <>0)\n"
                + "    order by CALENDAR )\n"
                + "    union all\n"
                + "    select calendar, company_code, decode(zb_name, '炼铁单成', '炼铁成本费', zb_name) as zb_name, zb_value from sgmc.v_mc_zb_ac_gxcb where zb_name like '%炼铁单成%'\n"
                + "    and calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.v_mc_zb_ac_gxcb where zb_name like '%炼铁单成%')\n"
                + "    and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB where zb_name like '%炼铁单成%' and zb_value <>0)\n"
                + "    union all\n"
                + "    (select CALENDAR, COMPANY_CODE, decode(zb_name, '冷硬加工费', '顺义冷轧加工费', zb_name) zb_name, sum(ZB_VALUE) as zb_value\n"
                + "    from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A20' and\n"
                + "    calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.v_mc_zb_ac_gxcb where ZB_NAME like '%加工费%' and company_code ='1A20') and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A20' and zb_value <> 0) group by zb_name, calendar, company_code order by CALENDAR ))\n"
                + "where zb_name like '"+tableName+"%费'\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getCbgcSylzYdcbf(String tableName) {
        String sql = "select *\n"
                + "from ((select CALENDAR, COMPANY_CODE, ZB_NAME, ZB_VALUE\n"
                + "    from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%'\n"
                + "    and calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%' and zb_value <>0) and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%' and zb_value <>0)\n"
                + "    order by CALENDAR )\n"
                + "    union all\n"
                + "    select calendar, company_code, decode(zb_name, '炼铁单成', '炼铁成本费', zb_name) as zb_name, zb_value from sgmc.v_mc_zb_ac_gxcb where zb_name like '%炼铁单成%'\n"
                + "    and calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.v_mc_zb_ac_gxcb where zb_name like '%炼铁单成%')\n"
                + "    and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB where zb_name like '%炼铁单成%' and zb_value <>0)\n"
                + "    union all\n"
                + "    (select CALENDAR, COMPANY_CODE, decode(zb_name, '冷硬加工费', '顺义冷轧加工费', zb_name) zb_name, sum(ZB_VALUE) as zb_value\n"
                + "    from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A20' and\n"
                + "    calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.v_mc_zb_ac_gxcb where ZB_NAME like '%加工费%' and company_code ='1A20') and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A20' and zb_value <> 0) group by zb_name, calendar, company_code order by CALENDAR ))\n"
                + "where zb_name like '"+tableName+"%费'\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getCbgcJgfgc(String areaName) {
        String sql = "select *\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                decode(zb_namea, '钢坯综合', '炼钢', '铁综合', '炼铁', '热卷综合', '热轧', '热轧酸洗卷', '酸洗', '球团综合', '球团', '烧结综合', '烧结',\n"
                + "                       '无取向硅钢', '无取向', '取向硅钢', '取向', '冷轧冷硬', '顺义冷轧', '冷轧连退', '连退', '冷轧镀锌合计', '镀锌',\n"
                + "                       zb_namea) as zb_namea,\n"
                + "                zb_nameb,\n"
                + "                zb_value\n"
                + "         from (\n"
                + "                  SELECT calendar,\n"
                + "                         SUBSTR(ZB_NAME, 1, INSTR(ZB_NAME, '-', 1, 1) - 1) as zb_namea,\n"
                + "                         SUBSTR(ZB_NAME, INSTR(ZB_NAME, '-', -1, 1) + 1)   as zb_nameb,\n"
                + "                         zb_value\n"
                + "                  from SGMC.V_MC_ZB_AC_JGFGC\n"
                + "                  where company_code in ('1A10', '1A20')\n"
                + "                    and CALENDAR = (SELECT MAX(CALENDAR) from SGMC.V_MC_ZB_AC_JGFGC where zb_value <> 0)\n"
                + "                    and zb_name <> '原料冷硬卷')\n"
                + "     )\n"
                + "where zb_namea = '"+areaName+"'";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getCbgcZxgsJgfgc(String tableName) {
        String sql = "select *\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                decode(zb_namea, '钢坯综合', '炼钢', '铁综合', '炼铁', '热卷综合', '热轧', '热轧酸洗卷', '酸洗', '球团综合', '球团', '烧结综合', '烧结',\n"
                + "                       '无取向硅钢', '无取向', '取向硅钢', '取向', '冷轧冷硬', '顺义冷轧', '冷轧连退', '连退', '冷轧镀锌合计', '镀锌',\n"
                + "                       zb_namea) as zb_namea,\n"
                + "                zb_nameb,\n"
                + "                zb_value\n"
                + "         from (\n"
                + "                  SELECT calendar,\n"
                + "                         SUBSTR(ZB_NAME, 1, INSTR(ZB_NAME, '-', 1, 1) - 1) as zb_namea,\n"
                + "                         SUBSTR(ZB_NAME, INSTR(ZB_NAME, '-', -1, 1) + 1)   as zb_nameb,\n"
                + "                         zb_value\n"
                + "                  from SGMC.V_MC_ZB_AC_JGFGC\n"
                + "                  where company_code in ('1A10', '1A20')\n"
                + "                    and CALENDAR = (SELECT MAX(CALENDAR) from SGMC.V_MC_ZB_AC_JGFGC where zb_value <> 0)\n"
                + "                    and zb_name <> '原料冷硬卷')\n"
                + "     )\n"
                + "where zb_namea = '"+tableName+"'";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getCbgcJgfgcMidval(String areaName) {
        String sql = " select *\n"
                + "from ((select CALENDAR, COMPANY_CODE, ZB_NAME, ZB_VALUE\n"
                + "    from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%'\n"
                + "    and calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%' and zb_value <>0) and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A10' and zb_name not like '%炼铁%' and zb_value <>0)\n"
                + "    order by CALENDAR )\n"
                + "    union all\n"
                + "    select calendar, company_code, decode(zb_name, '炼铁单成', '炼铁成本费', zb_name) as zb_name, zb_value from sgmc.v_mc_zb_ac_gxcb where zb_name like '%炼铁单成%'\n"
                + "    and calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.v_mc_zb_ac_gxcb where zb_name like '%炼铁单成%')\n"
                + "    and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB where zb_name like '%炼铁单成%' and zb_value <>0)\n"
                + "    union all\n"
                + "    (select CALENDAR, COMPANY_CODE, decode(zb_name, '冷硬加工费', '顺义冷轧加工费', zb_name) zb_name, sum(ZB_VALUE) as zb_value\n"
                + "    from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A20' and\n"
                + "    calendar > (select to_char(to_date(max (CALENDAR), 'yyyy-mm')-6 month, 'yyyy-mm') from sgmc.v_mc_zb_ac_gxcb where ZB_NAME like '%加工费%' and company_code ='1A20') and calendar <= (select (max (calendar)) from sgmc.V_MC_ZB_AC_GXCB\n"
                + "    where ZB_NAME like '%加工费%' and company_code ='1A20' and zb_value <> 0) group by zb_name, calendar, company_code order by CALENDAR ))\n"
                + "where zb_name like '"+areaName+"%费'\n"
                + "order by calendar desc limit 1";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getcwglGxcbTqcbglxsl( ) {
        String sql = "select CALENDAR, zb_name, sum(zb_value) zb_value\n"
                + "from (select calendar, zb_name, round(zb_value, 2) as zb_value\n"
                + "      from sgmc.V_MC_ZB_AC_GXCB\n"
                + "      where company_code in ('1A10', '1A20')\n"
                + "        and calendar = (select max(calendar)\n"
                + "                        from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                        where zb_name not like '%加工费%'\n"
                + "                          and ZB_NAME not like '%单成%'\n"
                + "                          and zb_value <> 0))\n"
                + "where  zb_name not like '%加工费%'\n"
                + "  and ZB_NAME not like '%单成%'\n"
                + "group by zb_name, calendar\n"
                + "union all\n"
                + "select calendar, zb_name, round(zb_value, 2) zb_value\n"
                + "from sgmc.V_MC_ZB_AC_GXCB\n"
                + "where zb_name = '炼铁单成'\n"
                + "  and company_code in ('1A10', '1A20')\n"
                + "  and calendar = (select max(calendar)\n"
                + "                  from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                  where zb_name not like '%加工费%'\n"
                + "                    and ZB_NAME not like '%单成%'\n"
                + "                    and zb_value <> 0)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getcwglGxcbTqcbhbsy( ) {
        String sql = "select calendar, zb_name, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         (SELECT '环比'                                                                                        CALENDAR,\n"
                + "                 decode(A.ZB_NAME, null, b.zb_name, a.ZB_NAME)                                               zb_name,\n"
                + "\n"
                + "                 ROUND(decode(a.ZB_VALUE, null, 0, a.ZB_VALUE) - decode(b.ZB_VALUE, null, 0, b.ZB_VALUE), 2) ZB_VALUE\n"
                + "          FROM (SELECT *\n"
                + "                FROM SGMC.V_MC_ZB_AC_GXCB\n"
                + "                WHERE ZB_NAME NOT LIKE '%单成%'\n"
                + "                  AND ZB_NAME NOT LIKE '%加工费%'\n"
                + "                  AND COMPANY_CODE IN ('1A10',\n"
                + "                                       '1A20')\n"
                + "                  AND CALENDAR = (select max(calendar)\n"
                + "                                  from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                                  where ZB_NAME NOT LIKE '%单成%'\n"
                + "                                    AND ZB_NAME NOT LIKE '%加工费%'\n"
                + "                                    AND COMPANY_CODE IN ('1A10',\n"
                + "                                                         '1A20')\n"
                + "                                    and zb_value <> 0)) A\n"
                + "                   full join\n"
                + "               (SELECT *\n"
                + "                FROM SGMC.V_MC_ZB_AC_GXCB\n"
                + "                WHERE ZB_NAME NOT LIKE '%单成%'\n"
                + "                  AND ZB_NAME NOT LIKE '%加工费%'\n"
                + "                  AND COMPANY_CODE IN ('1A10',\n"
                + "                                       '1A20')\n"
                + "                  AND CALENDAR = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 1 month, 'yyyy-mm')\n"
                + "                                  from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                                  where ZB_NAME NOT LIKE '%单成%'\n"
                + "                                    AND ZB_NAME NOT LIKE '%加工费%'\n"
                + "                                    AND COMPANY_CODE IN ('1A10',\n"
                + "                                                         '1A20')\n"
                + "                                    and zb_value <> 0)) B\n"
                + "               on\n"
                + "                   A.ZB_NAME = B.ZB_NAME\n"
                + "         )\n"
                + "         union all\n"
                + "         (select '环比' calendar, a.zb_name, round(a.zb_value - b.zb_value, 2) zb_value\n"
                + "          from (\n"
                + "                   select *\n"
                + "                   from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                   where zb_name = '炼铁单成'\n"
                + "                     and company_code in ('1A10', '1A20')\n"
                + "                     and calendar = (select max(calendar)\n"
                + "                                     from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                                     where zb_name = '炼铁单成'\n"
                + "                                       and company_code in ('1A10', '1A20')\n"
                + "                                       and zb_value <> 0)) a,\n"
                + "               (select *\n"
                + "                from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                where zb_name = '炼铁单成'\n"
                + "                  and company_code in ('1A10', '1A20')\n"
                + "                  and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 1 month, 'yyyy-mm')\n"
                + "                                  from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                                  where zb_name = '炼铁单成'\n"
                + "                                    and company_code in ('1A10', '1A20'))) b\n"
                + "          where a.zb_name = b.zb_name))\n"
                + "group by zb_name, calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getcwglGxcbTqcbCbydqst(String typeName) {
        String sql = "SELECT CALENDAR,\n"
                + "       decode(ZB_NAME, '炼铁单成', '铁水', '外购废钢', '废钢', zb_name) zb_name,\n"
                + "       ROUND(ZB_VALUE, 2) AS                                ZB_VALUE\n"
                + "FROM SGMC.V_MC_ZB_AC_GXCB\n"
                + "WHERE ZB_NAME NOT LIKE '%加工费%'\n"
                + "  AND ZB_NAME LIKE '%"+typeName+"%'\n"
                + "  AND COMPANY_CODE IN ('1A10',\n"
                + "                       '1A20')\n"
                + "  AND CALENDAR >= TO_CHAR(CURRENT DATE - 12 MONTH,\n"
                + "                          'yyyy-mm')\n"
                + "  AND CALENDAR <= (select max(calendar)\n"
                + "                   FROM SGMC.V_MC_ZB_AC_GXCB\n"
                + "                   WHERE ZB_NAME NOT LIKE '%加工费%'\n"
                + "                     AND ZB_NAME LIKE '%"+typeName+"%'\n"
                + "                     AND COMPANY_CODE IN ('1A10',\n"
                + "                                          '1A20'))\n"
                + "ORDER BY CALENDAR";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getcwglGxcbGhcbglxsl( ) {
        String sql = "SELECT\n"
                + " CALENDAR,\n"
                + " ZB_NAME,\n"
                + " ROUND(ZB_VALUE,2) AS ZB_VALUE\n"
                + "FROM\n"
                + " SGMC.V_MC_ZB_AC_GXCB\n"
                + "WHERE\n"
                + " ZB_NAME LIKE '%单成%' AND\n"
                + " COMPANY_CODE IN( '1A10',\n"
                + " '1A20') AND\n"
                + " CALENDAR=(SELECT MAX(CALENDAR)\n"
                + "FROM\n"
                + " SGMC.V_MC_ZB_AC_GXCB\n"
                + "WHERE\n"
                + " ZB_NAME LIKE '%单成%' and company_code in( '1A10','1A20') and ZB_VALUE <>0)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getcwglGxcbGhcbHbsy( ) {
        String sql = "select '环比'                                                                                        calendar,\n"
                + "       decode(a.zb_name, null, b.zb_name, a.zb_name)                                               zb_name,\n"
                + "       round(decode(a.zb_value, null, 0, a.zb_value) - decode(b.zb_value, null, 0, b.zb_value), 2) zb_value\n"
                + "from (\n"
                + "         select *\n"
                + "         from sgmc.V_MC_ZB_AC_GXCB\n"
                + "         where zb_name like '%单成%'\n"
                + "           and company_code in ('1A10', '1A20')\n"
                + "           and calendar = (select max(calendar)\n"
                + "                           from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                           where zb_name like '%单成%'\n"
                + "                             and company_code in ('1A10', '1A20')\n"
                + "                             and zb_value <> 0)) a\n"
                + "         full join\n"
                + "     (select *\n"
                + "      from sgmc.V_MC_ZB_AC_GXCB\n"
                + "      where zb_name like '%单成%'\n"
                + "        and company_code in ('1A10', '1A20')\n"
                + "        and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 1 month, 'yyyy-mm')\n"
                + "                        from sgmc.V_MC_ZB_AC_GXCB\n"
                + "                        where zb_name like '%单成%'\n"
                + "                          and company_code in ('1A10', '1A20')\n"
                + "                          and zb_value <> 0)) b\n"
                + "     on a.zb_name = b.zb_name";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getcwglGxcbGhcbCbydqst(String typeName) {
        String sql = "SELECT CALENDAR,\n"
                + "       decode(ZB_NAME, '炼钢单成', '板坯', '热轧单成', '热卷', '酸洗单成', '酸洗', '连退单成', '连退', '镀锌单成', '镀锌', '无取向单成', '无取向', '取向单成',\n"
                + "              '取向', zb_name) as zb_name,\n"
                + "       ROUND(ZB_VALUE, 2)    AS ZB_VALUE\n"
                + "FROM SGMC.V_MC_ZB_AC_GXCB\n"
                + "WHERE ZB_NAME LIKE '"+typeName+"'\n"
                + "  AND COMPANY_CODE IN ('1A10',\n"
                + "                       '1A20')\n"
                + "  AND CALENDAR >= TO_CHAR(CURRENT DATE - 12 MONTH,\n"
                + "                          'yyyy-mm')\n"
                + "  AND CALENDAR <= (SELECT MAX(CALENDAR)\n"
                + "                   FROM SGMC.V_MC_ZB_AC_GXCB\n"
                + "                   WHERE ZB_NAME LIKE '%单成'\n"
                + "                     and company_code in ('1A10', '1A20')\n"
                + "                     and ZB_VALUE <> 0)\n"
                + "ORDER BY CALENDAR";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getcwglCpylnlMllrzyzb(String companyName) {
        if (companyName.equals ( "迁顺" )){
            String qsSql = "select t.MAOLI as maoli, t.MAOLIlV*1000000 as maolilv, t.BIANJILIRUN as bjlr\n"
                    + "from SGMC.MC_SA_ZB_AC_CPYLNL_ZH t\n"
                    + "where prod_name = '总计'\n"
                    + "  and calendar = to_char(current date - 1 month, 'yyyy-mm')";
            return queryDataService.query ( qsSql );
        }else if (companyName.equals ( "迁钢" )){
            String qgSql = "select CALENDAR, 销售毛利 as maoli,毛利率 as maolilv,边际利润 as bjlr\n"
                    + "from sgmc.MC_SA_ZB_AC_CPYLNL t\n"
                    + "where t.COMPANY_CODE = '1A10'\n"
                    + "  and prod_code = '总计'";
            return queryDataService.query ( qgSql );
        }else {
            String sySql = "select CALENDAR, 销售毛利 as maoli,毛利率 as maolilv,边际利润 as bjlr\n"
                    + "from sgmc.MC_SA_ZB_AC_CPYLNL t\n"
                    + "where t.COMPANY_CODE = '1A20'\n"
                    + "  and prod_code = '总计'";
            return queryDataService.query ( sySql );
        }
    }

    @Override
    public Object getcwglCpylnlXsmlqst(String companyName) {
        if (companyName.equals ( "迁顺" )){
            String qsSql = "SELECT\n"
                    + "    RIGHT(prod_name,LENGTH(prod_name)-3) prod_name,t.MAOLI maoli,t.XIAOLIANG zhongliang\n"
                    + "FROM\n"
                    + "    SGMC.MC_SA_ZB_AC_CPYLNL_ZH t\n"
                    + "where "
                    + "      prod_name <>''\n"
                    + "      and\n"
                    + "t.prod_name <> '总计'";
            return queryDataService.query ( qsSql );
        }else if (companyName.equals ( "迁钢" )){
            String qgSql = "SELECT\n"
                    + "    RIGHT( prod_name,LENGTH(prod_name)-3 ) prod_name,t.销售毛利 maoli,t.重量 zhongliang\n"
                    + "FROM\n"
                    + "    sgmc.MC_SA_ZB_AC_CPYLNL t\n"
                    + "WHERE\n"
                    + "      prod_name <>''\n"
                    + "      and\n"
                    + "        t.COMPANY_CODE='1A10'\n"
                    + "  AND prod_code<>'总计'";
            return queryDataService.query ( qgSql );
        }else {
            String sySql = "SELECT\n"
                    + "    RIGHT( prod_name,LENGTH(prod_name)-3 ) prod_name,t.销售毛利 maoli,t.重量 zhongliang\n"
                    + "FROM\n"
                    + "    sgmc.MC_SA_ZB_AC_CPYLNL t\n"
                    + "WHERE\n"
                    + "      prod_name <>''\n"
                    + "      and\n"
                    + "        t.COMPANY_CODE='1A20'\n"
                    + "  AND prod_code<>'总计'";
            return queryDataService.query ( sySql );
        }
    }

    @Override
    public Object getcwglCpylnlBjlrqst(String companyName) {
        if (companyName.equals ( "迁顺" )){
            String qsSql = "select *\n"
                    + "from (\n"
                    + "         select right(prod_name, length(prod_name) - 3) prod_name, t.BIANJILIRUN bjlr, t.XIAOLIANG zhongliang\n"
                    + "         from SGMC.MC_SA_ZB_AC_CPYLNL_ZH t where prod_name <> ''\n"
                    + "     )\n"
                    + "where prod_name <> ''";
            return queryDataService.query ( qsSql );
        }else if (companyName.equals ( "迁钢" )){
            String qgSql = "select right(prod_name, length(prod_name) - 3) prod_name, t.边际利润 bjlr, t.重量 zhongliang\n"
                    + "from sgmc.MC_SA_ZB_AC_CPYLNL t\n"
                    + "where t.COMPANY_CODE = '1A10'\n"
                    + " AND prod_name <> ''\n"
                    + "  and prod_code <> '总计'";
            return queryDataService.query ( qgSql );
        }else {
            String sySql = "select right(prod_name, length(prod_name) - 3) prod_name, t.边际利润 bjlr, t.重量 zhongliang\n"
                    + "from sgmc.MC_SA_ZB_AC_CPYLNL t\n"
                    + "where t.COMPANY_CODE = '1A20'\n"
                    + " AND prod_name <> ''\n"
                    + "  and prod_code <> '总计'";
           return queryDataService.query ( sySql );
        }
    }
}
