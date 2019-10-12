package com.sgai.cxmc.service.impl;

import com.sgai.cxmc.service.QueryDataService;
import com.sgai.cxmc.util.MyStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @Description: 针对无参数的sql，查询数据，并封装为list对象，将数据库字段映射为返回参数名，并转为驼峰格式
 * @Author: 李锐平
 * @Date: 2019/7/31 13:54
 * @Version 1.0
 */


@Service
public class QueryDataServiceImpl implements QueryDataService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> query(String sql) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        return MyStringUtil.listFormatHumpName(result);
    }
}
