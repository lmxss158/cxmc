package com.sgai.cxmc.service;

/**
 * @Description
 * @Author 张年禄
 * @Date 2019/9/26 13:29
 * @Version 1.0
 */
public interface CaiwuguanliService {

    Object getZyzbYysr(String companyName);

    Object getZyzbLr(String companyName);

    Object getZyzbYlnl(String indexName,String companyName);

    Object getZyzbYqst(String indexName,String companyName);

    Object getZyzbHbsy(String indexName,String companyName);

    Object getZyzbYdtb(String indexName,String companyName);

    Object getZcxlYsj(String indexName,String companyName);

    Object getZyzbZcxlHbsy(String indexName,String companyName);

    Object getZyzbZcxlYdtb(String indexName,String companyName);

    Object getZyzbZcxlYqst(String indexName,String companyName);

    Object getZyzbZjglYlnl(String indexName,String companyName);

    Object getZyzbZjglYqst(String indexName,String companyName);

    Object getCbgcYdcbf(String areaName);

    Object getCbgcZxgsYdcbf(String tableName);

    Object getCbgcSylzYdcbf(String tableName);

    Object getCbgcJgfgc(String areaName);

    Object getCbgcZxgsJgfgc(String tableName);

    Object getCbgcJgfgcMidval(String areaName);

    Object getcwglGxcbTqcbglxsl();

    Object getcwglGxcbTqcbhbsy( );

    Object getcwglGxcbTqcbCbydqst(String typeName);

    Object getcwglGxcbGhcbglxsl( );

    Object getcwglGxcbGhcbHbsy( );

    Object getcwglGxcbGhcbCbydqst(String typeName);

    Object getcwglCpylnlMllrzyzb(String companyName);

    Object getcwglCpylnlXsmlqst(String companyName);

    Object getcwglCpylnlBjlrqst(String companyName);
}
