package com.sgai.cxmc.service;

/**
 * @Description: 制造模块接口
 * @Author: 李锐平
 * @Date: 2019/8/5 16:05
 * @Version 1.0
 */

public interface ZhizaoService {


    Object clClzl();

    Object clYdqst(String zbName,Integer monthNums);

    Object clGfrfd();

    Object clGfyfd();

    Object clGffdlydqs(Integer monthNums);

    Object clSxrcl();

    Object clSxycl();

    Object clSxyspccljg(Integer monthNums);

    Object clQtrcl();

    Object clQtycl();

    Object clSjrcl();

    Object clSjycl();

    Object clLtrcl();

    Object clLtycl();

    Object clLtglcl();

    Object clLgrcl();

    Object clLgycl();

    Object clLgcxcl();

    Object clLgrlgls();

    Object clRzrcl();

    Object clRzycl();

    Object clRzcxcl();

    Object clRzyspccljg(String cxName);

    Object clZxrcl();

    Object clZxycl();

    Object clZxyspccljg();

    Object clSlrcl();

    Object clSlycl();

    Object clSlyspccljg();

    Object clSlyspccljglz();

    Object clQjrcl();

    Object clQjycl();

    Object clQjygsjl();


    Object kcZbcpkcmx();

    Object kcZbcpkcfb();

    Object kcLgkcjg(String typeName);

    Object kcLg1kcqs(String typeName);

    Object kcLg2kcqs();

    Object kcRzkcjg(String typeName);

    Object kcRzkcqs(String typeName);

    Object kcSxkcjg();

    Object kcSxkcqs(String typeName);

    Object kcSlkcjg();

    Object kcSlkcqs(String typeName);

    Object kcZxkcjg(String typeName);

    Object kcZxkcqs(String typeName);

    Object gxzbLiantie(String typeName);

    Object gxzbLiangang(String typeName);

    Object gxzbRezha(String typeName);

    Object gxzbLengzha(String typeName);

    Object gxzbSuanxi(String typeName);

    Object gjzbZlztlqs(String typeName, String flTypeName,String dateTypeName);

    Object gjzbZxZlztlylj();

    Object gjzbZlztlfb(String typeName,String dateTypeName);

    Object gjzbDcplqs(String typeName, String flTypeName,String dateTypeName);

    Object gjzbZxDcplylj();

    Object gjzbDcpgc(String typeName, String dateTypeName);

    Object gjzbFcjlqs(String typeName, String flTypeName,String dateTypeName);

    Object gjzbZxFcjlylj();

    Object gjzbFcjgc(String typeName, String dateTypeName);

    Object gjzbDxlydqs(String typeName, String qsTypeName);

    Object clSlyclbjh();

    Object gxzbLiantieGlfh(String typeName);
}
