package com.sgai.cxmc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sgai.cxmc.bean.UserAclCache;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.service.UserService;
import com.sgai.cxmc.util.AesEncryptUtil;
import com.sgai.cxmc.util.DateUtils;
import com.sgai.cxmc.vo.AppLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/7/31 13:54
 * @Version 1.0
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    static JSONObject aclObj = null; // 权限对象p
    static StringBuffer aclStrs = new StringBuffer(); //权限认证保存

    //INSERT INTO 表名称 VALUES (值1, 值2,....)
    @Override
    public Object addAppLoginInfo(AppLoginInfo appLoginInfo) {
        String userDepart = appLoginInfo.getUserDepart ( );
        String username = appLoginInfo.getUsername ( );
        String usernamecn = appLoginInfo.getUsernamecn ( );
        String loginDate = DateUtils.dateAdd2String(new Date(), "d", -1, DateUtils.defaultPattern);

        String sql = String.format ( "INSERT INTO MC_APP_LOGIN_INFO VALUES('%s','%s','%s','%s')", username, usernamecn, userDepart, loginDate );
        jdbcTemplate.execute ( sql );
        return JsonData.success ();
    }

    @Override
    public Object login(String userName, String password) {

        //登录
        /*
         *
         * 从服务器拉取的信息有：
         * 用户信息、权限信息、token及token时间、拉取时间、请求的的是迁钢还是京唐的接口(以便下次直接请求接口)；
         *
         * userName唯一，以此为主键
         *
         *
         */

        try {
//            return ossGfLogin(userName, password);
            Object obj = ossGfLogin(userName, password);
            if (obj instanceof String) {
                if(((String) obj).startsWith("帐号不存在")) {
                    return ossJtLogin(userName, password);
                }
            }
            return obj;
//            return ossJtLogin(userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("登录认证出现异常,{}" , e.getMessage());
            throw new RuntimeException("登录认证出现异常," + e.getMessage());
        }


        // 临时写测试数据返回=============================================================================================
/*

        String s = ReadJsonFileUtil.readFromClassPath("gfqxall.json");
        JSONObject menuTreeObj = JSON.parseObject(s);

        String token = UUID.randomUUID().toString().replace("-","");
        Long tokenTime = System.currentTimeMillis();

        String authorizationPlaintext = token+"|"+tokenTime+"|"+userName+"|"
                + "CXMC,MCJT,MCZL,MCFI,MCFIZYZB,MCFIGXCBGF,MCFICBGC,MCFICPYLNL,MCZZ,MCZZCLGL,MCZZKC,MCZZJSZB,"
                + "MCZZGJZB,MCZZGXZB,MCXS,MCXSDDGL,MCXSCPTJ,MCXSKHFW,MCWL,MCCG,MCCGCGGY,MCCGYRLKC,MCSB,MCNH,MCHJ,MCNY,"
                + "MCRL,MCRLDGLCL,MCRLQMRS,MCPRO,MCPRO1,MCPRO2,MCPRO3,MCPRO4,MCPRO5,MCQS,QSZL,SS02,SS0201,SS0202,SS0203,"
                + "SS0204,SS03,SS04,SS0401,SS0402,SS0403,SS0404,SS05,SS0501,SS0502,SS06,SS0601,SS0602,SS07,SS0701,SS0702,"
                + "SS0703,QSRZ,QSRZ01,QSRZ02,SS09";

        String authorization;
        try {
            authorization = AesEncryptUtil.aesEncrypt(authorizationPlaintext, KEY);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("authorization签发异常"+e.getMessage());
        }

        JSONObject obj = new JSONObject();
        obj.put("userName",userName);
        obj.put("userNameCn","测试用户名称");
        obj.put("token",token);
        obj.put("authorization",authorization);
        obj.put("menuTree",menuTreeObj);
        // 临时写测试数据返回end==========================================================================================
        return obj ;
*/

    }



    //股份用户认证
    public Object ossGfLogin(String userName,String password) throws Exception {

        //认证
        String loginUrl = "http://sso.zngc.com/auth/ac/login/dologin.action"
                + "?username="+userName+"&password="+password;
        String loginRs = restTemplate.getForObject(loginUrl,String.class);
//        System.out.println("登录认证返回：\n"+loginRs);

        JSONObject loginRsObj = JSON.parseObject(loginRs);

        boolean loginSuccess = loginRsObj.getBoolean("success");

        if (!loginSuccess) {
            //失败
            boolean metaSuccess = loginRsObj.getJSONObject("meta").getBoolean("success");
            String errMsg = loginRsObj.getJSONObject("meta").getString("message");
            log.warn("登录失败，{}" , userName);
            return errMsg;
        }

        //登录成功
        JSONObject userObj = loginRsObj.getJSONObject("data");


        //获取用户信息
        String token = userObj.getString("token");
        String userName1 = userObj.getString ("username");
        String userNameCn = userObj.getString("usernameCn");
        String orgId = userObj.getString ( "orgId" );
        AppLoginInfo appLoginInfo = new AppLoginInfo ();
        appLoginInfo.setUsername ( userName1 );
        appLoginInfo.setUsernamecn ( userNameCn );
        appLoginInfo.setUserDepart ( orgId );
        appLoginInfo.setLoginDate ( new Date ().toString () );

        addAppLoginInfo(appLoginInfo);

        String appCode = "PSDSS";
//        String appCode = "CXMC";

        //获取菜单资源
        String getMenuUrl = "http://sso.zngc.com/auth/ac/ac-resource/getUserAssignedResourceTree.action"
                + "?loginId="+userName+"&appCode="+appCode ;

//        String getMenuRs = restTemplate.getForObject(getMenuUrl,String.class);

        // getMenuUrl 这个url需要header里传递token

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization",token);

//        JSONObject jsonObj = JSONObject.fromObject(params);
        JSONObject paramsObj = new JSONObject();
//        paramsObj.put("p","p");

        HttpEntity<String> formEntity = new HttpEntity<>(paramsObj.toJSONString(), headers);

        String getMenuRs = restTemplate.postForObject(getMenuUrl, formEntity, String.class);

//        System.out.println("获取菜单资源返回：\n"+getMenuRs);

        JSONObject getMenuRsObj = JSON.parseObject(getMenuRs);

        boolean getMenuSuccess = getMenuRsObj.getBoolean("success");
        if (!getMenuSuccess) {
            log.error("获取权限资源失败，{}" , userName);
            return "获取权限资源失败";
        }

        JSONArray applicationItmes = getMenuRsObj.getJSONArray("items");
//        JSONObject item = null;
        for (int i = 0; i < applicationItmes.size(); i++) {
            //管理驾驶舱的代码为：CXMC
            if ("CXMC".equals(applicationItmes.getJSONObject(i).getString("resId"))){
//                item = applicationItmes.getJSONObject(i);
                aclObj = applicationItmes.getJSONObject(i);
                break;
            }
        }

        //执行成功后处理逻辑
        //管理驾驶舱的item下面包含了该用户可用的菜单信息，是树状结构，需要递归遍历获取对应的菜单SID
//        System.out.println("获取菜单资源筛选管理驾驶舱菜单权限：\n"+aclObj.toJSONString());
        return getLoginSucessObj(userName,userNameCn);

    }

    private Object getLoginSucessObj(String userName, String userNameCn){
        JSONObject returnObj = new JSONObject();
        returnObj.put("userName",userName);
        returnObj.put("userNameCn",userNameCn);
        processTree(aclObj);
        String myToken = UUID.randomUUID().toString().replace("-","");
        Long tokenTime = System.currentTimeMillis();

        // 将aclStrs保存
        UserAclCache.getUserAclMap().put(userName,aclStrs.toString());

//        String authorizationPlaintext = myToken+"|"+tokenTime+"|"+userName+"|"+aclStrs;
        String authorizationPlaintext = myToken+"|"+tokenTime+"|"+userName; // 2019-9-5 16:30:59  由于aclStrs太大无法回传，因此需要存在数据，不能在这里传递了
        String authorization;
        try {
            authorization = AesEncryptUtil.aesEncrypt(authorizationPlaintext, AesEncryptUtil.KEY);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("authorization签发异常");
            throw new RuntimeException("authorization签发异常"+e.getMessage());
        }
        returnObj.put("menuTree",aclObj);
        returnObj.put("token",myToken);
        returnObj.put("authorization",authorization);
        return returnObj;
    }

    //京唐用户认证
    public Object ossJtLogin(String userName,String password) throws Exception {

        //认证
        String loginUrl = "http://yhsq.sgjtsteel.com/ac/login/doOutLogin.action"
                + "?accountId="+userName+"&password="+password;
        String loginRs = restTemplate.getForObject(loginUrl,String.class);
//        System.out.println("登录认证返回：\n"+loginRs);

        JSONObject loginRsObj = JSON.parseObject(loginRs);

        //请求成功标识
        boolean requestSuccess = loginRsObj.getBoolean("success");

        if (!requestSuccess) {
            //失败
            boolean metaSuccess = loginRsObj.getJSONObject("meta").getBoolean("success");
            String errMsg = loginRsObj.getJSONObject("meta").getString("message");
            //执行失败
            log.warn("登录失败 ，{}" , userName);
            return errMsg;
        }

        JSONObject loginData = loginRsObj.getJSONObject("data");
        boolean loginSuccess = loginData.getBoolean("bamAuthSuccess");
        if (!loginSuccess) {
            //登录失败
            String statusCode = loginData.getString("statusCode");

            // 若为密码错误，errMsg则可直接返回给客户端，示例返回：用户认证失败! 请检查用户名或密码是否输入正确。
            // 若为用户不存在，则调用股份认证接口(若之前没有调用股份接口的情况下，否则直接返回给用户)
            String errMsg = loginData.getString("message");

            //密码不正确："{\"exception\":{\"message\":\"Invalid Password!!\",\"name\":\"com.bamboocloud.bam.idsvcs.InvalidPassword\"}}"
            //用户不存在："{\"exception\":{\"message\":\"Authentication Failed!!\",\"name\":\"com.bamboocloud.bam.idsvcs.InvalidCredentials\"}}"
            JSONObject reason = JSON.parseObject(loginData.getString("reason"));
            log.warn("登录失败 ，{}， {} " , errMsg, reason);
            //失败
            return errMsg;
        }

        //登录成功
        JSONObject userObj = loginData.getJSONObject("userBean");


        //获取用户信息
        String token = userObj.getString("token");
        String userNameCn = userObj.getString("usernameCn");

        String appCode = "PSDSS";

        //获取菜单资源
        String getMenuUrl = "http://yhsq.sgjtsteel.com/ac/ac-resource/getOutUserAssignedResourceTree.action"
                + "?loginId="+userName+"&appCode="+appCode ;

//        String getMenuRs = restTemplate.getForObject(getMenuUrl,String.class);

        // getMenuUrl 这个url需要header里传递token

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization",token);

//        JSONObject jsonObj = JSONObject.fromObject(params);
        JSONObject paramsObj = new JSONObject();
//        paramsObj.put("p","p");

        HttpEntity<String> formEntity = new HttpEntity<>(paramsObj.toJSONString(), headers);

        String getMenuRs = restTemplate.postForObject(getMenuUrl, formEntity, String.class);

//        System.out.println("获取菜单资源返回：\n"+getMenuRs);

        JSONObject getMenuRsObj = JSON.parseObject(getMenuRs);

        boolean getMenuSuccess = getMenuRsObj.getBoolean("success");
        if (!getMenuSuccess) {
            log.warn("获取权限资源失败，{} " , userName);
            return "获取权限资源失败";
        }

        JSONArray applicationItmes = getMenuRsObj.getJSONArray("items");
//        JSONObject item = null;
        for (int i = 0; i < applicationItmes.size(); i++) {
            //管理驾驶舱的代码为：CXMC
            if ("CXMC".equals(applicationItmes.getJSONObject(i).getString("resId"))){
//                item = applicationItmes.getJSONObject(i);
                aclObj = applicationItmes.getJSONObject(i);
                break;
            }
        }
//        System.out.println("获取菜单资源筛选管理驾驶舱菜单权限：\n"+aclObj.toJSONString());

        // 成功
        return getLoginSucessObj(userName,userNameCn);
    }

    private static void processTree(JSONObject item){
        item.remove("updatedBy");
        item.remove("resVisibility");
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
        aclStrs.append(item.getString("sid")).append(",");
//        strs.append(item.getString("resId")).append(",");
        if (!item.getBoolean("leaf")) {
            JSONArray items = item.getJSONArray("items");
            for (int i=0; i<items.size(); i++) {
                processTree(items.getJSONObject(i));
            }
        }
    }

//    public static void main(String[] args) {
//        System.out.println(UUID.randomUUID().toString().replace("-",""));
//
//    }

}
