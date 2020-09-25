package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {

    String credentialid;
    String credential_url;
    String credential_username;
    String credentials_password;
    String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getCredential_url() {
        return credential_url;
    }

    public String getCredential_username() {
        return credential_username;
    }

    public String getCredentialid() {
        return credentialid;
    }

    public String getCredentials_password() {
        return credentials_password;
    }

    public void setCredential_url(String credential_url) {
        this.credential_url = credential_url;
    }

    public void setCredential_username(String credential_username) {
        this.credential_username = credential_username;
    }

    public void setCredentialid(String credentialid) {
        this.credentialid = credentialid;
    }

    public void setCredentials_password(String credentials_password) {
        this.credentials_password = credentials_password;
    }
}
