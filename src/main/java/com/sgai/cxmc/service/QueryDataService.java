package com.sgai.cxmc.service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/7/31 13:53
 * @Version 1.0
 */

public interface QueryDataService {
    List<Map<String, Object>> query(String sql);
}
