package com.billlog.rest.salsapan.model.comment;

public class SalsaCommentManage {
    private int comment_manage_idx;
    private String regdate;
    private String use_yn;

    public int getComment_manage_idx() {
        return comment_manage_idx;
    }

    public void setComment_manage_idx(int comment_manage_idx) {
        this.comment_manage_idx = comment_manage_idx;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getUse_yn() {
        return use_yn;
    }

    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }

    @Override
    public String toString() {
        return "CommentManage{" +
                "comment_manage_idx=" + comment_manage_idx +
                ", regdate='" + regdate + '\'' +
                ", use_yn='" + use_yn + '\'' +
                '}';
    }
}
