package com.billlog.rest.salsapan.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SalsaUser implements UserDetails {
    private int user_idx;
    private String user_id;
    private String user_pwd;
    private String user_name;
    private int user_city;
    private String user_city_nm;
    private String user_gender;
    private String user_email;
    private int user_role;
    private String user_yn;
    private String user_role_nm;
    private String user_team;

    // 이메일 인증 관련 변수
    private String user_auth; // Y, N
    private String user_auth_key; // 발급된 인증키

    // ============= //
    private long msrl;
    //카카오 연동을 위해서 회원의 서비스 제공자를 알기위해 provider필드 추가.
    private String provider;

    public int getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(int user_idx) {
        this.user_idx = user_idx;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_city() {
        return user_city;
    }

    public void setUser_city(int user_city) {
        this.user_city = user_city;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_city_nm() {
        return user_city_nm;
    }

    public void setUser_city_nm(String user_city_nm) {
        this.user_city_nm = user_city_nm;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public String getUser_role_nm() {
        return user_role_nm;
    }

    public void setUser_role_nm(String user_role_nm) {
        this.user_role_nm = user_role_nm;
    }

    public String getUser_yn() {
        return user_yn;
    }

    public void setUser_yn(String user_yn) {
        this.user_yn = user_yn;
    }

    public String getUser_team() {
        return user_team;
    }

    public void setUser_team(String user_team) {
        this.user_team = user_team;
    }

    public long getMsrl() {
        return msrl;
    }

    public void setMsrl(long msrl) {
        this.msrl = msrl;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add((new SimpleGrantedAuthority(user_role_nm)));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user_pwd;
    }

    @Override
    public String getUsername() {
        return this.user_id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUser_auth() {
        return user_auth;
    }

    public void setUser_auth(String user_auth) {
        this.user_auth = user_auth;
    }

    public String getUser_auth_key() {
        return user_auth_key;
    }

    public void setUser_auth_key(String user_auth_key) {
        this.user_auth_key = user_auth_key;
    }

    @Override
    public String toString() {
        return "SalsaUser{" +
                "user_idx=" + user_idx +
                ", user_id='" + user_id + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_city=" + user_city +
                ", user_city_nm='" + user_city_nm + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_role=" + user_role +
                ", user_yn='" + user_yn + '\'' +
                ", user_role_nm='" + user_role_nm + '\'' +
                ", user_team='" + user_team + '\'' +
                ", user_auth='" + user_auth + '\'' +
                ", user_auth_key='" + user_auth_key + '\'' +
                ", msrl=" + msrl +
                ", provider='" + provider + '\'' +
                '}';
    }
}
