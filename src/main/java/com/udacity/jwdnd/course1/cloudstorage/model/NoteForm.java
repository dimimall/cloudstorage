package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {

    private String noteId;
    private String noteTitle;
    private String noteDescription;

    public void setNoteid(String noteid) {
        this.noteId = noteid;
    }

    public String getNotedescription() {
        return noteDescription;
    }

    public void setNotetitle(String notetitle) {
        this.noteTitle = notetitle;
    }

    public String getNoteid() {
        return noteId;
    }

    public String getNotetitle() {
        return noteTitle;
    }

    public void setNotedescription(String notedescription) {
        this.noteDescription = notedescription;
    }
}
