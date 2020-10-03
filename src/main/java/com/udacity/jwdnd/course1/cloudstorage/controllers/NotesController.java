package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private final EncryptionService encryptionService;
    private Logger logger = LoggerFactory.getLogger(NotesController.class);

    public NotesController(NotesService notesService, UserService userService, FileUploadService fileUploadService, CredentialService credentialService, EncryptionService encryptionService){
        this.notesService = notesService;
        this.fileUploadService = fileUploadService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/notes")
    public String noteController(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model){
        User user = userService.getUser(principal.getName());

        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }

    @PostMapping("/notes")
    public String postNotesForm(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model){

        User user = userService.getUser(principal.getName());

        if (noteForm.getNoteid().equalsIgnoreCase(""))
        {
            try {
                this.notesService.createNotes(noteForm,user.getUserId());
                model.addAttribute("uploadSuccess",
                        "You successfully create '" + noteForm.getNotetitle() + "'");
            }catch (Exception e){
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("uploadError",
                        "You cannot create note ");
            }
        }
        else {
            try{
                this.notesService.updateNote(noteForm,user.getUserId());
                model.addAttribute("uploadSuccess",
                        "You successfully update '" + noteForm.getNotetitle() + "'");
            }catch (Exception e)
            {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("uploadError", "You cannot update note ");
            }
        }

        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }

    @GetMapping("/notes/delete/{notetitle}")
    public String deleteNote(Principal principal, @PathVariable("notetitle") String notetitle, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model){
        User user = userService.getUser(principal.getName());

        String uploadError = null;

        if (uploadError == null) {
            int rowsAdded = this.notesService.deleteNote(notetitle);
            if (rowsAdded < 0) {
                uploadError = "There was an error delete note. Please try again.";
            }
        }

        if (uploadError == null) {
            model.addAttribute("uploadSuccess",
                    "You successfully delete '" + notetitle + "'");
        } else {
            model.addAttribute("uploadError",
                    "You cannot delete note '" + uploadError + "'");
        }

        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }
}
