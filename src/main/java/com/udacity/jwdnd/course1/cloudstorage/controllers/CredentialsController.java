package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class CredentialsController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final NotesService notesService;
    private final FileUploadService fileUploadService;
    private final EncryptionService encryptionService;
    private Logger logger = LoggerFactory.getLogger(CredentialsController.class);

    public CredentialsController(CredentialService credentialService, UserService userService, NotesService notesService, FileUploadService fileUploadService, EncryptionService encryptionService)
    {
        this.credentialService = credentialService;
        this.userService = userService;
        this.notesService = notesService;
        this.fileUploadService = fileUploadService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/credentials")
    public String credentialController(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model)
    {
        User user = userService.getUser(principal.getName());

        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }

    @PostMapping("/credentials")
    public String postCredentialsForm(Principal principal, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model)
    {
        User user = userService.getUser(principal.getName());

        if (!credentialForm.getCredentialid().equalsIgnoreCase("")){
            try {
                this.credentialService.updateCredentials(credentialForm,user.getUserId());
                model.addAttribute("uploadSuccess",
                        "You successfully update credential");
            }catch (Exception e){
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("uploadError",
                        "You cannot update credential");
            }
        }else {
            try{
                this.credentialService.createCredentials(credentialForm,user.getUserId());
                model.addAttribute("uploadSuccess",
                        "You successfully insert credential");
            }catch (Exception e){
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("uploadError",
                        "You cannot insert credential");
            }
        }

        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }

    @GetMapping("/credentials/delete/{credentialId}")
    public String deleteCredentials(Principal principal,@PathVariable("credentialId") int credentialId, @ModelAttribute("Files") Files files, @ModelAttribute("NoteForm") NoteForm noteForm, @ModelAttribute("CredentialsForm") CredentialForm credentialForm, Model model)
    {
        User user = userService.getUser(principal.getName());

        String uploadError = null;

        if (uploadError == null) {
            int rowsAdded = this.credentialService.deleteCredentials(credentialId);
            if (rowsAdded < 0) {
                uploadError = "There was an error delete credentials. Please try again.";
            }
        }

        if (uploadError == null) {
            model.addAttribute("uploadSuccess",
                    "You successfully delete credential ");
        } else {
            model.addAttribute("uploadError",
                    "You cannot delete credential '" + uploadError + "'");
        }

        model.addAttribute("credentials",this.credentialService.getCredentails(user.getUserId()));
        model.addAttribute("notesupload",this.notesService.getNotesList(user.getUserId()));
        model.addAttribute("filesUpload", this.fileUploadService.getAllFiles(user.getUserId()));
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }
}
