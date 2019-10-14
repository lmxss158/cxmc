package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.QueryDataService;
import com.sgai.cxmc.service.XiaoshouService;
import com.sgai.cxmc.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/8/11 10:28
 * @Version 1.0
 */

@Service("xiaoshouServiceQs")
public class XiaoshouServiceQsImpl implements XiaoshouService {

    @Autowired
    QueryDataService queryDataService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Object ddzxDdl(String typeName) {

        String sql = "select calendar,\n"
                + "       decode(zb_name, '结转" + typeName + "', '结转', '次月" + typeName + "', '次月', '当月" + typeName + "', '当月', zb_name) zb_name,\n"
                + "       cast(round(zb_value / 10000 * 10 / 10, 2) as decimal(20, 2)) as          zb_value\n"
                + "from SGMC.MC_SA_ZB_so_01\n"
                + "where zb_name in ('结转" + typeName + "', '次月" + typeName + "', '当月" + typeName + "')";
        return queryDataService.query ( sql );
    }


    @Override
    public Object ddzxZdcpwcjd() {

        String sql = "    select calendar,zb_name,round(wcjd,2) as zb_value from (\n"
                + "          select a.calendar,left(a.zb_name,instr(a.zb_name,'_',1,1)-1) zb_name,a.zb_value/decode(b.zb_value,null,null,0,null,b.zb_value)*100 wcjd from\n"
                + "    (select * from sgmc.MC_SA_ZB_SO_01 t where t.ZB_NAME like '%重点产品_订单量%') b,\n"
                + "     (select * from sgmc.MC_SA_ZB_SO_01 t where t.ZB_NAME like '%重点产品_准发量%' )a\n"
                + "     where a.CALENDAR=b.CALENDAR and left(a.zb_name,instr(a.zb_name,'_',1,1)-1)=left(b.zb_name,instr(b.zb_name,'_',1,1)-1))  where wcjd <> 0 order by wcjd ";
        return queryDataService.query ( sql );
    }


    @Override
    public Object ddzxZdkhwcjd() {

        String sql = "select *\n"
                + "from (select calendar, zb_name, round(wcjd, 2) as zb_value\n"
                + "      from (\n"
                + "               select a.calendar,\n"
                + "                      SUBSTR(a.ZB_NAME, INSTR(a.ZB_NAME, '_', 1) + 1,\n"
                + "                             INSTR(a.ZB_NAME, '_', -1) - INSTR(a.ZB_NAME, '_') - 1)          zb_name,\n"
                + "                      a.zb_value / decode(b.zb_value, null, null, 0, null, b.zb_value) * 100 wcjd\n"
                + "               from (select * from sgmc.MC_SA_ZB_SO_01 t where t.ZB_NAME like '%重点客户接单量%') b,\n"
                + "                    (select * from sgmc.MC_SA_ZB_SO_01 t where t.ZB_NAME like '%重点客户准发量%') a\n"
                + "               where a.CALENDAR = b.CALENDAR\n"
                + "                 and SUBSTR(a.ZB_NAME, INSTR(a.ZB_NAME, '_', 1) + 1,\n"
                + "                            INSTR(a.ZB_NAME, '_', -1) - INSTR(a.ZB_NAME, '_') - 1)\n"
                + "                   = SUBSTR(b.ZB_NAME, INSTR(a.ZB_NAME, '_', 1) + 1,\n"
                + "                            INSTR(b.ZB_NAME, '_', -1) - INSTR(b.ZB_NAME, '_') - 1)\n"
                + "           )\n"
                + "      where wcjd is not null\n"
                + "      order by wcjd desc\n"
                + "      fetch first 10 rows only)\n"
                + "order by zb_value";

        return queryDataService.query ( sql );
    }


    @Override
    public Object ddzxKcjg(String typeName) {

        if (typeName.equals ( "硅钢" )) {
            String ggSql = "select '硅钢_寄售国内库存量' zb_name, zb_value / 10000 zb_value\n"
                    + "from SGMC.MC_SA_ZB_SO_01\n"
                    + "where zb_name = '硅钢_寄售国内库存量'\n"
                    + "  and calendar = to_char(current date - 1, 'yyyy-mm')\n"
                    + "union all\n"
                    + "select '硅钢_寄售出口库存量' zb_name,zb_value/10000 zb_value from SGMC.MC_SA_ZB_SO_01\n"
                    + "where zb_name = '硅钢_寄售出口库存量'\n"
                    + "  and calendar = to_char(current date-1,'yyyy-mm')\n"
                    + "union all\n"
                    + "select '硅钢_厂内库存量' zb_name,zb_value/10000 zb_value from SGMC.MC_SA_ZB_SO_01\n"
                    + "where zb_name = '硅钢_厂内库存量'\n"
                    + "  and calendar = to_char(current date-1,'yyyy-mm')";
            return queryDataService.query ( ggSql );
        } else {
            String sql = "select company_code||'厂内' zb_name, sum_amt / 10000 zb_value\n"
                    + "from sgso.JSC_KCJG_QG_CN\n"
                    + "where company_code = '" + typeName + "'\n"
                    + "\n"
                    + "union all\n"
                    + "\n"
                    + "select company_code||'出口' zb_name, 码单净重差值 / 10000 zb_value\n"
                    + "from sgso.JSC_KCJG_QG_JSCK\n"
                    + "where company_code = '" + typeName + "'\n"
                    + "\n"
                    + "union all\n"
                    + "select company_code||'国内' zb_name, 码单净重差值 / 10000 zb_value\n"
                    + "from sgso.JSC_KCJG_QG_JSGN\n"
                    + "where company_code = '" + typeName + "'";
            return queryDataService.query ( sql );
        }
    }

    @Override
    public Object ddzxKcjgfl(String typeName) {

        String sql = "select calendar, zb_code, SUBSTR(ZB_NAME, '1', INSTR(ZB_NAME, '_') - 1)                                                  as fl,\n"
                + "       SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1) + 1, INSTR(ZB_NAME, '_', -1) - INSTR(ZB_NAME, '_') - 1) as zb_name,\n"
                + "       zb_value, unit_name\n"
                + "from SGMC.MC_SA_ZB_so_01\n"
                + "where zb_name like '%库存结构%'\n"
                + "  and zb_name like '%" + typeName + "%'\n"
                + "  and calendar = to_char(current date, 'yyyy-mm')";
        return queryDataService.query ( sql );
    }


