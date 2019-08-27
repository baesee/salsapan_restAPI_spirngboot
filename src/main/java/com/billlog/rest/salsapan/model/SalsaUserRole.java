package com.billlog.rest.salsapan.model;

public class SalsaUserRole {
    private int user_role_idx;
    private int user_id;
    private int role_id;

    public int getUser_role_idx() {
        return user_role_idx;
    }

    public void setUser_role_idx(int user_role_idx) {
        this.user_role_idx = user_role_idx;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
