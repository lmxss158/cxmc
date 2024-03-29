package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.NenghuanGuanliService;
import com.sgai.cxmc.service.QueryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/10/11 10:41
 * @Version 1.0
 */
@Service("nenghuanGuanliServiceQs")
public class NenghuanGuanliServiceImpl implements NenghuanGuanliService {

    @Autowired
    QueryDataService queryDataService;

    @Override
    public Object getAqizs() {
        String sql = "select * from (\n"
                + "select dtime CALENDAR,\n"
                + "                ZB_VALUE,\n"
                + "                ZB_NAME,\n"
                + "                '日'   ETL_FREQ\n"
                + "         from (\n"
                + "                  SELECT to_char(to_date(LEFT(dtime, 8), 'yyyymmdd'), 'yyyy-mm-dd') dtime,\n"
                + "                         avg(CO)                                         CO,\n"
                + "                         avg(NO2)                                        NO2,\n"
                + "                         avg(SO2)                                        SO2,\n"
                + "                         avg(O3)                                         O3,\n"
                + "                         avg(PM25)                                       PM25,\n"
                + "                         avg(PM10)                                       PM10\n"
                + "                  FROM SGMc.mc_sa_ny_zb_01\n"
                + "                  WHERE LEFT(dtime, 8) >= to_char(CURRENT DATE - 30, 'yyyymmdd')\n"
                + "                  GROUP BY LEFT(dtime, 8)\n"
                + "              ) as jr,\n"
                + "              TABLE(VALUES\n"
                + "                    (jr.CO, 'CO'),\n"
                + "                    (jr.NO2, 'NO2'),\n"
                + "                    (jr.SO2, 'SO2'),\n"
                + "                    (jr.O3, 'O3'),\n"
                + "                    (jr.PM25, 'PM25'),\n"
                + "                    (jr.PM10, 'PM10')\n"
                + "                  ) AS VT (ZB_VALUE, ZB_NAME)\n"
                + ")\n"
                + "where CALENDAR = to_char(current date-1,'yyyy-mm-dd')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getAqizsBhqst(String indexName, String timeName) {
        String sql = "select CALENDAR, ZB_VALUE, ZB_NAME, ETL_FREQ\n"
                + "from (\n"
                + "         select CALENDAR, ZB_VALUE, decode(zb_name, 'PM25', 'PM2.5', zb_name) ZB_NAME, ETL_FREQ\n"
                + "         from (\n"
                + "                  select dtime CALENDAR,\n"
                + "                         ZB_VALUE,\n"
                + "                         ZB_NAME,\n"
                + "                         '日'   ETL_FREQ\n"
                + "                  from (\n"
                + "                           SELECT to_char(to_date(LEFT(dtime, 8), 'yyyymmdd'), 'yyyy-mm-dd') dtime,\n"
                + "                                  avg(CO)                                                    CO,\n"
                + "                                  avg(NO2)                                                   NO2,\n"
                + "                                  avg(SO2)                                                   SO2,\n"
                + "                                  avg(O3)                                                    O3,\n"
                + "                                  avg(PM25)                                                  PM25,\n"
                + "                                  avg(PM10)                                                  PM10\n"
                + "                           FROM SGMc.mc_sa_ny_zb_01\n"
                + "                           WHERE LEFT(dtime, 8) >= to_char(CURRENT DATE - 30, 'yyyymmdd')\n"
                + "                           GROUP BY LEFT(dtime, 8)\n"
                + "                       ) as jr,\n"
                + "                       TABLE(VALUES\n"
                + "                                 (jr.CO, 'CO'),\n"
                + "                             (jr.NO2, 'NO2'),\n"
                + "                             (jr.SO2, 'SO2'),\n"
                + "                             (jr.O3, 'O3'),\n"
                + "                             (jr.PM25, 'PM25'),\n"
                + "                             (jr.PM10, 'PM10')\n"
                + "                           ) AS VT (ZB_VALUE, ZB_NAME)\n"
                + "                  union all\n"
                + "                  select dtime CALENDAR,\n"
                + "                         ZB_VALUE,\n"
                + "                         ZB_NAME,\n"
                + "                         '月'   ETL_FREQ\n"
                + "                  from (\n"
                + "                           SELECT to_char(to_date(LEFT(dtime, 6), 'yyyymm'), 'yyyy-mm') dtime,\n"
                + "                                  avg(CO)                                               CO,\n"
                + "                                  avg(NO2)                                              NO2,\n"
                + "                                  avg(SO2)                                              SO2,\n"
                + "                                  avg(O3)                                               O3,\n"
                + "                                  avg(PM25)                                             PM25,\n"
                + "                                  avg(PM10)                                             PM10\n"
                + "                           FROM SGMc.mc_sa_ny_zb_01\n"
                + "                           WHERE LEFT(dtime, 6) >= to_char(CURRENT DATE - 6 MONTH, 'yyyymm')\n"
                + "                           GROUP BY LEFT(dtime, 6)\n"
                + "                       ) as jr,\n"
                + "                       TABLE(VALUES\n"
                + "                                 (jr.CO, 'CO'),\n"
                + "                             (jr.NO2, 'NO2'),\n"
                + "                             (jr.SO2, 'SO2'),\n"
                + "                             (jr.O3, 'O3'),\n"
                + "                             (jr.PM25, 'PM25'),\n"
                + "                             (jr.PM10, 'PM10')\n"
                + "                           ) AS VT (ZB_VALUE, ZB_NAME)\n"
                + "                  union all\n"
                + "                  select dtime CALENDAR,\n"
                + "                         ZB_VALUE,\n"
                + "                         ZB_NAME,\n"
                + "                         '时'   ETL_FREQ\n"
                + "                  from (\n"
                + "                           SELECT to_char(to_date(dtime, 'yyyymmddhh24'), 'yyyy-MM-dd HH24:mm:ss') dtime,\n"
                + "                                  avg(CO)                                                          CO,\n"
                + "                                  avg(NO2)                                                         NO2,\n"
                + "                                  avg(SO2)                                                         SO2,\n"
                + "                                  avg(O3)                                                          O3,\n"
                + "                                  avg(PM25)                                                        PM25,\n"
                + "                                  avg(PM10)                                                        PM10\n"
                + "                           FROM SGMc.mc_sa_ny_zb_01\n"
                + "                           WHERE dtime >= to_char(TRUNC(SYSDATE - 2), 'yyyymmddhh24')\n"
                + "                           GROUP BY dtime\n"
                + "                       ) as jr,\n"
                + "                       TABLE(VALUES\n"
                + "                                 (jr.CO, 'CO'),\n"
                + "                             (jr.NO2, 'NO2'),\n"
                + "                             (jr.SO2, 'SO2'),\n"
                + "                             (jr.O3, 'O3'),\n"
                + "                             (jr.PM25, 'PM25'),\n"
                + "                             (jr.PM10, 'PM10')\n"
                + "                           ) AS VT (ZB_VALUE, ZB_NAME)))\n"
                + "where ETL_FREQ = '" + timeName + "'\n"
                + "  and ZB_NAME = '" + indexName + "'";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getWryjcJkdxx(String companyName) {
        String sql = "select CALENDAR, SN_NAME, SN1, ZB_NAME, ZB_VALUE, ETL_FREQ\n"
                + "from (select dtime CALENDAR,\n"
                + "             ZB_VALUE,\n"
                + "             ZB_NAME,\n"
                + "             SN    SN_NAME,\n"
                + "             sn1   sn1,\n"
                + "             '日'   ETL_FREQ\n"
                + "      from (\n"
                + "               select dtime,\n"
                + "                      SN,\n"
                + "                      sn1,\n"
                + "                      avg(CO)   CO,\n"
                + "                      avg(NO2)  NO2,\n"
                + "                      avg(SO2)  SO2,\n"
                + "                      avg(O3)   O3,\n"
                + "                      avg(PM25) PM25,\n"
                + "                      avg(PM10) PM10\n"
                + "               from (\n"
                + "                        SELECT to_char(to_date(LEFT(dtime, 8), 'yyyymmdd'), 'yyyy-mm-dd') dtime,\n"
                + "                               decode(right(SN, 4),\n"
                + "                                      '5049', '鼎盛成',\n"
                + "                                      '5073', '鼎盛成',\n"
                + "                                      '5064', '鼎盛成',\n"
                + "                                      '5093', '鼎盛成',\n"
                + "                                      '5071', '鼎盛成',\n"
                + "                                      '5091', '鼎盛成',\n"
                + "                                      '5030', '炼铁作业部',\n"
                + "                                      '5029', '炼铁作业部',\n"
                + "                                      '5081', '炼铁作业部',\n"
                + "                                      '5055', '炼铁作业部',\n"
                + "                                      '18011', '热轧作业部',\n"
                + "                                      '5019', '炼铁作业部',\n"
                + "                                      '4143', '能源部',\n"
                + "                                      '5040', '能源部',\n"
                + "                                      '4959', '能源部',\n"
                + "                                      '18043', '能源部',\n"
                + "                                      '4159', '炼钢作业部',\n"
                + "                                      '4875', '炼钢作业部',\n"
                + "                                      '5013', '炼钢作业部',\n"
                + "                                      '4194', '炼钢作业部',\n"
                + "                                      '4134', '炼钢作业部',\n"
                + "                                      '5044', '炼钢作业部',\n"
                + "                                      '5072', '热轧作业部',\n"
                + "                                      '5074', '热轧作业部',\n"
                + "                                      '5088', '热轧作业部',\n"
                + "                                      '5015', '保卫处',\n"
                + "                                      '4106', '保卫处',\n"
                + "                                      '5095', '保卫部',\n"
                + "                                      '5016', '办公室',\n"
                + "                                      '4123', '电磁公司',\n"
                + "                                      '4184', '电磁公司',\n"
                + "                                      '未知')                                               SN,\n"
                + "                               decode(right(SN, 4),\n"
                + "                                      '5049', '粗破料场',\n"
                + "                                      '5073', '物资循环作业区',\n"
                + "                                      '5064', '一废钢',\n"
                + "                                      '5093', '烧结南料场',\n"
                + "                                      '5071', '二废钢',\n"
                + "                                      '5091', '水渣料场',\n"
                + "                                      '5030', '烧结西门',\n"
                + "                                      '5029', '烧结一烧机尾',\n"
                + "                                      '5081', '烧结白灰作业区',\n"
                + "                                      '5055', '球团汽车衡',\n"
                + "                                      '18011', '东配楼南',\n"
                + "                                      '5019', '汽车受料槽',\n"
                + "                                      '4143', '配水泵站北',\n"
                + "                                      '5040', '综水中心',\n"
                + "                                      '4959', '一污水',\n"
                + "                                      '18043', '东配楼北',\n"
                + "                                      '4159', '二炼钢渣垮',\n"
                + "                                      '4875', '一炼钢一次除尘',\n"
                + "                                      '5013', '质检站',\n"
                + "                                      '4194', '3#套筒窑',\n"
                + "                                      '4134', '1#套筒窑北',\n"
                + "                                      '5044', '炼钢办公楼东',\n"
                + "                                      '5072', '一热轧9号门',\n"
                + "                                      '5074', '热轧办公楼东南',\n"
                + "                                      '5088', '原线材厂房西门',\n"
                + "                                      '5015', '迁钢六号门',\n"
                + "                                      '4106', '首钢迁钢南',\n"
                + "                                      '5095', '矿业北门北',\n"
                + "                                      '5016', '环境公司',\n"
                + "                                      '4123', '36#门南',\n"
                + "                                      '4184', '首钢迁钢北',\n"
                + "                                      '未知')                                               sn1,\n"
                + "                               CO,\n"
                + "                               NO2,\n"
                + "                               SO2,\n"
                + "                               O3,\n"
                + "                               PM25,\n"
                + "                               PM10\n"
                + "                        FROM SGMc.mc_sa_ny_zb_01\n"
                + "                        WHERE LEFT(dtime, 8) >= to_char(CURRENT DATE - 1, 'yyyymmdd')\n"
                + "                        union all\n"
                + "                        SELECT to_char(to_date(LEFT(dtime, 8), 'yyyymmdd'), 'yyyy-mm-dd') dtime,\n"
                + "                               decode(right(SN, 5),\n"
                + "                                      '18043', '能源部',\n"
                + "                                      '18011', '热轧作业部',\n"
                + "                                      '未知')                                               SN,\n"
                + "                               decode(right(SN, 5),\n"
                + "                                      '18043', '东配楼北',\n"
                + "                                      '18011', '东配楼南',\n"
                + "                                      '未知')                                               sn1,\n"
                + "                               CO,\n"
                + "                               NO2,\n"
                + "                               SO2,\n"
                + "                               O3,\n"
                + "                               PM25,\n"
                + "                               PM10\n"
                + "                        FROM SGMc.mc_sa_ny_zb_01\n"
                + "                        WHERE LEFT(dtime, 8) >= to_char(CURRENT DATE - 1, 'yyyymmdd')\n"
                + "                          and right(SN, 5) in ('18043', '18011')\n"
                + "                    )\n"
                + "               group by sn, sn1, dtime\n"
                + "           ) as jr,\n"
                + "           TABLE(VALUES\n"
                + "                     (jr.CO, 'CO'),\n"
                + "                 (jr.NO2, 'NO2'),\n"
                + "                 (jr.SO2, 'SO2'),\n"
                + "                 (jr.O3, 'O3'),\n"
                + "                 (jr.PM25, 'PM25'),\n"
                + "                 (jr.PM10, 'PM10')\n"
                + "               ) AS VT (ZB_VALUE, ZB_NAME))\n"
                + "where SN_NAME = '" + companyName + "'\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm-dd')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getWryjcAqimx(String companyName) {
        String sql = "select CALENDAR, ZB_VALUE, ZB_NAME, SN_NAME, ETL_FREQ\n"
                + "from (select dtime CALENDAR,\n"
                + "             ZB_VALUE,\n"
                + "             ZB_NAME,\n"
                + "             SN    SN_NAME,\n"
                + "             '日'   ETL_FREQ\n"
                + "      from (\n"
                + "               select dtime,\n"
                + "                      SN,\n"
                + "                      avg(CO)   CO,\n"
                + "                      avg(NO2)  NO2,\n"
                + "                      avg(SO2)  SO2,\n"
                + "                      avg(O3)   O3,\n"
                + "                      avg(PM25) PM25,\n"
                + "                      avg(PM10) PM10\n"
                + "               from (\n"
                + "                        SELECT to_char(to_date(LEFT(dtime, 8), 'yyyymmdd'), 'yyyy-mm-dd') dtime,\n"
                + "                               decode(right(SN, 4),\n"
                + "                                      '5049', '鼎盛成',\n"
                + "                                      '5073', '鼎盛成',\n"
                + "                                      '5064', '鼎盛成',\n"
                + "                                      '5093', '鼎盛成',\n"
                + "                                      '5071', '鼎盛成',\n"
                + "                                      '5091', '鼎盛成',\n"
                + "                                      '5030', '炼铁作业部',\n"
                + "                                      '5029', '炼铁作业部',\n"
                + "                                      '5081', '炼铁作业部',\n"
                + "                                      '5055', '炼铁作业部',\n"
                + "                                      '18011', '热轧作业部',\n"
                + "                                      '5019', '炼铁作业部',\n"
                + "                                      '4143', '能源部',\n"
                + "                                      '5040', '能源部',\n"
                + "                                      '4959', '能源部',\n"
                + "                                      '18043', '能源部',\n"
                + "                                      '4159', '炼钢作业部',\n"
                + "                                      '4875', '炼钢作业部',\n"
                + "                                      '5013', '炼钢作业部',\n"
                + "                                      '4194', '炼钢作业部',\n"
                + "                                      '4134', '炼钢作业部',\n"
                + "                                      '5044', '炼钢作业部',\n"
                + "                                      '5072', '热轧作业部',\n"
                + "                                      '5074', '热轧作业部',\n"
                + "                                      '5088', '热轧作业部',\n"
                + "                                      '5015', '保卫处',\n"
                + "                                      '4106', '保卫处',\n"
                + "                                      '5095', '保卫部',\n"
                + "                                      '5016', '办公室',\n"
                + "                                      '4123', '电磁公司',\n"
                + "                                      '4184', '电磁公司',\n"
                + "                                      '未知')                                               SN,\n"
                + "                               CO,\n"
                + "                               NO2,\n"
                + "                               SO2,\n"
                + "                               O3,\n"
                + "                               PM25,\n"
                + "                               PM10\n"
                + "                        FROM SGMc.mc_sa_ny_zb_01\n"
                + "                        WHERE LEFT(dtime, 8) >= to_char(CURRENT DATE - 30, 'yyyymmdd')\n"
                + "                        union all\n"
                + "                        SELECT to_char(to_date(LEFT(dtime, 8), 'yyyymmdd'), 'yyyy-mm-dd') dtime,\n"
                + "                               decode(right(SN, 5),\n"
                + "                                      '18043', '能源部',\n"
                + "                                      '18011', '热轧作业部',\n"
                + "                                      '未知')                                               SN,\n"
                + "                               CO,\n"
                + "                               NO2,\n"
                + "                               SO2,\n"
                + "                               O3,\n"
                + "                               PM25,\n"
                + "                               PM10\n"
                + "                        FROM SGMc.mc_sa_ny_zb_01\n"
                + "                        WHERE LEFT(dtime, 8) >= to_char(CURRENT DATE - 30, 'yyyymmdd')\n"
                + "                          and right(SN, 5) in ('18043', '18011')\n"
                + "                    )\n"
                + "               group by sn, dtime\n"
                + "           ) as jr,\n"
                + "           TABLE(VALUES\n"
                + "                     (jr.CO, 'CO'),\n"
                + "                 (jr.NO2, 'NO2'),\n"
                + "                 (jr.SO2, 'SO2'),\n"
                + "                 (jr.O3, 'O3'),\n"
                + "                 (jr.PM25, 'PM25'),\n"
                + "                 (jr.PM10, 'PM10')\n"
                + "               ) AS VT (ZB_VALUE, ZB_NAME))\n"
                + "where SN_NAME = '" + companyName + "'\n"
                + "  AND CALENDAR = to_char(TRUNC(SYSDATE - 1), 'yyyy-MM-dd')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getPwzlKednjh() {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_NY_HJGL\n"
                + "where zb = '年计划'\n"
                + "  and calendar = (select max(calendar) from SGMC.V_MC_ZB_NY_HJGL where zb = '年计划' and zb_value <> 0)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getPwzlKedyjh() {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_NY_HJGL\n"
                + "where zb = '月计划'\n"
                + "  and calendar =\n"
                + "      (select max(calendar)\n"
                + "       from SGMC.V_MC_ZB_NY_HJGL\n"
                + "       where zb = '月实际'\n"
                + "         and zb_value <> 0)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getPwzlKedysj() {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_NY_HJGL\n"
                + "where zb = '月实际'\n"
                + "  and calendar =\n"
                + "      (select max(calendar)\n"
                + "       from SGMC.V_MC_ZB_NY_HJGL\n"
                + "       where zb = '月实际'\n"
                + "         and zb_value <> 0)\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getPwzlTbjhb(String typeName) {
        String sql = "select CALENDAR, ZB || '最大月份' ysjzdyf, ZQ, ZB_VALUE\n"
                + "from SGMC.V_MC_ZB_NY_HJGL\n"
                + "where zb = '月实际'\n"
                + "  and zq = '" + typeName + "'\n"
                + "  and calendar = (select max(calendar) from SGMC.V_MC_ZB_NY_HJGL where zb = '月实际' and zq = '" + typeName + "' and zb_value <> 0)\n"
                + "union all\n"
                + "select CALENDAR, ZB || '减去1个月' ysjjqygy, ZQ, ZB_VALUE\n"
                + "from SGMC.V_MC_ZB_NY_HJGL\n"
                + "where zb = '月实际'\n"
                + "  and zq = '" + typeName + "'\n"
                + "  and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 1 month, 'yyyy-mm')\n"
                + "                  from SGMC.V_MC_ZB_NY_HJGL\n"
                + "                  where zb = '月实际'\n"
                + "                    and zq = '" + typeName + "'\n"
                + "                    and zb_value <> 0)\n"
                + "union all\n"
                + "select CALENDAR, ZB || '减去13个月' ysjjqssgy, ZQ, ZB_VALUE\n"
                + "from SGMC.V_MC_ZB_NY_HJGL\n"
                + "where zb = '月实际'\n"
                + "  and zq = '" + typeName + "'\n"
                + "  and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 13 month, 'yyyy-mm')\n"
                + "                  from SGMC.V_MC_ZB_NY_HJGL\n"
                + "                  where zb = '月实际'\n"
                + "                    and zq = '" + typeName + "'\n"
                + "                    and zb_value <> 0)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getPwzlYdqst(String typeName) {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_NY_HJGL\n"
                + "where zb = '月实际'\n"
                + "  and zq = '" + typeName + "'\n"
                + "  and calendar <= (select max(calendar) from SGMC.V_MC_ZB_NY_HJGL where zb = '月实际' and zq = '" + typeName + "' and zb_value <> 0)\n"
                + "  and calendar > (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 6 month, 'yyyy-mm')\n"
                + "                  from SGMC.V_MC_ZB_NY_HJGL\n"
                + "                  where zb = '月实际'\n"
                + "                    and zq = '" + typeName + "'\n"
                + "                    and zb_value <> 0)\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getPwzlLj(String typeName) {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_NY_HJGL\n"
                + "where zb = '月实际'\n"
                + "  and zq = '" + typeName + "'\n"
                + "  and calendar <= (select max(calendar) from SGMC.V_MC_ZB_NY_HJGL where zb = '月实际' and zq = '" + typeName + "' and zb_value <> 0)\n"
                + "  and calendar >=(select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 6 month, 'yyyy-mm')\n"
                + "                  from SGMC.V_MC_ZB_NY_HJGL\n"
                + "                  where zb = '月实际'\n"
                + "                    and zq = '" + typeName + "'\n"
                + "                    and zb_value <> 0)\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getNyglYdsj() {
        String sql = "select *\n"
                + "from sgmc.V_MC_ZB_NY\n"
                + "where zb_name in ('吨钢综合能耗', '吨钢电耗', '吨钢耗新水')\n"
                + "  and CALENDAR =\n"
                + "      (select max(calendar) from sgmc.V_MC_ZB_NY where zb_name in ('吨钢综合能耗', '吨钢电耗', '吨钢耗新水') and zb_value <> 0)\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getNyglNdsj() {
        String sql = "select calendar,zb_name, zb_value\n"
                + "from sgmc.V_MC_ZB_NY\n"
                + "where zb_name in ('吨钢综合能耗', '吨钢电耗', '吨钢耗新水')\n"
                + "  and calendar = to_char(current date, 'yyyy')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getNyglYdqdt(String typeName) {
        String sql = "select *\n"
                + "from sgmc.V_MC_ZB_NY\n"
                + "where zb_name = '" + typeName + "'\n"
                + "  and calendar >= (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 6 month, 'yyyy-mm')\n"
                + "                   from sgmc.V_MC_ZB_NY\n"
                + "                   where zb_name in ('吨钢综合能耗', '吨钢电耗', '吨钢耗新水')\n"
                + "                     and zb_value <> 0)\n"
                + "  and CALENDAR <=\n"
                + "      (select max(calendar) from sgmc.V_MC_ZB_NY where zb_name in ('吨钢综合能耗', '吨钢电耗', '吨钢耗新水') and zb_value <> 0)\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getGxnhYdjqst(String companyName) {
        String sql = "select *\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                zb_code,\n"
                + "                decode(zb_name, '二冷轧取向', '取向', '一冷轧', '无取向中低牌号', '二冷轧无取向', '无取向高牌号', '冷轧工序', '智新', '炼铁工序', '炼铁', '酸洗线',\n"
                + "                       '酸洗', zb_name) as                             zb_name,\n"
                + "                cast(round(zb_value * 10 / 10, 2) as decimal(20, 2)) zb_value,\n"
                + "                unit_name,\n"
                + "                etl_freq\n"
                + "         from sgmc.V_MC_ZB_NY\n"
                + "         where zb_name in ('一炼钢', '冷轧工序', '二炼钢', '炼铁工序', '一热轧', '二热轧', '二冷轧取向', '一冷轧', '二冷轧无取向', '酸洗线'))\n"
                + "where zb_name = '" + companyName + "'\n"
                + "  and calendar > (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 3 month, 'yyyy-mm')\n"
                + "                  from sgmc.V_MC_ZB_NY\n"
                + "                  where zb_name in ('一炼钢', '冷轧工序', '二炼钢', '炼铁工序', '一热轧', '二热轧', '二冷轧取向', '一冷轧', '二冷轧无取向', '酸洗线')\n"
                + "                    and zb_value <> 0)\n"
                + "  and calendar <= (select max(calendar)\n"
                + "                   from sgmc.V_MC_ZB_NY\n"
                + "                   where zb_name in ('一炼钢', '冷轧工序', '二炼钢', '炼铁工序', '一热轧', '二热轧', '二冷轧取向', '一冷轧', '二冷轧无取向', '酸洗线')\n"
                + "                     and zb_value <> 0)\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getGxnhNdsj(String companyName) {
        String sql = "select *\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                zb_code,\n"
                + "                decode(zb_name, '二冷轧取向', '取向', '一冷轧', '无取向中低牌号', '二冷轧无取向', '无取向高牌号', '冷轧工序', '智新', '炼铁工序', '炼铁', '酸洗线',\n"
                + "                       '酸洗', zb_name) as                             zb_name,\n"
                + "                cast(round(zb_value * 10 / 10, 2) as decimal(20, 2)) zb_value,\n"
                + "                unit_name,\n"
                + "                etl_freq\n"
                + "         from sgmc.V_MC_ZB_NY\n"
                + "         where zb_name in ('一炼钢', '冷轧工序', '二炼钢', '炼铁工序', '一热轧', '二热轧', '二冷轧取向', '一冷轧', '二冷轧无取向', '酸洗线'))\n"
                + "where zb_name = '" + companyName + "'\n"
                + "  and calendar = to_char(current date, 'yyyy')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getGxnycbYdjqst(String companyName) {
        String sql = "select *\n"
                + "from (select calendar,\n"
                + "             zb_code,\n"
                + "             decode(zb_name, '能源_单位成本', '公辅_单位成本', zb_name) as    zb_name,\n"
                + "             cast(round(zb_value * 10 / 10, 2) as decimal(20, 2)) zb_value\n"
                + "      from sgmc.mc_sa_zb_ny_01)\n"
                + "where zb_name = '" + companyName + "_单位成本'\n"
                + "  and calendar > (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 6 month, 'yyyy-mm')\n"
                + "                  from sgmc.mc_sa_zb_ny_01\n"
                + "                  where zb_name = '" + companyName + "_单位成本'\n"
                + "                    and zb_value <> 0)\n"
                + "  and calendar <= (select max(calendar)\n"
                + "                   from sgmc.mc_sa_zb_ny_01\n"
                + "                   where zb_name = '" + companyName + "_单位成本'\n"
                + "                     and zb_value <> 0)\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getGxnycbNdsj(String companyName) {
        String sql = "select *\n"
                + "from (select calendar,\n"
                + "             zb_code,\n"
                + "             decode(zb_name, '能源_单位成本', '公辅_单位成本', zb_name) as    zb_name,\n"
                + "             cast(round(zb_value * 10 / 10, 2) as decimal(20, 2)) zb_value\n"
                + "      from sgmc.mc_sa_zb_ny_01)\n"
                + "where zb_name = '" + companyName + "_单位成本'\n"
                + "  and calendar = to_char(current date, 'yyyy')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getHdlYdjqst(String companyName) {
        String sql = "select *\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                zb_code,\n"
                + "                decode(zb_name, '二冷轧取向电耗', '取向电耗', '二冷轧无取向电耗', '无取向高牌号电耗', '冷轧工序电耗', '智新电耗', '炼铁工序电耗', '炼铁电耗', '酸洗线电耗',\n"
                + "                       '酸洗电耗', '一冷轧电耗', '无取向中低牌号电耗', zb_name) as     zb_name,\n"
                + "                cast(round(zb_value * 10 / 10, 2) as decimal(20, 2)) zb_value,\n"
                + "                unit_name,\n"
                + "                etl_freq\n"
                + "         from sgmc.V_MC_ZB_NY\n"
                + "         where zb_name in\n"
                + "               ('一冷轧电耗', '一炼钢电耗', '一热轧电耗', '二冷轧取向电耗', '二冷轧无取向电耗', '二炼钢电耗', '二热轧电耗', '冷轧工序电耗', '炼铁工序电耗', '酸洗线电耗'))\n"
                + "where zb_name = '"+companyName+"电耗'\n"
                + "  and calendar > (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 6 month, 'yyyy-mm')\n"
                + "                  from sgmc.V_MC_ZB_NY\n"
                + "                  where zb_name in\n"
                + "                        ('一冷轧电耗', '一炼钢电耗', '一热轧电耗', '二冷轧取向电耗', '二冷轧无取向电耗', '二炼钢电耗', '二热轧电耗', '冷轧工序电耗', '炼铁工序电耗',\n"
                + "                         '酸洗线电耗')\n"
                + "                    and zb_value <> 0)\n"
                + "  and calendar <= (select max(calendar)\n"
                + "                   from sgmc.V_MC_ZB_NY\n"
                + "                   where zb_name in\n"
                + "                         ('一冷轧电耗', '一炼钢电耗', '一热轧电耗', '二冷轧取向电耗', '二冷轧无取向电耗', '二炼钢电耗', '二热轧电耗', '冷轧工序电耗', '炼铁工序电耗',\n"
                + "                          '酸洗线电耗')\n"
                + "                     and zb_value <> 0)\n"
                + "order by calendar\n";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getHdlNdsj(String companyName) {
        String sql = "select *\n"
                + "from (\n"
                + "         select calendar,\n"
                + "                zb_code,\n"
                + "                decode(zb_name, '二冷轧取向电耗', '取向电耗', '二冷轧无取向电耗', '无取向高牌号电耗', '冷轧工序电耗', '智新电耗', '炼铁工序电耗', '炼铁电耗', '酸洗线电耗',\n"
                + "                       '酸洗电耗', '一冷轧电耗', '无取向中低牌号电耗', zb_name) as     zb_name,\n"
                + "                cast(round(zb_value * 10 / 10, 2) as decimal(20, 2)) zb_value,\n"
                + "                unit_name,\n"
                + "                etl_freq\n"
                + "         from sgmc.V_MC_ZB_NY\n"
                + "         where zb_name in\n"
                + "               ('一冷轧电耗', '一炼钢电耗', '一热轧电耗', '二冷轧取向电耗', '二冷轧无取向电耗', '二炼钢电耗', '二热轧电耗', '冷轧工序电耗', '炼铁工序电耗', '酸洗线电耗'))\n"
                + "where zb_name like '%"+companyName+"电耗'\n"
                + "  and calendar = to_char(current date, 'yyyy')";
        return queryDataService.query ( sql );
    }
}
