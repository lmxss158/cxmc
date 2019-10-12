package com.sgai.cxmc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/8/3 12:28
 * @Version 1.0
 */

public class ReadJsonFileUtil {

    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从类路径读json文件
     * @param fileName 文件名，不包含全路径
     * @return
     */
    public static String readFromClassPath(String fileName){
        String path = ReadJsonFileUtil.class.getClassLoader().getResource(fileName).getPath();
        return readJsonFile(path);
    }

    private static void readAllAppItem(){
        String path = ReadJsonFileUtil.class.getClassLoader().getResource("jtqx.json").getPath();
        String s = readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);


        JSONArray applicationItmes = jobj.getJSONArray("items");
        JSONObject item = null;
        for (int i = 0; i < applicationItmes.size(); i++) {
            //管理驾驶舱的代码为：CXMC
            if ("CXMC".equals(applicationItmes.getJSONObject(i).getString("resId"))){
                item = applicationItmes.getJSONObject(i);
                break;
            }
        }

        System.out.println(item.toJSONString());
    }

    private static void processTreeArray(JSONArray items){

        for (int i=0; i<items.size(); i++) {

            JSONObject item = items.getJSONObject(i);

//            String resId = item.getString("resId");
//            String resName = item.getString("resName");
//            String parentSid = item.getString("parentSid");
//            String sid = item.getString("sid");

            item.remove("resVisibility");
            item.remove("updatedBy");
            item.remove("resType");
            item.remove("version");
            item.remove("connected");
            item.remove("createdDt");
            item.remove("resSeq");
            item.remove("resLevel");
            item.remove("createdBy");
            item.remove("updatedDt");
            item.remove("iconCls");
            item.remove("relAccessRul");

            if (!item.getBoolean("leaf")) {
                processTreeArray(item.getJSONArray("items"));
            }
        }

    }

    static JSONObject obj = null;
    static StringBuffer strs = new StringBuffer();
    private static void processTree(JSONObject item){
        item.remove("resVisibility");
        item.remove("updatedBy");
        item.remove("resType");
        item.remove("version");
        item.remove("connected");
        item.remove("createdDt");
        item.remove("resSeq");
        item.remove("resLevel");
        item.remove("createdBy");
        item.remove("updatedDt");
        item.remove("iconCls");
        item.remove("relAccessRul");
        item.remove("resPageUrl");
//        strs.append(item.getString("resId")).append(",");
        strs.append(item.getString("sid")).append(",");

        if (!item.getBoolean("leaf")) {
            JSONArray items = item.getJSONArray("items");
            for (int i=0; i<items.size(); i++) {
                processTree(items.getJSONObject(i));
            }
        }

    }

    private static void readGljscQx(){

        String path = ReadJsonFileUtil.class.getClassLoader().getResource("gljscgf.json").getPath();
        String s = readJsonFile(path);
//        JSONObject jobj = JSON.parseObject(s);
        obj = JSON.parseObject(s);
        // 读取的文件为菜单树，递归删除多余的属性
        processTree(obj);
        System.out.println(obj.toJSONString());
        System.out.println("===========");
        System.out.println(strs.toString());
    }

    public static void main(String[] args) {

//        readAllAppItem();
        readGljscQx();
    }
}
