package com.billlog.rest.salsapan.model;

public class SalsaNotice {

    private int notice_idx;
    private String writer;
    private String regdate;
    private String title;
    private String content;
    private int hit_count;
    private String use_yn;
    private int writer_user_idx;

    public int getNotice_idx() {
        return notice_idx;
    }

    public void setNotice_idx(int notice_idx) {
        this.notice_idx = notice_idx;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHit_count() {
        return hit_count;
    }

    public void setHit_count(int hit_count) {
        this.hit_count = hit_count;
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

    @Override
    public String toString() {
        return "SalsaNotice{" +
                "notice_idx=" + notice_idx +
                ", writer='" + writer + '\'' +
                ", regdate='" + regdate + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", hit_count=" + hit_count +
                ", use_yn='" + use_yn + '\'' +
                ", writer_user_idx=" + writer_user_idx +
                '}';
    }
}
