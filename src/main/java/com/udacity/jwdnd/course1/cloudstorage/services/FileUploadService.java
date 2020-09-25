package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.ErrorMessage.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class FileUploadService {

    private final FilesMapper filesMapper;

    public FileUploadService(FilesMapper filesMapper){
        this.filesMapper = filesMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileUploadService bean");
    }

    public int addFile(MultipartFile file, int userid){
        if (file.isEmpty()){
            throw new StorageException("Failed to store empty file");
        }
        Files files1 = new Files();
        files1.setUserId(userid);
        files1.setContentType(file.getContentType());
        files1.setFileSize(file.getSize());
        files1.setFileName(file.getOriginalFilename());

        try {
            files1.setFileData(file.getBytes());
            files1.setBase64str(Base64.getEncoder().encodeToString(file.getBytes()));
            return filesMapper.insert(files1);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            new StorageException(e.getMessage(),e);
            return 0;
        }
    }

    public int deleteFile(int fileid){
        return filesMapper.delete(fileid);
    }

    public Files getFileByName(String fileName,int userid) {return filesMapper.getFilesByName(fileName,userid);}

    public List<Files> getAllFiles(int userid) {
        return filesMapper.getFiles(userid);
    }
}