    @Override
    public Object ddzxDxlydqs(String typeName) {

        String sql = "select * from SGMC.MC_SA_ZB_SO_ZTZDDXL\n"
                + "where zb = '" + typeName + "'\n"
                + "  AND FL not like '%京唐%'\n"
                + "  and calendar <= to_char(current date-1 month,'yyyy-mm')\n"
                + "  and calendar >= to_char(current date-5 month,'yyyy-mm')  order by calendar";
        return queryDataService.query ( sql );
    }


    @Override
    public Object ddzzCyddjdl() {

        String sql = "select calendar, zb_code, zb_name, round((zb_value / 10000), 2) zb_value\n"
                + "from sgmc.MC_SA_ZB_SO_01\n"
                + "where zb_name like '%次月接单量%'\n"
                + "  and calendar = (\n"
                + "    case\n"
                + "        when to_char(current date - 1, 'dd') < 10 then to_char(current date - 1, 'yyyy-mm')\n"
                + "        else to_char(current date + 1 month, 'yyyy-mm') end)";
        return queryDataService.query ( sql );
    }


    @Override
    public Object ddzzWclydqs(String typeName) {
/*
        String sql = "select * "
                + " from SGMC.MC_SA_ZB_SO_01\n"
                + " where zb_name = '迁顺_"+typeName+"完成率' "
                + " and calendar >= to_char(current date - 6 month, 'yyyy-mm')and calendar <= to_char(current date, 'yyyy-mm')";
        */


        String sql = "SELECT *\n"
                + "FROM (SELECT a.calendar,\n"
                + "             '迁顺_销售计划完成率'\n"
                + "                zb_name,\n"
                + "               a.zb_value\n"
                + "             / decode (b.zb_value,\n"
                + "                       0,\n"
                + "                       NULL,\n"
                + "                       b.zb_value)\n"
                + "             * 100\n"
                + "                AS zb_value\n"
                + "      FROM (SELECT t.calendar,\n"
                + "                   SUBSTR (t.zb_name,\n"
                + "                             INSTR (t.zb_name,\n"
                + "                                    '-',\n"
                + "                                    1,\n"
                + "                                    1)\n"
                + "                           + 1)\n"
                + "                      AS zb_name,\n"
                + "                   t.zb_value\n"
                + "            FROM SGMC.MC_SA_ZB_SO_01 t\n"
                + "            WHERE ZB_NAME LIKE '%当月订单量%'\n"
                + "            and case when\n"
                + "             left(calendar,4)='2019' then left(calendar,4)=to_char(sysdate,'yyyy')and \n"
                + "           right(calendar,2)>=4\n"
                + "           else\n"
                + "           left(calendar,4)=to_char(current date,'yyyy')\n"
                + "            end) a,\n"
                + "           (SELECT t.calendar,\n"
                + "                   SUBSTR (t.zb_name,\n"
                + "                             INSTR (t.zb_name,\n"
                + "                                    '-',\n"
                + "                                    1,\n"
                + "                                    1)\n"
                + "                           + 1)\n"
                + "                      AS zb_name,\n"
                + "                   t.zb_value\n"
                + "            FROM SGMC.MC_SA_ZB_SO_01 t\n"
                + "            WHERE ZB_NAME LIKE '%当月计划量%') b\n"
                + "      WHERE a.calendar = b.calendar\n"
                + "      UNION ALL\n"
                + "      SELECT calendar, '迁顺_节点完成率' AS zb_name, zb_value\n"
                + "      FROM SGMC.V_MC_ZB_SO_ZTZDDXL\n"
                + "      WHERE     zb LIKE '%节点%'\n"
                + "            AND fl = '迁顺'\n"
                + "            AND ZB_VALUE IS NOT NULL\n"
                + "            AND calendar >= to_char (CURRENT DATE - 6 MONTH, 'yyyy-mm')\n"
                + "            AND calendar <= to_char (CURRENT DATE, 'yyyy-mm'))\n"
                + "WHERE zb_name = '迁顺_" + typeName + "完成率'\n";

        return queryDataService.query ( sql );
    }

    @Override
    public Object ddzzFzzxsjhwcl() {

        String sql = "select a.calendar, a.zb_name, a.zb_value / decode(b.zb_value, 0, null, b.zb_value) * 100 as zb_value\n"
                + " from (select t.calendar,\n"
                + "             SUBSTR(t.ZB_NAME, INSTR(t.ZB_NAME, '_', 1) + 1,\n"
                + "                    INSTR(t.ZB_NAME, '_', -1) - INSTR(t.ZB_NAME, '_') - 1) as zb_name,\n"
                + "             t.zb_value\n"
                + "      from SGMC.MC_SA_ZB_SO_01 t\n"
                + "      where ZB_NAME like '%销售计划%订货量%'\n"
                + "        and zb_name not like '%当月%') a,\n"
                + "     (select t.calendar,\n"
                + "             SUBSTR(t.ZB_NAME, INSTR(t.ZB_NAME, '_', 1) + 1,\n"
                + "                    INSTR(t.ZB_NAME, '_', -1) - INSTR(t.ZB_NAME, '_') - 1) as zb_name,\n"
                + "             t.zb_value\n"
                + "      from SGMC.MC_SA_ZB_SO_01 t\n"
                + "      where ZB_NAME like '%销售计划%计划量%'\n"
                + "        and zb_name not like '%当月%') b\n"
                + " where a.calendar = b.calendar\n"
                + "  and a.zb_name = b.zb_name";
        return queryDataService.query ( sql );
    }


