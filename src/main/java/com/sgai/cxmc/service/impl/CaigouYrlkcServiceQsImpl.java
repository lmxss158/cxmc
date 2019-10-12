package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.CaigouYrlkcService;
import com.sgai.cxmc.service.QueryDataService;
import com.sgai.cxmc.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("caigouYrlkcServiceQs")
public class CaigouYrlkcServiceQsImpl implements CaigouYrlkcService {

    @Autowired
    QueryDataService queryDataService;

    @Override
    public Object yrlTksCgzxJhla(String selectDate) {

        String sql = "select calendar, zb_name, sum(zb_value) zb_value\n" +
                "from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name = '进口块矿_计划量'\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar, zb_name\n" +
                "union all\n" +
                "select calendar, zb_name, sum(zb_value) zb_value\n" +
                "from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name = '进口粉矿_计划量'\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar, zb_name\n" +
                "union all\n" +
                "select calendar,\n" +
                "       SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1, 2) + 1,\n" +
                "              instr(zb_name, '_', 1, 3) - instr(zb_name, '_', 1, 2) - 1) as zb_name,\n" +
                "       sum(zb_value)                                                        zb_value\n" +
                "from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name = '迁安地区_国内矿_总计_计划数量'\n" +
                "  and calendar = left('" + selectDate + "', 7)\n" +
                "group by calendar,\n" +
                "         SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1, 2) + 1, instr(zb_name, '_', 1, 3) - instr(zb_name, '_', 1, 2) - 1)\n";

        return queryDataService.query(sql);
    }

