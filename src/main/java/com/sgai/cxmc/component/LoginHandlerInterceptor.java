package com.sgai.cxmc.component;

import com.alibaba.fastjson.JSON;
import com.sgai.cxmc.annotation.MenuAcls;
import com.sgai.cxmc.bean.UserAclCache;
import com.sgai.cxmc.common.JsonData;
import com.sgai.cxmc.util.AesEncryptUtil;
import com.sgai.cxmc.util.IpUtil;
import com.sgai.cxmc.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Description 登录及权限校验拦截器
 * @Author: 李锐平
 * @Date: 2019/5/17 14:35
 * @Version 1.0
 */
@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
//        log.info(uri);   http://localhost:8080/mc/app/version/info


        // TODO  临时测试开启或关闭登录及权限校验; 生产上线删除此代码块
        String chk = request.getParameter("chklrptest");
//        chk = "skipchklrptest";
       /* if (uri.indexOf("/mc/app")>0){
            chk = "skipchklrptest";
        }
*/
        if ("skipchklrptest".equals(chk)) {
            return true;
        }


//        String uri = request.getRequestURI();
//        log.info("未登录，访问IP:{},请求：{}", IpUtil.getRemoteIp(request), uri);

        /*
         * 逻辑梳理：
         * 1. 解析Authorization ， 判断token是否过期
         *  authorization解析后的构成 token|tokenTime|userName|aclStrs;
         * 2. 验证签名是否合法，并判断是否超时（签名的时间戳，距离当前时间不能超过一定的时长，比如不能超过10秒。）
         * 3. 获取Authorization的权限信息，并读取请求的controller上注解的权限，判断是否具有请求权限，
         *    需要结合companyCode。
         *    （比如某用户拥有京唐的请求总览权限，但是没有请求股份总览权限，则根据companyCode判断是请求股份还是京唐，做出对应处理）。
         *
         * */


        String authorization = request.getHeader("Authorization");
        String userName = request.getParameter("userName");
        String subCode = request.getParameter("subCode");
        String sign = request.getParameter("sign");
        String timeStr = request.getParameter("ts");


        // 判空
        if (StringUtils.isEmpty(authorization)
                || StringUtils.isEmpty(userName)
                || StringUtils.isEmpty(subCode)
                || StringUtils.isEmpty(sign)
                || StringUtils.isEmpty(timeStr) ) {
            preHandleProcessFail(request, response, "缺少必须的参数！");
            return false;
        }


        // 提交时间转换
        Long time = null;
        try {
            time = Long.valueOf(timeStr);
        } catch (Exception e) {
            preHandleProcessFail(request, response, "ts参数错误");
            return false;
        }

        // 权限信息解析
        String authorizationText = null;
        String[] authorizations = null;
        try {
            authorizationText = AesEncryptUtil.aesDecrypt(authorization, AesEncryptUtil.KEY);
            authorizations = authorizationText.split("\\|");
        } catch (Exception e) {
            log.warn("用户认证错误，{} " , userName);
            preHandleProcessFail(request, response, "用户认证错误，请重新登录");
            return false;
        }


        String token = authorizations[0];
        Long tokenTime = Long.valueOf(authorizations[1]); // token签发时间
        String userName1 = authorizations[2]; // 签发token和用户认证信息中的用户名


        //2019-9-5 16:32:51  由于权限信息太大，取消在header里保存,
//        String aclStrs = authorizations[3];  // 用户权限信息
        String aclStrs = UserAclCache.getUserAclMap().get(userName);
        if (StringUtils.isEmpty(aclStrs)) {
            log.error("用户权限错误，{} , {}" , userName, IpUtil.getRemoteIp(request));
            preHandleProcessFail(request, response, "用户无任何权限，请重新登录尝试或联系管理员分配权限");
            return false;
        }

        Long nowTs = System.currentTimeMillis(); // 当前时间戳

        //用户合法性校验
        if (!userName.equals(userName1)) {
            log.warn("用户认证错误，{} , {}" , userName, IpUtil.getRemoteIp(request));
            preHandleProcessFail(request, response, "用户非法");
            return false;
        }

        // 时效性校验
        int requestLong = 60 * 1000; // 暂定1分钟
