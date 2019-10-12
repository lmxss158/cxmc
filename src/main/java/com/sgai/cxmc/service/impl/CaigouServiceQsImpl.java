package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.CaigouService;
import com.sgai.cxmc.service.QueryDataService;
import com.sgai.cxmc.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/8/23 10:31
 * @Version 1.0
 */

@Service("caigouServiceQs")
public class CaigouServiceQsImpl implements CaigouService {

    //采购供应类别代码
    private final String TKS = "tks"; // 铁矿石
    private final String PCM = "pcm"; // 喷吹煤
    private final String JT = "jt";  // 焦炭
    private final String FG = "fg";  // 废钢
    private final String HJ = "hj";  // 合金

    private final String ZC = "zc";  // 资材
    private final String BJ = "bj";  // 备件


    @Autowired
    QueryDataService queryDataService;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Object gyJkkkfkJhlydl() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select CALENDAR,zb_name,sum(zb_value)/10000 zb_value from (\n" +
                "select left(t.CALENDAR,7) CALENDAR, t.ZB_NAME,ZB_VALUE from SGMC.MC_SA_ZB_PO_02  t where t.ZB_NAME in ('进口粉矿_到达量','进口块矿_到达量','进口块矿_计划量','进口粉矿_计划量')\n" +
                ") where CALENDAR = left('" + selectDate + "',7) group by CALENDAR,zb_name";
        return queryDataService.query ( sql );

    }

    private String getJkkkfkJhlydlAreaConditionSql(String type) {
        switch (type) {
            case TKS:
                return "'进口粉矿_到达量', '进口块矿_到达量', '进口块矿_计划量', '进口粉矿_计划量'";
            case PCM:
                return "'迁安地区_喷吹煤_总计_计划数量','顺义地区_喷吹煤_总计_计划数量'";
            case JT:
                return "'迁安地区_焦炭_总计_计划数量','顺义地区_焦炭_总计_计划数量'";
            case FG:
                return "'迁安地区_废钢_总计_计划数量','顺义地区_废钢_总计_计划数量'";
            case HJ:
                return "'迁安地区_合金_总计_计划数量','顺义地区_合金_总计_计划数量'";
            default:
                return null;
        }
    }

    private String getGnkJhlydlAreaConditionSql(String type) {
        switch (type) {
            case TKS:
                return "'迁安地区_国内矿_总计_计划数量'";
            case PCM:
                return "'迁安地区_喷吹煤_总计_计划数量','顺义地区_喷吹煤_总计_计划数量'";
            case JT:
                return "'迁安地区_焦炭_总计_计划数量','顺义地区_焦炭_总计_计划数量'";
            case FG:
                return "'迁安地区_废钢_总计_计划数量','顺义地区_废钢_总计_计划数量'";
            case HJ:
                return "'迁安地区_合金_总计_计划数量','顺义地区_合金_总计_计划数量'";
            default:
                return null;
        }
    }


    @Override
    public Object gyJkkBzsl() {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );


        String sql = "select calendar, '报支数量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_进口块矿_总计_报支数量', '首钢冷轧_进口块矿_总计_报支数量')\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar, '报支数量'";

