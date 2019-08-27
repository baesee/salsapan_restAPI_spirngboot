package com.billlog.rest.salsapan.model.common;

public class SalsaCity {
    private int city_idx;
    private String city_name;

    public int getCity_idx() {
        return city_idx;
    }

    public void setCity_idx(int city_idx) {
        this.city_idx = city_idx;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return "SalsaCity{" +
                "city_idx=" + city_idx +
                ", city_name='" + city_name + '\'' +
                '}';
    }
}
