package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credentials {

    private int credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private int userId;

    public Credentials() {}

    public Credentials(String url, String username, String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Credentials(int credentialId, String url, String username, String key, String password, int userId){
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public int getCredentialId() {
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

    public void setCredentialId(int credentialId) {
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

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