        return queryDataService.query ( sql );

    }


    @Override
    public Object gyJkkBzje() {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar,'报支金额'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01 where zb_name in('首钢迁钢_进口块矿_总计_报支金额','首钢冷轧_进口块矿_总计_报支金额')\n" +
                " and calendar=left('" + selectDate + "',7)\n" +
                "  group by calendar";

        return queryDataService.query ( sql );

    }

    @Override
    public Object gyJkfkkkKcl() {

        String sql = " select calendar,zb_name,zb_value/10000 zb_value from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in ('进口块矿_港存量','进口粉矿_港存量')\n" +
                "  and calendar = to_char(current date-1,'yyyy-mm-dd')\n" +
                "union all\n" +
                "select calendar,zb_name,zb_value/10000 from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in ('进口块矿_库存量','进口粉矿_库存量')\n" +
                "  and calendar = to_char(current date-1,'yyyy-mm-dd')";

        return queryDataService.query ( sql );

    }

    @Override
    public Object gyZjzy(String type) {

        String sqlCondition = getZjzyTypeConditionSql ( type );
        String sqlYrlCondition = getYrlZjzyTypeConditionSql ( type );
        String sql = " select left(calendar, 7) as calendar, zb_name, zb_value\n"
                + "from (select calendar, '资金占用'zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "       from sgmc.mc_sa_zb_po_01\n"
                + "       where zb_name in (" + sqlCondition + ") "
                + "         and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "         and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n"
                + "       group by calendar\n"
                + "      union all\n"
                + "      select calendar, '资金占用'zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "      from sgmc.mc_sa_zb_po_01\n"
                + "      where zb_name in (" + sqlYrlCondition + ")\n"
                + "        and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "      group by calendar)\n"
                + "order by calendar ";

        return queryDataService.query ( sql );

    }


    @Override
    public Object gyCgjhydlm(String typeName) {

        String sql = "select TJSJ,\n"
                + "       sum(YCGJHJE)   as YCGJHJE,\n"
                + "       sum(YCGHTJELJ) as YCGHTJELJ,\n"
                + "       sum(YDHJELJ)   as YDHJELJ\n"
                + "from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "where company_code in ('1A10', '1A20', '1A00')\n"
                + "  and item_type = '" + typeName + "'\n"
                + "  and TJSJ = to_char(current date - 1, 'yyyymm')\n"
                + "group by TJSJ";

        return queryDataService.query ( sql );

    }


    @Override
    public Object gyCgjhydly(String typeName) {

        String sql = "select tjsj,\n"
                + "       sum(ycgjhje)   ycgjhje,\n"
                + "       sum(ycghtjelj) ycghtjelj,\n"
                + "       sum(ydhjelj)   ydhjelj,\n"
                + "       sum(yztjhjelj) yztjhjelj,\n"
                + "       sum(yzthtjelj) yzthtjelj\n"
                + "from (\n"
                + "         select left(TJSJ, 4)     tjsj,\n"
                + "                sum(YCGJHJE)   as YCGJHJE,\n"
                + "                sum(YCGHTJELJ) as YCGHTJELJ,\n"
                + "                sum(YDHJELJ)   as YDHJELJ,\n"
                + "                sum(YZTJHJELJ) as YZTJHJELJ,\n"
                + "                sum(YZTHTJELJ) as YZTHTJELJ\n"
                + "         from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "         where company_code in ('1A10', '1A20', '1A00')\n"
                + "           and item_type = '" + typeName + "'\n"
                + "           and TJSJ <= to_char(current date - 1, 'yyyymm')\n"
                + "           and SUBSTR(TJSJ, 1, 4) = TO_CHAR(SYSDATE, 'YYYY')\n"
                + "         group by TJSJ)\n"
                + "group by tjsj";

        return queryDataService.query ( sql );

    }


    @Override
    public Object gyJhcgddjelj(String typeName) {

        String sql = "select TJSJ, sum(YCGJHJE) as YCGJHJE, sum(YCGHTJELJ) as YCGHTJELJ, sum(YDHJELJ) as YDHJELJ\n"
                + "from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "where company_code in ('1A10', '1A20', '1A00')\n"
                + "  and item_type = '" + typeName + "'\n"
                + "  and TJSJ <= to_char(current date - 1, 'yyyymm')\n"
                + "  and TJSJ >= to_char(current date - 6 month, 'yyyymm')\n"
                + "group by TJSJ";

        return queryDataService.query ( sql );

    }


    @Override
    public Object gyDqdgxhfy(String typeName) {

        String sql = " select b.calendar, '当期吨钢消耗费用' as zb_name, a.zb_value/b.zb_value as zb_value\n"
                + "from\n"
                + "       (select TJSJ, '月领用出库金额' zb_name, sum(YLYCKJE) * 10000 as zb_value\n"
                + "from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "where company_code in ('1A10', '1A20', '1A00')\n"
                + "  and item_type = '" + typeName + "'\n"
                + "  and TJSJ = to_char(current date - 1, 'yyyymm')\n"
                + "group by TJSJ ) a ,\n"
                + "       (select calendar, zb_name, round(sum(zb_value), 2)as zb_value\n"
                + "from (SELECT left(calendar, 7) calendar, '商品材上月合计'zb_name, zb_value * 10000 as zb_value\n"
                + "      FROM SGMC.MC_SA_ZB_PP_01\n"
                + "      where zb_name in ('炼钢产量明细日指标', '硅钢商品材日指标', '炼铁产量明细日指标', '酸洗商品材日指标', '顺冷商品材日指标', '热轧商品材日指标')\n"
                + "        and left(CALENDAR, 7) = to_char(current date - 1, 'yyyy-mm')\n"
                + "      order by calendar)\n"
                + "group by calendar, zb_name)  b ";

        return queryDataService.query ( sql );

    }


    @Override
    public Object gySqdgbjxhfy(String typeName) {
        String sql = "SELECT *\n"
                + "FROM (SELECT tjsj\n"
                + "                 AS calendar,\n"
                + "             trim(decode(dept_name,\n"
                + "                         '热轧作业部',\n"
                + "                         '热轧',\n"
                + "                         '鼎盛城',\n"
                + "                         '鼎盛成',\n"
                + "                         '炼铁作业部',\n"
                + "                         '炼铁',\n"
                + "                         '能源作业部',\n"
                + "                         '能源',\n"
                + "                         '质量检查部',\n"
                + "                         '质检',\n"
                + "                         '创业开发中心',\n"
                + "                         '创业',\n"
                + "                         '顺义冷轧',\n"
                + "                         '顺冷',\n"
                + "                         '炼钢作业部',\n"
                + "                         '炼钢',\n"
                + "                         dept_name))\n"
                + "                 AS zb_name,\n"
                + "             cast(\n"
                + "                     round(\n"
                + "                                 ylyckje\n"
                + "                                 / (SELECT sum(zb_value) zb_value\n"
                + "                                    FROM (SELECT left(calendar, 7)     calendar,\n"
                + "                                                 zb_name,\n"
                + "                                                 round(zb_value, 2) AS zb_value,\n"
                + "                                                 unit_name\n"
                + "                                          FROM SGMC.MC_SA_ZB_PP_01\n"
                + "                                          WHERE zb_name IN\n"
                + "                                                ('炼钢产量明细日指标')\n"
                + "                                            AND left(CALENDAR, 7) =\n"
                + "                                                to_char(CURRENT DATE - 1 MONTH,\n"
                + "                                                        'yyyy-mm'))),\n"
                + "                                 2) AS DECIMAL(20, 2))\n"
                + "                 AS zb_value\n"
                + "      FROM SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "      WHERE company_code IN ('1A10', '1A20', '1A00')\n"
                + "        AND item_type = '" + typeName + "'\n"
                + "        AND dept_name <> '空'\n"
                + "        AND dept_name IS NOT NULL\n"
                + "        AND TJSJ = to_char(CURRENT DATE - 1 MONTH, 'yyyymm'))\n"
                + "ORDER BY zb_value DESC";
        return queryDataService.query ( sql );

    }

    @Override
    public Object gyDqkczjzy(String typeName) {

        // 资材和备件一样的sql

        String sql = "select sum(INV_AMT)/10000 as zb_value\n"
                + "from (\n"
                + "         select *\n"
                + "         from (\n"
                + "                  SELECT T.COMPANY_CODE,\n"
                + "\n"
                + "                         case\n"
                + "                             when t.COMPANY_CODE = '1A10' then decode(T.DEPT_NAME, '总计', '迁钢', null, '其他', '炼钢作业部',\n"
                + "                                                                      '炼钢', '质量检查部', '质检', '鼎盛城', '鼎盛成', '炼铁作业部', '炼铁',\n"
                + "                                                                      '热轧作业部', '热轧', '能源作业部', '能源', '创业开发中心', '创业',\n"
                + "                                                                      t.DEPT_NAME)\n"
                + "                             else decode(t.DEPT_NAME, '总计', '顺义', t.DEPT_NAME)\n"
                + "                             end as dept_name,\n"
                + "                         T.INV_AMT\n"
                + "                  FROM SGPO.PO_SU_GLJSC_KCZJZY T\n"
                + "                  WHERE T.COMPANY_CODE IN ('1A10', '1A00',\n"
                + "                                           '1A20')\n"
                + "                    and t.item_type = '" + typeName + "'))";

        return queryDataService.query ( sql );

    }

    @Override
    public Object gyYdkczjzy(String typeName) {


        String sql = " select TJSJ, '库存资金占用' as zb_name, sum(YKCZJZY) / 10000 as YKCZJZY\n"
                + " from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + " where company_code in ('1A10', '1A20')\n"
                + "  and tjsj >= to_char(current date - 6 month, 'yyyymm')\n"
                + "  and tjsj <= to_char(current date - 1 month, 'yyyymm')\n"
                + "  and item_type = '" + typeName + "' "
                + " group by TJSJ\n"
                + " order by TJSJ ";

        return queryDataService.query ( sql );

    }

    @Override
    public Object gyGzybzjzy(String typeName) {

        // 资材和备件一样的sql
        String sql = "SELECT t.ACCT_PERIOD_NO calendar,\n"
                + "       t.SUB_CATALOG_NAME zb_name,\n"
                + "       cast(round(t.YCKJE/(SELECT sum (zb_value)*10000 zb_value\n"
                + "                      FROM (SELECT left (calendar, 7) calendar,\n"
                + "                                   zb_name,\n"
                + "                                   round (zb_value, 2) AS zb_value,\n"
                + "                                   unit_name\n"
                + "                            FROM SGMC.MC_SA_ZB_PP_01\n"
                + "                            WHERE     zb_name IN\n"
                + "                                         ('炼钢产量明细日指标')\n"
                + "                                  AND left (CALENDAR, 7) =\n"
                + "                                      to_char (CURRENT DATE - 1 MONTH,\n"
                + "                                               'yyyy-mm'))),2) as decimal(20,2)) zb_value\n"
                + "FROM SGPO.PO_SU_GLJSC_ZLCKJE t\n"
                + "WHERE     t.ACCT_PERIOD_NO = to_char (CURRENT DATE - 1 MONTH, 'yyyymm')\n"
                + "      AND t.COMPANY_CODE IN ('1A20', '1A10')\n"
                + "ORDER BY t.YCKJE DESC\n"
                + "FETCH FIRST 15 ROWS ONLY";

        return queryDataService.query ( sql );

    }

    @Override
    public Object gnkJhlydl() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select sum(zb_value) as zb_value,zb_name from (\n" +
                "select calendar,'计划量'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01 where zb_name in('迁安地区_国内矿_总计_计划数量') group by calendar \n" +
                "union all\n" +
                "select left(calendar,7) calendar,'到达量'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01 where zb_name in('首钢迁钢_国内矿_(原燃料)入库','首钢冷轧_国内矿_总计_入库数量')  group by calendar)\n" +
                "where calendar =left('" + selectDate + "',7) group by zb_name";
        return queryDataService.query ( sql );
    }

    @Override
    public Object gyGnkBzsl() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar,'报支数量'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '首钢迁钢_国内矿_总计_报支数量','首钢冷轧_国内矿_总计_报支数量')\n" +
                "  and calendar =left('" + selectDate + "',7)\n" +
                "group by calendar,'报支数量'";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyGnkBzje() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar, '报支金额' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "from sgmc.mc_sa_zb_po_01\n"
                + "where zb_name in ('首钢迁钢_国内矿_总计_报支金额', '首钢冷轧_国内矿_总计_报支金额')\n"
                + "  and calendar = left('" + selectDate + "',7)\n"
                + "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyGnkKcl() {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "m", 0, DateUtils.dateYMPattern );


        String sql = "SELECT LEFT(calendar, 7) AS calendar, zb_name, zb_value\n" +
                "FROM (\n" +
                "         SELECT calendar, zb_name, SUM(zb_value / 10000) AS zb_value\n" +
                "              , ROW_NUMBER() OVER (PARTITION BY LEFT(t.CALENDAR, 7), t.ZB_NAME ORDER BY t.CALENDAR DESC) AS RN\n" +
                "         FROM (\n" +
                "                  (SELECT calendar, '库存量' AS zb_name, zb_value\n" +
                "                   FROM sgmc.mc_sa_zb_po_01\n" +
                "                   WHERE zb_name IN ('首钢迁钢_国内矿_(原燃料)期末库存数量', '首钢冷轧_国内矿_(原燃料)期末库存数量'))\n" +
                "                  UNION ALL\n" +
                "                  (SELECT calendar, '资金占用' AS zb_name, zb_value\n" +
                "                   FROM sgmc.mc_sa_zb_po_01\n" +
                "                   WHERE zb_name ='首钢迁钢_国内矿_(原燃料)资金占用')\n" +
                "              ) t\n" +
                "\n" +
                "         GROUP BY calendar, zb_name\n" +
                "     )\n" +
                "WHERE rn = 1 and LEFT(calendar, 7) = '" + selectDate + "'";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyJcJhlydl() {

        String selectDated = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );
        String selectDatem = DateUtils.dateAdd2String ( new Date ( ), "m", 0, DateUtils.dateYMPattern );

        String sql = "select calendar, '迁焦焦碳计划量' zb_name, zb_value / 10000 as zb_value\n"
                + "from sgmc.mc_sa_zb_po_02\n"
                + "where zb_name = '迁焦_自产焦炭_计划量'\n"
                + "  and calendar = '" + selectDatem + "'\n"
                + "union  all\n"
                + "select calendar, '外购焦炭计划量' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "from sgmc.mc_sa_zb_po_01\n"
                + "where zb_name in ('迁安地区_外购焦炭_总计_计划数量', '顺义地区_外购焦炭_总计_计划数量')\n"
                + "  and calendar = left('" + selectDated + "', 7)\n"
                + "group by calendar\n"
                + "union all\n"
                + "select calendar,\n"
                + "       decode(zb_name, '首钢迁钢_迁焦焦炭_(原燃料)入库', '迁焦焦碳到达量', '首钢迁钢_外购焦炭_(原燃料)入库', '外购焦碳到达量') zb_name,\n"
                + "       sum(zb_value / 10000)                                                 zb_value\n"
                + "from (\n"
                + "         select left(calendar, 7) calendar, zb_name, zb_value\n"
                + "         from sgmc.mc_sa_zb_po_01\n"
                + "         where left(calendar, 7) = left('" + selectDated + "', 7)\n"
                + "           and zb_name in ('首钢迁钢_迁焦焦炭_(原燃料)入库', '首钢迁钢_外购焦炭_(原燃料)入库'))\n"
                + "group by calendar, zb_name\n";
        return queryDataService.query ( sql );

    }

    @Override
    public Object gyJcJhlydlqst() {
        //减去2个月
        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //第一天
        //LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth(), 1);
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, decode(zb_name, '焦炭_合计_计划量', '计划量', '焦炭_合计_到达量', '到达量') zb_name, zb_value / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in ('焦炭_合计_计划量', '焦炭_合计_到达量')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyJcBzslyje() {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar, '报支数量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name = '首钢迁钢_迁焦焦炭_总计_报支数量'\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar, '报支数量'\n" +
                "union all\n" +
                "select calendar, '报支金额' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name = '首钢迁钢_迁焦焦炭_总计_报支金额'\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyJcwgBzslyje() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar, '报支数量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name = '首钢迁钢_外购焦炭_总计_报支数量'\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar, '报支数量'\n" +
                "union all\n" +
                "select calendar, '报支金额' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name = '首钢迁钢_外购焦炭_总计_报支金额'\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyJcBzjeqst() {
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, '报支金额' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_外购焦炭_总计_报支金额', '首钢迁钢_迁焦焦炭_总计_报支金额')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyJcQjkclyzy() {

        String sql = "select *\n"
                + "from (\n"
                + "         select calendar, '库存量' zb_name, zb_value / 10000 as zb_value\n"
                + "         from sgmc.mc_sa_zb_po_01\n"
                + "         where zb_name in ('首钢迁钢_迁焦焦炭_(原燃料)期末库存数量', '首钢冷轧_迁焦焦炭_(原燃料)期末库存数量')\n"
                + "           and substr(calendar, 6) <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "           and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm'))\n"
                + "where calendar = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "union all\n"
                + "select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "from sgmc.mc_sa_zb_po_01\n"
                + "where zb_name = '首钢迁钢_迁焦焦炭_(原燃料)资金占用'\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyJcWgkclyzy() {

        String sql = "select *\n"
                + "from (\n"
                + "         select calendar, '库存量' zb_name, zb_value / 10000 as zb_value\n"
                + "         from sgmc.mc_sa_zb_po_01\n"
                + "         where zb_name in ('首钢迁钢_外购焦炭_(原燃料)期末库存数量', '首钢冷轧_外购焦炭_(原燃料)期末库存数量')\n"
                + "           and substr(calendar, 6) <= to_char(current date - 1 month, 'yyyy-mm')\n"
                + "           and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm'))\n"
                + "where calendar = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "union all\n"
                + "select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "from sgmc.mc_sa_zb_po_01\n"
                + "where zb_name = '首钢迁钢_外购焦炭_(原燃料)资金占用'\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyJckczyqst(String typeName) {
        String sql = "select left(calendar, 7) calendar, zb_name, nvl(zb_value,0) as zb_value\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                zb_name,\n"
                + "                sum(zb_value / 10000) zb_value,\n"
                + "                ROW_NUMBER()\n"
                + "                        OVER (PARTITION BY LEFT(t.CALENDAR, 7), t.ZB_NAME\n"
                + "                            ORDER BY t.CALENDAR DESC)\n"
                + "                    AS                RN\n"
                + "         from ((\n"
                + "                   select calendar, '库存量' zb_name, zb_value\n"
                + "                   from sgmc.mc_sa_zb_po_01\n"
                + "                   where zb_name in ('首钢迁钢_迁焦焦炭_(原燃料)期末库存数量', '首钢冷轧_迁焦焦炭_(原燃料)期末库存数量', '首钢迁钢_外购焦炭_(原燃料)期末库存数量',\n"
                + "                                     '首钢冷轧_外购焦炭_(原燃料)期末库存数量'))\n"
                + "               union all\n"
                + "               (\n"
                + "                   select calendar, '资金占用' zb_name, zb_value\n"
                + "                   from sgmc.mc_sa_zb_po_01\n"
                + "                   where zb_name in ('首钢迁钢_迁焦焦炭_(原燃料)资金占用', '首钢迁钢_外购焦炭_(原燃料)资金占用'))) t\n"
                + "         where zb_name = '" + typeName + "'\n"
                + "         group by calendar, zb_name)\n"
                + "where rn = 1";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyFgJhlydl() {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select *\n" +
                "from (\n" +
                "         select calendar, '计划量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "         from sgmc.mc_sa_zb_po_01\n" +
                "         where zb_name in ('迁安地区_外购废钢_总计_计划数量', '顺义地区_外购废钢_总计_计划数量')\n" +
                "         group by calendar\n" +
                "         union all\n" +
                "         select calendar, '到达量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "         from sgmc.mc_sa_zb_po_01\n" +
                "         where zb_name in ('首钢迁钢_外购废钢_总计_入库数量', '首钢冷轧_外购废钢_总计_入库数量')\n" +
                "         group by calendar)\n" +
                "where calendar = left('" + selectDate + "', 7)\n";
        return queryDataService.query ( sql );
    }

    @Override
    public Object gyFgJhlydlqst() {
        //减去2个月
        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //第一天
        //LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth(), 1);
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, '计划量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('迁安地区_外购废钢_总计_计划数量', '顺义地区_外购废钢_总计_计划数量')\n" +
                "  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar\n" +
                "union all\n" +
                "select calendar, '到达量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_外购废钢_总计_入库数量', '首钢冷轧_外购废钢_总计_入库数量')\n" +
                "  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyFgbzsl() {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar,'报支数量'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '首钢迁钢_外购废钢_总计_报支数量','首钢冷轧_外购废钢_总计_报支数量')\n" +
                "  and calendar =left('" + selectDate + "',7)\n" +
                "group by calendar,'报支数量'\n";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyFgBzjeqst() {
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, '报支金额' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_外购废钢_总计_报支金额', '首钢冷轧_外购废钢_总计_报支金额')\n" +
                "  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyFgkclyzy() {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select *\n" +
                "from (\n" +
                "         select left(calendar, 7) as calendar, zb_name, zb_value\n" +
                "         from ((select calendar, '库存量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "                from sgmc.mc_sa_zb_po_01\n" +
                "                where zb_name in ('首钢迁钢_外购废钢_总计_期末数量', '首钢冷轧_外购废钢_总计_期末数量')\n" +
                "                  and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n" +
                "                  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "                group by calendar\n" +
                "                union all\n" +
                "                select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "                from sgmc.mc_sa_zb_po_01\n" +
                "                where zb_name in ('首钢迁钢_外购废钢_总计_期末金额', '首钢冷轧_外购废钢_总计_期末金额')\n" +
                "                  and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n" +
                "                  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "                group by calendar)\n" +
                "               union all\n" +
                "               select calendar, '库存量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "               from sgmc.mc_sa_zb_po_01\n" +
                "               where zb_name in ('首钢迁钢_外购低硫废钢_(原燃料)期末库存数量', '首钢迁钢_外购普通废钢_(原燃料)期末库存数量')\n" +
                "                 and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n" +
                "               group by calendar\n" +
                "               union all\n" +
                "               select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "               from sgmc.mc_sa_zb_po_01\n" +
                "               where zb_name in ('首钢迁钢_外购低硫废钢_(原燃料)资金占用', '首钢迁钢_外购普通废钢_(原燃料)资金占用')\n" +
                "                 and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n" +
                "               group by calendar))\n" +
                "where calendar = left('" + selectDate + "', 7)";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyFgKclzyqst(String typeName) {

        String sql = "select left(calendar, 7) as calendar, zb_name, nvl(zb_value,0) as zb_value\n"
                + "from (select calendar,zb_name,zb_value,ROW_NUMBER ()\n"
                + "        OVER (PARTITION BY LEFT(t.CALENDAR, 7), t.ZB_NAME\n"
                + "            ORDER BY t.CALENDAR DESC)\n"
                + "    AS RN\n"
                + "    from(\n"
                + "      select calendar, '库存量' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "      from sgmc.mc_sa_zb_po_01\n"
                + "      where zb_name in ('首钢迁钢_外购低硫废钢_(原燃料)期末库存数量', '首钢迁钢_外购普通废钢_(原燃料)期末库存数量')\n"
                + "      group by calendar\n"
                + "      union all\n"
                + "      select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "      from sgmc.mc_sa_zb_po_01\n"
                + "      where zb_name in ('首钢迁钢_外购低硫废钢_(原燃料)资金占用', '首钢迁钢_外购普通废钢_(原燃料)资金占用')\n"
                + "      group by calendar) t\n"
                + "where zb_name = '" + typeName + "')\n"
                + "where rn = 1";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyHjJhlydl() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar, '计划量' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "from sgmc.mc_sa_zb_po_01\n"
                + "where zb_name in ('迁安地区_合金_总计_计划数量', '顺义地区_合金_总计_计划数量')\n"
                + "  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n"
                + "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n"
                + "group by calendar\n"
                + "union all\n"
                + "select calendar, '到达量' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "from sgmc.mc_sa_zb_po_01\n"
                + "where zb_name in ('首钢迁钢_合金_总计_入库数量', '首钢冷轧_合金_总计_入库数量')\n"
                + "  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n"
                + "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n"
                + "group by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object gyHjJhlydlqst() {
        //减去2个月
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //第一天
        //LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth(), 1);
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, '计划量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('迁安地区_合金_总计_计划数量', '顺义地区_合金_总计_计划数量')\n" +
                "  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar\n" +
                "union all\n" +
                "select calendar, '到达量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_合金_总计_入库数量', '首钢冷轧_合金_总计_入库数量')\n" +
                "  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyHjbzsl() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar,'报支数量'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '首钢迁钢_合金_总计_报支数量','首钢冷轧_合金_总计_报支数量')\n" +
                "  and calendar =left('" + selectDate + "',7)\n" +
                "group by calendar,'报支数量'\n";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyHjBzjeqst() {
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, '报支金额' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_合金_总计_报支金额', '首钢冷轧_合金_总计_报支金额')\n" +
                "  and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyHjkclyzy() {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select * from (select left(calendar,7) as calendar,zb_name,zb_value from (\n" +
                "select * from (select calendar,'库存量'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01 \n" +
                "where zb_name in( '首钢迁钢_合金_总计_期末数量','首钢冷轧_合金_总计_期末数量')\n" +
                "and calendar <= to_char(current date-1 month,'yyyy-mm') and calendar >=to_char(add_months(sysdate,-6),'yyyy-mm') group by calendar\n" +
                "union all\n" +
                "select calendar,'资金占用'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '首钢迁钢_合金_总计_期末金额','首钢冷轧_合金_总计_期末金额')\n" +
                "and calendar <= to_char(current date-1 month,'yyyy-mm') and calendar >=to_char(add_months(sysdate,-6),'yyyy-mm') group by calendar)\n" +
                "union all\n" +
                "select calendar,'资金占用'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in('首钢迁钢_复合合金_(原燃料)资金占用','首钢迁钢_普通合金_(原燃料)资金占用','首钢迁钢_特种合金_(原燃料)资金占用','首钢迁钢_有色合金_(原燃料)资金占用')\n" +
                "and calendar =to_char(current date-1,'yyyy-mm-dd') group by calendar\n" +
                "union all\n" +
                "select calendar,'库存量'zb_name,sum(zb_value)/10000 as zb_value from sgmc.mc_sa_zb_po_01 \n" +
                "where zb_name in('首钢迁钢_复合合金_(原燃料)期末库存数量','首钢迁钢_普通合金_(原燃料)期末库存数量','首钢迁钢_特种合金_(原燃料)期末库存数量','首钢迁钢_有色合金_(原燃料)期末库存数量')\n" +
                "and calendar  =to_char(current date-1,'yyyy-mm-dd') group by calendar))where calendar = left('" + selectDate + "',7)";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyHjKclzyqst(String typeName) {

        String sql = "select left(calendar, 7) as calendar, zb_name, nvl(zb_value, 0) as zb_value\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                zb_name,\n"
                + "                zb_value,\n"
                + "                ROW_NUMBER()\n"
                + "                        OVER (PARTITION BY LEFT(t.CALENDAR, 7), t.ZB_NAME\n"
                + "                            ORDER BY t.CALENDAR DESC)\n"
                + "                    AS RN\n"
                + "\n"
                + "         from (select calendar, '库存量' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "               from sgmc.mc_sa_zb_po_01\n"
                + "               where zb_name in\n"
                + "                     ('首钢迁钢_复合合金_(原燃料)期末库存数量', '首钢迁钢_普通合金_(原燃料)期末库存数量', '首钢迁钢_特种合金_(原燃料)期末库存数量',\n"
                + "                      '首钢迁钢_有色合金_(原燃料)期末库存数量')\n"
                + "               group by calendar\n"
                + "               union all\n"
                + "               select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "               from sgmc.mc_sa_zb_po_01\n"
                + "               where zb_name in\n"
                + "                     ('首钢迁钢_复合合金_(原燃料)资金占用', '首钢迁钢_普通合金_(原燃料)资金占用', '首钢迁钢_特种合金_(原燃料)资金占用', '首钢迁钢_有色合金_(原燃料)资金占用')\n"
                + "\n"
                + "               group by calendar) t\n"
                + "         where zb_name = '" + typeName + "'\n"
                + "         order by calendar\n"
                + "     )\n"
                + "where rn = 1";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyPcmJhlydl() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select *\n" +
                "from (\n" +
                "         select calendar, '计划量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "         from sgmc.mc_sa_zb_po_01\n" +
                "         where zb_name in ('迁安地区_喷吹煤_总计_计划数量', '顺义地区_喷吹煤_总计_计划数量')\n" +
                "         group by calendar\n" +
                "         union all\n" +
                "         select calendar, '到达量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "         from sgmc.mc_sa_zb_po_01\n" +
                "         where zb_name in ('首钢迁钢_喷吹煤_总计_入库数量', '首钢冷轧_喷吹煤_总计_入库数量')\n" +
                "         group by calendar)\n" +
                "where calendar = left('" + selectDate + "', 7)\n";
        return queryDataService.query ( sql );
    }

    @Override
    public Object gyPcmJhlydlqst() {
        //减去2个月
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //第一天
        //LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth(), 1);
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, '计划量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('迁安地区_喷吹煤_总计_计划数量', '顺义地区_喷吹煤_总计_计划数量')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar\n" +
                "union all\n" +
                "select calendar, '到达量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_喷吹煤_总计_入库数量', '首钢冷轧_喷吹煤_总计_入库数量')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyPcmbzsl() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select calendar, '报支数量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_喷吹煤_总计_报支数量', '首钢冷轧_喷吹煤_总计_报支数量')\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar, '报支数量'";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyPcmBzjeqst() {
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, '报支金额' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_喷吹煤_总计_报支金额', '首钢冷轧_喷吹煤_总计_报支金额')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyPcmkclyzy() {
        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );

        String sql = "select *\n" +
                "from (select left(calendar, 7) as calendar, zb_name, zb_value\n" +
                "      from ((select calendar, '库存量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "             from sgmc.mc_sa_zb_po_01\n" +
                "             where zb_name in ('首钢迁钢_喷吹煤_总计_期末数量', '首钢冷轧_喷吹煤_总计_期末数量')\n" +
                "               and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n" +
                "               and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "             group by calendar\n" +
                "             union all\n" +
                "             select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "             from sgmc.mc_sa_zb_po_01\n" +
                "             where zb_name in ('首钢迁钢_喷吹煤_总计_期末金额', '首钢冷轧_喷吹煤_总计_期末金额')\n" +
                "               and calendar <= to_char(current date - 1 month, 'yyyy-mm')\n" +
                "               and calendar >= to_char(add_months(sysdate, -6), 'yyyy-mm')\n" +
                "             group by calendar)\n" +
                "            union all\n" +
                "            (select calendar, '库存量' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "             from sgmc.mc_sa_zb_po_01\n" +
                "             where zb_name in\n" +
                "                   ('首钢迁钢_其他喷吹煤_(原燃料)期末库存数量', '首钢迁钢_潞安煤_(原燃料)期末库存数量', '首钢迁钢_神华煤_(原燃料)期末库存数量', '首钢迁钢_阳泉煤_(原燃料)期末库存数量')\n" +
                "               and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n" +
                "             group by calendar)\n" +
                "            union all\n" +
                "            (select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "             from sgmc.mc_sa_zb_po_01\n" +
                "             where zb_name in ('首钢迁钢_其他喷吹煤_(原燃料)资金占用', '首钢迁钢_潞安煤_(原燃料)资金占用', '首钢迁钢_神华煤_(原燃料)资金占用', '首钢迁钢_阳泉煤_(原燃料)资金占用')\n" +
                "               and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n" +
                "             group by calendar)))\n" +
                "where calendar = left('" + selectDate + "', 7)";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyPcmKclzyqst(String typeName) {

        String sql = "select left(calendar, 7) calendar, zb_name, nvl(zb_value, 0) as zb_value\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                zb_name,\n"
                + "                zb_value,\n"
                + "                ROW_NUMBER()\n"
                + "                        OVER (PARTITION BY LEFT(t.CALENDAR, 7), t.ZB_NAME\n"
                + "                            ORDER BY t.CALENDAR DESC)\n"
                + "                    AS RN\n"
                + "         from (select calendar, '库存量' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "               from sgmc.mc_sa_zb_po_01\n"
                + "               where zb_name in\n"
                + "                     ('首钢迁钢_其他喷吹煤_(原燃料)期末库存数量', '首钢迁钢_潞安煤_(原燃料)期末库存数量', '首钢迁钢_神华煤_(原燃料)期末库存数量', '首钢迁钢_阳泉煤_(原燃料)期末库存数量')\n"
                + "               group by calendar\n"
                + "               union all\n"
                + "               select calendar, '资金占用' zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "               from sgmc.mc_sa_zb_po_01\n"
                + "               where zb_name in\n"
                + "                     ('首钢迁钢_其他喷吹煤_(原燃料)资金占用', '首钢迁钢_潞安煤_(原燃料)资金占用', '首钢迁钢_神华煤_(原燃料)资金占用', '首钢迁钢_阳泉煤_(原燃料)资金占用')\n"
                + "               group by calendar) t\n"
                + "         where zb_name = '" + typeName + "'\n"
                + "     )\n"
                + "where rn = 1";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyTksKclzyqst(String typeName) {
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select t.CALENDAR, t.ZB_NAME, t.ZB_VALUE / 10000 as ZB_VALUE\n" +
                "from SGMC.MC_SA_ZB_PO_02 t\n" +
                "where t.ZB_NAME = '矿_合计_库存量'\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "union all\n" +
                "SELECT LEFT(calendar, 7) AS calendar, zb_name, zb_value\n" +
                "FROM (\n" +
                "         SELECT calendar\n" +
                "              , zb_name\n" +
                "              , SUM(zb_value / 10000)  AS zb_value\n" +
                "              , ROW_NUMBER() OVER (PARTITION BY LEFT(t.CALENDAR, 7), t.ZB_NAME ORDER BY t.CALENDAR DESC) AS RN\n" +
                "         FROM (\n" +
                "                  (SELECT calendar, '库存量' AS zb_name, zb_value\n" +
                "                   FROM sgmc.mc_sa_zb_po_01\n" +
                "                   WHERE zb_name IN ('首钢迁钢_国内矿_(原燃料)期末库存数量', '首钢冷轧_国内矿_(原燃料)期末库存数量'))\n" +
                "                  UNION ALL\n" +
                "                  (SELECT calendar, '资金占用' AS zb_name, zb_value\n" +
                "                   FROM sgmc.mc_sa_zb_po_01\n" +
                "                   WHERE zb_name = '首钢迁钢_国内矿_(原燃料)资金占用')\n" +
                "              ) t\n" +
                "         WHERE zb_name = '资金占用'\n" +
                "         GROUP BY calendar, zb_name\n" +
                "     )\n" +
                "WHERE rn = 1";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyTksBzjeqst() {
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select calendar, '报支金额' zb_name, sum(zb_value) / 10000 as zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('首钢迁钢_国内矿_总计_报支金额', '首钢冷轧_国内矿_总计_报支金额', '首钢迁钢_进口块矿_总计_报支金额', '首钢冷轧_进口块矿_总计_报支金额')\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "group by calendar";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyTksJhlydlqst() {
//        LocalDate today = LocalDate.now ( ).minusMonths ( 2 );
        //最后一天
//        LocalDate lastDay = today.with ( TemporalAdjusters.lastDayOfMonth ( ) );

        String sql = "select t.CALENDAR, '到达量' ZB_NAME, t.ZB_VALUE / 10000 ZB_VALUE\n" +
                "from SGMC.MC_SA_ZB_PO_02 t\n" +
                "where t.ZB_NAME = '矿_合计_到达量'\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')\n" +
                "union all\n" +
                "select t.CALENDAR, '计划量' ZB_NAME, t.ZB_VALUE / 10000 ZB_VALUE\n" +
                "from SGMC.MC_SA_ZB_PO_02 t\n" +
                "where t.ZB_NAME = '矿_合计_计划量'\n" +
                "  and calendar > to_char('2019-07-31', 'yyyy-mm')";

        return queryDataService.query ( sql );
    }

    @Override
    public Object gyBjCgjhydlm(String typeName) {
        String sql = "select TJSJ,sum(YCGJHJE) as YCGJHJE,sum(YCGHTJELJ) as YCGHTJELJ,\n"
                + "sum(YDHJELJ) as YDHJELJ,sum(YZTJHJELJ) as YZTJHJELJ,sum(YZTHTJELJ) as YZTHTJELJ\n"
                + ", sum(YLYCKJE)/10000 as YLYCKJE\n"
                + "from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "where company_code in ('1A10'\n"
                + "    , '1A20'\n"
                + "    , '1A00')\n"
                + "  and item_type = '" + typeName + "'\n"
                + "  and TJSJ = to_char(current date -1\n"
                + "    , 'yyyymm')\n"
                + "--and tjsj>=to_char(current date,'yyyy')\n"
                + "group by TJSJ";
        return queryDataService.query ( sql );
    }

    @Override
    public Object gyBjCgjhydly(String typeName) {
        String sql = "select tjsj,\n"
                + "       sum(ycgjhje)   ycgjhje,\n"
                + "       sum(ycghtjelj) ycghtjelj,\n"
                + "       sum(ydhjelj)   ydhjelj,\n"
                + "       sum(yztjhjelj) yztjhjelj,\n"
                + "       sum(yzthtjelj) yzthtjelj,\n"
                + "       sum(YLYCKJE)/10000  YLYCKJE\n"
                + "from (\n"
                + "         select left(TJSJ, 4)     tjsj,\n"
                + "                sum(YCGJHJE)   as YCGJHJE,\n"
                + "                sum(YCGHTJELJ) as YCGHTJELJ,\n"
                + "                sum(YDHJELJ)   as YDHJELJ,\n"
                + "                sum(YZTJHJELJ) as YZTJHJELJ,\n"
                + "                sum(YZTHTJELJ) as YZTHTJELJ,\n"
                + "                sum(YLYCKJE)   as YLYCKJE\n"
                + "         from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "         where company_code in ('1A10', '1A20', '1A00')\n"
                + "           and item_type = '" + typeName + "'\n"
                + "           and TJSJ <= to_char(current date - 1, 'yyyymm')\n"
                + "           and TJSJ > to_char(current date - 7 month, 'yyyymm')\n"
                + "           and SUBSTR(TJSJ, 1, 4) = TO_CHAR(SYSDATE, 'YYYY')\n"
                + "         group by TJSJ)\n"
                + "group by tjsj";
        return queryDataService.query ( sql );
    }

    @Override
    public Object gyBjZtjeCgjhydly(String typeName) {
        String sql = "select sum(YZTJHJELJ) as YZTJHJELJ, sum(YZTHTJELJ) as YZTHTJELJ\n"
                + "from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "where company_code in ('1A10', '1A20', '1A00')\n"
                + "  and item_type = '" + typeName + "'";
        return queryDataService.query ( sql );
    }

    @Override
    public Object gyBjJhcgddjelj(String typeName) {
        String sql = "select TJSJ, sum(YCGJHJE) as YCGJHJE, sum(YCGHTJELJ) as YCGHTJELJ, sum(YDHJELJ) as YDHJELJ, sum(YLYCKJE)/10000 as YLYCKJE\n"
                + "from SGPO.PO_SU_GLJSC_ZCBJ02\n"
                + "where company_code in ('1A10', '1A20', '1A00')\n"
                + "  and item_type = '" + typeName + "'\n"
                + "  and TJSJ <= to_char(current date - 1, 'yyyymm')\n"
                + "  and TJSJ >= to_char(current date - 6 month, 'yyyymm')\n"
                + "group by TJSJ";
        return queryDataService.query ( sql );
    }

    @Override
    public Object gyBjGzybzjzy(String typeName) {
        String sql = "SELECT DEPT_NAME,SUM(INV_AMT)/10000 as INV_AMT,1  FROM (\n"
                + "    SELECT\n"
                + "    COMPANY_CODE,\n"
                + "    CASE WHEN  COMPANY_CODE = '1A00'\n"
                + "    THEN CASE WHEN  DEPT_NAME = '总计'\n"
                + "    THEN '股份'\n"
                + "    WHEN  DEPT_NAME IS NULL\n"
                + "    THEN '其他'\n"
                + "    WHEN  DEPT_NAME = '落料线'\n"
                + "    THEN '落料'\n"
                + "    ELSE DEPT_NAME\n"
                + "    END\n"
                + "    WHEN  COMPANY_CODE = '1A10'\n"
                + "    THEN CASE WHEN  DEPT_NAME = '总计'\n"
                + "    THEN '迁钢'\n"
                + "    WHEN  DEPT_NAME IS NULL\n"
                + "    THEN '其他'\n"
                + "    WHEN  DEPT_NAME = '炼钢作业部'\n"
                + "    THEN '炼钢'\n"
                + "    WHEN  DEPT_NAME = '质量检查部'\n"
                + "    THEN '质检'\n"
                + "    WHEN  DEPT_NAME = '鼎盛城'\n"
                + "    THEN '鼎盛成'\n"
                + "    WHEN  DEPT_NAME = '炼铁作业部'\n"
                + "    THEN '炼铁'\n"
                + "    WHEN  DEPT_NAME = '热轧作业部'\n"
                + "    THEN '热轧'\n"
                + "    WHEN  DEPT_NAME = '能源作业部'\n"
                + "    THEN '能源部'\n"
                + "    WHEN  DEPT_NAME = '创业开发中心'\n"
                + "    THEN '创业'\n"
                + "    ELSE DEPT_NAME\n"
                + "    END\n"
                + "    WHEN  COMPANY_CODE = '1A20'\n"
                + "    THEN CASE WHEN  DEPT_NAME = '顺义冷轧'\n"
                + "    THEN '顺冷'\n"
                + "    WHEN  DEPT_NAME = '能源作业部'\n"
                + "    THEN '能源部'\n"
                + "    WHEN  DEPT_NAME IS NULL\n"
                + "    THEN '其他'\n"
                + "    ELSE DEPT_NAME\n"
                + "    END\n"
                + "    ELSE NULL\n"
                + "    END AS DEPT_NAME,\n"
                + "    SUM(INV_AMT) AS INV_AMT\n"
                + "    FROM SGPO.PO_SU_GLJSC_KCZJZY\n"
                + "    WHERE COMPANY_CODE IN ('1A00','1A10','1A20') AND ITEM_TYPE = '" + typeName + "'\n"
                + "    GROUP BY COMPANY_CODE, DEPT_NAME)\n"
                + "GROUP BY  DEPT_NAME order by INV_AMT desc";
        return queryDataService.query ( sql );
    }

  /*  private String getGnkJhlydlArea2ConditionSql(String type) {
        switch (type) {
            case TKS:
                return "'首钢迁钢_国内矿_(原燃料)入库', '首钢冷轧_国内矿_总计_入库数量'";
            case PCM:
                return "'迁安地区_喷吹煤_总计_计划数量','顺义地区_喷吹煤_总计_计划数量'";
            case JT:
                return "'迁安地区_焦炭_总计_计划数量','顺义地区_焦炭_总计_计划数量'";
            case FG:
                return "'迁安地区_废钢_总计_计划数量','顺义地区_废钢_总计_计划数量'";
            case HJ:
                return "'迁安地区_合金_总计_计划数量','顺义地区_合金_总计_计划数量'";
            default:
                return null;
        }

    }*/


    // 根据类型返回  库存查询条件  条件sql片段
   /* private String getKcTypeConditionSql(String type) {


        switch (type) {
            case TKS:
                return "'进口块矿_港存量','进口粉矿_港存量'";
            case PCM:
                return "'首钢迁钢_喷吹煤_总计_期末数量','首钢冷轧_喷吹煤_总计_期末数量'";
            case JT:
                return "'首钢迁钢_焦炭_总计_期末数量','首钢冷轧_焦炭_总计_期末数量'";
            case FG:
                return "'首钢迁钢_废钢_总计_期末数量','首钢冷轧_废钢_总计_期末数量'";
            case HJ:
                return "'首钢迁钢_合金_总计_期末数量','首钢冷轧_合金_总计_期末数量'";
            default:
                return null;
        }
    }*/

    // 根据类型返回  库存原燃料查询条件  条件sql片段
    /*private String getKcYrlTypeConditionSql(String type) {
        switch (type) {
            case TKS:
                return "'进口块矿_库存量','进口粉矿_库存量'";
            case PCM:
                return "'首钢迁钢_其他喷吹煤_(原燃料)期末库存数量','首钢迁钢_潞安煤_(原燃料)期末库存数量','首钢迁钢_神华煤_(原燃料)期末库存数量','首钢迁钢_阳泉煤_(原燃料)期末库存数量'";
            case JT:
                return "'首钢迁钢_焦炭_(原燃料)期末库存数量'";
            case FG:
                return "'首钢迁钢_外购低硫废钢_(原燃料)期末库存数量','首钢迁钢_外购普通废钢_(原燃料)期末库存数量'";
            case HJ:
                return "'首钢迁钢_复合合金_(原燃料)期末库存数量','首钢迁钢_普通合金_(原燃料)期末库存数量','首钢迁钢_特种合金_(原燃料)期末库存数量','首钢迁钢_有色合金_(原燃料)期末库存数量'";
            default:
                return null;
        }
    }
*/
    // 根据类型返回  资金占用 条件sql片段
    private String getZjzyTypeConditionSql(String type) {
        switch (type) {
            case TKS:
                return "'首钢迁钢_进口矿_总计_期末金额','首钢冷轧_进口矿_总计_期末金额','首钢迁钢_贸易矿_总计_期末金额','首钢冷轧_贸易矿_总计_期末金额'";
            case PCM:
                return "'首钢迁钢_喷吹煤_总计_期末金额','首钢冷轧_喷吹煤_总计_期末金额'";
            case JT:
                return "'首钢迁钢_焦炭_总计_期末金额','首钢冷轧_焦炭_总计_期末金额'";
            case FG:
                return "'首钢迁钢_废钢_总计_期末金额','首钢冷轧_废钢_总计_期末金额'";
            case HJ:
                return "'首钢迁钢_合金_总计_期末金额','首钢冷轧_合金_总计_期末金额'";
            default:
                return null;
        }
    }


    // 根据类型返回  资金占用原燃料 条件sql片段
    private String getYrlZjzyTypeConditionSql(String type) {
        switch (type) {
            case TKS:
                return "'首钢迁钢_进口块矿_(原燃料)资金占用','首钢迁钢_外购烧结矿_(原燃料)资金占用','首钢迁钢_其他金属矿_(原燃料)资金占用'";
            case PCM:
                return "'首钢迁钢_其他喷吹煤_(原燃料)资金占用','首钢迁钢_潞安煤_(原燃料)资金占用','首钢迁钢_神华煤_(原燃料)资金占用','首钢迁钢_阳泉煤_(原燃料)资金占用'";
            case JT:
                return "'首钢迁钢_焦炭_(原燃料)资金占用'";
            case FG:
                return "'首钢迁钢_外购低硫废钢_(原燃料)资金占用','首钢迁钢_外购普通废钢_(原燃料)资金占用'";
            case HJ:
                return "'首钢迁钢_复合合金_(原燃料)资金占用','首钢迁钢_普通合金_(原燃料)资金占用','首钢迁钢_特种合金_(原燃料)资金占用','首钢迁钢_有色合金_(原燃料)资金占用'";
            default:
                return null;
        }
    }

    // 根据类型返回  报支金额 条件sql片段
    private String getBzjeTypeConditionSql(String type) {
        switch (type) {
            case TKS:
                return "'首钢迁钢_贸易矿_总计_报支金额','首钢迁钢_进口矿_总计_报支金额','首钢冷轧_贸易矿_总计_报支金额','首钢冷轧_进口矿_总计_报支金额'";
            case PCM:
                return "'首钢迁钢_喷吹煤_总计_报支金额','首钢冷轧_喷吹煤_总计_报支金额'";
            case JT:
                return "'首钢迁钢_焦炭_总计_报支金额'";
            case FG:
                return "'首钢迁钢_废钢_总计_报支金额','首钢冷轧_废钢_总计_报支金额'";
            case HJ:
                return "'首钢迁钢_合金_总计_报支金额','首钢冷轧_合金_总计_报支金额'";
            default:
                return null;
        }
    }


    // 根据类型返回报支数量条件sql片段
    private String getBzslTypeConditionSql(String type) {
        switch (type) {
            case TKS:
                return null; //由于铁矿石有两种，因此单独处理了
            case PCM:
                return "'首钢迁钢_喷吹煤_总计_报支数量','首钢冷轧_喷吹煤_总计_报支数量'";
            case JT:
                return "'首钢迁钢_焦炭_总计_报支数量'";
            case FG:
                return "'首钢迁钢_废钢_总计_报支数量','首钢冷轧_废钢_总计_报支数量'";
            case HJ:
                return "'首钢迁钢_合金_总计_报支数量','首钢冷轧_合金_总计_报支数量'";
            default:
                return null;
        }
    }

    // 根据类型返回计划量条件sql片段
    private String getGnkJhlydlDateTypeConditionSql(String type) {

        String selectDate = DateUtils.dateAdd2String ( new Date ( ), "d", -1, DateUtils.defaultPattern );
        switch (type) {
            case TKS:
                return "left('" + selectDate + "', 7)";
            case PCM:
                return "'迁安地区_喷吹煤_总计_计划数量','顺义地区_喷吹煤_总计_计划数量'";
            case JT:
                return "'迁安地区_焦炭_总计_计划数量','顺义地区_焦炭_总计_计划数量'";
            case FG:
                return "'迁安地区_废钢_总计_计划数量','顺义地区_废钢_总计_计划数量'";
            case HJ:
                return "'迁安地区_合金_总计_计划数量','顺义地区_合金_总计_计划数量'";
            default:
                return null;
        }
    }

    // 根据类型返回达量条件sql片段
    private String getDlTypeConditionSql(String type) {
        switch (type) {
            case TKS:
                return "'首钢迁钢_进口矿_总计_入库数量', '首钢迁钢_贸易矿_总计_入库数量', '首钢冷轧_进口矿_总计_入库数量', '首钢冷轧_贸易矿_总计_入库数量'";
            case PCM:
                return "'首钢迁钢_喷吹煤_总计_入库数量','首钢冷轧_喷吹煤_总计_入库数量'";
            case JT:
                return "'首钢迁钢_焦炭_总计_入库数量'";
            case FG:
                return "'首钢迁钢_废钢_总计_入库数量','首钢冷轧_废钢_总计_入库数量'";
            case HJ:
                return "'首钢迁钢_合金_总计_入库数量','首钢冷轧_合金_总计_入库数量'";
            default:
                return null;
        }
    }

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );

    public static void main(String[] args) {
        Date now = new Date ( );
        System.out.println ( "当前日期：" + DATE_FORMAT.format ( now ) );
        Calendar c = Calendar.getInstance ( );
        c.setTime ( now );
        c.add ( Calendar.MONTH, -7 );
        Date newDate = c.getTime ( );
        String format = DATE_FORMAT.format ( newDate );
        String[] split = format.split ( "-" );
        System.out.println ( split[0] + "-" + split[1] );
        System.out.println ( format );
    }

}
