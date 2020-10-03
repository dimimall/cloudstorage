package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credentials {

    private Integer credentialId;
    private String url;
    private String username;
    String key;
    String password;
    private Integer userId;

    public Credentials() {}

    public Credentials(String url, String username, String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Credentials(Integer credentialId, String url, String username, String key, String password, Integer userId){
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
