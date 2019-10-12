package com.sgai.cxmc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sgai.cxmc.bean.LibraryThreshold;
import com.sgai.cxmc.service.QueryDataService;
import com.sgai.cxmc.service.ZhizaoService;
import com.sgai.cxmc.util.MyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 制造模块接口-迁钢实现
 * @Author: 李锐平
 * @Date: 2019/8/5 16:12
 * @Version 1.0
 */

@Service("zhizaoServiceQs")
public class ZhizaoServiceQsImpl implements ZhizaoService {

    @Autowired
    QueryDataService queryDataService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Object clClzl() {

       /* String[] zbNames = {"炼铁","炼钢","热轧","酸洗","顺冷","硅钢"};

        String sql = getClzlTypeSql(zbNames[0]) + " union all "
                + getClzlTypeSql(zbNames[1]) + " union all "
                + getClzlTypeSql(zbNames[2]) + " union all "
                + getClzlTypeSql(zbNames[3]) + " union all "
                + getClzlTypeSql(zbNames[4]) + " union all "
                + getClzlTypeSql(zbNames[5]) ;*/
        String sql = "select calendar,\n"
                + "       decode(zb_name, '炼铁产量明细月指标', '炼铁', '炼钢产量明细月指标', '炼钢', '热轧商品材月指标', '热轧', '酸洗商品材月指标', '酸洗', '顺冷商品材月指标', '顺冷',\n"
                + "              '硅钢商品材月指标', '硅钢', zb_name) as zb_name,\n"
                + "       zb_value\n"
                + "from (\n"
                + "         SELECT calendar,\n"
                + "                CASE\n"
                + "                    WHEN zb_name LIKE '炼钢产量明细%月%'\n"
                + "                        THEN\n"
                + "                        '炼钢产量明细月指标'\n"
                + "                    ELSE\n"
                + "                        zb_name\n"
                + "                    END\n"
                + "                    AS zb_name,\n"
                + "                round(SUM(ZB_VALUE), 2)\n"
                + "                    AS zb_value\n"
                + "         FROM SGMC.MC_SA_ZB_PP_01\n"
                + "         WHERE ZB_NAME LIKE '%产量%月%'\n"
                + "           AND CALENDAR = (SELECT MAX(CALENDAR)\n"
                + "                           FROM sgmc.mc_sa_zb_pp_01\n"
                + "                           WHERE ZB_NAME LIKE '%产量%月%')\n"
                + "         GROUP BY CALENDAR,\n"
                + "                  CASE\n"
                + "                      WHEN zb_name LIKE '炼钢产量明细%月%'\n"
                + "                          THEN\n"
                + "                          '炼钢产量明细月指标'\n"
                + "                      ELSE\n"
                + "                          zb_name\n"
                + "                      END\n"
                + "         UNION ALL\n"
                + "         SELECT left(calendar, 7)                calendar,\n"
                + "                concat(left(zb_name, 10), '月指标') zb_name,\n"
                + "                round(SUM(ZB_VALUE), 2) AS       zb_value\n"
                + "         FROM SGMC.MC_SA_ZB_PP_01\n"
                + "         WHERE ZB_NAME LIKE '%商品材%日%'\n"
                + "           AND left(CALENDAR, 7) = to_char(current date - 1, 'yyyy-mm')\n"
                + "         GROUP BY left(CALENDAR, 7), zb_name\n"
                + "     )\n"
                + "where zb_name in ('炼铁产量明细月指标', '炼钢产量明细月指标', '热轧商品材月指标', '酸洗商品材月指标', '顺冷商品材月指标', '硅钢商品材月指标')\n";
        return queryDataService.query(sql);
    }

    private String getClzlTypeSql(String zbName) {
        String yzbTypeName = getYzbTypeName(zbName);
        String sql = "SELECT '" + zbName + "' as zb_name, calendar, total_value\n"
                + "FROM (SELECT calendar, SUM(zb_value) AS total_value\n"
                + "      FROM SGMC.MC_SA_ZB_PP_01\n"
                + "      WHERE zb_name LIKE '" + yzbTypeName + "%月指标'\n"
                + "        and calendar = to_char(current date, 'yyyy-mm')\n"
                + "      GROUP BY calendar\n"
                + "      ORDER BY CALENDAR DESC) t";
        return sql;
    }

    @Override
    public Object clYdqst(String zbName, Integer monthNums) {
        String yzbTypeName = getYzbTypeName(zbName);
        if (yzbTypeName == null) {
            return null;
        }

        monthNums = formartMonthNums(monthNums);

        String sql = "select *\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                decode(zb_name, '炼铁产量明细月指标', '炼铁', '炼钢产量明细月指标', '炼钢', '热轧商品材日指标', '热轧', '酸洗商品材日指标', '酸洗', '顺冷商品材日指标',\n"
                + "                       '顺冷', '硅钢商品材日指标', '硅钢', zb_name) zb_name,\n"
                + "                zb_value\n"
                + "         from (\n"
                + "                  (select zb_name, calendar, zb_value\n"
                + "                   from SGMC.MC_SA_ZB_PP_01\n"
                + "                   where zb_name in ('炼铁产量明细月指标', '炼钢产量明细月指标')\n"
                + "                     and left(calendar, 7) >= '2019-03')\n"
                + "                  union all\n"
                + "                  (SELECT zb_name, left(calendar, 7) calendar, sum(zb_value) zb_value\n"
                + "                   FROM SGMC.MC_SA_ZB_PP_01\n"
                + "                   WHERE zb_name in ('热轧商品材日指标', '酸洗商品材日指标', '顺冷商品材日指标', '硅钢商品材日指标')\n"
                + "                     and left(calendar, 7) >= '2019-03'\n"
                + "                   group by zb_name, left(calendar, 7))))\n"
                + "where zb_name = '" + zbName + "'\n"
                + "order by calendar";

        return queryDataService.query(sql);
    }

