package com.billlog.rest.salsapan.model;

import com.billlog.rest.salsapan.model.comment.SalsaComment;

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
    private int writer_user_idx;
    private String image_url; // 사용자 프로필 이미지 URL
    private int comment_idx; // 댓글관리 번호
    private String writer_user_name;

    private SalsaComment salsaComment;

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

    public String getUse_yn() {
        return use_yn;
    }

    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }

    public int getWriter_user_idx() {
        return writer_user_idx;
    }

    public void setWriter_user_idx(int writer_user_idx) {
        this.writer_user_idx = writer_user_idx;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getComment_idx() {
        return comment_idx;
    }

    public void setComment_idx(int comment_idx) {
        this.comment_idx = comment_idx;
    }

    public SalsaComment getSalsaComment() {
        return salsaComment;
    }

    public void setSalsaComment(SalsaComment salsaComment) {
        this.salsaComment = salsaComment;
    }

    public String getWriter_user_name() {
        return writer_user_name;
    }

    public void setWriter_user_name(String writer_user_name) {
        this.writer_user_name = writer_user_name;
    }
}
