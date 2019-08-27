package com.billlog.rest.salsapan.model;

public class SalsaCommunity {
    private int community_idx;
    private String title;
    private String writer;
    private String regdate;
    private int hit_count;
    private int recommend_count;
    private String content;
    private String type;
    private String use_yn;

    private SalsaCommnet salsaCommnet;

    public int getCommunity_idx() {
        return community_idx;
    }

    public void setCommunity_idx(int community_idx) {
        this.community_idx = community_idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getHit_count() {
        return hit_count;
    }

    public void setHit_count(int hit_count) {
        this.hit_count = hit_count;
    }

    public int getRecommend_count() {
        return recommend_count;
    }

    public void setRecommend_count(int recommend_count) {
        this.recommend_count = recommend_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SalsaCommnet getSalsaCommnet() {
        return salsaCommnet;
    }

    public void setSalsaCommnet(SalsaCommnet salsaCommnet) {
        this.salsaCommnet = salsaCommnet;
    }

    public String getUse_yn() {
        return use_yn;
    }

    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }
}
