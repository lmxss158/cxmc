package com.sgai.cxmc.vo;

import java.util.List;

/**
 * @author 张年禄
 * @Descripsion
 * @date 2019/9/8 18:09
 * @Version V1.0
 */
public class ReturnVo {

    private List<String> brandList;
    private List<Integer> planValueList;
    private List<Integer> gkValueList;
    private List<Integer> rkValueList;
    private List<Integer> ckValueList;
    private List<Integer> cnkkList;
    private List<Integer> qmkList;

    public List<String> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<String> brandList) {
        this.brandList = brandList;
    }

    public List<Integer> getPlanValueList() {
        return planValueList;
    }

    public void setPlanValueList(List<Integer> planValueList) {
        this.planValueList = planValueList;
    }

    public List<Integer> getGkValueList() {
        return gkValueList;
    }

    public void setGkValueList(List<Integer> gkValueList) {
        this.gkValueList = gkValueList;
    }

    public List<Integer> getRkValueList() {
        return rkValueList;
    }

    public void setRkValueList(List<Integer> rkValueList) {
        this.rkValueList = rkValueList;
    }

    public List<Integer> getCkValueList() {
        return ckValueList;
    }

    public void setCkValueList(List<Integer> ckValueList) {
        this.ckValueList = ckValueList;
    }

    public List<Integer> getCnkkList() {
        return cnkkList;
    }

    public void setCnkkList(List<Integer> cnkkList) {
        this.cnkkList = cnkkList;
    }

    public List<Integer> getQmkList() {
        return qmkList;
    }

    public void setQmkList(List<Integer> qmkList) {
        this.qmkList = qmkList;
    }
}
