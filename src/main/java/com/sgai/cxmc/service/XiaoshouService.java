package com.sgai.cxmc.service;

/**
 * @Description: 销售模块
 * @Author: 李锐平
 * @Date: 2019/8/11 10:27
 * @Version 1.0
 */

public interface XiaoshouService {

    Object ddzxDdl(String typeName);

    Object ddzxZdcpwcjd();

    Object ddzxZdkhwcjd();

    Object ddzxKcjg( String typeName);

    Object ddzxKcjgfl(String typeName);

    Object ddzxDxlydqs(String typeName);

    Object ddzzCyddjdl();

    Object ddzzWclydqs(String typeName);

    Object ddzzFzzxsjhwcl();

    Object kfZlyyYlj();

    Object kfZlyyYyjsfltj(String typeName);

    Object kfZlyyYyjsydqs(String typeName);

    Object kfZlyyYyqxntj(String typeName);

    Object kfZlyyYyqxytj(String typeName);

    Object kfZlyyYyqxjs(String typeName);

    Object kfFwxlZlyyclzqydqs(String typeName);

    Object kfFwxlSqbywtjjlydqs(String typeName);

    Object kfFwxlJszxydzqydqs(String typeName);

    Object kfFwxlKhzfjsydqs(String typeName);

    Object kfQcbppmDyjfl();

    Object kfQcbppmyyl();

    Object kfQcbppmydqs(String customerName);

    Object cptjGdlxcpyljjd();

    Object cptjQtzdcpyljjdQcsxsj();

    Object cpRzqkfltj(String typeName);

    Object cpRzqkydqs(String typeName);

    Object cpJthjghlEvixms();

    Object cpXscpxlnlj();

    Object cpXscpydxlqs(String typeName);

    Object ddzxKcjgJcqhqst(String typeName);

    Object ddzxKcjgKyqzqst(String typeName);

    Object cptjQtzdcpyljjdDlwsj();

    Object cptjQtzdcpyljjdJjmshj();

    Object cptjQtzdcpyljjdJjmjh();

    Object cpyzflLjjdQcb();

    Object cpyzflLjjdLrjhz();

    Object cpyzflLjjdGg();

    Object kfZlyyNlj();

    Object kfZlyyYyjsnljtj(String typeName);

    Object kfZlyyYyqxflntj(String typeName);

    Object kfZlyyYyqxflytj(String typeName);

    Object kfFwxlZlyyZgzq(String typeName);

    Object cpKfyrztj(String approveName);

    Object cpJthjghlNdjh();

    Object cpJthjghlYdwc();

    Object cpJthjghlNdljwc();

    Object cpJthjghlNdljtb();

    Object cpGfhjghlEvixms();

    Object cpGfhjghlNdjh();

    Object cpGfhjghlYdjh();

    Object cpGfhjghlNdljwc();

    Object cpGfhjghlNdljtb();

    Object cpGhlRqslxms(String typeName);

    Object cpGfhjghlRqslJhwc(String typeName);

    Object cpJthjghlYdghjxsghqst(String typeName);

    Object cpGfhjghlYdghjxsghqst(String typeName);

    Object cpRqslghlYdghjxsghqst(String tabName, String productName);

    Object cpGfhjghlRqslWctb(String typeName);
}
