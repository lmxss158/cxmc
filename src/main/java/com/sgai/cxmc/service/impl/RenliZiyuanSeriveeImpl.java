package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.QueryDataService;
import com.sgai.cxmc.service.RenliZiyuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/10/16 13:57
 * @Version 1.0
 */
@Service("renliZiyuanServiceQs")
public class RenliZiyuanSeriveeImpl implements RenliZiyuanService {

    @Autowired
    QueryDataService queryDataService;

    @Override
    public Object getLclrgfLcxxjqst() {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb = '劳产率（转炉钢）'\n"
                + "  and fl in ('当月', '累计')\n"
                + "  and calendar >= (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 6 month, 'yyyy-mm')\n"
                + "                   from SGMC.V_MC_ZB_RLZY\n"
                + "                   where zb = '劳产率（转炉钢）'\n"
                + "                     and fl in ('当月', '累计')\n"
                + "                     and zb_value <> 0)\n"
                + "  and calendar <= (select max(calendar)\n"
                + "                   from SGMC.V_MC_ZB_RLZY\n"
                + "                   where zb = '劳产率（转炉钢）'\n"
                + "                     and fl in ('当月', '累计')\n"
                + "                     and zb_value <> 0)\n"
                + "  and zb_value is not null\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getLclrgfRgfjbqn() {
        String sql = "(select *\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb in ('人工费', '在岗人均人工费', '吨钢人工费')\n"
                + "  and calendar =\n"
                + "      (select to_char(to_date(max(CALENDAR),'yyyy-mm')-1 month,'yyyy-mm') CALENDAR from SGMC.V_MC_ZB_RLZY where  zb in ('人工费','在岗人均人工费','吨钢人工费') and zb_value <>0)\n"
                + "  and zb_value is not null\n"
                + "order by calendar)\n"
                + "union all\n"
                + "select *\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb in ('人工费', '在岗人均人工费', '吨钢人工费')\n"
                + "  and calendar =\n"
                + "      (select to_char(to_date(max(CALENDAR),'yyyy-mm')-13 month,'yyyy-mm') CALENDAR from SGMC.V_MC_ZB_RLZY where  zb in ('人工费','在岗人均人工费','吨钢人工费') and zb_value <>0)\n"
                + "  and zb_value is not null\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getLclrgfRgfydqst() {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb in ( '在岗人均人工费', '吨钢人工费')\n"
                + "  and calendar <=\n"
                + "      (select max(calendar) from SGMC.V_MC_ZB_RLZY where zb in ( '在岗人均人工费', '吨钢人工费') and zb_value <> 0)\n"
                + "  and zb_value is not null\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getLclrgfRgfdy() {
        String sql = "(select *\n"
                + " from SGMC.V_MC_ZB_RLZY\n"
                + " where zb ='人工费'\n"
                + "   and calendar =\n"
                + "       (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 0 month, 'yyyy-mm') CALENDAR\n"
                + "        from SGMC.V_MC_ZB_RLZY\n"
                + "        where zb = '人工费'\n"
                + "          and zb_value <> 0)\n"
                + "   and zb_value is not null\n"
                + " order by calendar)";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getLclrgfTrccndzb() {
        String sql = "select calendar, zblb, zb_name, SUM(SUMYEAR) SUMYEAR\n"
                + "from SGMC.V_MC_ZB_FI\n"
                + "where zb_name in ('营业总收入', '营业成本')\n"
                + "  and company_code in ('1A10', '1A20')\n"
                + "  and CALENDAR = (select max(calendar)\n"
                + "                  from SGMC.V_MC_ZB_FI\n"
                + "                  where zb_name in ('营业总收入', '营业成本')\n"
                + "                    and company_code = '1A10'\n"
                + "                    and SUMYEAR <> 0)\n"
                + "GROUP BY ZB_NAME, CALENDAR, ZBLB";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getQmrsZxrsydqst() {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb = '在册人数'\n"
                + "  and calendar >= (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 12 month, 'yyyy-mm')\n"
                + "                   from SGMC.V_MC_ZB_RLZY\n"
                + "                   where zb = '在册人数'\n"
                + "                     and zb_value <> 0)\n"
                + "  and calendar <= (select max(calendar) from SGMC.V_MC_ZB_RLZY where zb = '在册人数' and zb_value <> 0)\n"
                + "  and zb_value is not null\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getQmrsDqrs() {
        String sql = "(select calendar, sum(zb_value) as zb_value\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb in ('在册中在岗人数', '返聘人数')\n"
                + "  and calendar >= (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 12 month, 'yyyy-mm')\n"
                + "    from SGMC.V_MC_ZB_RLZY\n"
                + "    where zb in ('在册中在岗人数', '返聘人数')\n"
                + "  and zb_value <> 0)\n"
                + "  and calendar = (select max(calendar) from SGMC.V_MC_ZB_RLZY where zb in ('在册中在岗人数', '返聘人数') and zb_value <> 0)\n"
                + "  and zb_value is not null\n"
                + "group by calendar\n"
                + "order by calendar )\n"
                + "union all\n"
                + "(select calendar, sum(zb_value) as zb_value\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb in ('在册中在岗人数', '返聘人数')\n"
                + "  and calendar >= (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 12 month, 'yyyy-mm')\n"
                + "    from SGMC.V_MC_ZB_RLZY\n"
                + "    where zb in ('在册中在岗人数', '返聘人数')\n"
                + "  and zb_value <> 0)\n"
                + "  and calendar = (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 1 month, 'yyyy-mm') from SGMC.V_MC_ZB_RLZY where zb in ('在册中在岗人数', '返聘人数') and zb_value <> 0)\n"
                + "  and zb_value is not null\n"
                + "group by calendar\n"
                + "order by calendar)\n"
                + "union all\n"
                + "(select calendar, sum(zb_value) as zb_value\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb in ('在册中在岗人数', '返聘人数')\n"
                + "  and calendar >= (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 12 month, 'yyyy-mm')\n"
                + "    from SGMC.V_MC_ZB_RLZY\n"
                + "    where zb in ('在册中在岗人数', '返聘人数')\n"
                + "  and zb_value <> 0)\n"
                + "  and calendar = (select to_char(sysdate-month(sysdate) month,'yyyy-mm') from sysibm.dual)\n"
                + "  and zb_value is not null\n"
                + "group by calendar\n"
                + "order by calendar )\n";
        return queryDataService.query ( sql );
    }

    @Override
    public Object getQmrsCyryydtxt() {
        String sql = "select *\n"
                + "from SGMC.V_MC_ZB_RLZY\n"
                + "where zb in ('在册中在岗人数', '返聘人数')\n"
                + "  and calendar >= (select to_char(to_date(max(CALENDAR), 'yyyy-mm') - 12 month, 'yyyy-mm')\n"
                + "                   from SGMC.V_MC_ZB_RLZY\n"
                + "                   where zb in ('在册中在岗人数', '返聘人数')\n"
                + "                     and zb_value <> 0)\n"
                + "  and calendar <= (select max(calendar) from SGMC.V_MC_ZB_RLZY where zb in ('在册中在岗人数', '返聘人数') and zb_value <> 0)\n"
                + "  and zb_value is not null\n"
                + "order by calendar";
        return queryDataService.query ( sql );
    }
}
