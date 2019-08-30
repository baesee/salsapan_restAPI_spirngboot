package com.billlog.rest.salsapan.model.file;

public class FileManage {
    private int file_manage_idx;
    private String regdate;
    private String use_yn;

    public FileManage() {

    }

    public FileManage(int file_manage_idx, String regdate, String use_yn) {
        this.file_manage_idx = file_manage_idx;
        this.regdate = regdate;
        this.use_yn = use_yn;
    }

    public int getFile_manage_idx() {
        return file_manage_idx;
    }

    public void setFile_manage_idx(int file_manage_idx) {
        this.file_manage_idx = file_manage_idx;
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
}
