package com.billlog.rest.salsapan.model;

import java.util.Properties;

public class KakaoProfile {
    private Long id;
    private Properties properties;

    private static class Properties{
        private String nickname;
        private String thumbnail_image;
        private String profile_image;
        private String email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