    @Override
    public Object cptjGdlxcpyljjd() {

        String sql = "\n"
                + "select left(order_month,4)||'-'||right(order_month,2) calendar,prod_code zb_name,amt/10000 zb_value from sgso.JSC_CPTJ_QTCP_YLJJD\n"
                + "where order_month = to_char(current date-1,'yyyymm')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cptjQtzdcpyljjdQcsxsj() {

        String sql = "SELECT zb_name,round(zb_value,0) as zb_value FROM sgmc.MC_SA_ZB_SO_01 t WHERE t.ZB_NAME LIKE '%重点产品_准发量%'\n"
                + "AND ZB_NAME = '汽车酸洗_重点产品_准发量'\n"
                + "union all\n"
                + "SELECT zb_name,round(zb_value,0) as zb_value FROM sgmc.MC_SA_ZB_SO_01 t WHERE t.ZB_NAME LIKE '%重点产品_订单量%'\n"
                + "AND ZB_NAME = '汽车酸洗_重点产品_订单量'";
        return queryDataService.query ( sql );
    }


    @Override
    public Object cpRzqkfltj(String typeName) {
        String sql = " SELECT calendar, zb, fl, round(zb_value, 2) zb_value "
                + " FROM SGMC.V_MC_ZB_SO_RZJH "
                + " where fl = '" + typeName + "' "
                + "  and calendar = to_char(current date - 1 month, 'yyyy-mm')";
        return queryDataService.query ( sql );
    }


    @Override
    public Object cpRzqkydqs(String typeName) {
        String sql = "SELECT calendar,zb,fl,decode(zb_value,null,0,zb_value) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where fl = '汽车板'\n"
                + "  and zb ='" + typeName + "'\n"
                + "  and calendar <= to_char(current date -1 month\n"
                + "    , 'yyyy-mm')\n"
                + "  and calendar>=to_char(trunc (current date\n"
                + "    , 'yyyy')\n"
                + "    , 'yyyy-mm')\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpJthjghlEvixms() {
        String sql = "select calendar, zb, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         select calendar, zb, '股份合计' as fl, sum(zb_value) zb_value\n"
                + "         from sgmc.v_mc_zb_so_rzjh\n"
                + "         where zb = '实现供货EVI项目数'\n"
                + "           and fl in ('汽车板', '冷轧非汽车', '热系', '酸洗','电工钢')\n"
                + "           and calendar = to_char(current date - 1 month, 'yyyy-mm')\n"
                + "         group by calendar, zb\n"
                + "         union all\n"
                + "         SELECT calendar,\n"
                + "                zb,\n"
                + "                '京唐合计'        AS fl,\n"
                + "                sum(zb_value) AS zb_value\n"
                + "         FROM (SELECT *\n"
                + "               FROM sgmc.v_mc_zb_so_rzjh\n"
                + "               WHERE zb = '实现供货EVI项目数'\n"
                + "                 AND fl IN ('汽车板',\n"
                + "                            '镀锡板',\n"
                + "                            '中厚板',\n"
                + "                            '热轧板',\n"
                + "                            '酸洗板',\n"
                + "                            '冷轧非汽车板')\n"
                + "                 AND calendar = to_char(CURRENT DATE - 1 MONTH, 'yyyy-mm'))\n"
                + "         GROUP BY calendar, zb)\n"
                + "group by calendar, zb";
        return queryDataService.query ( sql );
    }