    @Override
    public Object clGfrfd() {

        //发电日计划
        String rjhSql = "SELECT DATE_CODE, GENERATION_DAY_PLAN DAY_PLAN, GENERATION_ACCUMULATIVE_PLAN ljjh "
                + "FROM SGPC.PC_GROUPDAILY_SJ_010 "
                + "where to_char(DATE_CODE,'YYYYMMdd') = to_char(SYSDATE,'yyyymmdd') order by date_code desc";

        //日发电
        String rfdSql = "select CALENDAR,ZB_CODE,ZB_NAME,ZB_VALUE from SGMC.MC_SA_ZB_PP_01 where zb_name = '迁钢发电计产量日指标'\n"
                + "and CALENDAR = to_char(current date-1 ,'yyyy-mm-dd') order by calendar desc ";

        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rfdList = jdbcTemplate.queryForList(rfdSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("DAY_PLAN"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rfdList != null && rfdList.size() > 0) {
            obj.put("zbValue", rfdList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;

    }

    @Override
    public Object clGfyfd() {

        //发电月累计计划
        String yjhSql = "SELECT MONTH_PLAN "
                + " FROM SGPC.PC_GROUPDAILY_SJ_001 "
                + " where to_char(DATE_CODE,'YYYYMM') = to_char(SYSDATE-1,'yyyymm') and type='电力分厂'";

        //月累计发电
        String yfdSql = "select calendar ,ZB_NAME ,ZB_VALUE "
                + " from SGMC.MC_SA_ZB_PP_01 "
                + " where zb_name = '迁钢发电计产量月指标' and CALENDAR=to_char(current date-1 ,'yyyy-mm') "
                + " order by calendar desc";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yfdList = jdbcTemplate.queryForList(yfdSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yfdList != null && yfdList.size() > 0) {
            obj.put("zbValue", yfdList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clGffdlydqs(Integer monthNums) {

        monthNums = formartMonthNums(monthNums);

        String sql = "select calendar,zb_name,zb_value "
                + " from SGMC.MC_SA_ZB_PP_01 "
                + " where zb_name ='迁钢发电计产量月指标' and calendar > to_char(current date-" + monthNums + " month,'yyyy-mm') "
                + " and  calendar <= to_char(current date-1,'yyyy-mm') order by calendar ";
        return queryDataService.query(sql);
    }


    @Override
    public Object clSxrcl() {
        //日产量
        String rclSql = "select ZB_VALUE from SGMC.MC_SA_ZB_PP_01 where ZB_NAME ='酸洗商品材日指标' "
                + " and CALENDAR =to_char(current date-1 ,'yyyy-mm-dd')";

        //日计划产量
        String rjhSql = "select sum(ZB_value) ZB_VALUE from sgmc.MC_SA_ZB_PP_01 t where t.ZB_NAME in ('酸洗计划量日指标') "
                + " and CALENDAR=to_char(current date -1,'yyyy-mm-dd')";

        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clSxycl() {
        //月计划产量
        String yjhSql = "SELECT TO_CHAR(DATE_CODE, 'YYYY-MM') AS DATE_CODE, "
                + "TYPE,MONTH_PLAN/10000 as MONTH_PLAN "
                + "FROM SGPC.PC_GROUPDAILY_SJ_001 "
                + "WHERE TYPE = '酸洗卷' "
                + "AND TO_CHAR(DATE_CODE, 'YYYY-MM') =  TO_CHAR(CURRENT DATE-1, 'YYYY-MM')";

        //月产量
        String yclSql = "SELECT CALENDAR, ZB_NAME, SUM(ZB_VALUE) AS ZB_VALUE "
                + " FROM SGMC.MC_SA_ZB_PP_01 "
                + " WHERE ZB_NAME = '酸洗商品材月指标' "
                + "  AND CALENDAR = TO_CHAR(CURRENT DATE-1, 'YYYY-MM') "
                + " GROUP BY CALENDAR, ZB_NAME";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clSxyspccljg(Integer monthNums) {
        String sql = "SELECT\n"
                + "   t.calendar,\n"
                + "   t.zb_name,\n"
                + "   CASE \n"
                + "      WHEN t.zb_name like '%以下汽车板'\n"
                + "      THEN 3\n"
                + "      WHEN t.zb_name='酸洗普通' \n"
                + "      THEN 1\n"
                + "      WHEN t.zb_name like '%以上汽车板'\n"
                + "      THEN 2 \n"
                + "   END orderby,\n"
                + "   t.zb_value \n"
                + "FROM\n"
                + "   (  SELECT to_char(current date-1,'yyyy-mm')as\n"
                + "         calendar,\n"
                + "         decode(ZB_NAME,'酸洗2.0以下汽车板计产量日指标','酸洗2.0以下汽车板', '酸洗普通计产量日指标','酸洗普通', \n"
                + "         '酸洗2.0以上汽车板计产量日指标','酸洗2.0以上汽车板') zb_name,\n"
                + "         sum(ZB_VALUE) as zb_value\n"
                + "      FROM\n"
                + "         SGMC.MC_SA_ZB_PP_01 \n"
                + "      WHERE\n"
                + "         ZB_NAME IN ('酸洗2.0以下汽车板计产量日指标',\n"
                + "         '酸洗普通计产量日指标',\n"
                + "         '酸洗2.0以上汽车板计产量日指标') AND\n"
                + "         CALENDAR<=to_char(current date-1,\n"
                + "         'yyyy-mm-dd')and calendar>=to_char(trunc(current date-1,'mm'),'yyyy-mm-dd')group by zb_name) t\n"
                + "         order by orderby desc";
        return queryDataService.query(sql);
    }


    @Override
    public Object clQtrcl() {
        //日计划产量
        String rjhSql = "select sum(ZB_value) ZB_VALUE from sgmc.MC_SA_ZB_PP_01 t  "
                + "where t.ZB_NAME in ('球团计划量日指标') and CALENDAR=to_char(current date -1,'yyyy-mm-dd')";

        //日产量
        String rclSql = "select ZB_VALUE from SGMC.MC_SA_ZB_PP_01 "
                + "where ZB_NAME ='球团产量明细日指标' and CALENDAR =to_char(current date-1 ,'yyyy-mm-dd')";

        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clQtycl() {
        //月计划产量
        String yjhSql = "SELECT TO_CHAR (DATE_CODE, 'YYYY-MM') AS DATE_CODE,\n"
                + "       TYPE,\n"
                + "       MONTH_PLAN / 10000 AS MONTH_PLAN\n"
                + "FROM SGPC.PC_GROUPDAILY_SJ_001\n"
                + "WHERE     TYPE = '球团合计'\n"
                + "  AND TO_CHAR (DATE_CODE, 'YYYY-MM') =\n"
                + "      TO_CHAR (CURRENT DATE - 1, 'YYYY-MM')";

        //月产量
        String yclSql = "SELECT CALENDAR, ZB_NAME, SUM (ZB_VALUE) AS ZB_VALUE\n"
                + "      FROM SGMC.MC_SA_ZB_PP_01\n"
                + "      WHERE     ZB_NAME = '球团产量明细月指标'\n"
                + "        AND CALENDAR = TO_CHAR (CURRENT DATE - 1, 'YYYY-MM')\n"
                + "      GROUP BY CALENDAR, ZB_NAME";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }


    @Override
    public Object clSjrcl() {
        //日计划产量
        String rjhSql = "select sum(ZB_value) zb_value from sgmc.MC_SA_ZB_PP_01 t \n"
                + "where t.ZB_NAME in ('烧结计划量日指标') and CALENDAR=to_char(current date -1,'yyyy-mm-dd')";

        //日产量
        String rclSql = "select zb_value from SGMC.MC_SA_ZB_PP_01 where ZB_NAME ='烧结产量明细日指标'\n"
                + "and CALENDAR =to_char(current date-1 ,'yyyy-mm-dd')";


        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clSjycl() {
        //月计划产量
        String yjhSql = "SELECT TO_CHAR(DATE_CODE, 'YYYY-MM') AS DATE_CODE, "
                + "       TYPE,MONTH_PLAN/10000 as MONTH_PLAN "
                + " FROM SGPC.PC_GROUPDAILY_SJ_001 "
                + " WHERE TYPE = '烧结合计' "
                + "  AND TO_CHAR(DATE_CODE, 'YYYY-MM') =  TO_CHAR(CURRENT DATE-1, 'YYYY-MM')";

        //月产量
        String yclSql = "SELECT CALENDAR, ZB_NAME, SUM(ZB_VALUE) AS ZB_VALUE\n"
                + "FROM SGMC.MC_SA_ZB_PP_01\n"
                + "WHERE ZB_NAME = '烧结产量明细月指标'\n"
                + "  AND CALENDAR = TO_CHAR(CURRENT DATE-1, 'YYYY-MM')\n"
                + "GROUP BY CALENDAR, ZB_NAME";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }


    @Override
    public Object clLtrcl() {
        //日计划产量
        String rjhSql = "select sum(ZB_value) ZB_VALUE from sgmc.MC_SA_ZB_PP_01 t \n"
                + "where t.ZB_NAME in ('炼铁高炉计划量日指标') and CALENDAR=to_char(current date -1,'yyyy-mm-dd')";

        //日产量
        String rclSql = "select calendar ,zb_name ,ZB_VALUE from SGMC.MC_SA_ZB_PP_01 \n"
                + "where ZB_NAME ='炼铁产量明细日指标' and CALENDAR = to_char(current date-1,'yyyy-mm-dd')  order by calendar desc ";


        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clLtycl() {
        //月计划产量
        String yjhSql = "SELECT TO_CHAR(DATE_CODE, 'YYYY-MM') AS DATE_CODE,\n"
                + "       TYPE,MONTH_PLAN/10000 as MONTH_PLAN\n"
                + "FROM SGPC.PC_GROUPDAILY_SJ_001\n"
                + "WHERE TYPE = '炼铁合计'\n"
                + "  AND TO_CHAR(DATE_CODE, 'YYYY-MM') =\n"
                + "      TO_CHAR(CURRENT DATE-1, 'YYYY-MM')";

        //月产量
        String yclSql = "SELECT CALENDAR, ZB_NAME, SUM(ZB_VALUE) AS ZB_VALUE\n"
                + "FROM SGMC.MC_SA_ZB_PP_01\n"
                + "WHERE ZB_NAME = '炼铁产量明细月指标'\n"
                + "  AND CALENDAR = TO_CHAR(CURRENT DATE-1, 'YYYY-MM')\n"
                + "GROUP BY CALENDAR, ZB_NAME";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clLtglcl() {
        String sql = "select * from (SELECT calendar,zb_code,\n"
                + "                      decode(ZB_NAME,'一高炉产量明细日指标','1#高炉产量','二高炉产量明细日指标','2#高炉产量','三高炉产量明细日指标','3#高炉产量')\n"
                + "                   as zb_name,zb_value,unit_name,etl_freq,etl_dt,rank()over(order by CALENDAR desc) rn\n"
                + "               FROM SGMC.MC_SA_ZB_PP_01\n"
                + "               WHERE etl_freq = '日'\n"
                + "                 AND zb_name LIKE '%高炉产量明细日指标%') where rn=1 and calendar= to_char(current date-1,'yyyy-mm-dd')\n"
                + "order by zb_name";
        return queryDataService.query(sql);
    }


    @Override
    public Object clLgrcl() {


        //日计划产量
        String rjhSql = "select sum(ZB_value) zb_value from sgmc.MC_SA_ZB_PP_01 t "
                + "where t.ZB_NAME in ('二炼钢计划量日指标','一炼钢计划量日指标') and CALENDAR=to_char(current date -1,'yyyy-mm-dd')";

        //日产量
        String rclSql = "select zb_value from SGMC.MC_SA_ZB_PP_01 "
                + " where ZB_NAME ='炼钢产量明细日指标' and CALENDAR =to_char(current date-1 ,'yyyy-mm-dd')";


        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clLgycl() {
        //月计划产量
        String yjhSql = "SELECT TO_CHAR(DATE_CODE, 'YYYY-MM') AS DATE_CODE,\n"
                + "       TYPE,MONTH_PLAN/10000 as month_plan\n"
                + "FROM SGPC.PC_GROUPDAILY_SJ_001\n"
                + "WHERE TYPE = '炼钢合计'\n"
                + "  AND TO_CHAR(DATE_CODE, 'YYYY-MM') =\n"
                + "      TO_CHAR(CURRENT DATE-1, 'YYYY-MM')";

        //月产量
        String yclSql = "SELECT CALENDAR, ZB_NAME, SUM(ZB_VALUE) AS ZB_VALUE\n"
                + "FROM SGMC.MC_SA_ZB_PP_01\n"
                + "WHERE ZB_NAME = '炼钢产量明细月指标'\n"
                + "  AND CALENDAR = TO_CHAR(CURRENT DATE-1, 'YYYY-MM')\n"
                + "GROUP BY CALENDAR, ZB_NAME";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clLgcxcl() {
        String sql = "SELECT case t.zb_code when 'LG1CLMX' then '一炼钢' when 'LG2CLMX' then '二炼钢' else '' end as cx_name, "
                + " t.zb_code, t.unit_name, t.zb_value as day_value , y.zb_value as month_value\n"
                + "FROM (\n"
                + "     SELECT calendar,zb_code,zb_name,zb_value,unit_name,etl_freq,etl_dt\n"
                + "     FROM SGMC.MC_SA_ZB_PP_01\n"
                + "     WHERE etl_freq = '日' AND zb_name LIKE '%炼钢产量明细日指标%'\n"
                + "       and calendar = to_char(current date-1,'yyyy-mm-dd') ) t\n"
                + " inner join (\n"
                + " select *\n"
                + " from (SELECT calendar,zb_code, decode(ZB_NAME,'一炼钢产量明细月指标','一炼钢','二炼钢产量明细月指标','二炼钢') as zb_name,\n"
                + "               zb_value,unit_name,etl_freq,etl_dt,rank()over(order by CALENDAR desc) rn\n"
                + "      FROM SGMC.MC_SA_ZB_PP_01\n"
                + "      WHERE etl_freq = '月' and calendar = to_char(current date-1,'yyyy-mm')\n"
                + "        AND zb_name LIKE '%炼钢产量明细月指标%') where rn=1 ) y on t.zb_code = y.zb_code and t.zb_code <> 'LGCLMX' "
                + " order by t.zb_value desc";
        return queryDataService.query(sql);
    }

    @Override
    public Object clLgrlgls() {
        String sql = "SELECT calendar,zb_code,decode(ZB_NAME,'一炼钢炉数日指标','一炼钢','二炼钢炉数日指标','二炼钢') zb_name,zb_value,unit_name,etl_freq,etl_dt\n"
                + "FROM SGMC.MC_SA_ZB_PP_01 where\n"
                + "    zb_name LIKE '%炼钢炉数%'\n"
                + "                           AND calendar = to_char(current date-1,'yyyy-mm-dd')\n"
                + "order by zb_name desc";
        return queryDataService.query(sql);
    }


    @Override
    public Object clRzrcl() {

        //日计划产量
        String rjhSql = "select sum(ZB_value) ZB_VALUE "
                + "     from sgmc.MC_SA_ZB_PP_01 t "
                + "     where t.ZB_NAME in ('一热轧计划量日指标','二热轧计划量日指标') "
                + "       and CALENDAR=to_char(current date -1,'yyyy-mm-dd')";

        //日产量
        String rclSql = "select zb_value from SGMC.MC_SA_ZB_PP_01 "
                + "      where ZB_NAME ='热轧商品材日指标' "
                + "        and CALENDAR =to_char(current date-1 ,'yyyy-mm-dd')";


        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;

    }

    @Override
    public Object clRzycl() {


        //月计划产量
        String yjhSql = " SELECT TO_CHAR(DATE_CODE, 'YYYY-MM') AS DATE_CODE,\n"
                + "       TYPE,MONTH_PLAN/10000 as MONTH_PLAN "
                + " FROM SGPC.PC_GROUPDAILY_SJ_001\n"
                + " WHERE TYPE = '热轧合计'\n"
                + "  AND TO_CHAR(DATE_CODE, 'YYYY-MM') =\n"
                + "      TO_CHAR(CURRENT DATE-1, 'YYYY-MM') ";

        //月产量
        String yclSql = "SELECT CALENDAR, ZB_NAME, SUM(ZB_VALUE) AS ZB_VALUE "
                + " FROM SGMC.MC_SA_ZB_PP_01 "
                + " WHERE ZB_NAME = '热轧商品材月指标' "
                + "  AND CALENDAR = TO_CHAR(CURRENT DATE-1, 'YYYY-MM') "
                + " GROUP BY CALENDAR, ZB_NAME ";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clRzcxcl() {
        String sql = "select case t.zb_code when 'RZ1CLMX' then '一热轧' when 'RZ2CLMX' then '二热轧' else '' end as cx_name,\n"
                + "       t.zb_code,t.unit_name, t.zb_value as day_value , y.zb_value as month_value\n"
                + "       from (\n"
                + "SELECT calendar,zb_code,zb_name,zb_value,unit_name,etl_freq,etl_dt\n"
                + "FROM SGMC.MC_SA_ZB_PP_01\n"
                + "WHERE etl_freq = '日'\n"
                + "  AND zb_name LIKE '%热轧产量明细日指标%' and calendar = to_char(current date-1,'yyyy-mm-dd')\n"
                + "ORDER BY calendar DESC ) t\n"
                + "inner join (\n"
                + "select * from SGMC.MC_SA_ZB_PP_01\n"
                + "         where ZB_NAME like '%热轧产量明细月指标%' AND CALENDAR = TO_CHAR(CURRENT DATE-1,'YYYY-MM')) y on t.zb_code = y.zb_code\n"
                + "order by t.zb_value desc";
        return queryDataService.query(sql);
    }

    @Override
    public Object clRzyspccljg(String cxName) {

        if ("一热轧".equals(cxName) || "二热轧".equals(cxName)) {
            String sql = "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME, '_',1) + 1,INSTR(ZB_NAME, '_',-1) - INSTR(ZB_NAME, '_') - 1) as zb_name,zb_value/10000 as zb_value,unit_name from sgmc.V_MC_ZB_PP_SPC_M\n"
                    + "where zb_name like '%商品材%' "
                    + "  and zb_name like '%" + cxName + "%' "
                    + "  and calendar=to_char(current date-1 ,'yyyy-mm') "
                    + "order by zb_value";
            return queryDataService.query(sql);
        } else {
            return null;
        }


    }


    @Override
    public Object clZxrcl() {

        //日计划产量
        String rjhSql = "select sum(ZB_value) zb_value\n"
                + "      from sgmc.MC_SA_ZB_PP_01 t\n"
                + "      where t.ZB_NAME in ('一冷轧连退计划量日指标', '二冷轧连退计划量日指标', '二冷轧取向计划量日指标')\n"
                + "        and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd')";

        //日产量
        String rclSql = "select zb_value\n"
                + "      from SGMC.MC_SA_ZB_PP_01\n"
                + "      where ZB_NAME = '硅钢商品材日指标'\n"
                + "        and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd') ";


        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;

    }

    @Override
    public Object clZxycl() {

        //月计划产量
        String yjhSql = "SELECT  (NVL(T.QG_LZ_LT_1,0)+NVL(T.QG_LZ_LT_2,0)+NVL(T.QG_LZ_QX,0))/10000 as MONTH_PLAN "
                + "     FROM SGPC.PC_GROUPDAILY_PLANMONTH_001 T WHERE  to_char(DATE_CODE,'YYYYMM') = to_char(SYSDATE-1,'yyyymm') ";

        //月产量
        String yclSql = "select calendar ,zb_name ,ZB_VALUE "
                + " from SGMC.MC_SA_ZB_PP_01 "
                + " where ZB_NAME ='硅钢商品材月指标'  and CALENDAR=to_char(current date-1 ,'yyyy-mm')";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clZxyspccljg() {
        String sql = "select calendar,\n"
                + "       right(left(zb_name, length(zb_name) - 14), length(left(zb_name, length(zb_name) - 14)) - 4) as zb_name,\n"
                + "       zb_value\n"
                + " from SGMC.MC_SA_ZB_PP_01\n"
                + " where ZB_NAME in ('硅钢无取向中低牌号产量明细月指标', '硅钢无取向高牌号产量明细月指标', '硅钢取向产量明细月指标')\n"
                + "  and CALENDAR = to_char(current date - 1, 'yyyy-mm')\n"
                + " order by zb_name";
        return queryDataService.query(sql);
    }

    @Override
    public Object clSlrcl() {
        //日计划产量
        String rjhSql = "select sum(ZB_value) zb_value\n"
                + "      from sgmc.MC_SA_ZB_PP_01 t\n"
                + "      where t.ZB_NAME in ('顺冷镀锌计划量日指标', '顺冷连退计划量日指标', '顺冷冷硬计划量日指标')\n"
                + "        and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd')";

        //日产量
        String rclSql = "select zb_value\n"
                + "      from SGMC.MC_SA_ZB_PP_01\n"
                + "      where ZB_NAME = '顺冷商品材日指标'\n"
                + "        and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd') ";

        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clSlycl() {

        //月计划产量
        String yjhSql = "SELECT date_code, month_plan / 10000 as MONTH_PLAN\n"
                + "      FROM SGPC.PC_GROUPDAILY_SJ_009\n"
                + "      where to_char(DATE_CODE, 'YYYYMM') = to_char(SYSDATE - 1, 'yyyymm')\n"
                + "        and TYPE = '顺冷合计' ";

        //月产量
        String yclSql = "select calendar, zb_name, sum(zb_value) zb_value\n"
                + "from (\n"
                + "         select left(calendar, 7) calendar, decode(zb_name, '顺冷商品材日指标', '顺冷商品材月指标', zb_name) zb_name, zb_value\n"
                + "         from SGMC.MC_SA_ZB_PP_01\n"
                + "         where ZB_NAME = '顺冷商品材日指标'\n"
                + "           and left(calendar, 7) = to_char(current date, 'yyyy-mm'))\n"
                + "group by zb_name, calendar";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clSlyspccljg() {
        String sql = "select calendar,zb_name,zb_code,zb_value,unit_name,etl_freq\n"
                + "from (SELECT calendar, zb_code,\n"
                + "             decode(ZB_NAME, '顺冷冷硬产量明细月指标', '冷硬', '顺冷镀锌产量明细月指标', '镀锌', '顺冷连退产量明细月指标', '连退') as zb_name,\n"
                + "             zb_value, unit_name, etl_freq, etl_dt, rank()over (order by CALENDAR desc)                                               rn\n"
                + "      FROM SGMC.MC_SA_ZB_PP_01\n"
                + "      WHERE etl_freq = '月'\n"
                + "        AND zb_name LIKE '顺冷%产量明细月指标%')\n"
                + "where rn = 1\n"
                + "order by zb_name\n"
                + "fetch first 3 rows only";
        return queryDataService.query(sql);
    }

    @Override
    public Object clSlyspccljglz() {
        String sql = "select calendar, '冷轧' || SUBSTR(ZB_NAME, instr(zb_name, '_', 1, 1) + 1,\n"
                + "                      INSTR(ZB_NAME, '_', 1, 2) - instr(zb_name, '_', 1, 1) - 1) zb_name, zb_value\n"
                + "from SGMC.V_MC_ZB_PP_SPC_M\n"
                + "where calendar = to_char(current date - 1, 'yyyy-mm')\n"
                + "  and zb_name like '%顺冷%'";
        return queryDataService.query(sql);
    }

    @Override
    public Object clQjrcl() {
        //日计划产量
        String rjhSql = "SELECT date_code,\n"
                + "             DAY_PLAN / 10000        as DAY_PLAN,\n"
                + "             ACCUMULATE_PLAN / 10000 as ACCUMULATE_PLAN,\n"
                + "             MONTH_PLAN / 10000      as MONTH_PLAN\n"
                + "      FROM SGPC.PC_GROUPDAILY_QJ_001\n"
                + "      WHERE date_code = to_char(current date - 1, 'yyyy-mm-dd')";

        //日产量
        String rclSql = "select calendar, zb_name, ZB_VALUE\n"
                + "      from SGMC.MC_SA_ZB_PP_01\n"
                + "      where ZB_NAME = '迁焦产量明细日指标'\n"
                + "        and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd') ";


        List<Map<String, Object>> rjhList = jdbcTemplate.queryForList(rjhSql);
        List<Map<String, Object>> rclList = jdbcTemplate.queryForList(rclSql);

        JSONObject obj = new JSONObject();
        if (rjhList != null && rjhList.size() > 0) {
            obj.put("dayPlan", rjhList.get(0).get("DAY_PLAN"));
        } else {
            obj.put("dayPlan", 0);
        }

        if (rclList != null && rclList.size() > 0) {
            obj.put("zbValue", rclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clQjycl() {

        //月计划产量
        String yjhSql = "SELECT date_code,\n"
                + "             DAY_PLAN / 10000        as DAY_PLAN,\n"
                + "             ACCUMULATE_PLAN / 10000 as ACCUMULATE_PLAN,\n"
                + "             MONTH_PLAN / 10000      as MONTH_PLAN\n"
                + "      FROM SGPC.PC_GROUPDAILY_QJ_001\n"
                + "      WHERE date_code = to_char(current date - 1, 'yyyy-mm-dd')";

        //月产量
        String yclSql = "select calendar, zb_name, zb_value\n"
                + "      from SGMC.MC_SA_ZB_PP_01\n"
                + "      where ZB_NAME = '迁焦产量明细月指标'\n"
                + "        and CALENDAR = to_char(current date - 1, 'yyyy-mm')";

        List<Map<String, Object>> yjhList = jdbcTemplate.queryForList(yjhSql);
        List<Map<String, Object>> yclList = jdbcTemplate.queryForList(yclSql);

        JSONObject obj = new JSONObject();
        if (yjhList != null && yjhList.size() > 0) {
            obj.put("monthPlan", yjhList.get(0).get("MONTH_PLAN"));
        } else {
            obj.put("monthPlan", 0);
        }

        if (yclList != null && yclList.size() > 0) {
            obj.put("zbValue", yclList.get(0).get("ZB_VALUE"));
        } else {
            obj.put("zbValue", 0);
        }
        return obj;
    }

    @Override
    public Object clQjygsjl() {
        String sql = "select calendar,decode(zb_name,'干焦累计原料量日指标','干焦','湿焦累计原料量日指标','湿焦')  as zb_name,zb_value\n"
                + " from SGMC.MC_SA_ZB_PP_01\n"
                + " where zb_name like '%焦累计原料量日指标%' and calendar = to_char(current date-1, 'yyyy-mm')\n"
                + " order by zb_name";
        return queryDataService.query(sql);
    }


    @Override
    public Object kcZbcpkcmx() {
        String sql = "select calendar, zb_name, sum(zb_value)zb_value\n"
                + "from (select *\n"
                + "      from (select calendar, zb_name, round(zb_value / 10000, 2) zb_value\n"
                + "            from (select calendar, '在半产品库存' zb_name, sum(zb_value) zb_value\n"
                + "                  from sgmc.MC_SA_ZB_PP_KC_H\n"
                + "                  where ZB_NAME like '%_半成品_库存'\n"
                + "                  group by calendar, '半成品库存'\n"
                + "                  union all\n"
                + "                  select calendar, '3月以上' zb_name, sum(zb_value) zb_value\n"
                + "                  from sgmc.MC_SA_ZB_PP_KCCL_H\n"
                + "                  where ZB_NAME like '%3月以上_库存超龄'\n"
                + "                  group by calendar, '3月以上')\n"
                + "            union all\n"
                + "            select calendar, '在半产品库存'zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "            from (SELECT calendar, SUBSTR(ZB_NAME, '1', INSTR(ZB_NAME, '_') - 1) as zb_name, zb_value, unit_name\n"
                + "                  FROM SGMC.V_MC_ZB_PP_ZXKC\n"
                + "                  where zb_name like '%智新一区%'\n"
                + "                    and calendar <= current date - 1)\n"
                + "            group by zb_name, calendar\n"
                + "            union all\n"
                + "            select calendar, '在半产品库存'zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "            from (SELECT calendar, SUBSTR(ZB_NAME, '1', INSTR(ZB_NAME, '_') - 1) as zb_name, zb_value, unit_name\n"
                + "                  FROM SGMC.V_MC_ZB_PP_ZXKC\n"
                + "                  where zb_name like '%智新二区%'\n"
                + "                    and calendar <= current date - 1)\n"
                + "            group by zb_name, calendar\n"
                + "            union all\n"
                + "            select calendar, '在半产品库存'zb_name, sum(zb_value) / 10000 as zb_value\n"
                + "            from (SELECT calendar, SUBSTR(ZB_NAME, '1', INSTR(ZB_NAME, '_') - 1) as zb_name, zb_value, unit_name\n"
                + "                  FROM SGMC.V_MC_ZB_PP_ZXKC\n"
                + "                  where zb_name like '%智新三区%'\n"
                + "                    and calendar <= current date - 1)\n"
                + "            group by zb_name, calendar\n"
                + "            order by calendar)\n"
                + "      where calendar >= to_char(current date - 30, 'yyyy-mm-dd'))\n"
                + "group by calendar, zb_name";
        return queryDataService.query(sql);

    }


    @Override
    public Object kcZbcpkcfb() {
        String sql = "select *\n"
                + "from (select calendar,\n"
                + "             decode(zb_name, '一炼钢', 1, '二炼钢', 2, '一热轧', 3, '二热轧', 4, '酸洗', 5, '顺义冷轧', 6, '顺冷', 6, '智新一区', 7, '智新二区', 8,\n"
                + "                    '智新三区', 9, null) orderby,\n"
                + "             zb_name,\n"
                + "             zb_value,\n"
                + "             '吨'unit_name\n"
                + "      from (select calendar, left(zb_name, INSTR(ZB_NAME, '_', 1) - 1) zb_name, zb_value / 10000 zb_value\n"
                + "            from sgmc.MC_SA_ZB_PP_01\n"
                + "            where ZB_NAME like '%_半成品_库存')\n"
                + "      union all\n"
                + "      select calendar, '7'orderby, zb_name, sum(zb_value) / 10000 as zb_value, unit_name\n"
                + "      from (SELECT calendar, SUBSTR(ZB_NAME, '1', INSTR(ZB_NAME, '_') - 1) as zb_name, zb_value, unit_name\n"
                + "            FROM SGMC.V_MC_ZB_PP_ZXKC\n"
                + "            where zb_name like '%智新一区%'\n"
                + "              and calendar = current date - 1)\n"
                + "      group by zb_name, calendar, unit_name\n"
                + "      union all\n"
                + "      select calendar, '8'orderby, zb_name, sum(zb_value) / 10000 as zb_value, unit_name\n"
                + "      from (SELECT calendar, SUBSTR(ZB_NAME, '1', INSTR(ZB_NAME, '_') - 1) as zb_name, zb_value, unit_name\n"
                + "            FROM SGMC.V_MC_ZB_PP_ZXKC\n"
                + "            where zb_name like '%智新二区%'\n"
                + "              and calendar = current date - 1)\n"
                + "      group by zb_name, calendar, unit_name\n"
                + "      union all\n"
                + "      select calendar, '9'orderby, zb_name, sum(zb_value) / 10000 as zb_value, unit_name\n"
                + "      from (SELECT calendar, SUBSTR(ZB_NAME, '1', INSTR(ZB_NAME, '_') - 1) as zb_name, zb_value, unit_name\n"
                + "            FROM SGMC.V_MC_ZB_PP_ZXKC\n"
                + "            where zb_name like '%智新三区%'\n"
                + "              and calendar = current date - 1)\n"
                + "      group by zb_name, calendar, unit_name)\n"
                + " where calendar = current date - 1 "
                + " order by orderby ";
        return queryDataService.query(sql);
    }


    @Override
    public Object kcLgkcjg(String typeName) {
        String sql = "SELECT CALENDAR,\n"
                + "       SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1, 1) + 1,\n"
                + "              INSTR(ZB_NAME, '_', 1, 2) - INSTR(ZB_NAME, '_', 1, 1) - 1) ZB_NAME,\n"
                + "       ZB_VALUE / 10000                                                  ZB_VALUE\n"
                + " FROM SGMC.MC_SA_ZB_PP_01\n"
                + " WHERE ZB_NAME LIKE '" + typeName + "%库存超龄%'"
                + "  AND CALENDAR = CURRENT DATE - 1";
        return queryDataService.query(sql);
    }


    @Override
    public Object kcLg1kcqs(String typeName) {
        String sql = "select calendar,\n"
                + "       substr(zb_name, instr(zb_name, '_', 1, 1) + 1,\n"
                + "              instr(zb_name, '_', 1, 2) - instr(zb_name, '_', 1, 1) - 1) zb_name,\n"
                + "       round(decode(zb_value, null, 0, zb_value) / 10000, 2) as          zb_value\n"
                + "from sgmc.MC_SA_ZB_PP_KC_H t\n"
                + "where t.ZB_NAME like '%" + typeName + "%库存趋势'\n"
                + "  and calendar >= current date - 30\n"
                + "order by calendar";
        Object kcqsData = queryDataService.query(sql);
        Object kcyzData = getThreshold("一炼钢" + typeName + "库");
        JSONObject obj = new JSONObject();
        obj.put("kcqsData", kcqsData);
        obj.put("kcyzData", kcyzData);
        return obj;
    }

    @Override
    public Object kcLg2kcqs() {
        String sql = "SELECT CALENDAR,\n"
                + "       SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1, 1) + 1, INSTR(ZB_NAME, '_', 1, 2) - INSTR(ZB_NAME\n"
                + "           , '_', 1, 1) - 1)                                    ZB_NAME,\n"
                + "       ROUND(decode(zb_value, null, 0, zb_value) / 10000, 2) AS ZB_VALUE\n"
                + "FROM SGMC.MC_SA_ZB_PP_KC_H T\n"
                + "WHERE T.ZB_NAME LIKE '二炼钢%半成品%'\n"
                + "  AND CALENDAR >= CURRENT DATE - 30\n"
                + "ORDER BY CALENDAR";
        Object kcqsData = queryDataService.query(sql);
        Object kcyzData = getThreshold("二炼钢板坯库");
        JSONObject obj = new JSONObject();
        obj.put("kcqsData", kcqsData);
        obj.put("kcyzData", kcyzData);
        return obj;
    }


    @Override
    public Object kcRzkcjg(String typeName) {
        String sql = "select calendar,\n"
                + "       substr(zb_name, instr(zb_name, '_', 1, 1) + 1,\n"
                + "              instr(zb_name, '_', 1, 2) - instr(zb_name, '_', 1, 1) - 1) zb_name,\n"
                + "       zb_value / 10000                                                  zb_value\n"
                + " from sgmc.MC_SA_ZB_PP_01\n"
                + " where zb_name like '" + typeName + "%结构%'\n"
                + "  and zb_name not like '%合计%'\n"
                + "  and zb_name not like '%销售发运%'\n"
                + "  and calendar = current date - 1";
        return queryDataService.query(sql);
    }


    @Override
    public Object kcRzkcqs(String typeName) {
        String sql = "select calendar,substr(zb_name,instr(zb_name,'_',1,1)+1,instr(zb_name,'_',1,2)-instr(zb_name,'_',1,1)-1) zb_name, "
                + " round(decode(zb_value,null,0,zb_value)/10000,2) as zb_value "
                + " from sgmc.MC_SA_ZB_PP_KC_H t "
                + " where t.ZB_NAME  like '%" + typeName + "%库存趋势' and calendar>=current date-30 order by calendar";

        Object kcqsData = queryDataService.query(sql);
        String yzName = typeName.replace("_", "") + "库";
        Object kcyzData = getThreshold(yzName);
        JSONObject obj = new JSONObject();
        obj.put("kcqsData", kcqsData);
        obj.put("kcyzData", kcyzData);

        return obj;
    }


    @Override
    public Object kcSxkcjg() {
        String sql = "select calendar,substr(zb_name,instr(zb_name,'_',1,1)+1,instr(zb_name,'_',1,2)-instr(zb_name,'_',1,1)-1) zb_name,"
                + " zb_value/10000 zb_value "
                + " from sgmc.MC_SA_ZB_PP_01 "
                + " where zb_name like '酸洗%结构%' and zb_name not like '%合计%' and zb_name not like '%销售发运%' and calendar=current date-1";
        return queryDataService.query(sql);
    }


    @Override
    public Object kcSxkcqs(String typeName) {
        String sql = "SELECT calendar,\n"
                + "       substr(zb_name, instr(zb_name, '_', 1, 1) + 1,\n"
                + "              instr(zb_name, '_', 1, 2) - instr(zb_name, '_', 1, 1) - 1) zb_name,\n"
                + "       round(decode(zb_value, null, 0, zb_value) / 10000, 2) AS          zb_value\n"
                + "FROM sgmc.MC_SA_ZB_PP_KC_H t\n"
                + "WHERE t.ZB_NAME = '酸洗_" + typeName + "_库存趋势' "
                + "  AND calendar >= current date - 30\n"
                + "ORDER BY calendar";

        Object kcqsData = queryDataService.query(sql);
        String yzName = "酸洗" + typeName + "库";
        Object kcyzData = getThreshold(yzName);
        JSONObject obj = new JSONObject();
        obj.put("kcqsData", kcqsData);
        obj.put("kcyzData", kcyzData);

        return obj;
    }


    @Override
    public Object kcSlkcjg() {
        String sql = "select calendar, substr(zb_name,instr(zb_name,'_',1,1)+1,instr(zb_name,'_',1,2)-instr(zb_name,'_',1,1)-1) zb_name,zb_value/10000 zb_value\n"
                + "from sgmc.MC_SA_ZB_PP_01 where zb_name like '顺冷%结构%' and zb_name not like '%合计%' and zb_name not like '%销售发运%' and calendar=current date-1";
        return queryDataService.query(sql);
    }


    @Override
    public Object kcSlkcqs(String typeName) {
        String sql = "select calendar,substr(zb_name,instr(zb_name,'_',1,1)+1,instr(zb_name,'_',1,2)-instr(zb_name,'_',1,1)-1) zb_name,round(decode(zb_value,null,0,zb_value)/10000,2) as zb_value\n"
                + "from sgmc.MC_SA_ZB_PP_KC_H t\n"
                + "where t.ZB_NAME  = '顺冷_" + typeName + "_库存趋势' and calendar>=current date-30 order by calendar";

        Object kcqsData = queryDataService.query(sql);
        String yzName = "顺义" + typeName + "库";
        Object kcyzData = getThreshold(yzName);
        JSONObject obj = new JSONObject();
        obj.put("kcqsData", kcqsData);
        obj.put("kcyzData", kcyzData);

        return obj;
    }


    @Override
    public Object kcZxkcjg(String typeName) {
        String sql = "select calendar,substr(zb_name,instr(zb_name,'_',1,1)+1,instr(zb_name,'_',1,2)-instr(zb_name,'_',1,1)-1) zb_name,zb_value/10000 zb_value \n"
                + "from sgmc.V_MC_ZB_PP_ZXKCJG \n"
                + "where zb_name like '" + typeName + "%结构%' and zb_name not like '%总库存%'  and calendar=current date-1";
        return queryDataService.query(sql);
    }


    @Override
    public Object kcZxkcqs(String typeName) {
        String sql = "select calendar, "
                + "       substr(zb_name, instr(zb_name, '_', 1, 1) + 1, "
                + "              instr(zb_name, '_', 1, 2) - instr(zb_name, '_', 1, 1) - 1) zb_name, "
                + "       round(decode(zb_value, null, 0, zb_value) / 10000, 2) as          zb_value "
                + " from sgmc.V_MC_ZB_PP_ZXKC t "
                + " where t.ZB_NAME = '" + typeName + "_库存趋势' "
                + "  and calendar >= current date - 30 "
                + " order by calendar";
        Object kcqsData = queryDataService.query(sql);
        JSONObject obj = new JSONObject();
        obj.put("kcqsData", kcqsData);

        return obj;
    }


    @Override
    public Object gxzbLiantie(String typeName) {
        String sql = "SELECT calendar, zb_code, zb_name, zb_value, unit_name, etl_freq, etl_dt\n"
                + "FROM SGMC.MC_SA_ZB_PP_01\n"
                + "WHERE zb_name = '" + typeName + "日指标'\n"
                + "  and calendar >= to_char(current date - 7, 'yyyy-mm-dd')\n"
                + "  AND calendar <= to_char(current date - 1, 'YYYY-MM-DD')\n"
                + "order by calendar";
        return queryDataService.query(sql);
    }

    @Override
    public Object gxzbLiangang(String typeName) {

        String sql;

        if (typeName.endsWith("_恒拉速率")) {
            sql = "SELECT * "
                    + "FROM SGMC.MC_SA_ZB_QM_01 "
                    + "WHERE zb_name = '" + typeName + "' "
                    + " and etl_freq = '日'\n"
                    + "and calendar>=to_char(current date-7,'yyyy-mm-dd') AND calendar<= to_char(current date-1,'YYYY-MM-DD') order by calendar";
        } else {
            sql = "SELECT calendar, zb_code, zb_name, zb_value, unit_name, etl_freq, etl_dt\n"
                    + "FROM SGMC.MC_SA_ZB_PP_01\n"
                    + "WHERE zb_name = '" + typeName + "日指标'\n"
                    + "  and calendar >= to_char(current date - 7, 'yyyy-mm-dd')\n"
                    + "  AND calendar <= to_char(current date - 1, 'YYYY-MM-DD')\n"
                    + "order by calendar";
        }

        return queryDataService.query(sql);
    }

    @Override
    public Object gxzbRezha(String typeName) {

        // 热轧热装热送率所在表名为：SGMC.MC_SA_ZB_PP_01 ，其他为：SGMC.MC_SA_ZB_QM_01
        String tableName;
        if (typeName.endsWith("热装热送率")) {
            tableName = "SGMC.MC_SA_ZB_PP_01";
        } else {
            tableName = "SGMC.MC_SA_ZB_QM_01";
        }

        String sql = "SELECT calendar,zb_code,zb_name,zb_value,unit_name,etl_freq,etl_dt "
                + " FROM " + tableName
                + " WHERE zb_name = '" + typeName + "' "
                + " and calendar>=to_char(current date-7,'yyyy-mm-dd') AND calendar<= to_char(current date-1,'YYYY-MM-DD') order by calendar";

        return queryDataService.query(sql);
    }


    @Override
    public Object gxzbLengzha(String typeName) {

        // 酸轧厚度命中率所在表名为：SGMC.V_MC_ZB_QM_SZCKHDMZL ，其他为：SGMC.MC_SA_ZB_QM_01
        String tableName;
        String queryName;
        if ("酸轧_出口厚度命中率".equals(typeName)) {
            tableName = "SGMC.V_MC_ZB_QM_SZCKHDMZL";
            queryName = typeName;
        } else {
            tableName = "SGMC.MC_SA_ZB_QM_01";
            queryName = typeName + "日指标";
        }
        String sql = " SELECT calendar,zb_code,zb_name,zb_value,unit_name,etl_freq,etl_dt\n"
                + " FROM " + tableName
                + " WHERE zb_name = '" + queryName + "' and calendar>=to_char(current date-7,'yyyy-mm-dd') AND calendar<= to_char(current date-1,'YYYY-MM-DD') order by calendar";

        return queryDataService.query(sql);
    }


    @Override
    public Object gxzbSuanxi(String typeName) {

        String sql = "SELECT calendar,zb_code,zb_name,zb_value,unit_name,etl_freq,etl_dt "
                + " FROM SGMC.MC_SA_ZB_QM_01 "
                + " WHERE zb_name = '" + typeName + "日指标' "
                + " and calendar>=to_char(current date-7,'yyyy-mm-dd') AND calendar<= to_char(current date-1,'YYYY-MM-DD') order by calendar ";

        return queryDataService.query(sql);
    }


    @Override
    public Object gjzbZlztlqs(String typeName, String flTypeName, String dateTypeName) {

        String sqlCondition1;
        String sqlCondition2;
        if ("顺义冷轧".equals(typeName)) {
            sqlCondition1 = " and ZB_NAME in ('连退_直通率月指标', '镀锌_直通率月指标', '酸轧_直通率月指标') ";
            sqlCondition2 = " zb_name in('镀锌直通率日指标', '连退直通率日指标', '酸轧直通率日指标') ";
        } else {
            sqlCondition1 = " and decode(left(zb_name,4),'顺冷','顺义冷轧',left(zb_name,6)) like '%" + typeName + "%' ";
            sqlCondition2 = " zb_name like '%" + typeName + "%直通率%' ";
        }


        String sql = " select SUBSTR(calendar, INSTR(calendar, '-', 1, 1) + 1) as calendar,\n"
                + "       zb_name,\n"
                + "       decode(zb_value, null, 0, zb_value)                 zb_value,\n"
                + "       etl_freq\n"
                + " from (select *\n"
                + "      from (select calendar, zb_name, zb_value, etl_freq from sgmc.V_MC_ZB_QM_ZTL_M\n"
                + "            union all\n"
                + "            select calendar, '智新_直通率月指标' zb_name, cast(COALESCE(zb_value, '0') as float), '月' etl_freq\n"
                + "            from sgmc.V_MC_ZB_QM_ZXLV_QS\n"
                + "            where zb_name like '%直通率%')\n"
                + "      where calendar >= to_char(current date - 7 month, 'yyyy-mm')\n"
                + sqlCondition1
                + "      union all select * from (\n"
                + "    select calendar, ZB_NAME, ZB_VALUE, etl_freq from SGMC.MC_SA_ZB_qm_01\n"
                + "      union all select calendar, '智新直通率日指标' zb_name, ZB_VALUE_D zb_value, '日' etl_freq from sgmc.V_MC_ZB_QM_ZXLV where zb_name like '%直通率%')\n"
                + "    where\n"
                + sqlCondition2
                + "    and calendar>=to_char(current date -30, 'yyyy-mm-dd') AND calendar<= to_char(current date -1, 'YYYY-MM-DD')) t\n"
                + "where t.zb_name not like '热轧%'\n"
                + "  and t.zb_name not like '炼钢%'\n"
                + "order by t.calendar";


        String newSql = " select * from (" + sql + ") n where n.zb_name like '%" + flTypeName + "%" + dateTypeName + "%' ";
        /* String yljSql = "select to_char(calendar, 'yyyy-mm') CALENDAR, zb_name, zb_value_m\n"
                 + "from SGMC.V_MC_ZB_QM_ZXLV\n"
                 + "where zb_name = '直通率'\n"
                 + "  and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd')";

         JSONObject object = new JSONObject();
        List<Map<String, Object>> yljList = queryDataService.query(yljSql);
        List<Map<String, Object>> qstList = queryDataService.query(newSql);
        object.put("yljList",yljList);
        object.put("qstList",qstList);*/

        return queryDataService.query(newSql);
    }

    @Override
    public Object gjzbZxZlztlylj() {
        String sql = "select to_char(calendar, 'yyyy-mm') CALENDAR, zb_name, zb_value_m\n"
                + "from SGMC.V_MC_ZB_QM_ZXLV\n"
                + "where zb_name = '直通率'\n"
                + "  and CALENDAR = to_char(current date - 1, 'yyyy-mm-dd')";
        return queryDataService.query(sql);
    }


    @Override
    public Object gjzbZlztlfb(String typeName, String dateTypeName) {

        String sql = " select *\n"
                + " from (select calendar, process, zb_name, sum(zb_value) as zb_value, etl_freq\n"
                + "      from ((select calendar,\n"
                + "                    decode(process, '一热轧', '热轧', '一炼钢', '炼钢', '酸轧', '顺义冷轧', process) process,\n"
                + "                    zb_name,\n"
                + "                    zb_value,\n"
                + "                    etl_freq\n"
                + "             from ((select calendar,\n"
                + "                           decode(process, '酸洗板材', '酸洗', process) process,\n"
                + "                           zb_name,\n"
                + "                           zb_value * 10000                       zb_value,\n"
                + "                           '日'                                    etl_freq\n"
                + "                    from SGMC.V_MC_ZB_QM_QXTOP5\n"
                + "                    where PROCESS in ('二炼钢', '二热轧', '酸洗板材', '一炼钢', '一热轧')\n"
                + "                      and jizu <> '横切'\n"
                + "                      and calendar = to_char(current date - 1, 'yyyy-mm-dd')and length(calendar) > 8)\n"
                + "                   union all\n"
                + "                   (select calendar, process, zb_name, sum(zb_value)zb_value, etl_freq\n"
                + "                    from (select left(calendar, 7)calendar,\n"
                + "                                 decode(process, '酸洗板材', '酸洗', process) process,\n"
                + "                                 zb_name,\n"
                + "                                 zb_value * 10000 zb_value,\n"
                + "                                 '月' etl_freq\n"
                + "                          from SGMC.V_MC_ZB_QM_QXTOP5\n"
                + "                          where PROCESS in ('二炼钢', '二热轧', '酸洗板材', '一炼钢', '一热轧')\n"
                + "                            and jizu <> '横切'\n"
                + "                            and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm'))\n"
                + "                    group by process, zb_name, calendar, etl_freq)\n"
                + "                   union all\n"
                + "                   (select calendar, jizu process, zb_name, zb_value * 10000 zb_value, '日' etl_freq\n"
                + "                    from SGMC.V_MC_ZB_QM_QXTOP5\n"
                + "                    where jizu in ('酸轧', '连退', '镀锌', '重卷')\n"
                + "                      and calendar = to_char(current date - 1, 'yyyy-mm-dd')and length(calendar) > 8)\n"
                + "                   union all\n"
                + "                   (select calendar, process, zb_name, sum(zb_value) as zb_value, etl_freq\n"
                + "                    from (select left(calendar, 7)as calendar,\n"
                + "                                 jizu                process,\n"
                + "                                 zb_name,\n"
                + "                                 zb_value * 10000    zb_value,\n"
                + "                                 '月'                 etl_freq\n"
                + "                          from SGMC.V_MC_ZB_QM_QXTOP5\n"
                + "                          where jizu in ('酸轧', '连退', '镀锌', '重卷')\n"
                + "                            and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm'))\n"
                + "                    group by process, zb_name, calendar, etl_freq)\n"
                + "                   union all\n"
                + "                   (select calendar, '智新' process, zb_name, zb_value, '日' etl_freq from SGMC.V_MC_ZB_QM_ZXQXTOP5_ZT))))\n"
                + "      where process = '" + typeName + "' "
                + "      and etl_freq = '" + dateTypeName + "' "
                + "      group by calendar, process, zb_name, etl_freq\n"
                + "      order by zb_value desc\n"
                + "      fetch first 5 rows only)\n"
                + " order by zb_value ";

        return queryDataService.query(sql);
    }


    @Override
    public Object gjzbDcplqs(String typeName, String flTypeName, String dateTypeName) {
        String sqlCondition1;
        String sqlCondition2;
        if ("顺义冷轧".equals(typeName)) {
            sqlCondition1 = " and ZB_NAME in ('连退_带出品率月指标', '镀锌_带出品率月指标', '酸轧_带出品率月指标') ";
            sqlCondition2 = " zb_name in('镀锌带出品率日指标', '连退带出品率日指标', '酸轧带出品率日指标') ";
        } else {
            sqlCondition1 = " and decode(left(zb_name,4),'顺冷','顺义冷轧',left(zb_name,6)) like '%" + typeName + "%' ";
            sqlCondition2 = " zb_name like '%" + typeName + "%带出品率%' ";
        }

        String sql = " select SUBSTR(calendar, INSTR(calendar, '-', 1, 1) + 1) as calendar,\n"
                + "       zb_name,\n"
                + "       decode(zb_value, null, 0, zb_value)                 zb_value,\n"
                + "       etl_freq\n"
                + " from (select *\n"
                + "      from (select calendar, zb_name, zb_value, etl_freq from sgmc.V_MC_ZB_QM_DCPL_M\n"
                + "            union all\n"
                + "            select calendar, '智新_带出品率月指标' zb_name, cast(COALESCE(zb_value, '0') as float), '月' etl_freq\n"
                + "            from sgmc.V_MC_ZB_QM_ZXLV_QS\n"
                + "            where zb_name like '%带出品率%')\n"
                + "      where calendar >= to_char(current date - 7 month, 'yyyy-mm')\n"
                + sqlCondition1
                + "      union all select * from (\n"
                + "    select calendar, ZB_NAME, ZB_VALUE, etl_freq from SGMC.MC_SA_ZB_qm_01\n"
                + "      union all select calendar, '智新带出品率日指标' zb_name, ZB_VALUE_D zb_value, '日' etl_freq from sgmc.V_MC_ZB_QM_ZXLV where zb_name like '%带出品率%')\n"
                + "    where "
                + sqlCondition2
                + "    and calendar>=to_char(current date -30, 'yyyy-mm-dd') AND calendar<= to_char(current date -1, 'YYYY-MM-DD')) t\n"
                + " where t.zb_name not like '热轧%'\n"
                + "  and t.zb_name not like '炼钢%'\n"
                + " order by t.calendar ";

        String newSql = " select * from (" + sql + ") n where n.zb_name like '%" + flTypeName + "%" + dateTypeName + "%' ";

        return queryDataService.query(newSql);
    }

    @Override
    public Object gjzbZxDcplylj() {
        String sql = " select to_char(current date-1,'yyyy-mm') CALENDAR,zb_name,zb_value_m "
                + " from SGMC.V_MC_ZB_QM_ZXLV "
                + " where zb_name = '带出品率' and  CALENDAR = to_char(current date-1,'yyyy-mm-dd') ";
        return queryDataService.query(sql);
    }

    @Override
    public Object gjzbDcpgc(String typeName, String dateTypeName) {
        String sql = " select *\n"
                + "from ((SELECT CALENDAR, '智新'PROCESS, ZB_NAME, ZB_VALUE, '日'etl_freq\n"
                + "       FROM sgmc.V_MC_ZB_QM_ZXQXTOP5\n"
                + "       where calendar = to_char(current date - 1, 'yyyy-mm-dd'))--智新日数据\n"
                + "      union all\n"
                + "      (select calendar, process, zb_name, sum(zb_value) zb_value, '月'etl_freq\n"
                + "       from (SELECT left(CALENDAR, 7) calendar, '智新'PROCESS, ZB_NAME, ZB_VALUE\n"
                + "             FROM sgmc.V_MC_ZB_QM_ZXQXTOP5\n"
                + "             where calendar <= to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "               and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm')\n"
                + "             order by calendar)\n"
                + "       group by zb_name, calendar, process)--智新月累计\n"
                + "      union all\n"
                + "      (select calendar, decode(process, '一热轧', '热轧', '顺义冷轧', '酸轧', process) as process, zb_name, zb_value, '日'etl_freq\n"
                + "       from (SELECT calendar,\n"
                + "                    left(zb_name, instr(zb_name, '_', 1, 1) - 1)process,\n"
                + "                    right(zb_name, length(zb_name) - instr(zb_name, '_', 1, 1)) ZB_name,\n"
                + "                    zb_value\n"
                + "             FROM sgmc.V_MC_ZB_QM_DCPYY\n"
                + "             WHERE zb_name NOT LIKE '%总计%'\n"
                + "               and length(calendar) > 8\n"
                + "               and calendar = to_char(current date - 1, 'yyyy-mm-dd')))--非智新日数据\n"
                + "      union all\n"
                + "      (select calendar,\n"
                + "              decode(process, '一热轧', '热轧', '顺义冷轧', '酸轧', process) as process,\n"
                + "              zb_name,\n"
                + "              sum(zb_value)                                       as zb_value,\n"
                + "              '月'etl_freq\n"
                + "       from (SELECT left(calendar, 7)calendar,\n"
                + "                    left(zb_name, instr(zb_name, '_', 1, 1) - 1)process,\n"
                + "                    right(zb_name, length(zb_name) - instr(zb_name, '_', 1, 1)) ZB_name,\n"
                + "                    zb_value\n"
                + "             FROM sgmc.V_MC_ZB_QM_DCPYY\n"
                + "             WHERE zb_name NOT LIKE '%总计%'\n"
                + "               and length(calendar) > 8\n"
                + "               and calendar <= to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "               and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm'))\n"
                + "       group by process, zb_name, calendar))\n"
                + "      where process = '" + typeName + "' "
                + "      and etl_freq = '" + dateTypeName + "' ";

        return queryDataService.query(sql);
    }


    @Override
    public Object gjzbFcjlqs(String typeName, String flTypeName, String dateTypeName) {
        String sqlCondition1;
        String sqlCondition2;
        if ("顺义冷轧".equals(typeName)) {
            sqlCondition1 = " and ZB_NAME in ('连退_废次降率月指标', '镀锌_废次降率月指标', '酸轧_废次降率月指标') ";
            sqlCondition2 = " zb_name in('镀锌废次降率日指标', '连退废次降率日指标', '酸轧废次降率日指标') ";
        } else {
            sqlCondition1 = " and decode(left(zb_name,4),'顺冷','顺义冷轧',left(zb_name,6)) like '%" + typeName + "%' ";
            sqlCondition2 = " zb_name like '%" + typeName + "%废次降率%' ";
        }

        String sql = " select SUBSTR(calendar, INSTR(calendar, '-', 1, 1) + 1) as calendar,\n"
                + "       zb_name,\n"
                + "       decode(zb_value, null, 0, zb_value)                 zb_value,\n"
                + "       etl_freq\n"
                + " from (select *\n"
                + "      from (select calendar, zb_name, zb_value, etl_freq from sgmc.V_MC_ZB_QM_FCJL_M\n"
                + "            union all\n"
                + "            select calendar, '智新_废次降率月指标' zb_name, cast(COALESCE(zb_value, '0') as float), '月' etl_freq\n"
                + "            from sgmc.V_MC_ZB_QM_ZXLV_QS\n"
                + "            where zb_name like '%废次降率%')\n"
                + "      where calendar >= to_char(current date - 7 month, 'yyyy-mm')\n"
                + sqlCondition1
                + "      union all select * from (\n"
                + "    select calendar, ZB_NAME, ZB_VALUE, etl_freq from SGMC.MC_SA_ZB_qm_01\n"
                + "      union all select calendar, '智新废次降率日指标' zb_name, ZB_VALUE_D zb_value, '日' etl_freq from sgmc.V_MC_ZB_QM_ZXLV where zb_name like '%废次降率%')\n"
                + "    where " + sqlCondition2
                + "    and calendar>=to_char(current date -30, 'yyyy-mm-dd') AND calendar<= to_char(current date -1, 'YYYY-MM-DD')) t\n"
                + " where t.zb_name not like '热轧%'\n"
                + "  and t.zb_name not like '炼钢%'\n"
                + " order by t.calendar ";

        String newSql = " select * from (" + sql + ") n where n.zb_name like '%" + flTypeName + "%" + dateTypeName + "%' ";

        return queryDataService.query(newSql);
    }

    @Override
    public Object gjzbZxFcjlylj() {
        String sql = " select to_char(current date-1,'mm') CALENDAR,zb_name,zb_value_m from SGMC.V_MC_ZB_QM_ZXLV\n"
                + " where zb_name = '废次降率' and  CALENDAR = to_char(current date-1,'yyyy-mm-dd') ";
        return queryDataService.query(sql);
    }


    @Override
    public Object gjzbFcjgc(String typeName, String dateTypeName) {
        String sql = " select *\n"
                + " from ((SELECT CALENDAR,\n"
                + "              left(zb_name, instr(zb_name, '_', 1, 1) - 1)process,\n"
                + "              right(zb_name, length(zb_name) - instr(zb_name, '_', 1, 1)) ZB_name,\n"
                + "              ZB_VALUE,\n"
                + "              '日'etl_freq\n"
                + "       FROM sgmc.V_MC_ZB_QM_ZXFCJGC\n"
                + "       where calendar = to_char(current date - 1, 'yyyy-mm-dd'))--智新日数据\n"
                + "      union all\n"
                + "      (select calendar, process, zb_name, sum(zb_value) zb_value, '月'etl_freq\n"
                + "       from (SELECT left(CALENDAR, 7) calendar, '智新'PROCESS, ZB_NAME, ZB_VALUE\n"
                + "             FROM sgmc.V_MC_ZB_QM_ZXFCJGC\n"
                + "             where calendar <= to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "               and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm')\n"
                + "             order by calendar)\n"
                + "       group by zb_name, calendar, process)--智新月累计\n"
                + "      union all\n"
                + "      (select calendar,\n"
                + "              decode(process, '一热轧', '热轧', '一炼钢', '炼钢', '顺义冷轧', '酸轧', process) as process,\n"
                + "              zb_name,\n"
                + "              zb_value,\n"
                + "              '日'etl_freq\n"
                + "       from (SELECT calendar,\n"
                + "                    left(zb_name, instr(zb_name, '_', 1, 1) - 1)process,\n"
                + "                    right(zb_name, length(zb_name) - instr(zb_name, '_', 1, 1)) ZB_name,\n"
                + "                    zb_value\n"
                + "             FROM sgmc.V_MC_ZB_QM_FCJGC\n"
                + "             WHERE zb_name NOT LIKE '%总计%'\n"
                + "               and length(calendar) > 8\n"
                + "               and calendar = to_char(current date - 1, 'yyyy-mm-dd')))--非智新日数据\n"
                + "      union all\n"
                + "      (select calendar,\n"
                + "              decode(process, '一热轧', '热轧', '顺义冷轧', '酸轧', '一炼钢', '炼钢', process) as process,\n"
                + "              zb_name,\n"
                + "              sum(zb_value)                                                    as zb_value,\n"
                + "              '月'etl_freq\n"
                + "       from (SELECT left(calendar, 7)calendar,\n"
                + "                    left(zb_name, instr(zb_name, '_', 1, 1) - 1)process,\n"
                + "                    right(zb_name, length(zb_name) - instr(zb_name, '_', 1, 1)) ZB_name,\n"
                + "                    zb_value\n"
                + "             FROM sgmc.V_MC_ZB_QM_FCJGC\n"
                + "             WHERE zb_name NOT LIKE '%总计%'\n"
                + "               and length(calendar) > 8\n"
                + "               and calendar <= to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "               and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm'))\n"
                + "       group by process, zb_name, calendar))\n"
                + " where process = '" + typeName + "' "
                + "  and etl_freq = '" + dateTypeName + "'\n"
                + "  and zb_name not like '%总计%' "
                + "  and zb_value <> 0 ";
        return queryDataService.query(sql);
    }

    @Override
    public Object gjzbDxlydqs(String typeName, String qsTypeName) {
        String sql = " select * from SGMC.V_MC_ZB_PP_ZTZDDXL "
                + " where  fl = '" + typeName + "' and zb = '" + qsTypeName + "' "
                + " and calendar < to_char(current date,'yyyy-mm') and calendar >= to_char(current date-6 month,'yyyy-mm') "
                + " order by calendar ";
        return queryDataService.query(sql);
    }

    @Override
    public Object clSlyclbjh() {
        String sql = "SELECT (T.ZB_VALUE - T1.month_PLAN) AS BJH_VALUE\n"
                + "FROM (select calendar, zb_name, sum(zb_value) zb_value\n"
                + "      from (\n"
                + "               select left(calendar, 7) calendar, decode(zb_name, '顺冷商品材日指标', '顺冷商品材月指标', zb_name) zb_name, zb_value\n"
                + "               from SGMC.MC_SA_ZB_PP_01\n"
                + "               where ZB_NAME = '顺冷商品材日指标'\n"
                + "                 and left(calendar, 7) = to_char(current date, 'yyyy-mm'))\n"
                + "      group by zb_name, calendar) T,\n"
                + "\n"
                + "\n"
                + "     (SELECT date_code, month_plan / 10000 as month_plan\n"
                + "      FROM SGPC.PC_GROUPDAILY_SJ_009\n"
                + "      where to_char(DATE_CODE, 'YYYYMM') = to_char(SYSDATE - 1, 'yyyymm')\n"
                + "        and TYPE = '顺冷合计') T1";
        return queryDataService.query(sql);
    }

    @Override
    public Object gxzbLiantieGlfh(String typeName) {
        String sql = "select right(left(calendar, 10), 5) calendar, zb_name, zb_value\n"
                + "from (\n"
                + "         select v_date as calendar, '综合' as zb_name, FACTORY_D_PLAN_COKE_LOAD as zb_value\n"
                + "         from SGPC.QGLIMSP_GREAT_COMPANY_IRON\n"
                + "         union all\n"
                + "         select v_date as calendar, '一高炉' as zb_name, FURNACE1_D_PLAN_COKE_LOAD as zb_value\n"
                + "         from SGPC.QGLIMSP_GREAT_COMPANY_IRON\n"
                + "         union all\n"
                + "         select v_date as calendar, '二高炉' as zb_name, FURNACE2_D_PLAN_COKE_LOAD as zb_value\n"
                + "         from SGPC.QGLIMSP_GREAT_COMPANY_IRON\n"
                + "         union all\n"
                + "         select v_date as calendar, '三高炉' as zb_name, GL3_D_JTFH as zb_value\n"
                + "         from SGPC.QGLIMSP_GREAT_COMPANY_IRON)\n"
                + "where left(calendar, 10) >= to_char(current date - 7, 'yyyy-mm-dd')\n"
                + "  and left(calendar, 10) <= to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "  and zb_name = '"+typeName+"'";
        return queryDataService.query(sql);
    }


    /*
    @Override
    public Object xxxxx(String typeName){
        String sql = "  ";
        return queryDataService.query(sql);
    }
    */


    //根据分类获取对应的指标参数名
    private String getYzbTypeName(String zbName) {
        switch (zbName) {
            case "炼铁":
                return "炼铁产量明细";
            case "炼钢":
                return "炼钢产量明细";
            case "热轧":
                return "热轧商品材";
            case "酸洗":
                return "酸洗商品材";
            case "顺冷":
                return "顺冷商品材";
            case "硅钢":
                return "硅钢商品材";
            default:
                return null;

        }
    }

    private Integer formartMonthNums(Integer monthNums) {
        if (monthNums == null || monthNums <= 0 || monthNums > 12) {
            monthNums = 6;
        }
        return monthNums;
    }


    private LibraryThreshold getThreshold(String libraryName) {
        switch (libraryName) {
            case "一炼钢机清库":
                return new LibraryThreshold("一炼钢机清库", 4.0, 0.0, 3.6, "万吨"); // 原max=1.0 warn 0.6
            case "一炼钢板坯库":
                return new LibraryThreshold("一炼钢板坯库", 4.0, 0.0, 3.6, "万吨"); // 原max=4.0 warn 2.7
            case "二炼钢板坯库":
                return new LibraryThreshold("二炼钢板坯库", 4.0, 0.0, 3.6, "万吨");// 原max=3.6 warn 2.1
            case "一热轧横切成品库":
                return new LibraryThreshold("一热轧横切成品库", 0.45, 0.0, 0.27, "万吨");
            case "一热轧横切原料库":
                return new LibraryThreshold("一热轧横切原料库", 0.3, 0.1, 0.18, "万吨");
            case "一热轧剪切成品库":
                return new LibraryThreshold("一热轧剪切成品库", 0.3, 0.0, 0.18, "万吨");
            case "一热轧剪切原料库":
                return new LibraryThreshold("一热轧剪切原料库", 0.3, 0.1, 0.18, "万吨");
            case "一热轧板卷库":
                return new LibraryThreshold("一热轧板卷库", 10.1, 2.0, 6.06, "万吨");
            case "一热轧原料库":
                return new LibraryThreshold("一热轧原料库", 0.4, 0.0, 0.24, "万吨");
            case "二热轧原料库":
                return new LibraryThreshold("二热轧原料库", 0.4, 0.0, 0.24, "万吨");
            case "二热轧板卷库":
                return new LibraryThreshold("二热轧板卷库", 9.0, 2.0, 5.4, "万吨");
            case "酸洗成品库":
                return new LibraryThreshold("酸洗成品库", 1.0, 0.4, 0.6, "万吨");
            case "酸洗横切成品库":
                return new LibraryThreshold("酸洗横切成品库", 1.0, 0.4, 0.6, "万吨");
            case "酸洗原料库":
                return new LibraryThreshold("酸洗原料库", 1.0, 0.0, 0.6, "万吨");
            case "顺义原料库":
                return new LibraryThreshold("顺义原料库", 11.0, 3.0, 6.6, "万吨");
            case "顺义轧后库":
                return new LibraryThreshold("顺义轧后库", 3.2, 2.5, 1.92, "万吨");
            case "顺义成品库":
                return new LibraryThreshold("顺义成品库", 5.5, 0.0, 3.3, "万吨");
            case "顺义落料原料库":
                return new LibraryThreshold("顺义落料原料库", 0.25, 0.0, 0.15, "万吨");
            case "顺义落料成品库":
                return new LibraryThreshold("顺义落料成品库", 0.2, 0.0, 0.12, "万吨");
            default:
                return null;
        }

    }
}
