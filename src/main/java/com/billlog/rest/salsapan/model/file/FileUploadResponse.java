package com.billlog.rest.salsapan.model.file;

public class FileUploadResponse {

    private int file_idx;
    private int file_sn;
    private String file_name;
    private String org_filename;
    private String file_path;
    private String regdate;
    private long size;
    private String file_download_uri;
    private String use_yn;
    private String file_type;

    public FileUploadResponse(String file_name, String org_filename, String file_download_uri, String file_type, long size) {
        this.file_name = file_name;
        this.org_filename = org_filename;
        this.file_download_uri = file_download_uri;
        this.file_type = file_type;
        this.size = size;
    }

    public int getFile_idx() {
        return file_idx;
    }

    public void setFile_idx(int file_idx) {
        this.file_idx = file_idx;
    }

    public int getFile_sn() {
        return file_sn;
    }

    public void setFile_sn(int file_sn) {
        this.file_sn = file_sn;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getOrg_filename() {
        return org_filename;
    }

    public void setOrg_filename(String org_filename) {
        this.org_filename = org_filename;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFile_download_uri() {
        return file_download_uri;
    }

    public void setFile_download_uri(String file_download_uri) {
        this.file_download_uri = file_download_uri;
    }

    public String getUse_yn() {
        return use_yn;
    }

    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    @Override
    public String toString() {
        return "FileUploadResponse{" +
                "file_idx=" + file_idx +
                ", file_sn=" + file_sn +
                ", file_name='" + file_name + '\'' +
                ", org_filename='" + org_filename + '\'' +
                ", file_path='" + file_path + '\'' +
                ", regdate='" + regdate + '\'' +
                ", size=" + size +
                ", file_download_uri='" + file_download_uri + '\'' +
                ", use_yn='" + use_yn + '\'' +
                ", file_type='" + file_type + '\'' +
                '}';
    }
}
