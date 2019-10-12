package com.sgai.cxmc.service;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/8/24 10:03
 * @Version 1.0
 */

public interface ShebeiService {


    Object zlGlyljtjsj();

    Object zlTjsjqsd(String typeName);

    Object zlTjsjqsm(String typeName);

    Object zlYljtjyygc(String typeName);

    Object mxGlyljtjsj();

    Object mxTjsjqsd(String typeName);

    Object mxTjsjqsm(String typeName);

    Object mxYljtjyygc(String typeName);


    Object fjhtjGlyljtjsj();

    Object fjhtjTjsjqsd(String typeName);

    Object fjhtjTjsjqsm(String typeName);

    Object fjhtjYljtjyygc(String typeName);

    Object jzyxzt(String typeCode);

    Object wxcbGdwxlfy();

    Object wxcbWxfyydqs(String typeName);

    Object wxcbXlfygc(String typeName);

}
