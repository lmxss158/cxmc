package com.sgai.cxmc.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/7/31 11:20
 * @Version 1.0
 */

@Data
@NoArgsConstructor
public class JsonData {

    private static final int SUCCESS = 1;
    private static final int FAIL = 0;
    private int code ;
    private String msg ;
    private Object data ;

    public JsonData(int code){
        this.code = code;
    }

    public static JsonData success(Object data, String msg) {
        JsonData jsonData = new JsonData(SUCCESS);
        jsonData.setData(data);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static JsonData success(Object obj){
        JsonData jsonData = new JsonData(SUCCESS);
        jsonData.data = obj;
        return jsonData;
    }

    public static JsonData success(){
        JsonData jsonData = new JsonData(SUCCESS);
        return jsonData;
    }

    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(FAIL);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",data);
        return map ;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