    @Override
    public Object yrlTksCgzxGckcla(String selectDate) {
        String sql = "select calendar, zb_name, zb_value\n" +
                "from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in ('进口块矿_港存量', '进口粉矿_港存量')\n" +
                "  and calendar = '" + selectDate + "'";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlTksCgzxCrkckkc(String selectDate) {
        String sql = "select\n" +
                "    calendar,\n" +
                "    zb,\n" +
                "    sum(rk) rk,\n" +
                "    sum(ck) ck,\n" +
                "    sum(qmkcs) qm,\n" +
                "    sum(rj) rj\n" +
                "from\n" +
                "    (\n" +
                "        select\n" +
                "            calendar,\n" +
                "            (case when  ZB_NAME like '%进口块矿%' then '进口块矿'\n" +
                "                  when ZB_NAME like '%进口粉矿%' then '进口粉矿'\n" +
                "                  when ZB_NAME like '%国内矿%' then '国内矿'\n" +
                "                end) zb,\n" +
                "            (case when zb_name like '%入库' then ZB_VALUE END) rk,\n" +
                "            (case when zb_name like '%出库' then ZB_VALUE END) ck,\n" +
                "            (case when zb_name like '%期末库存数量%' then ZB_VALUE END) qmkcs,\n" +
                "            (case when zb_name like '%日均消耗' then ZB_VALUE END) rj\n" +
                "        from sgmc.mc_sa_zb_po_01 where left(ZB_NAME,8) in ('首钢迁钢','首钢冷轧')\n" +
                "                                   and calendar ='" + selectDate + "'\n" +
                "--${if(len(selectDate)=0,\"\",\" and substr(calendar,1,8) = SUBSTR(TO_CHAR(date('" + selectDate + "'),'yyyymmdd'),1,8)\")}\n" +
                "    ) where zb in ('进口块矿','进口粉矿','国内矿') group by calendar,zb";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlTksCgzxGnkJhl() {
        String sql = "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in( '迁安地区_进口矿_进口块矿_计划数量','顺义地区_喷出煤_神华煤_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in( '迁安地区_贸易矿_进口粉矿_计划数量','顺义地区_喷吹煤_阳泉煤_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in( '迁安地区_国内矿_总计_计划数量','顺义地区_喷吹煤_潞安煤_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlTksCgzxCnkc(String selectDate) {
        String sql = "select calendar,zb_name,zb_value from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in ('进口块矿_库存量','进口粉矿_库存量')\n" +
                "  and calendar = '" + selectDate + "'";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlTksCgzxJssrkcqst(String typeName) {
        String beforeMonthLastDay = DateUtils.getBeforeMonthLastDay();
        String sql = "          select * from (\n"
                + "             select calendar,\n"
                + "              '国内矿' zb_name, zb_value from sgmc.MC_SA_ZB_PO_01\n"
                + "             where zb_name like\n"
                + "              '%国内矿%期末库存数量%'\n"
                + "             and calendar >= to_char(trunc (sysdate-30,\n"
                + "              'dd'),\n"
                + "              'yyyy-mm-dd') and calendar <= to_char(trunc (sysdate-1,\n"
                + "              'dd'),\n"
                + "              'yyyy-mm-dd') and calendar >\n"
                + "              '" + beforeMonthLastDay + "'\n"
                + "             union all\n"
                + "             select calendar,\n"
                + "              '进口块矿' zb_name, sum(zb_value) as zb_value from sgmc.mc_sa_zb_po_02\n"
                + "             where zb_name in (\n"
                + "              '进口块矿_港存量',\n"
                + "              '进口块矿_库存量')\n"
                + "             and calendar >= to_char(trunc (sysdate-30,\n"
                + "              'dd'),\n"
                + "              'yyyy-mm-dd') and calendar <= to_char(trunc (sysdate-1,\n"
                + "              'dd'),\n"
                + "              'yyyy-mm-dd') and calendar >\n"
                + "              '" + beforeMonthLastDay + "'\n"
                + "             group by calendar\n"
                + "             union all\n"
                + "             select calendar,\n"
                + "              '进口粉矿' zb_name, sum(zb_value) as zb_value from sgmc.mc_sa_zb_po_02\n"
                + "             where zb_name in (\n"
                + "              '进口粉矿_港存量',\n"
                + "              '进口粉矿_库存量')\n"
                + "             and calendar >= to_char(trunc (sysdate-30,\n"
                + "              'dd'),\n"
                + "              'yyyy-mm-dd') and calendar <= to_char(trunc (sysdate-1,\n"
                + "              'dd'),\n"
                + "              'yyyy-mm-dd') and calendar >\n"
                + "              '" + beforeMonthLastDay + "'\n"
                + "             group by calendar)\n"
                + "             where zb_name =\n"
                + "              '" + typeName + "'";

        return queryDataService.query(sql);
    }

    @Override
    public Object yrlPcmCgzx(String selectDate) {
        String sql = "select\n" +
                "    calendar,\n" +
                "    zb,\n" +
                "    sum(rk) rk,\n" +
                "    sum(ck) ck,\n" +
                "    sum(qmkcs) qm,\n" +
                "    sum(rj) rj\n" +
                "from\n" +
                "    (\n" +
                "        select\n" +
                "            calendar,\n" +
                "            (case when  ZB_NAME like '%神华煤%' then '神华煤'\n" +
                "                  when ZB_NAME like '%潞安煤%' then '潞安煤'\n" +
                "                  when ZB_NAME like '%阳泉煤%' then '阳泉煤'\n" +
                "                  when ZB_NAME like '%其他喷吹煤%' then '其他喷吹煤'\n" +
                "                end) zb,\n" +
                "            (case when right(ZB_NAME,4)='入库' then ZB_VALUE END) rk,\n" +
                "            (case when right(ZB_NAME,4)='出库' then ZB_VALUE END) ck,\n" +
                "            (case when zb_name like '%期末库存数量%' then ZB_VALUE END) qmkcs,\n" +
                "            (case when right(ZB_NAME,8)='日均消耗' then ZB_VALUE END) rj\n" +
                "        from sgmc.mc_sa_zb_po_01 where left(ZB_NAME,8) in ('首钢迁钢','首钢冷轧')\n" +
                "                                   and calendar= '" + selectDate + "'\n" +
                "    ) where zb in ('神华煤','潞安煤','阳泉煤','其他喷吹煤') group by calendar,zb";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlPcmDrkc(String selectDate) {
        String sql = "SELECT\n" +
                "    T.CALENDAR,\n" +
                "    substr(T.ZB_NAME,instr(t.ZB_NAME,'_',1,1)+1,instr(t.ZB_NAME,'_',1,2)-instr(t.ZB_NAME,'_',1,1)-1) ZB,\n" +
                "    T.ZB_VALUE\n" +
                "FROM\n" +
                "    SGMC.MC_SA_ZB_PO_01 T\n" +
                "WHERE\n" +
                "        T.ZB_NAME LIKE '%煤%期末库存数量%' AND\n" +
                "        T.CALENDAR= '" + selectDate + "'";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlPcmJwrxhjkyts(String selectDate) {
        String sql = "SELECT\n" +
                "    T.CALENDAR,\n" +
                "    substr(T.ZB_NAME,instr(t.ZB_NAME,'_',1,1)+1,instr(t.ZB_NAME,'_',1,2)-instr(t.ZB_NAME,'_',1,1)-1) ZB,\n" +
                "    T.ZB_VALUE\n" +
                "FROM\n" +
                "    SGMC.MC_SA_ZB_PO_01 T\n" +
                "WHERE\n" +
                "        T.ZB_NAME LIKE '%煤%期末库存数量%' AND\n" +
                "        T.CALENDAR= '" + selectDate + "'";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlPcmJssrkcqst(String typeName) {
        String sql = "select t.CALENDAR,'" + typeName + "' zb,t.ZB_VALUE from sgmc.MC_SA_ZB_PO_01 t\n" +
                "where t.ZB_NAME like '%" + typeName + "%期末库存数量%'\n" +
                "  and t.calendar >= to_char(trunc(sysdate-30,'dd'),'yyyy-mm-dd') and t.calendar <= to_char(trunc(sysdate-1,'dd'),'yyyy-mm-dd')\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlJcCgzx(String selectDate) {
        String sql = "select\n" +
                "    calendar,\n" +
                "    zb,\n" +
                "    sum(rk) rk,\n" +
                "    sum(ck) ck,\n" +
                "    sum(qmkcs) qm,\n" +
                "    sum(rj) rj\n" +
                "from\n" +
                "    (\n" +
                "        select\n" +
                "            calendar,\n" +
                "            (case when  ZB_NAME like '%迁焦焦炭%' then '迁焦焦炭'\n" +
                "                  when  ZB_NAME like '%外购焦炭%' then '外购焦炭'\n" +
                "                end) zb,\n" +
                "            (case when right(ZB_NAME,4)='入库' then ZB_VALUE END) rk,\n" +
                "            (case when right(ZB_NAME,4)='出库' then ZB_VALUE END) ck,\n" +
                "            (case when zb_name like '%期末库存数量%' then ZB_VALUE END) qmkcs,\n" +
                "            (case when right(ZB_NAME,8)='日均消耗' then ZB_VALUE END) rj\n" +
                "        from sgmc.mc_sa_zb_po_01 where left(ZB_NAME,8) in ('首钢迁钢','首钢冷轧')\n" +
                "                                   and calendar= '" + selectDate + "'\n" +
                "    ) where zb in ('迁焦焦炭','外购焦炭') group by calendar,zb";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlJcDrkc(String selectDate) {
        String sql = "SELECT\n" +
                "    T.CALENDAR,\n" +
                "    substr(T.ZB_NAME,instr(t.ZB_NAME,'_',1,1)+1,instr(t.ZB_NAME,'_',1,2)-instr(t.ZB_NAME,'_',1,1)-1) ZB,\n" +
                "    T.ZB_VALUE\n" +
                "FROM\n" +
                "    SGMC.MC_SA_ZB_PO_01 T\n" +
                "WHERE\n" +
                "        T.ZB_NAME LIKE '%焦炭%期末库存数量%' AND\n" +
                "        T.CALENDAR='" + selectDate + "'";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlJcJssrkcqst() {

        String sql = "select n.calendar, '焦炭' zb, sum(n.zb_value) zb_value\n"
                + "from (\n"
                + "         select t.calendar, '焦炭' zb, t.zb_value\n"
                + "         from sgmc.MC_SA_ZB_PO_01 t\n"
                + "         where t.ZB_NAME like '%焦炭%期末库存数量%'\n"
                + "           and t.calendar >= to_char(trunc(sysdate - 30, 'dd'), 'yyyy-mm-dd')\n"
                + "           and t.calendar <= to_char(trunc(sysdate - 1, 'dd'), 'yyyy-mm-dd')\n"
                + "           and calendar > to_char(trunc(sysdate - 30, 'dd'), 'yyyy-mm-dd')\n"
                + "     ) n\n"
                + "group by n.calendar";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlWgfgCgzx(String selectDate) {
        String sql = "select\n" +
                "    calendar,\n" +
                "    zb,\n" +
                "    sum(rk) rk,\n" +
                "    sum(ck) ck,\n" +
                "    sum(qmkcs) qm,\n" +
                "    sum(rj) rj\n" +
                "from\n" +
                "    (\n" +
                "        select\n" +
                "            calendar,\n" +
                "            (case when  ZB_NAME like '%外购普通废钢%' then '外购普通废钢'\n" +
                "                  when ZB_NAME like '%外购低硫废钢%' then '外购低硫废钢'\n" +
                "                  when ZB_NAME like '%生铁%' then '生铁'\n" +
                "                end) zb,\n" +
                "            (case when right(ZB_NAME,4)='入库' then ZB_VALUE END) rk,\n" +
                "            (case when right(ZB_NAME,4)='出库' then ZB_VALUE END) ck,\n" +
                "            (case when zb_name like '%期末库存数量%' then ZB_VALUE END) qmkcs,\n" +
                "            (case when right(ZB_NAME,8)='日均消耗' then ZB_VALUE END) rj\n" +
                "        from sgmc.mc_sa_zb_po_01 where left(ZB_NAME,8) in ('首钢迁钢','首钢冷轧')\n" +
                "                                   and calendar= '" + selectDate + "'\n" +
                "    ) where zb in ('外购普通废钢','外购低硫废钢','生铁') group by calendar,zb\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlWgfgDrkc(String selectDate) {
        String sql = "SELECT\n" +
                "    T.CALENDAR,\n" +
                "    substr(T.ZB_NAME,instr(t.ZB_NAME,'_',1,1)+1,instr(t.ZB_NAME,'_',1,2)-instr(t.ZB_NAME,'_',1,1)-1) ZB,\n" +
                "    T.ZB_VALUE\n" +
                "FROM\n" +
                "    SGMC.MC_SA_ZB_PO_01 T\n" +
                "WHERE\n" +
                "        T.ZB_NAME LIKE '%废钢%期末库存数量%' AND\n" +
                "        T.CALENDAR=TO_CHAR(CURRENT DATE-1,\n" +
                "                           'yyyy-mm-dd')\n" +
                "union all\n" +
                "SELECT\n" +
                "    T.CALENDAR,\n" +
                "    substr(T.ZB_NAME,instr(t.ZB_NAME,'_',1,1)+1,instr(t.ZB_NAME,'_',1,2)-instr(t.ZB_NAME,'_',1,1)-1) ZB,\n" +
                "    T.ZB_VALUE\n" +
                "FROM\n" +
                "    SGMC.MC_SA_ZB_PO_01 T\n" +
                "WHERE\n" +
                "        T.ZB_NAME LIKE '%生铁%期末库存数量%' AND\n" +
                "        T.CALENDAR='" + selectDate + "'";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlWgfgJssrkcqstP(String typeName) {
        String sql = "select t.CALENDAR,'" + typeName + "' zb,t.ZB_VALUE from sgmc.MC_SA_ZB_PO_01 t\n" +
                "where t.ZB_NAME like '%" + typeName + "%期末库存数量%'\n" +
                "  and t.calendar >= to_char(trunc(sysdate-30,'dd'),'yyyy-mm-dd') and t.calendar <= to_char(trunc(sysdate-1,'dd'),'yyyy-mm-dd')\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlThjCgzx(String selectDate) {
        String sql = "select\n" +
                "    calendar,\n" +
                "    zb,\n" +
                "    sum(rk) rk,\n" +
                "    sum(ck) ck,\n" +
                "    sum(qmkcs) qm,\n" +
                "    sum(rj) rj\n" +
                "from\n" +
                "    (\n" +
                "        select\n" +
                "            calendar,\n" +
                "            (case when  ZB_NAME like '%普通合金%' then '普通合金'\n" +
                "                  when ZB_NAME like '%有色合金%' then '有色合金'\n" +
                "                  when ZB_NAME like '%特种合金%' then '特种合金'\n" +
                "                  when ZB_NAME like '%复合合金%' then '复合合金'\n" +
                "                end) zb,\n" +
                "            (case when right(ZB_NAME,4)='入库' then ZB_VALUE END) rk,\n" +
                "            (case when right(ZB_NAME,4)='出库' then ZB_VALUE END) ck,\n" +
                "            (case when zb_name like '%期末库存数量%' then ZB_VALUE END) qmkcs,\n" +
                "            (case when right(ZB_NAME,8)='日均消耗' then ZB_VALUE END) rj\n" +
                "        from sgmc.mc_sa_zb_po_01 where left(ZB_NAME,8) in ('首钢迁钢','首钢冷轧')\n" +
                "                                   and calendar= '" + selectDate + "'\n" +
                "    ) where zb in ('普通合金','有色合金','特种合金','复合合金') group by calendar,zb";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlThjDrkc(String selectDate) {
        String sql = "SELECT\n" +
                "    T.CALENDAR,\n" +
                "    substr(T.ZB_NAME,instr(t.ZB_NAME,'_',1,1)+1,instr(t.ZB_NAME,'_',1,2)-instr(t.ZB_NAME,'_',1,1)-1) ZB,\n" +
                "    T.ZB_VALUE\n" +
                "FROM\n" +
                "    SGMC.MC_SA_ZB_PO_01 T\n" +
                "WHERE\n" +
                "        T.ZB_NAME LIKE '%合金%期末库存数量%' AND\n" +
                "        T.CALENDAR='" + selectDate + "'\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlThjJssrkcqst(String typeName) {
        String sql = "select t.CALENDAR,'" + typeName + "' zb,t.ZB_VALUE from sgmc.MC_SA_ZB_PO_01 t\n" +
                "where t.ZB_NAME like '%" + typeName + "%期末库存数量%'\n" +
                "  and t.calendar >= to_char(trunc(sysdate-30,'dd'),'yyyy-mm-dd') and t.calendar <= to_char(trunc(sysdate-1,'dd'),'yyyy-mm-dd')\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlPcmCgzxJhl() {
        String sql = "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_喷吹煤_神华煤_计划数量','顺义地区_喷出煤_神华煤_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_喷吹煤_阳泉煤_计划数量','顺义地区_喷吹煤_阳泉煤_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_喷吹煤_潞安煤_计划数量','顺义地区_喷吹煤_潞安煤_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_喷吹煤_其他喷吹煤_计划数量','顺义地区_喷吹煤_其他喷吹煤_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlJcCgzxJhl(String selectDate) {
        String sql = "select calendar, zb_name, zb_value zb_value\n" +
                "from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name = '迁焦_自产焦炭_计划量'\n" +
                "  and calendar = to_char(current date, 'yyyy-mm')\n" +
                "union  all\n" +
                "select calendar,\n" +
                "       SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1, 2) + 1,\n" +
                "              instr(zb_name, '_', 1, 3) - instr(zb_name, '_', 1, 2) - 1) as zb_name,\n" +
                "       sum(zb_value)                                                        zb_value\n" +
                "from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in ('迁安地区_外购焦炭_总计_计划数量', '顺义地区_外购焦炭_总计_计划数量')\n" +
                "  and calendar = to_char(current date, 'yyyy-mm')\n" +
                "group by calendar,\n" +
                "         SUBSTR(ZB_NAME, INSTR(ZB_NAME, '_', 1, 2) + 1, instr(zb_name, '_', 1, 3) - instr(zb_name, '_', 1, 2) - 1)\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlWgfgCgzxJhl() {
        String sql = "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_外购废钢_外购普通废钢_计划数量','顺义地区_外购废钢_外购普通废钢_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_外购废钢_外购低硫废钢_计划数量','顺义地区_外购废钢_外购低硫废钢_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_生铁_总计_计划数量','顺义地区_生铁_总计_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlThjCgzxJhl() {
        String sql = "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_合金_有色合金_计划数量','顺义地区_合金_有色合金_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_合金_普通合金_计划数量','顺义地区_合金_普通合金_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_合金_特种合金_计划数量','顺义地区_合金_特种合金_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n" +
                "union all\n" +
                "select calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1) as zb_name,sum(zb_value) zb_value from sgmc.mc_sa_zb_po_01\n" +
                "where zb_name in( '迁安地区_合金_复合合金_计划数量','顺义地区_合金_复合合金_计划数量')\n" +
                "  and calendar = to_char(current date,'yyyy-mm') group by calendar,SUBSTR(ZB_NAME,INSTR(ZB_NAME,'_',1,2)+1,instr(zb_name,'_',1,3)-instr(zb_name,'_',1,2)-1)\n";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlTksCgzxKgfkcnrk(String selectDate) {
        String sql = "select calendar,zb_name,zb_value from sgmc.mc_sa_zb_po_02 \n" +
                "where zb_name in ('进口块矿_到达量','进口粉矿_到达量')\n" +
                "and calendar = '" + selectDate + "'";
        return queryDataService.query(sql);
    }

    @Override
    public Object yrlTksCgzxKgfkcnck(String selectDate) {
        String sql = "select calendar,zb_name,zb_value from sgmc.mc_sa_zb_po_02\n" +
                "where zb_name in ('进口块矿_消耗量','进口粉矿_消耗合计')\n" +
                "  and calendar = '" + selectDate + "'";
        return queryDataService.query(sql);
    }
}