//        int requestLong = 48 * 60  * 60 * 1000; // 测试暂定时间长
//        if (time > nowTs) {
//            //传递的时间戳大于当前时间，不合逻辑
//            preHandleProcessFalse(request, response, "请求非法");
//            return false;
//        }
        if (nowTs - time > requestLong) {
            preHandleProcessFail(request, response, "请求已过期");
            return false;
        }

        //token时间校验
        int tokenLong = 48 * 60 * 60 * 1000; // 暂定48小时
//        int tokenLong = 4800 * 60 * 60 * 1000; // 测试暂时放时间长一些
        if (nowTs - tokenTime > tokenLong) {
            preHandleProcessFail(request, response, "token已过期");
            return false;
        }

        //校验签名
        //使用简易的url签名，规则为：请求路径、userName、token、时间戳ts 四项按此顺序直接连接后的md5值；
        String correctSign = MD5Util.encrypt(uri+userName+token+timeStr); // 正确的签名值
        if (!sign.equals(correctSign)) {
            log.warn("签名不正确，{} , {} " , userName, IpUtil.getRemoteIp(request));
            preHandleProcessFail(request, response, "签名不正确");
            return false;
        }

        HandlerMethod methodHandler = (HandlerMethod) handler;

        // 权限校验：当前精确到菜单，若需要精确到具体接口，则需要在需要的接口对应的方法上添加注解，在此处获取校验（获取的代码下面已经注释）
        String[] menuAclvalues = methodHandler.getBean().getClass().getAnnotation(MenuAcls.class).value();
//        if (menuAclvalues!=null && menuAclvalues.length>0) {
//            Arrays.asList(menuAclvalues).forEach(item -> System.out.println("获取类上的注解值："+item));
//        }


//        String[] apiAclvalues = methodHandler.getMethod().getAnnotation(MenuAcls.class).value();
//        if (apiAclvalues!=null && apiAclvalues.length>0) {
//            Arrays.asList(apiAclvalues).forEach(item -> System.out.println("获取方法上的注解值："+item));
//        }



        if (menuAclvalues != null && menuAclvalues.length > 0) {
            for (String menuAcl : menuAclvalues ) {
                if (aclStrs.contains(menuAcl)) {
                    //有权限
                    return true;
                }
            }
            log.warn("无权限访问，{} , {} " , userName, IpUtil.getRemoteIp(request));
            preHandleProcessFail(request, response, "无权限访问");
            return false;
        } else {
            // 若没有注解的方法，暂时设置为有权限，后续根据情况可设置为无权限
            return true;
        }


/*
        System.out.println("======================");
        System.out.println(authorization);
        System.out.println(uri);
        System.out.println(System.currentTimeMillis());
        System.out.println(userName);
        System.out.println(sign);
        System.out.println(timeStr);
        System.out.println("======================");
        System.out.println(MD5Util.encrypt("/test/queryzhangsanabcdefg1564732250261"));
        System.out.println("123456:"+MD5Util.encrypt("123456"));
        System.out.println("======================");

        HandlerMethod methodHandler = (HandlerMethod) handler ;

        String[] values = methodHandler.getBean().getClass().getAnnotation(MenuAcls.class).value();
        if (values!=null && values.length>0) {
            Arrays.asList(values).forEach(item -> System.out.println("获取类上的注解值："+item));
        }

        if ("abcd".equals(userName)) {
            String msg = "test interceptor e";
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(JsonData.fail(msg)));
            writer.flush();
            writer.close();
            return false;
        }
*/
//        return true;

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }


    private void preHandleProcessFail(HttpServletRequest request, HttpServletResponse response, String msg) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(JsonData.fail(msg)));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            return;
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }


    }

}
