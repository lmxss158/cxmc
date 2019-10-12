package com.sgai.cxmc.vo;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/9/29 18:38
 * @Version 1.0
 */
public class AppLoginInfo {

    private  String  username;
    private  String  usernamecn;
    private  String  userDepart;
    private  String  loginDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernamecn() {
        return usernamecn;
    }

    public void setUsernamecn(String usernamecn) {
        this.usernamecn = usernamecn;
    }

    public String getUserDepart() {
        return userDepart;
    }

    public void setUserDepart(String userDepart) {
        this.userDepart = userDepart;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }
}
