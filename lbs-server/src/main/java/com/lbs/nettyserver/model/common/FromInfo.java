package com.lbs.nettyserver.model.common;

import java.math.BigDecimal;

/**
 * Created by will on 18/1/5.
 *
 */
public class FromInfo {

    private String userId;

    private String loginId;

    private String nickName;

    private String headImg;

    private String sex;

    private BigDecimal jd;

    private BigDecimal wd;

    private String locationName;

    private String lv;

    private Integer liveScore;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BigDecimal getJd() {
        return jd;
    }

    public void setJd(BigDecimal jd) {
        this.jd = jd;
    }

    public BigDecimal getWd() {
        return wd;
    }

    public void setWd(BigDecimal wd) {
        this.wd = wd;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public Integer getLiveScore() {
        return liveScore;
    }

    public void setLiveScore(Integer liveScore) {
        this.liveScore = liveScore;
    }
}
