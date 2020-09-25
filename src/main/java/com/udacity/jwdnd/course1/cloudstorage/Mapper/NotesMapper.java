package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid} and userid=#{userid}")
    Notes getNotesById(int noteid,int userid);

    @Select("SELECT * FROM NOTES where userid=#{userid}")
    List<Notes> getNotes(int userid);

    @Delete("DELETE FROM NOTES WHERE notetitle = #{noteTitle}")
    int delete(String notetitle);

    @Update("UPDATE NOTES SET  notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteid}")
    int update(Notes notes);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes notes);
}
