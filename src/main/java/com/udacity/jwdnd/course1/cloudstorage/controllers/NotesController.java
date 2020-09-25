package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class NotesController {
    private final NotesService notesService;
    private final FileUploadService fileUploadService;
    private final UserService userService;
    private final CredentialService credentialService;
    private Logger logger = LoggerFactory.getLogger(NotesController.class);

    public NotesController(NotesService notesService, UserService userService, FileUploadService fileUploadService, CredentialService credentialService){
        this.notesService = notesService;
        this.fileUploadService = fileUploadService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("/notes")
    public String noteController(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model){
        User user = userService.getUser(principal.getName());

        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));

        return "home";
    }

    @PostMapping("/notes")
    public String postNotesForm(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model){

        User user = userService.getUser(principal.getName());

        if (noteForm.getNoteid().equalsIgnoreCase(""))
        {
            try {
                this.notesService.createNotes(noteForm,user.getUserId());
            }catch (Exception e){
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            }
        }
        else {
            try{
                this.notesService.updateNote(noteForm,user.getUserId());
            }catch (Exception e)
            {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            }
        }

        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));

        return "home";
    }

    @GetMapping("/notes/delete/{notetitle}")
    public String deleteNote(Principal principal, @PathVariable("notetitle") String notetitle, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model){
        this.notesService.deleteNote(notetitle);

        User user = userService.getUser(principal.getName());

        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));

        return "home";
    }
}
