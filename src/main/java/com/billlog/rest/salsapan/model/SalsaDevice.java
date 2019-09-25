package com.billlog.rest.salsapan.model;

public class SalsaDevice {

    private String user_id;
    private String device_token;
    private String device_name;
    private String device_brand;
    private String device_product;
    private String sdk_version;
    private String os_version;
    private String regdate;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public String getDevice_product() {
        return device_product;
    }

    public void setDevice_product(String device_product) {
        this.device_product = device_product;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "SalsaDevice{" +
                "user_id='" + user_id + '\'' +
                ", device_token='" + device_token + '\'' +
                ", device_name='" + device_name + '\'' +
                ", device_brand='" + device_brand + '\'' +
                ", device_product='" + device_product + '\'' +
                ", sdk_version='" + sdk_version + '\'' +
                ", os_version='" + os_version + '\'' +
                ", regdate='" + regdate + '\'' +
                '}';
    }
}
