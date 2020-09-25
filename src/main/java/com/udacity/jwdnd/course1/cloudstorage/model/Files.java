package com.udacity.jwdnd.course1.cloudstorage.model;

import javax.swing.*;

public class Files
{
    private int fileId;
    private String fileName;
    private String contentType;
    private long fileSize;
    private int userId;
    private byte[] fileData;
    private String base64str;

    public Files (){}

    public Files(int fileId, String fileName, String contentType, long fileSize, int userId, byte[] fileData, String base64str){
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
        this.base64str = base64str;
    }

    public int getFileId() {
        return fileId;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public int getUserId() {
        return userId;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public String getBase64str() {
        return base64str;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public void setBase64str(String base64str) {
        this.base64str = base64str;
    }
}
