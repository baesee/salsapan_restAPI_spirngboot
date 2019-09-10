package com.billlog.rest.salsapan.model.comment;

public class SalsaComment {
    private int comment_idx;
    private int comment_sn;
    private String writer;
    private int writer_user_idx;
    private String regdate;
    private String content;
    private String use_yn;
    private String user_name;
    private String image_url;

    public int getComment_idx() {
        return comment_idx;
    }

    public void setComment_idx(int comment_idx) {
        this.comment_idx = comment_idx;
    }

    public int getComment_sn() {
        return comment_sn;
    }

    public void setComment_sn(int comment_sn) {
        this.comment_sn = comment_sn;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getWriter_user_idx() {
        return writer_user_idx;
    }

    public void setWriter_user_idx(int writer_user_idx) {
        this.writer_user_idx = writer_user_idx;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUse_yn() {
        return use_yn;
    }

    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
