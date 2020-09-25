package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} and userid = #{userid}")
    Files getFilesByName(String filename,int userid);

    @Select("SELECT * FROM FILES where userid = #{userid}")
    List<Files> getFiles(int userid);

    @Delete("DELETE FROM FILES WHERE fileid=#{fileid}")
    int delete(int fileid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(Files files);
}
