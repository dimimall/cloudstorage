package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.ErrorMessage.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NotesService {

    private final NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper){
        this.notesMapper = notesMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating Notes Service bean");
    }

    public int createNotes(NoteForm noteForm, int userid){
        if (noteForm == null){
            throw new StorageException("Failed to store empty notes");
        }
        return notesMapper.insert(new Notes(null,noteForm.getNotetitle(),noteForm.getNotedescription(),userid));
    }

    public int updateNote(NoteForm noteForm,int userid)
    {
        return notesMapper.update(new Notes(Integer.parseInt(noteForm.getNoteid()),noteForm.getNotetitle(),noteForm.getNotedescription(),userid));
    }

    public Boolean existNote(int noteid,int userid){
        Notes note = notesMapper.getNotesById(noteid,userid);
        if (note != null)
            return true;
        else
            return false;
    }

    public int deleteNote(String notetitle){
        return notesMapper.delete(notetitle);
    }

    public List<Notes> getNotesList(int userid){
        return notesMapper.getNotes(userid);
    }

    public Notes getNoteById(int noteid,int userid){
        return notesMapper.getNotesById(noteid,userid);
    }
}
