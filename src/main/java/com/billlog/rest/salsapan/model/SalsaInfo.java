package com.billlog.rest.salsapan.model;

import com.billlog.rest.salsapan.model.file.FileUploadResponse;

public class SalsaInfo{

    private int info_idx;
    private String locale;
    private String city;
    private String title;
    private String sub_title;
    private String content;
    private int money;
    private String people;
    private String start_date;
    private String end_date;
    private String location;
    private String phone;
    private String teacher;
    private int hit_count;
    private int recommend_count;
    private String writer;
    private String type;
    private String use_yn;
    private String start_time;
    private String end_time;
    private String level;
    private String regdate;
    private String dj;
    private String open_class;
    private String detail_date; // 주 1회 월요일. 이런식의 세부 시작일을 작성하는 필드.
    private int writer_user_idx;

    //file -> List 로 받아야한다.
    private int file_idx;
    private String file_download_uri;

    public int getInfo_idx() {
        return info_idx;
    }

    public void setInfo_idx(int info_idx) {
        this.info_idx = info_idx;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getFile_idx() {
        return file_idx;
    }

    public void setFile_idx(int file_idx) {
        this.file_idx = file_idx;
    }

    public String getFile_download_uri() {
        return file_download_uri;
    }

    public void setFile_download_uri(String file_download_uri) {
        this.file_download_uri = file_download_uri;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getOpen_class() {
        return open_class;
    }

    public void setOpen_class(String open_class) {
        this.open_class = open_class;
    }

    public String getDetail_date() {
        return detail_date;
    }

    public void setDetail_date(String detail_date) {
        this.detail_date = detail_date;
    }

    public int getWriter_user_idx() {
        return writer_user_idx;
    }

    public void setWriter_user_idx(int writer_user_idx) {
        this.writer_user_idx = writer_user_idx;
    }
}
