package com.sgai.cxmc.service;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/10/11 10:39
 * @Version 1.0
 */
public interface NenghuanGuanliService {


    Object getAqizs();

    Object getAqizsBhqst(String indexName, String timeName);

    Object getWryjcJkdxx(String companyName);

    Object getWryjcAqimx(String companyName);

    Object getPwzlKednjh();

    Object getPwzlKedyjh();

    Object getPwzlKedysj();

    Object getPwzlTbjhb(String typeName);

    Object getPwzlYdqst(String typeName);

    Object getPwzlLj(String typeName);

    Object getNyglYdsj();

    Object getNyglNdsj();

    Object getNyglYdqdt( String typeName );
}
