package com.sgai.cxmc.util;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 下划线与驼峰互转
 * @Author: 李锐平
 * @Date: 2019/7/31 15:00
 * @Version 1.0
 */

public class MyStringUtil {

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线,最后转为大写
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toUpperCase();
    }

    /**
     * 下划线转驼峰,正常输出
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = linePattern.matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }



    //把list对象中的map的key格式化为驼峰
    public static List<Map<String, Object>> listFormatHumpName(List<Map<String, Object>> list) {
        if (list == null || list.size() <=0) {
            return list;
        }
        List<Map<String, Object>> returnData = new ArrayList<>(list.size());
        for (Map map: list ) {
            returnData.add(formatHumpName(map));
        }
        return returnData;
    }


    //把map的key格式化为驼峰
    public static Map<String, Object> formatHumpName(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = lineToHump(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }


}