    @Override
    public Object cpXscpxlnlj() {
        String sql = " SELECT zb, fl, sum (ZB_VALUE) zb_value "
                + " FROM SGMC.V_MC_ZB_SO_RZJH "
                + " where zb = '新试产品销量' AND left(calendar,4) = left(to_char(current date,'yyyy-mm'),4) "
                + " and calendar <= to_char(current date-1 month,'yyyy-mm') "
                + " GROUP BY fl, zb ";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpXscpydxlqs(String typeName) {
        String sql = " SELECT calendar, zb, fl, decode(zb_value, null, 0, zb_value) zb_value\n"
                + " FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + " where fl = '" + typeName + "' "
                + "  and zb = '新试产品销量'\n"
                + "  and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "  and calendar >= to_char(trunc(current date, 'yyyy'), 'yyyy-mm')\n"
                + " order by calendar ";

        return queryDataService.query ( sql );
    }

    @Override
    public Object ddzxKcjgJcqhqst(String typeName) {
        String sql = "select company_code||'寄售' zb_name,码单净重差值/10000 zb_value\n"
                + "from sgso.JSC_KCJG_QG_JSKCL\n"
                + "where company_code = '" + typeName + "'\n"
                + "\n"
                + "union all\n"
                + "\n"
                + "select company_code||'期货' zb_name,sum_wt/10000 zb_value\n"
                + "from sgso.JSC_KCJG_QG_QHKCL\n"
                + "where company_code = '" + typeName + "'\n"
                + "\n"
                + "union all\n"
                + "\n"
                + "select company_code||'现货' zb_name,sum_wt/10000 zb_value\n"
                + "from sgso.JSC_KCJG_QG_XHKCL\n"
                + "where company_code = '" + typeName + "'";
        return queryDataService.query ( sql );
    }

    @Override
    public Object ddzxKcjgKyqzqst(String typeName) {
        if (typeName.equals ( "硅钢" )){
            String ggSql = "select calendar,substr(zb_name,instr(zb_name,'_',1,1)+1,instr(zb_name,'_',1,2)-instr(zb_name,'_',1,1)-1) zb_name,zb_value/10000 zb_value from sgmc.MC_SA_ZB_SO_02\n"
                    + "where calendar = to_char(current date-1,'yyyy-mm')\n"
                    + "and zb_name like '%库存量'";
            return queryDataService.query ( ggSql );
        }

        String sql = "select zb_name, mat_name, zb_value\n"
                + "from (\n"
                + "         select decode(company_code, '顺义冷轧', '顺义', company_code) zb_name, mat_name, sum_wt / 10000 zb_value\n"
                + "         from sgso.JSC_KCJG_QG_FS)\n"
                + "where zb_name = '" + typeName + "'";

        return queryDataService.query ( sql );
    }

    @Override
    public Object cptjQtzdcpyljjdDlwsj() {
        String sql = "SELECT zb_name, round(NVL(zb_value,0),0) as zb_value\n"
                + "FROM sgmc.MC_SA_ZB_SO_01 t\n"
                + "WHERE t.ZB_NAME LIKE '%重点产品_准发量%'\n"
                + "union all\n"
                + "SELECT zb_name, round(NVL(zb_value,0), 0) as zb_value\n"
                + "FROM sgmc.MC_SA_ZB_SO_01 t\n"
                + "WHERE t.ZB_NAME LIKE '%重点产品_订单量%'";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cptjQtzdcpyljjdJjmshj() {
        String sql = "select calendar, zb_name, nvl(round(sum(zb_value), 0), 0) as zb_value\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1) + 1,\n"
                + "                       INSTR(ZB_NAME, '_', -1) - INSTR(ZB_NAME, '_') - 1) as zb_name,\n"
                + "                zb_value\n"
                + "         from sgmc.mc_sa_zb_so_01\n"
                + "         where zb_name like '%其他重点产品月实际值%'\n"
                + "           and zb_name not like '%首钢股份_%'\n"
                + "           and zb_name not like '%首钢京唐_%')\n"
                + "group by zb_name, calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cptjQtzdcpyljjdJjmjh() {
        String sql = "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME, '_',1) + 1,INSTR(ZB_NAME, '_',-1) - INSTR(ZB_NAME, '_') - 1) as zb_name,  round( zb_value/10000,2) zb_value\n"
                + "from sgmc.mc_sa_zb_so_01\n"
                + "where zb_name like '%其他重点产品月计划值%'\n"
                + "  and zb_name like '%迁顺%'\n"
                + "union all\n"
                + "select calendar,zb,nvl(round(zb_value ,2),0) as zb_value from SGMC.V_MC_ZB_SO_CPJGjh\n"
                + "where calendar = to_char(current date,'yyyy-mm')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpyzflLjjdQcb() {
        String sql = "select calendar,\n"
                + "       SUBSTR(ZB_NAME, '1', INSTR(ZB_NAME, '_') - 1) zb_name,\n"
                + "       fl,\n"
                + "       sum(zb_value/10000)                            zb_value\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1) + 1,\n"
                + "                       INSTR(ZB_NAME, '_', -1) - INSTR(ZB_NAME, '_') - 1) as zb_name,\n"
                + "                SUBSTR(zb_name, INSTR(zb_name, '_', 2, 3) + 1)            as fl,\n"
                + "                zb_value\n"
                + "         from sgmc.mc_sa_zb_so_01\n"
                + "         where zb_name like '%汽车板战略产品%'\n"
                + "           and zb_name not like '%首钢股份_%'\n"
                + "           and zb_name not like '%首钢京唐_%')\n"
                + "where fl = '月准发量'\n"
                + "group by zb_name, calendar, fl";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpyzflLjjdLrjhz() {
        String sql = "select calendar, zb, nvl(round(zb_value, 0), 0) as zb_value\n"
                + "from SGMC.V_MC_ZB_SO_CPJGjh\n"
                + "where calendar = to_char(current date, 'yyyy-mm')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpyzflLjjdGg() {
        String sql = "select calendar, zb_name, round(zb_value/10000, 2) zb_value\n"
                + "from SGMC.MC_SA_ZB_SO_02\n"
                + "where zb_name in ('硅钢_取向_准发量', '硅钢_无取向_准发量', '硅钢_取向_准发计划量', '硅钢_无取向_准发计划量')\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object kfZlyyNlj() {
        String selectDatem = DateUtils.dateAdd2String ( new Date ( ), "m", 0, DateUtils.dateYMPattern );
        String zlyylajsnlj = "select tb_year calendar,'lajsnlj' zb_name, sum(con) zb_value\n"
                + "from SGSO.ZLYY_TBYEAR\n"
                + "where tb_year = left('" + selectDatem + "', 4)\n"
                + "  and tb_year <> ''\n"
                + "  and company_name <> '首钢京唐'\n"
                + "group by tb_year";

        String zlyyjajsnlj = "select ja_year calendar,'jajsnlj' zb_name,sum(con) zb_value\n"
                + "from SGSO.ZLYY_JAYEAR\n"
                + "where ja_year = left('" + selectDatem + "',4)\n"
                + "and ja_year <> ''\n"
                + "and company_name <> '首钢京唐'\n"
                + "group by ja_year";

        String zlyypfjenlj = "select pf_year calendar,'pfjenlj' zb_name, sum(sum_amt / 10000) zb_value\n"
                + "from SGSO.ZLYY_PFYEAR\n"
                + "where pf_year = left('" + selectDatem + "', 4)\n"
                + "  and pf_year <> ''\n"
                + "  and company_name <> '首钢京唐'\n"
                + "group by pf_year";

        String njsje = "select js_year calendar,'njsje' zb_name, sum(js_amt) zb_value\n"
                + "from SGSO.JS_YEAR\n"
                + "where js_year = left('" + selectDatem + "', 4)\n"
                + "  and js_year <> ''\n"
                + "  and company_name <> '首钢京唐'\n"
                + "group by js_year";

        String nlpje = "select lp_year calendar,'nlpje' zb_name,sum(lp_amt) zb_value\n"
                + "from SGSO.LP_YEAR\n"
                + "where lp_year = left('" + selectDatem + "',4)\n"
                + "and lp_year <> ''\n"
                + "and company_name <> '首钢京唐'\n"
                + "group by lp_year";


        String sql = zlyylajsnlj + " union all " + zlyyjajsnlj + " union all " + zlyypfjenlj + " union all " + njsje + " union all " + nlpje;

        return queryDataService.query ( sql );
    }

    @Override
    public Object kfZlyyYyjsnljtj(String typeName) {
        String sql = "select dl_year calendar,sum(con) zb_value from SGSO.DL_YEAR\n"
                + "where dl_year=to_char(current date-1,'yyyy')\n"
                + "  and company_code in ('" + typeName + "')\n"
                + "group by dl_year";
        return queryDataService.query ( sql );
    }

    @Override
    public Object kfZlyyYyqxflntj(String typeName) {

        if (typeName.equals ( "SGSO.YYBT_YEARSX_DL" )) {
            String qcyhTypeName = typeName.substring ( 0, typeName.length ( ) );
            String qcyhSxSql = "select bt_year calendar, defect_desc zb_name, sum(con) zb_value\n"
                    + "from " + qcyhTypeName + "\n"
                    + "where company_code in ('1A10', '1A20')\n"
                    + "group by bt_year, defect_desc";

            return queryDataService.query ( qcyhSxSql );
        } else {
            String qcyhTypeName = typeName.substring ( 0, typeName.length ( ) );
            String sql = "select bt_year calendar, defect_desc zb_name, sum(con) zb_value\n"
                    + "from " + qcyhTypeName + "\n"
                    + "where company_code in ('1A10', '1A20')\n"
                    + "group by bt_year, defect_desc";

            return queryDataService.query ( sql );
        }
    }

    @Override
    public Object kfZlyyYyqxflytj(String typeName) {
        String qcyhTypeName = typeName.substring ( 0, typeName.length ( ) );
        String sql = " select left(mon, 4) || '-' || right(mon, 2) calendar, defect_desc zb_name, sum(con) zb_value\n"
                + "        from " + qcyhTypeName + "\n"
                + "        where company_code in ('1A10', '1A20')\n"
                + "        group by left(mon, 4) || '-' || right(mon, 2), defect_desc";

        return queryDataService.query ( sql );
    }

    @Override
    public Object kfFwxlZlyyZgzq(String typeName) {
        String sql = "select quarter, zb_name, zb_value\n"
                + "from (\n"
                + "         select QUARTER, '总计' zb_name, USER_SATISFACTION_SUM zb_value\n"
                + "         from sgso.SATISFACTION_QS\n"
                + "         union all\n"
                + "         select QUARTER, '汽车板' zb_name, USER_SATISFACTION_SUM zb_value\n"
                + "         from sgso.SATISFACTION_QS_CAR\n"
                + "         union all\n"
                + "         select QUARTER, '热轧板' zb_name, USER_SATISFACTION_SUM zb_value\n"
                + "         from sgso.SATISFACTION_QS_RZ\n"
                + "         union all\n"
                + "         select QUARTER, '酸洗板' zb_name, USER_SATISFACTION_SUM zb_value\n"
                + "         from sgso.SATISFACTION_QS_SX\n"
                + "         union all\n"
                + "         select QUARTER, '冷轧非汽车板' zb_name, USER_SATISFACTION_SUM zb_value\n"
                + "         from sgso.SATISFACTION_QS_LZ)\n"
                + "where zb_name = '" + typeName + "'";

        return queryDataService.query ( sql );
    }

    @Override
    public Object cpKfyrztj(String approveName) {
        String sql = "select left(calendar, 4), zb, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         SELECT calendar, zb, fl, zb_value\n"
                + "         FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "         where fl = '汽车板'\n"
                + "           and calendar <= to_char(current date - 1 day , 'yyyy-mm-dd'))\n"
                + "\n"
                + "where  zb = '"+approveName+"' AND left(calendar, 4) = left(to_char(current date, 'yyyy-mm'), 4)\n"
                + "group by left(calendar, 4), zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpJthjghlNdjh() {
        String sql = "select calendar, zb, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         SELECT calendar, zb, '股份合计' as fl, sum(zb_value) zb_value\n"
                + "         FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "         where fl in ('汽车板', '冷轧非汽车', '热系', '酸洗', '电工钢')\n"
                + "           and calendar = to_char(current date - 1 month, 'yyyy-mm')\n"
                + "         group by calendar, zb\n"
                + "         union all\n"
                + "         SELECT calendar,\n"
                + "                zb,\n"
                + "                '京唐合计'        AS fl,\n"
                + "                sum(zb_value) AS zb_value\n"
                + "         FROM (SELECT *\n"
                + "               FROM SGMC.V_MC_ZB_SO_RZJH_jt\n"
                + "               WHERE fl IN ('汽车板',\n"
                + "                            '热轧板',\n"
                + "                            '酸洗板',\n"
                + "                            '镀锡板',\n"
                + "                            '中厚板',\n"
                + "                            '冷轧非汽车板')\n"
                + "                 AND calendar = to_char(CURRENT DATE - 1 MONTH, 'yyyy-mm'))\n"
                + "         GROUP BY calendar, zb)\n"
                + "group by calendar, zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpJthjghlYdwc() {
        String sql = "select calendar, zb, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         SELECT calendar, zb, '股份合计' as fl, sum(zb_value) zb_value\n"
                + "         FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "         where fl in ('汽车板', '冷轧非汽车', '热系', '酸洗','硅钢')\n"
                + "           and calendar = to_char(current date - 1 month, 'yyyy-mm')\n"
                + "         group by calendar, zb\n"
                + "         union all\n"
                + "         SELECT calendar,\n"
                + "                zb,\n"
                + "                '京唐合计'        AS fl,\n"
                + "                sum(zb_value) AS zb_value\n"
                + "         FROM (SELECT *\n"
                + "               FROM SGMC.V_MC_ZB_SO_RZJH_jt\n"
                + "               WHERE fl IN ('汽车板',\n"
                + "                            '热轧板',\n"
                + "                            '酸洗板',\n"
                + "                            '镀锡板',\n"
                + "                            '中厚板',\n"
                + "                            '冷轧非汽车板')\n"
                + "                 AND calendar = to_char(CURRENT DATE - 1 MONTH, 'yyyy-mm'))\n"
                + "         GROUP BY calendar, zb)\n"
                + "group by calendar, zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpJthjghlNdljwc() {
        String sql = "select zb, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         SELECT zb, '股份合计' as fl, sum(ZB_VALUE) zb_value\n"
                + "         FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "         where zb = 'EVI供货量'\n"
                + "           and fl not in ('硅钢', '迁顺')\n"
                + "           AND left(calendar, 4) = left(to_char(current date, 'yyyy-mm'), 4)\n"
                + "           and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "         GROUP BY zb\n"
                + "         union all\n"
                + "         SELECT zb, '京唐合计' AS fl, sum(zb_value) AS zb_value\n"
                + "         FROM (SELECT zb, fl, sum(ZB_VALUE) zb_value\n"
                + "               FROM SGMC.V_MC_ZB_SO_RZJH_jt\n"
                + "               WHERE zb = 'EVI供货量'\n"
                + "                 AND left(calendar, 4) =\n"
                + "                     left(to_char(CURRENT DATE, 'yyyy-mm'), 4)\n"
                + "                 AND calendar <= to_char(CURRENT DATE - 1 MONTH, 'yyyy-mm')\n"
                + "               GROUP BY fl, zb)\n"
                + "         WHERE fl IN ('汽车板',\n"
                + "                      '热轧板',\n"
                + "                      '酸洗板',\n"
                + "                      '镀锡板',\n"
                + "                      '中厚板',\n"
                + "                      '冷轧非汽车板')\n"
                + "         GROUP BY zb)\n"
                + "group by zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpJthjghlNdljtb() {
        String sql = "    select zb,sum(zb_value) zb_value from(\n"
                + "    SELECT zb,'股份合计' as fl,sum (ZB_VALUE) zb_value\n"
                + "    FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "    where zb = 'EVI供货量'\n"
                + "    and fl not in ('硅钢','迁顺')\n"
                + "    AND left(calendar,4) = left(to_char(current date-12 month,'yyyy-mm'),4)\n"
                + "    and calendar <= to_char(current date-13 month,'yyyy-mm')\n"
                + "    GROUP BY zb\n"
                + "    union all\n"
                + "    SELECT zb, fl, sum (ZB_VALUE) zb_value\n"
                + "    FROM SGMC.V_MC_ZB_SO_RZJH_jt\n"
                + "    WHERE     zb = 'EVI供货量'\n"
                + "    AND left (calendar, 4) =\n"
                + "    left (to_char (CURRENT DATE - 12 MONTH, 'yyyy-mm'), 4)\n"
                + "    AND calendar <= to_char (CURRENT DATE - 13 MONTH, 'yyyy-mm')\n"
                + "    GROUP BY fl, zb)\n"
                + "group by zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGfhjghlEvixms() {
        String sql = "select calendar, sum(zb_value) zb_value\n"
                + "from sgmc.v_mc_zb_so_rzjh\n"
                + "where zb = '实现供货EVI项目数'\n"
                + "  and fl in ('汽车板', '冷轧非汽车', '热系', '酸洗','电工钢')\n"
                + "  and calendar = to_char(current date - 1 month, 'yyyy-mm')\n"
                + "group by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGfhjghlNdjh() {
        String sql = "SELECT calendar, zb, sum(zb_value) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where fl in ('汽车板', '冷轧非汽车', '热系', '酸洗','电工钢')\n"
                + "  and calendar = to_char(current date - 1 month, 'yyyy-mm')\n"
                + "group by calendar, zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGfhjghlYdjh() {
        String sql = "SELECT calendar, zb, sum(zb_value) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where fl in ('汽车板', '冷轧非汽车', '热系', '酸洗','电工钢')\n"
                + "  and calendar = to_char(current date - 1 month, 'yyyy-mm')\n"
                + "group by calendar, zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGfhjghlNdljwc() {
        String sql = "SELECT zb, sum(ZB_VALUE) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where zb = 'EVI供货量'\n"
                + "  and fl not in ('硅钢', '迁顺')\n"
                + "  AND left(calendar, 4) = left(to_char(current date, 'yyyy-mm'), 4)\n"
                + "  and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "GROUP BY zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGfhjghlNdljtb() {
        String sql = "SELECT zb, sum(ZB_VALUE) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where zb = 'EVI供货量'\n"
                + "  and fl not in ('硅钢', '迁顺')\n"
                + "  AND left(calendar, 4) = left(to_char(current date - 12 month, 'yyyy-mm'), 4)\n"
                + "  and calendar <= to_char(current date - 13 month, 'yyyy-mm')\n"
                + "GROUP BY zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGhlRqslxms(String typeName) {
        String sql = "select *\n"
                + "from sgmc.v_mc_zb_so_rzjh\n"
                + "where zb = '实现供货EVI项目数' and fl = '" + typeName + "' and calendar = to_char(current date -1 month, 'yyyy-mm')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGfhjghlRqslJhwc(String typeName) {
        String sql = "SELECT *\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where fl = '"+typeName+"'\n"
                + "  and calendar = to_char(current date - 1 month, 'yyyy-mm')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpJthjghlYdghjxsghqst(String typeName) {

        String sql = "select calendar, zb, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         select calendar, zb, '股份合计' as fl, sum(zb_value) zb_value\n"
                + "         from (\n"
                + "                  SELECT calendar, zb, decode(zb_value, null, 0, zb_value) zb_value\n"
                + "                  FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "                  where fl not in ('硅钢', '迁顺')\n"
                + "                    and zb = '"+typeName+"'\n"
                + "                    and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "                    and calendar >= to_char(trunc(current date, 'yyyy'), 'yyyy-mm'))\n"
                + "         group by calendar, zb\n"
                + "         union all\n"
                + "         SELECT calendar,\n"
                + "                zb,\n"
                + "                '京唐合计'        AS fl,\n"
                + "                sum(zb_value) AS zb_value\n"
                + "         FROM (SELECT calendar,\n"
                + "                      zb,\n"
                + "                      fl,\n"
                + "                      decode(zb_value,\n"
                + "                             NULL,\n"
                + "                             0,\n"
                + "                             zb_value)\n"
                + "                          zb_value\n"
                + "               FROM SGMC.V_MC_ZB_SO_RZJH_jt\n"
                + "               WHERE fl IN ('汽车板',\n"
                + "                            '热轧板',\n"
                + "                            '镀锡板',\n"
                + "                            '中厚板',\n"
                + "                            '酸洗板',\n"
                + "                            '冷轧非汽车板')\n"
                + "                 AND zb = '"+typeName+"'\n"
                + "                 AND calendar <= to_char(CURRENT DATE - 1 MONTH, 'yyyy-mm')\n"
                + "                 AND calendar >= to_char(trunc(CURRENT DATE, 'yyyy'), 'yyyy-mm'))\n"
                + "         GROUP BY calendar, zb)\n"
                + "group by calendar, zb";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGfhjghlYdghjxsghqst(String typeName) {
        String sql = "SELECT calendar, zb, decode(sum(zb_value), null, 0, sum(zb_value)) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where fl not in ('硅钢', '迁顺')\n"
                + "  and zb = '"+typeName+"'\n"
                + "  and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "  and calendar >= to_char(trunc(current date, 'yyyy'), 'yyyy-mm')\n"
                + "group by calendar, zb\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpRqslghlYdghjxsghqst(String tabName, String productName) {
        String sql = "SELECT calendar, zb, fl, decode(zb_value, null, 0, zb_value) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where fl = '"+tabName+"'\n"
                + "  and zb = '"+productName+"'\n"
                + "  and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "  and calendar >= to_char(trunc(current date, 'yyyy'), 'yyyy-mm')\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object cpGfhjghlRqslWctb(String typeName) {
        String sql = "SELECT zb||'年度累计完成' as zb_name, fl, sum(ZB_VALUE) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where zb = 'EVI供货量'\n"
                + "  AND left(calendar, 4) = left(to_char(current date, 'yyyy-mm'), 4)\n"
                + "  and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "and fl = '"+typeName+"'\n"
                + "GROUP BY fl, zb\n"
                + "union all\n"
                + "SELECT zb||'年度累计同比' as zb_name, fl, sum(ZB_VALUE) zb_value\n"
                + "FROM SGMC.V_MC_ZB_SO_RZJH\n"
                + "where zb = 'EVI供货量'\n"
                + "  AND left(calendar, 4) = left(to_char(current date - 12 month, 'yyyy-mm'), 4)\n"
                + "  and calendar <= to_char(current date - 13 month, 'yyyy-mm')\n"
                + "  and fl = '"+typeName+"'\n"
                + "GROUP BY fl, zb";
        return queryDataService.query ( sql );
    }

    // 客户服务的

    @Override
    public Object kfZlyyYlj() {

        String selectDatem = DateUtils.dateAdd2String ( new Date ( ), "m", 0, DateUtils.dateYMPattern );
        String zlyylajsylj = "select calendar, 'lajsylj' zb_name, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         select concat(left(mon, 4), concat('-', right(mon, 2))) calendar, company_name zb_name, con zb_value\n"
                + "         from SGSO.ZLYY_TBMON)\n"
                + "where calendar = '" + selectDatem + "'\n"
                + "  and calendar <> ''\n"
                + "  and zb_name <> '首钢京唐'\n"
                + "group by calendar";

        String zlyyjajsylj = "select calendar, 'jajsylj' zb_name, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         select concat(left(mon, 4), concat('-', right(mon, 2))) calendar, company_name zb_name, con zb_value\n"
                + "         from SGSO.ZLYY_JAMON)\n"
                + "where calendar = '" + selectDatem + "'\n"
                + "  and calendar <> ''\n"
                + "  and zb_name <> '首钢京唐'\n"
                + "group by calendar";

        String zlyypfjeylj = "select calendar, 'pfjeylj' zb_name, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "   company_name      zb_name,\n"
                + "                sum_amt / 10000   zb_value\n"
                + "         from SGSO.ZLYY_PFMON)\n"
                + "where calendar = '" + selectDatem + "'\n"
                + "  and calendar <> ''\n"
                + "  and zb_name <> '首钢京唐'\n"
                + "group by calendar";

        String yjsje = "select calendar, 'yjsje' zb_name, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         select concat(left(settle_month, 4), concat('-', right(settle_month, 2))) calendar,\n"
                + "                company_name                                                       zb_name,\n"
                + "                js_amt                                                             zb_value\n"
                + "         from SGSO.JS_MON)\n"
                + "where calendar = '" + selectDatem + "'\n"
                + "  and calendar <> ''\n"
                + "  and zb_name <> '首钢京唐'\n"
                + "group by calendar";

        String yljje = "select calendar, 'ylpje' zb_name, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         select concat(left(mon, 4), concat('-', right(mon, 2))) calendar, company_name zb_name, lp_amt zb_value\n"
                + "         from SGSO.LP_MON)\n"
                + "where calendar = '" + selectDatem + "'\n"
                + "  and calendar <> ''\n"
                + "  and zb_name <> '首钢京唐'\n"
                + "group by calendar";


        String sql = zlyylajsylj + " union all " + zlyyjajsylj + " union all " + zlyypfjeylj + " union all " + yjsje + " union all " + yljje;

        return queryDataService.query ( sql );
    }


    @Override
    public Object kfZlyyYyjsfltj(String typeName) {

        String sql = "select dl_year calendar,defect_desc zb_name,sum(con) zb_value from SGSO.DL_YEAR_DEFECT\n"
                + "where dl_year=to_char(current date-1,'yyyy')\n"
                + "  and company_code in ('" + typeName + "')\n"
                + "group by dl_year,defect_desc";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfZlyyYyjsydqs(String typeName) {

        String sql = "select left(mon, 4) || '-' || right(mon, 2) calendar, 'yytbjs' zb_name, sum(con) zb_value\n"
                + "from SGSO.YYTB_MON\n"
                + "where company_code in ('" + typeName + "')\n"
                + "group by left(mon, 4) || '-' || right(mon, 2)\n"
                + "union all\n"
                + "select left(mon, 4) || '-' || right(mon, 2) calendar, 'yyjajs' zb_name, sum(con) zb_value\n"
                + "from SGSO.YYJA_MON\n"
                + "where company_code in ('" + typeName + "')\n"
                + "group by left(mon, 4) || '-' || right(mon, 2)";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfZlyyYyqxntj(String typeName) {
        String qcyhTypeName = typeName.substring ( 0, typeName.length ( ) );
        String sql = "select bt_year calendar,sum(con) zb_value from " + qcyhTypeName + "\n"
                + "where company_code in ('1A10','1A20')\n"
                + "group by bt_year";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfZlyyYyqxytj(String typeName) {

        String qcyhTypeName = typeName.substring ( 0, typeName.length ( ) );
        String sql = "select left(mon,4)||'-'||right(mon,2) calendar,sum(con) zb_value from " + qcyhTypeName + "\n"
                + "where company_code in ('1A10','1A20')\n"
                + "group by left(mon,4)||'-'||right(mon,2)";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfZlyyYyqxjs(String typeName) {
        String qcyhTypeName = typeName.substring ( 0, typeName.length ( ) );
        String sql = "select pm_year calendar, defect_desc_1 zb_name, con zb_value\n"
                + "from " + qcyhTypeName + "";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfFwxlZlyyclzqydqs(String typeName) {

        String sql = "select calendar, zb_value, etl\n"
                + "from (\n"
                + "         select calendar, sum(zb_value) zb_value, '总计' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         zlyy_zq                                          zb_value\n"
                + "                  from SGSO.ZLYY_ZQ)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '首钢京唐'\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '热轧板' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         zlyy_zq                                          zb_value\n"
                + "                  from SGSO.ZLYY_ZQ_RZ)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '首钢京唐'\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '汽车板' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         zlyy_zq                                          zb_value\n"
                + "                  from SGSO.ZLYY_ZQ_CAR)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '首钢京唐'\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '酸洗板' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         zlyy_zq                                          zb_value\n"
                + "                  from SGSO.ZLYY_ZQ_SX)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '首钢京唐'\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '冷轧非汽车板' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         zlyy_zq                                          zb_value\n"
                + "                  from SGSO.ZLYY_ZQ_LZ)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '首钢京唐'\n"
                + "         group by calendar)\n"
                + "where etl = '" + typeName + "'\n";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfFwxlSqbywtjjlydqs(String typeName) {

        String sql = "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME, '_',1) + 1,INSTR(ZB_NAME, '_',-1) - INSTR(ZB_NAME, '_') - 1) as zb_name,zb_value,unit_name\n"
                + "from SGMC.MC_SA_ZB_SO_01\n"
                + "where zb_name like '%" + typeName + "_诉求抱怨问题解决率月度趋势%' and zb_name like '%首钢迁钢%'\n"
                + "  and calendar<=to_char(current date-1 month,'yyyy-mm')and calendar>=to_char(current date-6 month,'yyyy-mm') order by calendar";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfFwxlJszxydzqydqs(String typeName) {

        String sql = "select calendar, zb_value, etl\n"
                + "from (\n"
                + "         select calendar, sum(zb_value) zb_value, '总计' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         proc_cycle                                       zb_value\n"
                + "                  from SGSO.ENQUIRY_ZQ)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '首钢京唐'\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '热轧板' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         proc_cycle                                       zb_value\n"
                + "                  from SGSO.ENQUIRY_ZQ_RZ)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '京唐'\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '汽车板' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         proc_cycle                                       zb_value\n"
                + "                  from SGSO.ENQUIRY_ZQ_CAR)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '京唐'\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '酸洗板' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         proc_cycle                                       zb_value\n"
                + "                  from SGSO.ENQUIRY_ZQ_SX)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '京唐'\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '冷轧非汽车板' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar,\n"
                + "                         company_name                                     zb_name,\n"
                + "                         proc_cycle                                       zb_value\n"
                + "                  from SGSO.ENQUIRY_ZQ_LZ)\n"
                + "         where calendar <> ''\n"
                + "           and zb_name <> '京唐'\n"
                + "         group by calendar)\n"
                + "where etl = '" + typeName + "'";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfFwxlKhzfjsydqs(String typeName) {

        String sql = "select calendar, zb_value, etl\n"
                + "from (\n"
                + "         select calendar, sum(zb_value) zb_value, '总计' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar, con zb_value\n"
                + "                  from SGSO.ACTIVITY_CON)\n"
                + "         where calendar <> ''\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '战略用户' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar, con zb_value\n"
                + "                  from SGSO.ACTIVITY_CON_ZLYH)\n"
                + "         where calendar <> ''\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '潜在战略用户' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar, con zb_value\n"
                + "                  from SGSO.ACTIVITY_CON_QZZL)\n"
                + "         where calendar <> ''\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '重点用户' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar, con zb_value\n"
                + "                  from SGSO.ACTIVITY_CON_ZDYH)\n"
                + "         where calendar <> ''\n"
                + "         group by calendar\n"
                + "         union all\n"
                + "         select calendar, sum(zb_value) zb_value, '一般用户' etl\n"
                + "         from (\n"
                + "                  select concat(left(mon, 4), concat('-', right(mon, 2))) calendar, con zb_value\n"
                + "                  from SGSO.ACTIVITY_CON_YBYH)\n"
                + "         where calendar <> ''\n"
                + "         group by calendar)\n"
                + "where etl = '" + typeName + "'\n"
                + "  and calendar >= to_char(current date - 7 month, 'yyyy-mm')";

        return queryDataService.query ( sql );

    }

    @Override
    public Object kfQcbppmDyjfl() {

        String sql = " SELECT * FROM SGMC.v_MC_ZB_so_ppm "
                + " where zb = '当月交付量' and CALENDAR = to_char(current date-2 month,'yyyy-mm') ";
        return queryDataService.query ( sql );

    }

    @Override
    public Object kfQcbppmyyl() {

        String sql = "SELECT * FROM SGMC.v_MC_ZB_so_ppm "
                + " where zb = '异议量' and CALENDAR = to_char(current date-2 month,'yyyy-mm')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object kfQcbppmydqs(String customerName) {

        String sql = "SELECT calendar, zb, nvl(zb_value, 0) zb_value, customer\n"
                + "FROM SGMC.v_MC_ZB_so_ppm\n"
                + "where customer = '" + customerName + "'\n"
                + "  and calendar >= to_char(current date - 12 month, 'yyyy-mm')\n"
                + "  and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "  and zb in ('投诉PPM', '异议PPM')\n"
                + "order by calendar";

        return queryDataService.query ( sql );

    }

}
