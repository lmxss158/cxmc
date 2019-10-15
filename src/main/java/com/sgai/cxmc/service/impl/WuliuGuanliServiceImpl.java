package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.QueryDataService;
import com.sgai.cxmc.service.WuliuGuanliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/10/9 17:59
 * @Version 1.0
 */
@Service("wuliuGuanliServiceQs")
public class WuliuGuanliServiceImpl implements WuliuGuanliService {
    @Autowired
    QueryDataService queryDataService;

    @Override
    public Object getCgddlTst() {
        String sql = "select calendar,decode(zb_name,'国内矿合计_累计进厂','国内矿','焦炭合计_累计落地','焦炭','进口矿合计_累计进厂','进口矿','秘矿产粉_累计生产','秘矿产粉','秘鲁原矿_累计加工','秘鲁原矿','喷吹煤合计_累计进厂','喷吹煤','外购废钢合计_累计进厂','外购废钢','优质石灰石_累计进厂','优质石灰石',zb_name) as zb_name,zb_ysfs,(zb_value/10000) as zb_value\n"
                + "from sgmc.MC_SA_ZB_SL_02\n"
                + "where zb_name like '%累计%' and zb_name not like '%发运量%' and zb_ysfs <> '火汽运' and calendar = to_char(current date-1,'yyyy-mm-dd')\n"
                + "order by calendar,zb_ysfs,zb_value desc\n";

        return queryDataService.query ( sql );
    }

    @Override
    public Object getRddlQst(String typeName) {
        String sql = "select calendar, zb_ysfs, sum(zb_value) as zb_value\n"
                + "from sgmc.MC_SA_ZB_SL_02\n"
                + "where zb_ysfs = '"+typeName+"'\n"
                + "  and zb_name not like '%累计%'\n"
                + "  and zb_ysfs <> '火汽运'\n"
                + "  and (zb_name like '%进厂%'\n"
                + "    or zb_name like '%加工%' or zb_name like '%生产%'\n"
                + "    or zb_name like '%落地%')\n"
                + "  and calendar >= '2019-09-01'\n"
                + "group by calendar, zb_ysfs\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getQjysysgchxt() {
        String sql = "select calendar, zb_ysfs, sum(zb_value / 10000) as zb_value\n"
                + "from sgmc.MC_SA_ZB_SL_02\n"
                + "where zb_name like '%累计%'\n"
                + "and zb_ysfs <> '火汽运'"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "group by calendar, zb_ysfs";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getCpfylZfyl() {
        String sql = "select sum(zb_value) / 10000 zb_value\n"
                + "from sgmc.MC_SA_ZB_SL_02\n"
                + "where zb_name like '%日累计_发运量%'\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm-dd')";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getCpfylZfyltxt() {
        String sql = "select left(zb_name, 4) as zb_name, zb_value / 10000 as zb_value, zb_ysfs\n"
                + "from sgmc.MC_SA_ZB_SL_02\n"
                + "where zb_name like '%日累计_发运量%'\n"
                + "  and calendar = to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "order by zb_ysfs asc, zb_value desc";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getRfylQst(String typeName) {
        String sql = "select sum(zb_value) zb_value, zb_ysfs, calendar\n"
                + "from sgmc.MC_SA_ZB_SL_02\n"
                + "where zb_ysfs = '"+typeName+"'\n"
                + "  and zb_name like '%发运量%'\n"
                + "  and zb_name not like '%累计_发运量%'\n"
                + "  and calendar <= to_char(current date - 1, 'yyyy-mm-dd')\n"
                + "  and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm')\n"
                + "group by calendar, zb_ysfs";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getGffyhxt() {
        String sql = "select calendar, tieyun, sum(zb_value) as zb_value\n"
                + "from (select '汽运'                              as tieyun,\n"
                + "             to_char(current date -1 , 'yyyy-mm-dd')     calendar,\n"
                + "             SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1, 2) + 1, instr(zb_name, '_', 1, 3) - instr(zb_name, '_', 1, 2) - 1),\n"
                + "             round((sum(zb_value) / 10000), 2) as zb_value\n"
                + "      from sgmc.MC_SA_ZB_SL_01\n"
                + "      where calendar <= to_char(current date, 'yyyy-mm-dd')\n"
                + "        and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm')\n"
                + "        and ZB_NAME like '%首钢迁钢_汽运%发运量'\n"
                + "      group by zb_name\n"
                + "      order by zb_value desc)\n"
                + "group by calendar, tieyun\n"
                + "union all\n"
                + "select calendar, tieyun, sum(zb_value) as zb_value\n"
                + "from (select '铁运'                              as tieyun,\n"
                + "             to_char(current date -1, 'yyyy-mm-dd')     calendar,\n"
                + "             SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1, 2) + 1, instr(zb_name, '_', 1, 3) - instr(zb_name, '_', 1, 2) - 1),\n"
                + "             round((sum(zb_value) / 10000), 2) as zb_value\n"
                + "      from sgmc.MC_SA_ZB_SL_01\n"
                + "      where calendar <= to_char(current date, 'yyyy-mm-dd')\n"
                + "        and left(calendar, 7) = to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm')\n"
                + "        and ZB_NAME like '%首钢迁钢_铁运%发运量'\n"
                + "      group by zb_name\n"
                + "      order by zb_value desc)\n"
                + "group by calendar, tieyun";
        return queryDataService.query ( sql );
    }
}
